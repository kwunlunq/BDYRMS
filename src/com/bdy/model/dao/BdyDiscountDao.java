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

import com.bdy.model.BdyDiscount;


public class BdyDiscountDao {
	
	private SessionFactory sf = null;
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyDiscountDao(){		
	}
	@SuppressWarnings("unchecked")
	public List<BdyDiscount> getAllDiscount(){
		Session session = sf.openSession();
		List<BdyDiscount> result = session.createCriteria(BdyDiscount.class).list();
		session.close();
		return result;
	}
	public BdyDiscount getDiscount(int disId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyDiscount.class)
							   .add(Restrictions.eq("disId", disId))
							   .list()
							   .iterator();
		BdyDiscount result = null;
		if (iter.hasNext()) {
			result = (BdyDiscount) iter.next();
		}
		session.close();
		return result;
	}
	public int insert(BdyDiscount discount) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(discount);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	public int delete(int disId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyDiscount.class);
		Iterator iter = criteria.add(Restrictions.eq("disId", disId)).list().iterator();
		while (iter.hasNext()) {
			BdyDiscount order = (BdyDiscount) iter.next();
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
	public int update(BdyDiscount discount) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyDiscount.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("disId", discount.getDisId())).list().iterator();
		if (iter.hasNext()) {
			BdyDiscount tmpDiscount = (BdyDiscount) iter.next();
			tmpDiscount.setDisId(discount.getDisId());
			tmpDiscount.setName(discount.getName());
			tmpDiscount.setDisPrice(discount.getDisPrice());
			tmpDiscount.setBdyBills(discount.getBdyBills());
			
		} else {
			System.out.println("修改失敗 : 資料不存在 (odId:"+discount.getDisId()+")");
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
