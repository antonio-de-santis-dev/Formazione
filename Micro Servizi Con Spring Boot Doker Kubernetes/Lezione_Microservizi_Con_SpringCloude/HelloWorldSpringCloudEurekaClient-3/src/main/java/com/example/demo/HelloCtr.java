package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/imp")
public class HelloCtr {

	 @Autowired
	 private HelloWorldCtrClient helloWorldCtrClient;

	 
	// imp/new  --> api/ciao --> hello
	@RequestMapping(value = "/new", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> helloWorld()  
			
	{
		String res = helloWorldCtrClient.helloWorld().getBody();
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
}
