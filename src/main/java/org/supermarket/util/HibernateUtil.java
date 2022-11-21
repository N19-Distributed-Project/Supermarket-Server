package org.supermarket.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {
	private static HibernateUtil instance;
	private final EntityManagerFactory managerFactory;
	
	public HibernateUtil () {
		managerFactory = Persistence.createEntityManagerFactory("SuperMarket");
	}
	
	public synchronized static HibernateUtil getInstance () {
		return instance == null ? new HibernateUtil() : instance;
	}
	
	public EntityManagerFactory getManagerFactory () {
		return managerFactory;
	}
}
