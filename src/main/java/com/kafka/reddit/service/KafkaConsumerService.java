package com.kafka.reddit.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.kafka.reddit.pojo.RedditData;
import com.kafka.reddit.utils.CSVFileWriter;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumerService {

	@Autowired
	Consumer<String, String> consumer;

	@Autowired
	private CSVFileWriter csvFileWriter;

	List<RedditData> reddits = new ArrayList<>();

	/**
	 * Consumer fetches messages from Kafka server for every 1000milli seconds
	 * This method is scheduled to run every 1000Milli seconds
	 */
	@Scheduled(fixedDelay = 1000)
	public void pollKafka() {
		reddits.clear();
		
		//Fetch data from kafka by polling every 1000ms 
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
		
		records.forEach(record -> {

			JSONObject messages = new JSONObject(record.value());
			JSONObject data = messages.getJSONObject("data");
			JSONArray children = data.getJSONArray("children");

			for (Object child : children) {
				processRecord(child, record);
			}

		});
		
		//Commit the offset after successfully fetching the messages from kafka
		consumer.commitSync();
		
		//Write processed data to CSV File
		if (!reddits.isEmpty()) {
			
			csvFileWriter.writeRedditDataToCSV(reddits);
		}
	}

	/**
	 * This method processes the Messages fetched from kafka
	 * The messages are processed to be useful for further analysis
	 * @param child
	 * @param record
	 */
	private void processRecord(Object child, ConsumerRecord<String, String> record) {
		try {
			// Convert the object to JSONObject
			JSONObject jsonObject = (JSONObject) child;

			// Access elements within the JSON object
			JSONObject redditData = jsonObject.getJSONObject("data");

			RedditData reddit = new RedditData();
			reddit.setTitle(redditData.get("title").toString());
			reddit.setAuthor(redditData.get("author").toString());
			reddit.setCreated_utc((BigDecimal) redditData.get("created_utc"));
			reddit.setDowns((int) redditData.get("downs"));
			reddit.setIs_video((boolean) redditData.get("is_video"));
			reddit.setNum_comments((int) redditData.get("num_comments"));
			reddit.setSubreddit(redditData.get("subreddit").toString());
			reddit.setThumbnail(redditData.get("thumbnail").toString());
			reddit.setUps((int) redditData.get("ups"));
			reddit.setUrl(redditData.get("url").toString().toString());
			reddits.add(reddit);

		} catch (Exception e) {
			log.error("Error processing message: " + e.getMessage());

			TopicPartition partition = new TopicPartition(record.topic(), record.partition());
			long nextOffset = record.offset() + 1; // Skip the problematic message
			consumer.seek(partition, nextOffset);
		}
	}
	
	@PreDestroy
	public void stop() {
		if (consumer != null) {
			consumer.wakeup();
		}
	}

}
