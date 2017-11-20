package com.mycompany.controllers.test;

import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mycompany.models.TestDao;
import com.mycompany.models.entity.RankingEntity;
import com.mycompany.models.entity.TestEntity;

public class TestHibernate extends WebPage {
	
	private static final long serialVersionUID = 1L;

	public TestHibernate(final PageParameters parameters) {
		super(parameters);

		testQuery();
		
		String name = parameters.get(0).toString();
		
		RankingEntity test = new RankingEntity();
		TestDao dao = new TestDao();
		dao.create(test);

//		create(test);


		
	}
	
	public List testQuery() {
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
		
		Session session = sessionFactory.openSession();
		List result = session.createSQLQuery("select now()").list();
		System.out.println(result);
		
		if(sessionFactory != null) {
			sessionFactory.close();
		}
		return result;
	}
	
	public Boolean create(TestEntity test) {
		Boolean result = true;

		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		Session session = sessionFactory.openSession();

		session.beginTransaction();
		session.save(test);
		session.getTransaction().commit();
		session.close();

		if(sessionFactory != null) {
			sessionFactory.close();
		}

		return result;
	}
	
	public TestEntity select() {
		TestEntity test = new TestEntity();
		
		return test;
		
	}
	
}
