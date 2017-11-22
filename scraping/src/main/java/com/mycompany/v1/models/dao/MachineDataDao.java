package com.mycompany.v1.models.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.mycompany.v1.models.entity.DataEntity;

public class MachineDataDao {
	
	public Boolean create(DataEntity data) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		
		session.beginTransaction();
		session.save(data);
		session.getTransaction().commit();	

		ConnectionManager.closeSession();

		return result;
	}

	public Boolean update(DataEntity data) {
		Boolean result = true;
		
		Session session = ConnectionManager.getSession();
		Transaction tran = session.beginTransaction();
		
		try {
		    DataEntity loadData = (DataEntity) session.load(DataEntity.class, data.getId());
		    loadData.setMachineName(data.getMachineName());
		    loadData.setDataDetail(data.getDataDetail());
		    loadData.setDeterminationData(data.getDeterminationData());
		
		    session.update(loadData);
		    tran.commit();
		
		} catch(Exception e) {
		    tran.rollback();
			e.printStackTrace();
		} finally {
    		ConnectionManager.closeSession();
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public DataEntity search(Long machineNo) {
		
		DataEntity data = new DataEntity();

		Session session = ConnectionManager.getSession();
		Query<DataEntity> query = session.createQuery("FROM DataEntity WHERE machineNumber = ?").setParameter(0, machineNo);
		List<DataEntity> dataList = query.list();
		
		Iterator<DataEntity> iterator = dataList.iterator();
		if(iterator.hasNext()) {
			data = iterator.next();
		}

		ConnectionManager.closeSession();

		return data;
	}

	@SuppressWarnings("unchecked")
	public List<DataEntity> getList() {
		
		List<DataEntity> dataList = null;

		Session session = ConnectionManager.getSession();
		Query<DataEntity> query = session.createQuery("FROM DataEntity");
		dataList = query.list();
		
		ConnectionManager.closeSession();

		return dataList;
	}

	@SuppressWarnings("unchecked")
	public int isExsist(DataEntity data) {
		int id = -1;

		Session session = ConnectionManager.getSession();

		Query<DataEntity> query = session
			                     	 .createQuery("FROM DataEntity WHERE machineNumber = ? AND date = ?")
				                     .setParameter(0, data.getMachineNumber())
				                     .setParameter(1, data.getDate());
		List <DataEntity>dataList = query.list();
		Iterator<DataEntity> iterator = dataList.iterator();
		if(iterator.hasNext()) {
			data = iterator.next();
			id = data.getId();
		}
		
		return id;
	}}
