package com.adviters.app.Bootcamp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BootcampApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootcampApplication.class, args);
	}
}