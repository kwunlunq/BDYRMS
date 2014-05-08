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

import com.bdy.model.BdyMainkind;


public class BdyMainkindDao {
	
	private SessionFactory sf = null;
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyMainkindDao(){		
	}
	@SuppressWarnings("unchecked")
	public List<BdyMainkind> getAllMainkind(){
		Session session = sf.openSession();
		List<BdyMainkind> result = session.createCriteria(BdyMainkind.class).list();
		session.close();
		return result;
	}
	public BdyMainkind getMainkind(int mkId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyMainkind.class)
							   .add(Restrictions.eq("mkId", mkId))
							   .list()
							   .iterator();
		BdyMainkind result = null;
		if (iter.hasNext()) {
			result = (BdyMainkind) iter.next();
		}
		session.close();
		return result;
	}
	public int insert(BdyMainkind mainkind) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(mainkind);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	public int delete(int mkId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyMainkind.class);
		Iterator iter = criteria.add(Restrictions.eq("mkId", mkId)).list().iterator();
		while (iter.hasNext()) {
			BdyMainkind order = (BdyMainkind) iter.next();
			session.delete(order);
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
	public int update(BdyMainkind mainkind) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyMainkind.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("mkId", mainkind.getMkId())).list().iterator();
		if (iter.hasNext()) {
			BdyMainkind tmpMainkind = (BdyMainkind) iter.next();
			tmpMainkind.setMkId(mainkind.getMkId());
			tmpMainkind.setName(mainkind.getName());
			
		} else {
			System.out.println("修改失敗 : 資料不存在 (odId:"+mainkind.getMkId()+")");
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
