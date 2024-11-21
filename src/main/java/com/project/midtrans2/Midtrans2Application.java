package com.project.midtrans2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.project.midtrans2")
public class Midtrans2Application {
	public static void main(String[] args) {
		SpringApplication.run(Midtrans2Application.class, args);
	}
}
