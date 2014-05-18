package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.bdy.model.BdyFoodkind;

public class BdyFoodkindDao {

	public BdyFoodkindDao(){};
	private SessionFactory sf=null;

	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	@SuppressWarnings("unchecked")
	public List<BdyFoodkind> getAllFoodkind(){
		Session session = sf.openSession();
		List<BdyFoodkind> result = session.createCriteria(BdyFoodkind.class)
				   						  .addOrder(Order.asc("seq"))
				   						  .list();
		session.close();
		return result;
	}
	public int insert(BdyFoodkind foodkind) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(foodkind);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	public BdyFoodkind getFoodkind(int fkId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyFoodkind.class)
							   .add(Restrictions.eq("fkId", fkId))
							   .list()
							   .iterator();
		BdyFoodkind result = null;
		if (iter.hasNext()) {
			result = (BdyFoodkind) iter.next();
		}
		session.close();
		return result;
	}
	public int delete(int fkId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyFoodkind.class);
		Iterator iter = criteria.add(Restrictions.eq("fkId", fkId)).list().iterator();
		while (iter.hasNext()) {
			BdyFoodkind foodkind = (BdyFoodkind) iter.next();
			session.delete(foodkind);
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
	public int update(BdyFoodkind foodkind) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyFoodkind.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("fkId", foodkind.getFkId())).list().iterator();
		if (iter.hasNext()) {
			BdyFoodkind tmpFoodkind = (BdyFoodkind) iter.next();
			tmpFoodkind.setFkId(foodkind.getFkId());
			tmpFoodkind.setName(foodkind.getName());
			tmpFoodkind.setPeriod(foodkind.getPeriod());
			tmpFoodkind.setSeq(foodkind.getSeq());
			tmpFoodkind.setBdyMakearea(foodkind.getBdyMakearea());
			
		} else {
			System.out.println("修改失敗 : 資料不存在 (fdId:"+foodkind.getFkId()+")");
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
	 * by 皓元
	 */
	
	@SuppressWarnings("unchecked")
	public List<BdyFoodkind> getMainFoodkinds() {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyFoodkind.class);
		List<BdyFoodkind> result = criteria.add(Restrictions.eq("isMain", 1)).list();
		session.close();
		return result;
								   
	}
	
}
