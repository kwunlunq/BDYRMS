package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdyTable;

public class BdyOrderlistDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyOrderlistDao(){
		
	}
	@SuppressWarnings("unchecked")
	public List<BdyOrderlist> getAllorderlist(){
		Session session = sf.openSession();
		List<BdyOrderlist> result = session.createCriteria(BdyOrderlist.class).list();
		session.close();
		return result;
	}
	
	public BdyOrderlist getorderlist(int odlistId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyOrderlist.class)
							   .add(Restrictions.eq("odlistId", odlistId))
							   .list()
							   .iterator();
		BdyOrderlist result = null;
		if (iter.hasNext()) {
			result = (BdyOrderlist) iter.next();
		}
		session.close();
		return result;
	}
	
	public int batchInsert(List<BdyOrderlist> lists) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		for (BdyOrderlist list : lists) {
			session.save(list);
		}
		try {
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	public int insert(BdyOrderlist orderlist) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(orderlist);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int delete(int odlistId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyOrderlist.class);
		Iterator iter = criteria.add(Restrictions.eq("odlistId", odlistId)).list().iterator();
		while (iter.hasNext()) {
			BdyOrderlist orderlist = (BdyOrderlist) iter.next();
			session.delete(orderlist);
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
	
	public int update(BdyOrderlist orderlist) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyOrderlist.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("odlistId", orderlist.getOdlistId())).list().iterator();
		if (iter.hasNext()) {
			BdyOrderlist tmporderlist = (BdyOrderlist) iter.next();
			tmporderlist.setOdlistId(orderlist.getOdlistId());
			tmporderlist.setAddmoney(orderlist.getAddmoney());
			tmporderlist.setBdyFood(orderlist.getBdyFood());
			tmporderlist.setBdyFoodkind(orderlist.getBdyFoodkind());
			tmporderlist.setBdyOrder(orderlist.getBdyOrder());
			tmporderlist.setBdySet(orderlist.getBdySet());
			tmporderlist.setOlState(orderlist.getOlState());
		} else {
			System.out.println("修改失敗 : 資料不存在 (odlistId:"+orderlist.getOdlistId()+")");
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
	
	public int getUnservedOdlist() {
		Session session = sf.openSession();
		int result = ((Number)session.createCriteria(BdyOrderlist.class)
									 .add(Restrictions.eq("olState", 0))
				 					 .setProjection(Projections.count("odlistId"))
				 					 .uniqueResult())
				 					 .intValue();
		session.close();
		return result;
	}

}
