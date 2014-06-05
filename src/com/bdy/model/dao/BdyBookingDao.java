package com.bdy.model.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.bdy.model.BdyBooking;


public class BdyBookingDao {
	private SessionFactory sf = null;
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyBookingDao() {
		
	}
	@SuppressWarnings("unchecked")
	public List<BdyBooking> getAllBooking(){
		Session session = sf.openSession();
		List<BdyBooking> result = session.createCriteria(BdyBooking.class).list();
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<BdyBooking> getBookingByTbId(int tbId){
		Session session = sf.openSession();
		List<BdyBooking> result = session.createCriteria(BdyBooking.class).add(Restrictions.eq("tbId", tbId)).list();
		session.close();
		return result;
	}
	
	public BdyBooking getBookingTodayAndTbId(Date startDate,Date endDate,int tbId){
		Session session = sf.openSession();
		BdyBooking result = (BdyBooking)session.createCriteria(BdyBooking.class)
								.add(Restrictions.between("bkEatdate", startDate,endDate))
								.add(Restrictions.eq("tbId", tbId))
								.addOrder(Order.asc("bkEatdate"))
								.setMaxResults(1)
								.uniqueResult();
		session.close();
		return result;
	}
	
	public BdyBooking getBooking(int bkId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyBooking.class)
							   .add(Restrictions.eq("bkId", bkId))
							   .list()
							   .iterator();
		BdyBooking result = null;
		if (iter.hasNext()) {
			result = (BdyBooking) iter.next();
		}
		session.close();
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<BdyBooking> getBookingByDate(Date startDate,Date endDate){
		Session session = sf.openSession();
		List<BdyBooking> result = session.createCriteria(BdyBooking.class)
								.add(Restrictions.between("bkEatdate", startDate,endDate))
								.list();
		session.close();
		return result;
	}
	
	public int insert(BdyBooking booking) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			session.save(booking);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int delete(int bkId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyBooking.class);
		Iterator iter = criteria.add(Restrictions.eq("bkId", bkId)).list().iterator();
		while (iter.hasNext()) {
			BdyBooking booking = (BdyBooking) iter.next();
			session.delete(booking);
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
	
	public int update(BdyBooking booking) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyBooking.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("bkId", booking.getBkId())).list().iterator();
		if (iter.hasNext()) {
			BdyBooking tmpBooking = (BdyBooking) iter.next();
			tmpBooking.setBkId(booking.getBkId());
			tmpBooking.setBkName(booking.getBkName());
			tmpBooking.setBkPhone(booking.getBkPhone());
			tmpBooking.setTbId(booking.getTbId());
			tmpBooking.setBkOrderdate(booking.getBkOrderdate());
			tmpBooking.setBkEatdate(booking.getBkEatdate());
			tmpBooking.setBkContent(booking.getBkContent());
			tmpBooking.setBkState(booking.getBkState());
			tmpBooking.setBkNumber(booking.getBkNumber());
			
		} else {
			System.out.println("修改失敗 : 資料不存在 (bkId:"+booking.getBkId()+")");
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
