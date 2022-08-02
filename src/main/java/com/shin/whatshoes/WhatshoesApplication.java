package com.shin.whatshoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WhatshoesApplication {

//	public static final String APPLICATION_LOCATIONS = "spring.config.location="
//			+ "classpath:application.properties,"
//			+ "classpath:aws.yml";

    public static void main(String[] args) {
//	        	new SpringApplicationBuilder(WhatShoesApplication.class)
//				.properties(APPLICATION_LOCATIONS)
//				.run(args);
        SpringApplication.run(WhatshoesApplication.class, args);
    }

}
