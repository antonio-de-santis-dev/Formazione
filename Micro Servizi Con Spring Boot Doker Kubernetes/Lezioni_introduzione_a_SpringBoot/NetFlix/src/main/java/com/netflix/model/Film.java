package com.netflix.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Film {

	@Id
	private int id;

	@Column(name = "TITOLO")
	private String titolo;

	@Column(name="ANNO")
	private int anno;
	
	@Column(name="REGISTA")
	private String regista;
	
	@Column(name = "INCASSO")
	private String incasso;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public int getAnno() {
		return anno;
	}

	public void setAnno(int anno) {
		this.anno = anno;
	}

	public String getRegista() {
		return regista;
	}

	public void setRegista(String regista) {
		this.regista = regista;
	}

	public String getIncasso() {
		return incasso;
	}

	public void setIncasso(String incasso) {
		this.incasso = incasso;
	}

	
}
