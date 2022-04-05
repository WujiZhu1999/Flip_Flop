# Flip_Flop

## Instance management
Use zookeeper to manage the four instance I have (3 [AWS](https://aws.amazon.com/cn/free/?trk=63ea80e2-b442-4217-b2c0-97eadef037cf&sc_channel=ps&sc_campaign=acquisition&sc_medium=ACQ-P|PS-GO|Brand|Desktop|SU|Core-Main|Core|AU|EN|Text&s_kwcid=AL!4422!3!454645972981!e!!g!!aws&ef_id=EAIaIQobChMIjrKjjoDX9gIVhXwrCh1T9glYEAAYASAAEgIZLfD_BwE:G:s&s_kwcid=AL!4422!3!454645972981!e!!g!!aws&all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all) and 1 [AliCloud](https://cn.aliyun.com/) )

Note that jdk17 have some issue with maven, should use java 11 instead
sudo apt install openjdk-11-jdk
sudo update-alternatives --config java
export JAVA_HOME=/usr/lib/jvm/java-1.11.0-openjdk-amd64

## Communication
Between Servers use apache kafka to transfer data of clients, use https to communicate with frontend and hold a web socket for continuous update of flip flop game

## Storage
The images are stored on [to be completed soom]

## Web Socket
Followed this tutorial to integrate web socket into the game.
https://spring.io/guides/gs/messaging-stomp-websocket/

## S3 storage
https://aws.amazon.com/getting-started/hands-on/backup-files-to-amazon-s3/


## Useful urls
https://mkyong.com/spring-mvc/spring-mvc-refactoring-a-jquery-ajax-post-example/
https://www.youtube.com/watch?v=vtPkZShrvXQ
https://www.youtube.com/watch?v=96ompySpPHE
https://www.baeldung.com/spring-redirect-and-forward
https://www.baeldung.com/spring-mvc-model-model-map-model-view
https://stackoverflow.com/questions/18486660/what-are-the-differences-between-model-modelmap-and-modelandview
https://www.baeldung.com/spring-thymeleaf-request-parameters
https://spring.io/guides/gs/messaging-stomp-websocket/

## Security
This video provides detailed explaination about how to let spring run over https instead of http, however, self signed ssl certificate will be marked as dangerous by Chrome, so I will just keep the web running over http.https://www.youtube.com/watch?v=rm9OKTSm-4A

## Port Redirection
https://stackoverflow.com/questions/33703965/how-can-i-run-a-spring-boot-application-on-port-80
This website shows how to redirect request from 80 to 8080. Remember to run with sudo.
