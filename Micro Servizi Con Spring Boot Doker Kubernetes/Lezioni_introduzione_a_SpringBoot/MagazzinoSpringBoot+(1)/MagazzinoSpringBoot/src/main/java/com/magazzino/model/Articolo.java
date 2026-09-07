package com.magazzino.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter 
public class Articolo {

	@Id
	private int codice;
	private String descrizione;
	private int prezzo;
//	public int getCodice() {
//		return codice;
//	}
//	public void setCodice(int codice) {
//		this.codice = codice;
//	}
//	public String getDescrizione() {
//		return descrizione;
//	}
//	public void setDescrizione(String descrizione) {
//		this.descrizione = descrizione;
//	}
//	public int getPrezzo() {
//		return prezzo;
//	}
//	public void setPrezzo(int prezzo) {
//		this.prezzo = prezzo;
//	}
	
	
	
}
