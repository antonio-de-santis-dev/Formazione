package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("esempio")
	public String esempio() {
		return "hello2";
	}
	
//	spring version 2.7.14-snapshot
//	cloud version 2021.0.8
//	docker 24.0.2
//	java 17
//	yaml version 3.7
//
//	dipendenze identiche a questo esempio
}
