package com.shujuelin.datawork.overwirte;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.shujuelin.datawork.overwirte.dao")
@SpringBootApplication
public class DataworkOverwirteApplication  {

    public static void main(String[] args) {
        SpringApplication.run(DataworkOverwirteApplication.class, args);
    }

}
