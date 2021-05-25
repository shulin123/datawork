package com.shujuelin.datawork.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DataworkGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataworkGatewayApplication.class, args);
    }

}
