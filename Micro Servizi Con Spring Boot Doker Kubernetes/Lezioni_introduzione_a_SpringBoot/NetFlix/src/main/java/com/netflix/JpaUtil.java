package com.netflix;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static final EntityManagerFactory emF;
	
	static {
		emF = Persistence.createEntityManagerFactory("NetFlix");
	}
	
	
	public static EntityManagerFactory getFactory() {
		return emF;
	}
	
}
