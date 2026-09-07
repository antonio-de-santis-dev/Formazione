package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class CircuitBreakerController {
	private Logger log = LoggerFactory.getLogger(CircuitBreakerController.class);

	
	//for /l %g in () do @(curl http://localhost:8080/esempio & timeout /t 5)
	@GetMapping("esempio")
	@CircuitBreaker(name = "default", fallbackMethod = "metodoFallBack")	
	public String esempio() {
		log.info("dentro servizio");
		ResponseEntity<String> res = new RestTemplate().getForEntity("http://localhost:8080/api/ciao", String.class);
		return res.getBody();
	}
	
	
	public String metodoFallBack(Exception ex) {
		log.info("dentro metodoFallBack");
		return "tutti i tentativi sono falliti";

	}
}
