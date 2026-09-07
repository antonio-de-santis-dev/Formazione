package com.magazzino.repository;

import com.magazzino.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticoloRepository  extends JpaRepository<Articolo, Integer> {
	
}


