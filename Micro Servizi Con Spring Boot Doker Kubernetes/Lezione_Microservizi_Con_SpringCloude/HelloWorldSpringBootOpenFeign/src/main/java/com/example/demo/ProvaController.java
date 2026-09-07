package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provaws")
public class ProvaController {

	@Autowired
	private TestClient testClient;

	@RequestMapping(value = "/helloWorld", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> helloWorld() {

		return testClient.helloWorld();
	}

	@RequestMapping(value = "/testArticolo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<String> testArticolo()

	{
		Articoli a = testClient.getArticolo().getBody();
		return new ResponseEntity<String>(a.getDescrizione(), HttpStatus.OK);
	}

	@RequestMapping(value = "/testLista", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Articoli>> elencoArticoli()

	{
		System.out.println("****** dentro *******" + testClient.helloWorld());
		List<Articoli> a = testClient.elencoArticoli().getBody();

		return new ResponseEntity<List<Articoli>>(a, HttpStatus.OK);
	}

}
