package com.bdy.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

import com.bdy.model.BdyBill;


public class BdyBillDao {
	private SessionFactory sf = null;

	public void setSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}

	public BdyBillDao() {

	}

	@SuppressWarnings("unchecked")
	public List<BdyBill> getAllBill() {
		Session session = sf.openSession();
		List<BdyBill> result = session.createCriteria(BdyBill.class).list();
		session.close();
		return result;
	}

	public BdyBill getBill(int billId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyBill.class)
				.add(Restrictions.eq("billId", billId)).list().iterator();
		BdyBill result = null;
		if (iter.hasNext()) {
			result = (BdyBill) iter.next();
		}
		session.close();
		return result;
	}

	public int insert(BdyBill bill) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			session.save(bill);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}

	public int delete(int billId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(BdyBill.class);
		Iterator iter = criteria.add(Restrictions.eq("billId", billId)).list()
				.iterator();
		while (iter.hasNext()) {
			BdyBill bill = (BdyBill) iter.next();
			session.delete(bill);
		}

		try {
			tx.commit();
		} catch (StaleStateException e) {
			System.out.println("刪除失敗 : 資料不存在 ( " + e.getMessage() + " )");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}

	public int update(BdyBill bill) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyBill.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria
				.add(Restrictions.eq("billId", bill.getBillId())).list()
				.iterator();
		if (iter.hasNext()) {
			BdyBill tmpBill = (BdyBill) iter.next();
			tmpBill.setBillId(bill.getBillId());
			tmpBill.setEndDate(bill.getEndDate());
			tmpBill.setCustNum(bill.getCustNum());
			tmpBill.setBdyDiscount(bill.getBdyDiscount());
			tmpBill.setBdyEmp(bill.getBdyEmp());
			tmpBill.setPrice(bill.getPrice());
			tmpBill.setFinPrice(bill.getFinPrice());
		} else {
			System.out
					.println("修改失敗 : 資料不存在 (billId:" + bill.getBillId() + ")");
			session.close();
			return 0;
		}

		try {
			tx.commit();
		} catch (StaleStateException e) {
			System.out.println("修改失敗 : 資料不存在 ( " + e.getMessage() + " )");
			session.close();
			return 0;
		}

		session.close();
		return 1;
	}

	/*--------------------------以下為報表與資料庫作查詢-------------------------*/

	@SuppressWarnings("unchecked")
	// 日營收清單
	public List<BdyBill> getDayRevenueDetailsDB(java.util.Date endDate) {

		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		Session session = sf.openSession();
		List<BdyBill> bills = session
				.createSQLQuery(
						"select * from BDY_BILL  where END_DATE between '"
								+ sqlEndDate + " 00:00:00' and '" + sqlEndDate
								+ " 23:59:59'").addEntity(BdyBill.class).list();
		session.close();
		return bills;
	}
	
	/*---------拿目前收入----------*/
	public Double getTodayIncome() {
		Session session = sf.openSession();
		Calendar today = Calendar.getInstance();
		today.setTime(new Date());
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		
		Double result = 0.0;
		try {
			result = ((Number)session.createCriteria(BdyBill.class)
										 	.add(Restrictions.ge("endDate", today.getTime()))
										 	.setProjection(Projections.sum("finPrice"))
										 	.uniqueResult())
										 	.doubleValue();
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
//			System.out.println("--");
//			System.out.println("No income found");
		}
		session.close();
		return result;
	}
	
	
}





