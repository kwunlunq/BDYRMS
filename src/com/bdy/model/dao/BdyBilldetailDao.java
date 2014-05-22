package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.bdy.model.BdyBilldetail;


public class BdyBilldetailDao {
	
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}

	public BdyBilldetailDao() {
		
	}
	
	public List<BdyBilldetail> getAllBilldetail(){
		Session session = sf.openSession();
		List<BdyBilldetail> result = session.createCriteria(BdyBilldetail.class).list();
		session.close();
		return result;
	}
	public BdyBilldetail getBilldetail(int bdId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyBilldetail.class)
							   .add(Restrictions.eq("bdId", bdId))
							   .list()
							   .iterator();
		BdyBilldetail result = null;
		if (iter.hasNext()) {
			result = (BdyBilldetail) iter.next();
		}
		session.close();
		return result;
	}
	
	public int insert(BdyBilldetail billdetail) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(billdetail);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int delete(int bdId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Iterator iter = session.createCriteria(BdyBilldetail.class)
							   .add(Restrictions.eq("bdId", bdId))
							   .list().iterator();
		while (iter.hasNext()) {
			BdyBilldetail billdetail = (BdyBilldetail) iter.next();
			session.delete(billdetail);
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
	
	public int update(BdyBilldetail billdetail) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyBilldetail.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("bdId", billdetail.getBdId())).list().iterator();
		if (iter.hasNext()) {
			BdyBilldetail tmpBilldetail = (BdyBilldetail) iter.next();
			tmpBilldetail.setBdId(billdetail.getBdId());
			tmpBilldetail.setBdyBill(billdetail.getBdyBill());
			tmpBilldetail.setBdyOrder(billdetail.getBdyOrder());
			
		} else {
			System.out.println("修改失敗 : 資料不存在 (bdId:"+billdetail.getBdId()+")");
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
