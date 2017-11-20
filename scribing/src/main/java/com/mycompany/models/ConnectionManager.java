package com.mycompany.models;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionManager {
	
	private static Session session = null;
	private static SessionFactory sessionFactory = null;
	
	private ConnectionManager() {
	}
	
	public static Session getSession() {
		if(session == null || !session.isConnected()) {
		    sessionFactory = new Configuration().configure().buildSessionFactory();
		    session = sessionFactory.openSession();
		}
		
		return session;
	}
	
	public static Boolean closeSession() {
		Boolean result = true;
		
		session.close();
		if(sessionFactory != null) {
			sessionFactory.close();
		}

		return result;
	}

}
