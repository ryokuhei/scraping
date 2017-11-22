package com.mycompany.v1.controllers.test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.wicketstuff.rest.annotations.MethodMapping;

import com.mycompany.v1.controllers.RestAPI;
import com.mycompany.v1.models.dao.ConnectionManager;
import com.mycompany.v1.models.test.TestDao;
import com.mycompany.v1.models.test.TestEntity;

public class TestHibernate extends RestAPI {
	
	private static final long serialVersionUID = 1L;

	public TestHibernate() {
		super();
	}
	
	@MethodMapping("/list")
	public List<TestEntity> getList() {

		List<TestEntity> test = null;
//		this.testQuery();

		TestDao dao = new TestDao();
		test = dao.getList();
		
		return test;
	}
	
	public List testQuery() {
		
		Session session = ConnectionManager.getSession();
		List list = session.createNativeQuery("select now()").list();
		ConnectionManager.closeSession();
		System.out.println(list);


//		System.out.println("testQuery");
//		List list = null;
//
//		System.out.println("create sessionFactory");
//		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//
//		if(sessionFactory.isOpen()) {
//    		System.out.println("sessionFactory is Opened");
//		} else {
//	    	System.out.println("essionFactory is Closed");
//		}
//		
//		Session session = sessionFactory.openSession();
//		if(!session.isConnected()) {
//			System.out.println("not Connection....");
//		}
//		list = session.createSQLQuery("select now()").list();
//		System.out.println(list);
//		
//		if(sessionFactory != null) {
//			sessionFactory.close();
//		}
		return list;
	}
	
}
