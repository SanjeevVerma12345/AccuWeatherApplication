package com.entrego.weather.application.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages="com.entrego.*")
public class SpringBootApplicationRunner {

	public static void main(final String ...args){
		SpringApplication.run(SpringBootApplicationRunner.class,args);
	}
}
