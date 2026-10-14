package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
public class ServiceEsempio {

	@Autowired
	private CountryService cs;
	
	@Transactional
	public void hello(Country c) {
		cs.create(c);
	}
}
