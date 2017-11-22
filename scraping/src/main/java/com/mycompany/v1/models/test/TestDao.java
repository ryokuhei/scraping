package com.mycompany.v1.models.test;


import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.mycompany.v1.models.dao.ConnectionManager;
import com.mycompany.v1.models.entity.DataEntity;
import com.mycompany.v1.models.entity.RankingEntity;


public class TestDao {
	
	public Boolean create(RankingEntity test) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		
		session.beginTransaction();
		session.save(test);
		session.getTransaction().commit();	

		ConnectionManager.closeSession();

		return result;
	}

	public Boolean create(DataEntity test) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		
		session.beginTransaction();
		session.save(test);
		session.getTransaction().commit();	

		ConnectionManager.closeSession();

		return result;
	}

	@SuppressWarnings("unchecked")
	public TestEntity search(Long machineNo) {
		
		TestEntity data = new TestEntity();

		Session session = ConnectionManager.getSession();
		Query<TestEntity> query = session.createQuery("FROM TestEntity WHERE id = ?").setParameter(0, machineNo);
		List<TestEntity> dataList = query.list();
		
		Iterator<TestEntity> iterator = dataList.iterator();
		if(iterator.hasNext()) {
			data = iterator.next();
		}

		ConnectionManager.closeSession();

		return data;
	}

	@SuppressWarnings("unchecked")
	public List<TestEntity> getList() {
		
		List<TestEntity> dataList = null;

		Session session = ConnectionManager.getSession();
		Query<TestEntity> query = session.createQuery("FROM TestEntity");
		dataList = query.list();
		
		ConnectionManager.closeSession();

		return dataList;
	}


}
