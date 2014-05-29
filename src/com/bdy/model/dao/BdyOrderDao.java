package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.bdy.model.BdyOrder;

public class BdyOrderDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyOrderDao(){
		
	}
	@SuppressWarnings("unchecked")
	public List<BdyOrder> getAllOrder(){
		Session session = sf.openSession();
		List<BdyOrder> result = session.createCriteria(BdyOrder.class).list();
		session.close();
		return result;
	}
	
	public BdyOrder getOrder(int odId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyOrder.class)
							   .add(Restrictions.eq("odId", odId))
							   .list()
							   .iterator();
		BdyOrder result = null;
		if (iter.hasNext()) {
			result = (BdyOrder) iter.next();
		}
		session.close();
		return result;
	}
	
	public int insert(BdyOrder order) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(order);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int delete(int odId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyOrder.class);
		Iterator iter = criteria.add(Restrictions.eq("odId", odId)).list().iterator();
		while (iter.hasNext()) {
			BdyOrder order = (BdyOrder) iter.next();
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
	
	public int update(BdyOrder order) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyOrder.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("odId", order.getOdId())).list().iterator();
		if (iter.hasNext()) {
			BdyOrder tmpOrder = (BdyOrder) iter.next();
			tmpOrder.setOdId(order.getOdId());
			tmpOrder.setBdyTable(order.getBdyTable());
			tmpOrder.setBdyEmp(order.getBdyEmp());
			tmpOrder.setOrdTime(order.getOrdTime());
			tmpOrder.setIsCheckout(order.getIsCheckout());
			tmpOrder.setCustNum(order.getCustNum());
		} else {
			System.out.println("修改失敗 : 資料不存在 (odId:"+order.getOdId()+")");
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
	
	/*
	 *  新增by Kevin 
	 *  使用已知物件 Example 查詢
	 */
	
	public BdyOrder getOrderByOrder(BdyOrder order) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyOrder.class)
							   .add(Example.create(order))
							   .list()
							   .iterator();
		BdyOrder result = null;
		if (iter.hasNext()) {
			result = (BdyOrder) iter.next();
		}
		session.close();
		return result;
	}
	
	public void upDateOrder(BdyOrder order){
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(order);
		tx.commit();
		session.close();
	}
	
	public int getOrderNotCheckNum() {
		Session session = sf.openSession();
		int result = ((Number)session.createCriteria(BdyOrder.class)
									 .add(Restrictions.eq("isCheckout", Integer.valueOf(0)))
									 .setProjection(Projections.count("isCheckout"))
									 .uniqueResult())
									 .intValue();
		session.close();
		return result;
	}
}
