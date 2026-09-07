package com.magazzino.ctr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magazzino.model.Articolo;
import com.magazzino.services.ArticoloService;

@RestController
@RequestMapping("/articolo")
public class ArticoloCtr {

	@Autowired
	private ArticoloService articoloS;
	
	@GetMapping
	public List<Articolo> getArticles(){
		return articoloS.getArticles();
	}
}
