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

import com.bdy.model.BdyNews;



public class BdyNewsDao {
	private SessionFactory sf = null;

	public void setSessionFactory(SessionFactory sf) {
		this.sf = sf;
	}

	public BdyNewsDao() {
	}
	
	public List<BdyNews> getAllNews() {
		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		List<BdyNews> result = session.createCriteria(BdyNews.class).list();
		session.close();
		return result;
	}
	
	public List<BdyNews> getAllNewsSortByDateDESC() {
		Session session = sf.openSession();
		@SuppressWarnings("unchecked")
		List<BdyNews> result = session.createCriteria(BdyNews.class)
							   .addOrder(Order.desc("newsPostdate"))
							   .list();
		session.close();
		return result;
	}
	
	public BdyNews getNews(int newsId) {
		Session session = sf.openSession();
		@SuppressWarnings("rawtypes")
		Iterator iter = session.createCriteria(BdyNews.class)
				.add(Restrictions.eq("newsId", newsId)).list().iterator();
		BdyNews result = null;
		if (iter.hasNext()) {
			result = (BdyNews) iter.next();
		}
		session.close();
		return result;
	}
	
	public int insert(BdyNews news) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(news);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	public int deleteNewsById(int newsId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		Criteria criteria = session.createCriteria(BdyNews.class);
		@SuppressWarnings("rawtypes")
		Iterator iter = criteria.add(Restrictions.eq("newsId", newsId)).list()
				.iterator();
		while (iter.hasNext()) {
			BdyNews bill = (BdyNews) iter.next();
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
	
	public int update(BdyNews news) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyNews.class);
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("rawtypes")
		Iterator iter = criteria
				.add(Restrictions.eq("newsId", news.getNewsId())).list()
				.iterator();
		if (iter.hasNext()) {
			BdyNews tmpNews = (BdyNews) iter.next();
			tmpNews.setNewsId(news.getNewsId());
			tmpNews.setNewsPostdate(news.getNewsPostdate());
			tmpNews.setNewsContent(news.getNewsContent());
			tmpNews.setNewsTitle(news.getNewsTitle());
			tmpNews.setNewsPostname(news.getNewsPostname());
		} else {
			System.out
					.println("修改失敗 : 資料不存在 (newsId:" + news.getNewsId() + ")");
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
	
}
