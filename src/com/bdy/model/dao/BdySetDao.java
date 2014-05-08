package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.bdy.model.BdySet;

public class BdySetDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdySetDao(){
		
	}
	@SuppressWarnings("unchecked")
	public List<BdySet> getAllSet(){
		Session session = sf.openSession();
		List<BdySet> result = session.createCriteria(BdySet.class).list();
		session.close();
		return result;
	}
	
	public BdySet getSet(int setId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdySet.class)
							   .add(Restrictions.eq("setId", setId))
							   .list()
							   .iterator();
		BdySet result = null;
		if (iter.hasNext()) {
			result = (BdySet) iter.next();
		}
		session.close();
		return result;
	}
	
	public int insert(BdySet set) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(set);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int delete(int setId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Iterator iter = session.createCriteria(BdySet.class)
							   .add(Restrictions.eq("setId", setId))
							   .list().iterator();
		while (iter.hasNext()) {
			BdySet set = (BdySet) iter.next();
			session.delete(set);
		}
		
		try {
			tx.commit();
		} catch (StaleStateException e) {
			System.out.println("刪除失敗 : 資料不存在 ( " + e.getMessage()+" )");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int update(BdySet set) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdySet.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("setId", set.getSetId())).list().iterator();
		if (iter.hasNext()) {
			BdySet tmpSet = (BdySet) iter.next();
			tmpSet.setSetId(set.getSetId());
			tmpSet.setName(set.getName());
			tmpSet.setPrice(set.getPrice());
			tmpSet.setBdyOrderlists(set.getBdyOrderlists());
			tmpSet.setBdySetdetails(set.getBdySetdetails());
			
		} else {
			System.out.println("修改失敗 : 資料不存在 (odId:"+set.getSetId()+")");
			session.close();
			return 0;
		}
		
		try {
			tx.commit();
		} catch (StaleStateException e) {
			System.out.println("修改失敗 : 資料不存在 ( " + e.getMessage()+" )");
			session.close();
			return 0;
		}
		
		session.close();
		return 1;
	}
	



}
