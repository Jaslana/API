package com.Api1.API1.Kafka.Start;

import java.io.IOException;

public class ServersStarts {

    public static void startServers (){
        ProcessBuilder Zookeeper = new ProcessBuilder("C:\\Kafka\\batch\\Start_Zookeeper.bat");
        ProcessBuilder Server1 = new ProcessBuilder("C:\\Kafka\\batch\\Start_Kafka_Server1.bat");
        ProcessBuilder Server2 = new ProcessBuilder("C:\\Kafka\\batch\\Start_Kafka_Server2.bat");
        ProcessBuilder ConsumerTopic = new ProcessBuilder("C:\\Kafka\\batch\\Consumer_product_topic.bat");

        try {
            Zookeeper.start();
            Server1.start();
            Server2.start();
            ConsumerTopic.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
