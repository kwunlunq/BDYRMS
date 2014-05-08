package com.bdy.model.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.bdy.model.BdyFloor;

public class BdyFloorDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	
	@SuppressWarnings("unchecked")
	public List<BdyFloor> getRestById(int id){
		Session session = sf.openSession();
		List<BdyFloor> result = session.createCriteria(BdyFloor.class).list();
		session.close();
		return result;
	}
	
	public BdyFloorDao(){
		
	}
}
