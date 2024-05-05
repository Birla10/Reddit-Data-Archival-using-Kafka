# Reddit-Data-Archival-using-Kafka
This is a Java Springboot Application which Utilizes Kafka and Reddit API to prepare refined Reddit Data which can be used for further analysis.

Prerequisites:
  1. Reddit API Account
  2. Reddit API Credentials
  3. java 17
  4. Kafka Server
  5. Spring tool Suite or any other IDE
  6. Postman for Accessing the API

--> You can create and get Reddit API Account Credentials using https://old.reddit.com/prefs/apps/
--> Once you got Credentials, you need to get the Access Token to make the authorized requests to RedditAPI
--> You can download Kafka from official website (https://kafka.apache.org/downloads)

Steps to run the Project:
1. Place the Access Token acquired above in the Application.properties file.
2. Start Kafka (Zookeeper and Kafka Server)

    If you installed kafka using WSL:
        1. Start Kafka zookeeper (bin/zookeeper-server-start.sh config/zookeeper.properties)
        2. Start kafka Server (bin/kafka-server-start.sh config/server.properties) or if you have Kafka in Local Machine then use

    If you installed Kafka in local machine
        1. C:\kafka_2.13-3.6.0\bin\zookeeper-server-start.bat C:\kafka_2.13-3.6.0\config\zookeeper.properties
        2. C:\kafka_2.13-3.6.0\bin\kafka-server-start.bat C:\kafka_2.13-3.6.0\config\server.properties

3. Open Postman, then make a POST call to http://localhost/8081/fetchredditdata with request body title:title_you_want.
4. Once the API Call is successful, you can check src/main/resources/RedditData folder for the .csv files with your data.
5. If the API call is unsuccessful, you can check the console for proper logs or also ./logs folder for the complete logs.

You can customize this project to Get different data from reddit such as posts based on title, hashtags etc.
For titie posts use -> title:title_you_want while making the POST call
For hashtag based data use -> %23:hashtag_you_want while making the POST call

