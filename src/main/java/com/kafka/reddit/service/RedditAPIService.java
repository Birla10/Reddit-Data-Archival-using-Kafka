package com.kafka.reddit.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kafka.reddit.Exception.KafkaRedditException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RedditAPIService {

	@Autowired
	private KafkaProducerService service;

	@Value("${reddit.api.authcode}")
	private String authcode;

	/**
	 * This method makes a Search API call to Reddit and Fetches Requested Data 
	 * @param reqStr
	 */
	public void callRedditAPI(String reqStr) {
		HttpResponse<String> apiResponse;
		try {
			
			//Create a httpclient to make Reddit API Call
			HttpClient client = HttpClient.newHttpClient();
			String redditApiUrl = "https://oauth.reddit.com/search.json?q=" + reqStr;
			HttpRequest apiRequest = HttpRequest.newBuilder().uri(URI.create(redditApiUrl))
					.header("Authorization", "bearer " + authcode).header("User-Agent", "YourApp/0.1").build();

			log.info("calling Reddit API with the following payload - {}", apiRequest.toString());
			
			//Send a request to Reddit API
			apiResponse = client.send(apiRequest, HttpResponse.BodyHandlers.ofString());

			log.info("Reddit API Response: {}", apiResponse.statusCode());
			
			//Send Data to Kafka server only when the API Call is successful and contains required data 
			if (apiResponse.statusCode() == 200 && apiResponse.body().contains("subreddit") ) {
				String responseContent = apiResponse.body();
				service.produceMessageToKafka(responseContent);

			}else {
				throw new KafkaRedditException("Reddit API Call falied with status "+apiResponse.statusCode()+ " and " +apiResponse.body());
			}

		} catch (IOException | InterruptedException e) {
			throw new KafkaRedditException("Unable to Process Request ", e);
		}

	}

}
