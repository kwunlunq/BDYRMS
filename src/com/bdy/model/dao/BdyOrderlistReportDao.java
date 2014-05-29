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
import com.bdy.model.BdyOrderlistReport;

public class BdyOrderlistReportDao {
	private SessionFactory sf = null;

	public void setSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}

	public BdyOrderlistReportDao() {

	}

	@SuppressWarnings("unchecked")
	public List<BdyOrderlistReport> getAllOrderlistReport() {
		Session session = sf.openSession();
		List<BdyOrderlistReport> result = session.createCriteria(BdyOrderlistReport.class).list();
		session.close();
		return result;
	}

	public BdyOrderlistReport getOrderlistReport(int odlistId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyOrderlistReport.class)
				.add(Restrictions.eq("odlistId", odlistId)).list().iterator();
		BdyOrderlistReport result = null;
		if (iter.hasNext()) {
			result = (BdyOrderlistReport) iter.next();
		}
		session.close();
		return result;
	}

	public int insert(BdyOrderlistReport orderlistReport) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		try {
			session.save(orderlistReport);
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

		Criteria criteria = session.createCriteria(BdyOrderlistReport.class);
		Iterator iter = criteria.add(Restrictions.eq("odlistId", odlistId)).list()
				.iterator();
		while (iter.hasNext()) {
			BdyOrderlistReport orderlistReport = (BdyOrderlistReport) iter.next();
			session.delete(orderlistReport);
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

	public int update(BdyOrderlistReport orderlistReport) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyOrderlistReport.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria
				.add(Restrictions.eq("odlistId", orderlistReport.getOdlistId())).list()
				.iterator();
		if (iter.hasNext()) {
			BdyOrderlistReport tmpOrderlistReport = (BdyOrderlistReport) iter.next();
			tmpOrderlistReport.setOdlistId(tmpOrderlistReport.getOdlistId());
			tmpOrderlistReport.setOdId(tmpOrderlistReport.getOdId());
			tmpOrderlistReport.setOdEmpId(tmpOrderlistReport.getOdEmpId());
			tmpOrderlistReport.setOdEmpName(tmpOrderlistReport.getOdEmpName());
			tmpOrderlistReport.setFoodName(tmpOrderlistReport.getFoodName());
			tmpOrderlistReport.setFoodPrice(tmpOrderlistReport.getFoodPrice());
			tmpOrderlistReport.setSetName(tmpOrderlistReport.getSetName());
			tmpOrderlistReport.setSetPrice(tmpOrderlistReport.getSetPrice());
			tmpOrderlistReport.setFoodkindName(tmpOrderlistReport.getFoodkindName());
			tmpOrderlistReport.setMainkindName(tmpOrderlistReport.getMainkindName());
			tmpOrderlistReport.setAddmoney(tmpOrderlistReport.getAddmoney());
			tmpOrderlistReport.setBdyBillHistory(tmpOrderlistReport.getBdyBillHistory());
		} else {
			System.out
					.println("修改失敗 : 資料不存在 (odlistId:" + orderlistReport.getOdlistId() + ")");
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
	
	/* Frank
	 	用BillId找Orderlist 
	 */
	
	@SuppressWarnings("unchecked")
	public List<BdyOrderlistReport> getOrderlistReportByBillId(int billId) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyOrderlistReport.class);
		List<BdyOrderlistReport> result = criteria.add(Restrictions.eq("bdyBillHistory.billId", billId)).list();
		session.close();
		return result;
	}
}
