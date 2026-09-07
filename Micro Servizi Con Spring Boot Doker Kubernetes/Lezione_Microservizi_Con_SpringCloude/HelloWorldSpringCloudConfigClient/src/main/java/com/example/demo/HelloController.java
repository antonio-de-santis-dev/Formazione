package com.example.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class HelloController {
	
	@Value("${welcome.message}")
	    private String welcomeMessage;
	// ------------------- Ricerca Per Codice ------------------------------------
	//api/ciao
		@RequestMapping(value = "/ciao", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<String> helloWorld()  
				
		{
		


			return new ResponseEntity<String>(welcomeMessage, HttpStatus.OK);
		}



}
