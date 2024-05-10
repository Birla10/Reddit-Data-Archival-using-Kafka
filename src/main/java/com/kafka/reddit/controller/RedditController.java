package com.kafka.reddit.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.reddit.service.RedditAPIService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
public class RedditController {

	@Autowired
	private RedditAPIService redditAPIService; 
	
	/**
	 * This API call fetches and Processes the Reddit Data
	 * @param reqStr
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@PostMapping("/fetchredditdata")
	public ResponseEntity<String> fetchRedditData(@RequestBody String reqStr) throws InterruptedException, IOException {
		
		log.info("Reddit Data Requested for {}", reqStr);
		redditAPIService.callRedditAPI(reqStr);
		
		log.info("Reddit data fetched and Processed successfully");
		return new ResponseEntity<>("Data fetched and Message sent successfully!", HttpStatus.OK);
	}

	
}