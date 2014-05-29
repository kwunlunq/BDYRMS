package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.bdy.model.BdyEmp;
import com.bdy.model.BdyOrder;
import com.bdy.model.BdyTable;

public class BdyTableDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	
	@SuppressWarnings("unchecked")
	public List<BdyTable> getAllTable(){
		Session session = sf.openSession();
		List<BdyTable> result = session.createCriteria(BdyTable.class).list();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<BdyTable> getTableByFloor(int floorid){
		Session session = sf.openSession();
		List<BdyTable> result = session.createCriteria(BdyTable.class)
									   .add(Restrictions.eq("bdyFloor.floorid", floorid))
									   .list();
		session.close();
		return result;
	}
	

	public BdyTable getTableById(int tableId) {
		Session session = sf.openSession();
		@SuppressWarnings("rawtypes")
		Iterator iter = session.createCriteria(BdyTable.class)
							   .add(Restrictions.eq("tbId", tableId))
							   .list()
							   .iterator();
		BdyTable result = null;
		if (iter.hasNext()) {
			result = (BdyTable) iter.next();
		}
		session.close();
		return result;
	}
	
	public void saveTable(BdyTable table){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(table);
		tx.commit();
		session.close();
	}
	
	public void updateTable(BdyTable table){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.update(table);
		tx.commit();
		session.close();
	}
	
	public void deleteTableByFloor(int floor){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(BdyTable.class).add(Restrictions.eq("bdyFloor.floorid", floor));
		@SuppressWarnings("rawtypes")
		Iterator tables = criteria.list().iterator();
		while (tables.hasNext()) {
			BdyTable table = (BdyTable) tables.next();
			System.out.println(table.getName());
			session.delete(table);
		}
		tx.commit();
		session.close();
	}
	
	public void deleteTableById(int tbid){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(BdyTable.class).add(Restrictions.eq("tbId", tbid));
		@SuppressWarnings("rawtypes")
		Iterator tables = criteria.list().iterator();
		while (tables.hasNext()) {
			BdyTable table = (BdyTable) tables.next();
			System.out.println(table.getName());
			session.delete(table);
		}
		tx.commit();
		session.close();
	}
	
	public int getCustNum() {
		Session session = sf.openSession();
		int result = ((Number)session.createCriteria(BdyTable.class)
				 					 .setProjection(Projections.sum("custNum"))
				 					 .uniqueResult())
				 					 .intValue();
		session.close();
		return result;
	}
}
