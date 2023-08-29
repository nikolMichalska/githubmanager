package com.michalska;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan({"com.michalska"})
public class GitManagerApp {

    public static void main(String[] args) {
        SpringApplication.run(GitManagerApp.class, args);
    }

}