package com.magazzino.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magazzino.model.Articolo;
import com.magazzino.repository.ArticoloRepository;

@Service
public class ArticoloService {

	@Autowired
	private ArticoloRepository articoloR;
	
	public List<Articolo> getArticles(){
		
		return articoloR.findAll();
	}
}
