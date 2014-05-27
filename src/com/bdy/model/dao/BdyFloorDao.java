package com.bdy.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import com.bdy.model.BdyFloor;

public class BdyFloorDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	
	@SuppressWarnings("unchecked")
	public List<BdyFloor> getAllFloor(){
		Session session = sf.openSession();
		List<BdyFloor> result = session.createCriteria(BdyFloor.class).addOrder(Order.asc("sort")).list();
		session.close();
		return result;
	}
	
	public void insertFloor(BdyFloor floorBean){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.save(floorBean);
		tx.commit();
		session.close();
	}
	
	public void deleteFloor(BdyFloor floorBean){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.delete(floorBean);
		tx.commit();
		session.close();
	}
	
	public void updateFloor(BdyFloor floorBean){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.update(floorBean);
		tx.commit();
		session.close();
	}
	
	public BdyFloorDao(){
		
	}
}
