package com.bdy.model.dao;

import java.util.Iterator;
import java.util.List;

import com.bdy.model.BdyFood;
import com.bdy.model.BdyOrder;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class BdyFoodDao {

	public BdyFoodDao() {
		super();
		
	}
	
	private SessionFactory sf=null;

	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	@SuppressWarnings("unchecked")
	public List<BdyFood> getAllFood(){
		Session session = sf.openSession();
		List<BdyFood> result = session.createCriteria(BdyFood.class).list();
		session.close();
		return result;
	}

	public int insert(BdyFood bean) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(bean);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	public BdyFood getFood(int fdId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyFood.class)
							   .add(Restrictions.eq("fdId", fdId))
							   .list()
							   .iterator();
		BdyFood result = null;
		if (iter.hasNext()) {
			result = (BdyFood) iter.next();
		}
		session.close();
		return result;
	}
	public int delete(int fdId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyFood.class);
		Iterator iter = criteria.add(Restrictions.eq("fdId", fdId)).list().iterator();
		while (iter.hasNext()) {
			BdyFood food = (BdyFood) iter.next();
			session.delete(food);
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
	public int update(BdyFood food) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyFood.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("fdId", food.getFdId())).list().iterator();
		if (iter.hasNext()) {
			BdyFood tmpFood = (BdyFood) iter.next();
			tmpFood.setFdId(food.getFdId());
			tmpFood.setName(food.getName());
			tmpFood.setPrice(food.getPrice());
			tmpFood.setQty(food.getQty());
			tmpFood.setDescript(food.getDescript());
			tmpFood.setBdyOrderlists(food.getBdyOrderlists());
			tmpFood.setBdyFoodkind(food.getBdyFoodkind());
			tmpFood.setBdyMainkind(food.getBdyMainkind());
		} else {
			System.out.println("修改失敗 : 資料不存在 (fdId:"+food.getFdId()+")");
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
	/*
	 * 以fk_id查詢符合的foods
	 */
	@SuppressWarnings("unchecked")
	public List<BdyFood> getFoodsByFkId(int fkId) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyFood.class);
		List<BdyFood> result = criteria.createAlias("bdyFoodkind", "fk")
				   .add(Restrictions.eq("fk.fkId", fkId))
				   .list();
		session.close();
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<BdyFood> getFoodsByMkId(int mkId) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyFood.class);
		List<BdyFood> result = criteria.createAlias("bdyMainkind", "mk")
				   .add(Restrictions.eq("mk.mkId", mkId))
				   .list();
		session.close();
		return result;
	}
	
	// ================================
}
