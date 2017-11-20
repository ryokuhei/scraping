package com.mycompany.models;


import org.hibernate.Session;

import com.mycompany.models.entity.DataEntity;
import com.mycompany.models.entity.RankingEntity;

public class TestDao {
	
	public Boolean create(RankingEntity test) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		
		session.beginTransaction();
		session.save(test);
		session.getTransaction().commit();	

		Boolean isClosed = ConnectionManager.closeSession();

		return result;
	}

	public Boolean create(DataEntity test) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		
		session.beginTransaction();
		session.save(test);
		session.getTransaction().commit();	

		Boolean isClosed = ConnectionManager.closeSession();

		return result;
	}



}
