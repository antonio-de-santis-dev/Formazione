package com.netflix.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.netflix.JpaUtil;
import com.netflix.model.Film;

public class FilmDaoImpl implements FilmDao {

	public void save(Film f) {
		EntityManager em = JpaUtil.getFactory().createEntityManager();

		try {
			EntityTransaction t = em.getTransaction();
			t.begin();
			
			
			String hash = BCrypt.hashpw(f.getIncasso(), BCrypt.gensalt());
			f.setIncasso(hash);
			
			em.persist(f);
			t.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	/**
	 *  Ricerca film tramite regista
	 * 
	 *  @author Antonio Pagano
	 *  @param regista regista da ricercare 
	 *  @return Lista film con quel regista
	 */
	public List<Film> search(String regista) {
		EntityManager em = JpaUtil.getFactory().createEntityManager();
	
		Query q = em.createQuery(" from Film where regista = :r");
		
		q.setParameter("r", regista);
		
		List<Film> result  = q.getResultList();
		
		return result;
	}

}
