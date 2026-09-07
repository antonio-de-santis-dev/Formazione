package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {
	private Logger log = LoggerFactory.getLogger(CircuitBreakerController.class);

	
	//for /l %g in () do @(curl http://localhost:8080/esempio & timeout /t 5)
	@GetMapping("esempio")
	//@RateLimiter(name="default") //10 sec per 10000 chiamate
	@Bulkhead(name="default")
	public String esempio() {
		log.info("dentro servizio");

		return "OKKKKKK";
	}
	

}
