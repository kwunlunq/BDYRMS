package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.BdyMakearea;

public class BdyMakeareaDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyMakeareaDao(){
		
	}
	@SuppressWarnings("unchecked")
	public List<BdyMakearea> getAllMakeArea(){
		Session session = sf.openSession();
		List<BdyMakearea> result = session.createCriteria(BdyMakearea.class).list();
		session.close();
		return result;
	}
	
	public BdyMakearea getMakeAreaById(int maId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyMakearea.class)
							   .add(Restrictions.eq("maId", maId))
							   .list()
							   .iterator();
		BdyMakearea result = null;
		if (iter.hasNext()) {
			result = (BdyMakearea) iter.next();
		}
		session.close();
		return result;
	}
	
	public int insert(BdyMakearea MA) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(MA);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int deleteMakeareaById(int maId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyMakearea.class);
		Iterator iter = criteria.add(Restrictions.eq("maId", maId)).list().iterator();
		while (iter.hasNext()) {
			BdyMakearea order = (BdyMakearea) iter.next();
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
	
	public int update(BdyMakearea MA) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyMakearea.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("maId", MA.getMaId())).list().iterator();
		if (iter.hasNext()) {
			BdyMakearea tmpMA = (BdyMakearea) iter.next();
			tmpMA.setMaId(MA.getMaId());
			tmpMA.setName(MA.getName());
			tmpMA.setBdyFoodkinds(MA.getBdyFoodkinds());
		} else {
			System.out.println("修改失敗 : 資料不存在 (maId:"+MA.getMaId()+")");
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
