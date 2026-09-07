package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("HelloWorldSpringCloudEurekaClient-2")
public interface HelloWorldCtrClient {

	@RequestMapping(value = "/api/ciao", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> helloWorld();  

}
