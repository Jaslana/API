package com.Api1.API1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.Api1.API1.Kafka.Start.ServersStarts.startServers;

@SpringBootApplication
public class Api1Application {

    public static void main(String[] args) {
        SpringApplication.run(Api1Application.class, args);
        startServers();

    }

}
