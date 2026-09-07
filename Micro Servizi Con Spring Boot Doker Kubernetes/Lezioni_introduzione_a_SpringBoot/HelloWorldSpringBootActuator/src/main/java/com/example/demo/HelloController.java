package com.example.demo;


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
	
	// ------------------- Ricerca Per Codice ------------------------------------
		@RequestMapping(value = "/ciao", method = RequestMethod.GET, produces = "application/json")
		public ResponseEntity<String> helloWorld()  
				
		{
			System.out.println("****** dentro *******");

	
			return new ResponseEntity<String>("ciao mamma", HttpStatus.OK);
		}


}
