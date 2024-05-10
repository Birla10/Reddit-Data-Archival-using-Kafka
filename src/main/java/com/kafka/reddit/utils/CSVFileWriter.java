package com.kafka.reddit.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import com.kafka.reddit.pojo.RedditData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CSVFileWriter {

    private static final String DIRECTORY_PATH = "src/main/resources/RedditData/";

    public void writeRedditDataToCSV(List<RedditData> dataRecords) {
        ensureDirectoryExists(DIRECTORY_PATH);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = LocalDateTime.now().format(dtf);
        String fileName = DIRECTORY_PATH + "redditdata_" + timestamp + ".csv";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {
             
        	log.info("Writing {} records to CSV File", dataRecords.size());
            // Printing the headers manually
            csvPrinter.printRecord("Title", "Author", "URL", "Thumbnail", "Created_UTC (MM/dd/yyyy)", 
            		"Subreddit", "Number of Comments", "Ups", "Downs");

            for (RedditData data : dataRecords) {
                csvPrinter.printRecord(data.getTitle(), data.getAuthor(), data.getUrl(),
                		data.getThumbnail(), data.getCreated_utc(), data.getSubreddit(),
                		data.getNum_comments(), data.getUps(), data.getDowns());
            }
            log.info("Created csv file - {} with the Reddit Data", fileName );
            
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ensureDirectoryExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}
