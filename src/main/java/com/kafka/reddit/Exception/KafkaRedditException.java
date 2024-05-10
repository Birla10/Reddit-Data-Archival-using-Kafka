package com.kafka.reddit.Exception;

public class KafkaRedditException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public KafkaRedditException(String message) {
		super(message);
	}

	public KafkaRedditException(String message, Throwable cause) {
		super(message, cause);
	}
}
