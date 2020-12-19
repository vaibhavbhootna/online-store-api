package com.store.api.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.store.api")
public class OnlineStoreApplication {

    public static void main(String[] args) {
    	SpringApplication.run(OnlineStoreApplication.class, args);
    }
}