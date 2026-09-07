package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class HelloWorldSpringCloudConfig2Application {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldSpringCloudConfig2Application.class, args);
	}

}
