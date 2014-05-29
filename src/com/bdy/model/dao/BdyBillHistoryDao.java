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

import com.bdy.model.BdyBillHistory;

public class BdyBillHistoryDao {
	private SessionFactory sf = null;

	public void setSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}

	public BdyBillHistoryDao() {

	}

	@SuppressWarnings("unchecked")
	public List<BdyBillHistory> getAllBillHistory() {
		Session session = sf.openSession();
		List<BdyBillHistory> result = session.createCriteria(BdyBillHistory.class).list();
		session.close();
		return result;
	}

	public BdyBillHistory getBillHistory(int billId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyBillHistory.class)
				.add(Restrictions.eq("billId", billId)).list().iterator();
		BdyBillHistory result = null;
		if (iter.hasNext()) {
			result = (BdyBillHistory) iter.next();
		}
		session.close();
		return result;
	}

	public int insert(BdyBillHistory billHistory) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			session.save(billHistory);
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

		Criteria criteria = session.createCriteria(BdyBillHistory.class);
		Iterator iter = criteria.add(Restrictions.eq("billId", billId)).list()
				.iterator();
		while (iter.hasNext()) {
			BdyBillHistory billHistory = (BdyBillHistory) iter.next();
			session.delete(billHistory);
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

	public int update(BdyBillHistory billHistory) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyBillHistory.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria
				.add(Restrictions.eq("billId", billHistory.getBillId())).list()
				.iterator();
		if (iter.hasNext()) {
			BdyBillHistory tmpBillHistory = (BdyBillHistory) iter.next();
			tmpBillHistory.setBillId(billHistory.getBillId());
			tmpBillHistory.setEndDate(billHistory.getEndDate());
			tmpBillHistory.setCustNum(billHistory.getCustNum());
			tmpBillHistory.setDisName(billHistory.getDisName());
			tmpBillHistory.setBillEmpId(billHistory.getBillEmpId());
			tmpBillHistory.setBillEmpName(billHistory.getBillEmpName());
			tmpBillHistory.setPrice(billHistory.getPrice());
			tmpBillHistory.setFinPrice(billHistory.getFinPrice());
			tmpBillHistory.setDiscription(billHistory.getDiscription());
		} else {
			System.out
					.println("修改失敗 : 資料不存在 (billId:" + billHistory.getBillId() + ")");
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
	public List<BdyBillHistory> getDayBillHistoryList(java.util.Date endDate) {
		java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
		Session session = sf.openSession();
		List<BdyBillHistory> result = session
				.createSQLQuery(
						"select * from BDY_BILL_HISTORY where END_DATE between '"
								+ sqlEndDate + " 00:00:00' and '" + sqlEndDate
								+ " 23:59:59'").addEntity(BdyBillHistory.class).list();
		session.close();
		return result;
	}
}
