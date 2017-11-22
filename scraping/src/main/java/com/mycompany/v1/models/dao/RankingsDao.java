package com.mycompany.v1.models.dao;


import org.hibernate.Session;

import com.mycompany.v1.models.entity.RankingEntity;


public class RankingsDao {
	
	public Boolean create(RankingEntity ranking) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		
		session.beginTransaction();
		session.save(ranking);
		session.getTransaction().commit();	

		ConnectionManager.closeSession();

		return result;
	}
}
