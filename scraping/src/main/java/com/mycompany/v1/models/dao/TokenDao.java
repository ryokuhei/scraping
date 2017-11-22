package com.mycompany.v1.models.dao;


import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mycompany.v1.models.entity.TokenEntity;


public class TokenDao {
	
	public Boolean create(TokenEntity token) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		
		session.beginTransaction();
		session.save(token);
		session.getTransaction().commit();	

		ConnectionManager.closeSession();

		return result;
	}

	@SuppressWarnings("unchecked")
	public Boolean checkToken(String token) {
		Boolean result = false;
		
		Session session = ConnectionManager.getSession();
		String hql = "FROM TokenEntity "
		           + "WHERE token = ? "
				   + " AND created_at INTERVAL + expires_in SECOND > now()";
		Query<TokenEntity> query = session
				.createQuery(hql)
				.setParameter(0, token);
		List<TokenEntity> dataList = query.list();
		
		Iterator<TokenEntity> iterator = dataList.iterator();
		if(iterator.hasNext()) {
			result = true;
		}
		ConnectionManager.closeSession();

		return result;
	}
}
