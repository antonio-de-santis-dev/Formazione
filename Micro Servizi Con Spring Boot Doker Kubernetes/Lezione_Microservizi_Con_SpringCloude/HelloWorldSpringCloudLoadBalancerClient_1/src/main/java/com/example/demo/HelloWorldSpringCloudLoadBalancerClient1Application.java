package com.example.demo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class HelloWorldSpringCloudLoadBalancerClient1Application {

	public static void main(String[] args) {

		ConfigurableApplicationContext ctx = new SpringApplicationBuilder(HelloWorldSpringCloudLoadBalancerClient1Application.class)
		          .web(WebApplicationType.NONE)
		          .run(args);

		        WebClient loadBalancedClient = ctx.getBean(WebClient.Builder.class).build();

		        for(int i = 1; i <= 10; i++) {
		            String response =
		              loadBalancedClient.get().uri("http://localhost:8081/api/ciao")
		                .retrieve().toEntity(String.class)
		                .block().getBody();
		            System.out.println(response);
		        }
	
	
	}

}
