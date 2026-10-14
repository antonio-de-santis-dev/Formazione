package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CountryService {

	@Autowired
	private CountryRepository repository;

	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void create(Country c) {
		repository.save(c);

	}
	
//	//Isolation
//	@Transactional(isolation = Isolation.DEFAULT)
	//utilizza il livello di isolamento usato dal
	//db sottostante
//	@Transactional(isolation = Isolation.SERIALIZABLE)
	//agiamo come se fossimo gli unici ad agire su quei dati
	//livello massimo di isolamento
	//se però quei dati, mentre ci stiamo lavorando, sono
	//stati modificati da un'altra transazione
	//andiamo in rollback (la nostra transazione fallisce)
	//ci possono essere quindi molti rollback
	//problemi performance
//	@Transactional(isolation = Isolation.REPEATABLE_READ)
	//i dati selezionati vengono bloccati. nessuno li può 
	//modificare
	//problemi: performance, più veloce di serializable
	//ma in ogni caso blocco tante righe
	//inoltro posso avere le letture fantasma,
	//ossia se rieseguo la stessa query posso avere 
	//risultati diversi, nel senso ci possono più righe
	// ma sicuramente le righe che ho bloccato non 
	//cambiano
//	@Transactional(isolation = Isolation.READ_COMMITTED)
	//non viene effettuato il lock in lettura delle righe
	//quindi più performanti rispetto al livello precedente
	//ma abbiamo un ulteriore problema:letture non ripetibili
	//ossia se, durante la transazione, rieseguo la stessa query
	//potrei avere dati diversi, se nel frattempo un'altra
	//transazione li ha modificati
//	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	//il più performante ma oltre ai problemi degli altri livelli
	//abbiamo il problema dei dati sporchi
	//ossia possiamo leggere dei dati che in quel momento
	//sono modificati da un'altra transazione che ancora
	//non ha fatto commit, quindi potremmo avere
	//dei dati inconsistenti


//	
	
	//Propagation
//  @Transactional(propagation = Propagation.REQUIRED)
	// se chi ci chiama ha startato una transazione
	//utilizza quella
	//altrimenti ne crea una nuova	
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
	//crea sempre una nuova transazione 
	// sospendendo l eventuale transazione chiamante
//	@Transactional(propagation = Propagation.SUPPORTS)
	//se esiste una transazione chiamante, usa quella
	//altrimenti il metodo viene eseguito senza transazioni
//	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	// il metodo è eseguito sempre senza transazione
	// se esiste transazione chiamante, la sospende
//	@Transactional(propagation = Propagation.MANDATORY)
	//il metodo deve essere sempre eseguito dentro 
	//una transazione
	// se non esiste un transazione chiamante
	//lancia una eccezione
//	@Transactional(propagation = Propagation.NEVER)
	//metodo eseguito sempre senza transazione
	//se esiste una transazione chiamante, viene lanciata
	//una eccezione
//	@Transactional(propagation = Propagation.NESTED)
	//se esiste una transazione chiamante, viene creato una specie di
	// punto di ripristino e se il nostro codice va in eccezione
	// il sistema verrà rollbackato su questo punto di ripristino
	// se non esiste transazione ne crea una lui.
	
	

//	//mix di usi
//	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)


	

}
