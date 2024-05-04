package com.kafka.reddit.service;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducerService {

	@Autowired
	Producer<String, String> producer;

	@Value("${spring.kafka.producertopic}")
	private String producertopic;

	public void produceMessageToKafka(String message) {

		ProducerRecord<String, String> record = new ProducerRecord<>(producertopic, message);
		
		//send data to kafka server
		log.info("Sending data to Kafka topic - {}",producertopic);
		producer.send(record);
		
		log.info("Successfully published data to kafka topic {}", producertopic);
	}
}
