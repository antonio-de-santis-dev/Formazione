package com.example.demo;


import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class HelloController {
	
	   private static final Logger log = LoggerFactory.getLogger(HelloController.class);

	   
	//ATTENZIONE
	// aggiungere dependency
		//http://localhost:8080/api/prova
			@RequestMapping(value = "/prova", method = RequestMethod.GET, produces = "application/json")
			public String prova()
					
			{  

				log.info("invocato");
				return "OK";
			}
			
	

}
