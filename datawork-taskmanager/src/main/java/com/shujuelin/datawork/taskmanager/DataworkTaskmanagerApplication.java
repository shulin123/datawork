package com.shujuelin.datawork.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages="com.shujuelin.datawork.taskmanager.feign")
@SpringBootApplication
public class DataworkTaskmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataworkTaskmanagerApplication.class, args);
    }

}
