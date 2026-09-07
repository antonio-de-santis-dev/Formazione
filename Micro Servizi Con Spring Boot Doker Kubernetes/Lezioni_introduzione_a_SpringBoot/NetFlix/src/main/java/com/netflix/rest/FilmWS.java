package com.netflix.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.dao.FilmDao;
import com.netflix.dao.FilmDaoImpl;
import com.netflix.model.Film;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/FilmWS")
public class FilmWS {

	
	@PostMapping
	public ResponseEntity<String> save(@RequestBody Film f){
		
		FilmDao fD = new FilmDaoImpl();
		
		fD.save(f);
		
		
		return new ResponseEntity<String>("Inserimento effettuato", HttpStatus.OK);
	}
	
	
	@ApiOperation(
		      value = "Ricerca film per regista", 
		      notes = "Permette la ricerca dei film fatti da un regista",
		      response = List.class, 
		      produces = "application/json")
	@GetMapping
	public ResponseEntity<List<Film>> search(@RequestParam String regista){
		FilmDao fD = new FilmDaoImpl();
		
		List<Film> res = fD.search(regista);
		
		return new ResponseEntity<List<Film>>(res, HttpStatus.OK);
		
		
	}
}
