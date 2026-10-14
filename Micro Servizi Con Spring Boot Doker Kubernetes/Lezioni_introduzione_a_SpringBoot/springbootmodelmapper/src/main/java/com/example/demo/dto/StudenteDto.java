package com.example.demo.dto;

public class StudenteDto {

	private String nome;
	private String cognome;

	public StudenteDto(String nome, String cognome) {
		super();
		this.nome = nome;
		this.cognome = cognome;
	}
	
	public StudenteDto() {
		super();
	}
	
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

}
