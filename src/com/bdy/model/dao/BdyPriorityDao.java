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
import com.bdy.model.BdyPriority;

public class BdyPriorityDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyPriorityDao(){
		
	}
	@SuppressWarnings("unchecked")
	public List<BdyPriority> getAllProiority(){
		Session session = sf.openSession();
		List<BdyPriority> result = session.createCriteria(BdyPriority.class).list();
		session.close();
		return result;
	}
	
	public BdyPriority getProiorityById(int priId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyPriority.class)
							   .add(Restrictions.eq("priId", priId))
							   .list()
							   .iterator();
		BdyPriority result = null;
		if (iter.hasNext()) {
			result = (BdyPriority) iter.next();
		}
		session.close();
		return result;
	}
	
	public int insert(BdyPriority pri) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(pri);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int deleteById(int priId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyPriority.class);
		Iterator iter = criteria.add(Restrictions.eq("priId", priId)).list().iterator();
		while (iter.hasNext()) {
			BdyPriority order = (BdyPriority) iter.next();
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
	
	public int update(BdyPriority pri) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyPriority.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("priId", pri.getPriId())).list().iterator();
		if (iter.hasNext()) {
			BdyPriority tmpPri = (BdyPriority) iter.next();
			tmpPri.setPriId(pri.getPriId());
			tmpPri.setJobname(pri.getJobname());
			tmpPri.setPrio(pri.getPrio());

		} else {
			System.out.println("修改失敗 : 資料不存在 (priId:"+pri.getPriId()+")");
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

