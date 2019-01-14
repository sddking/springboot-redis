package com.gree.dbup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.gree.dbup")
public class DbupsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbupsApplication.class, args);
	}

}

