package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldCtr {
	@RequestMapping(value = "/ciao", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> helloWorld()  
			
	{
		return new ResponseEntity<String>("helloooo", HttpStatus.OK);
	}

}
