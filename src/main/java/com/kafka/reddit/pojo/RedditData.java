package com.kafka.reddit.pojo;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class RedditData {
	@Override
	public String toString() {
		return "RedditData [title=" + title + ", author=" + author + ", url=" + url + ", created_utc=" + created_utc
				+ ", subreddit=" + subreddit + ", num_comments=" + num_comments + ", ups=" + ups + ", downs=" + downs
				+ ", thumbnail=" + thumbnail + ", is_video=" + is_video + "]";
	}

	private String title;
	private String author;
	private String url;
	private String created_utc;
	private String subreddit;
	private int num_comments;
	private int ups;
	private int downs;
	private String thumbnail;
	private boolean is_video;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreated_utc() {
		return created_utc;
	}

	public void setCreated_utc(BigDecimal created_utc) {

		Instant instant = Instant.ofEpochSecond(created_utc.longValue());

		// Convert the Instant to LocalDateTime in the system default timezone
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

		// Formatter to convert LocalDateTime to String in MM-dd-yyyy format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		// Format the LocalDateTime
		String formattedDate = dateTime.format(formatter);
		this.created_utc = formattedDate;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public int getNum_comments() {
		return num_comments;
	}

	public void setNum_comments(int num_comments) {
		this.num_comments = num_comments;
	}

	public int getUps() {
		return ups;
	}

	public void setUps(int ups) {
		this.ups = ups;
	}

	public int getDowns() {
		return downs;
	}

	public void setDowns(int downs) {
		this.downs = downs;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public boolean isIs_video() {
		return is_video;
	}

	public void setIs_video(boolean is_video) {
		this.is_video = is_video;
	}

}
