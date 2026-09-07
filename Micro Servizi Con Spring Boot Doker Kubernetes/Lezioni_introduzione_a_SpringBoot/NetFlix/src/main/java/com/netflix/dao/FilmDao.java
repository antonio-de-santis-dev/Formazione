package com.netflix.dao;

import java.util.List;

import com.netflix.model.Film;

public interface FilmDao {
	
	public void save(Film f);
	public List<Film> search(String regista);

}
