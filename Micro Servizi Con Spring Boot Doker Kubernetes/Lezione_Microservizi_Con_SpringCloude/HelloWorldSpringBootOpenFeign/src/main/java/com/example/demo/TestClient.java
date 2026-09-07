package com.example.demo;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name ="HelloWorldSpringBoot", url="localhost:8080")
public interface TestClient 
{
	
	@RequestMapping(value = "/api/ciao", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> helloWorld();
	//public ResponseEntity<String> helloWorld(@RequestHeader("Authorization") String AuthHeader);
	
	
	@RequestMapping(value = "/api/getArticolo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Articoli> getArticolo();
	
	
	@GetMapping(value="/api/elencoArticoli")
	public ResponseEntity<List<Articoli>> elencoArticoli();
	
}
