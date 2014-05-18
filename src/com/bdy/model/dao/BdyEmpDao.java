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

import com.bdy.model.BdyEmp;

public class BdyEmpDao {
private SessionFactory sf = null;
	
	public void setSessionFactory(SessionFactory sf){
		this.sf = sf;
	}
	public BdyEmpDao(){
		
	}
	@SuppressWarnings("unchecked")
	public List<BdyEmp> getAllEmp(){
		Session session = sf.openSession();
		List<BdyEmp> result = session.createCriteria(BdyEmp.class).list();
		session.close();
		return result;
	}
	
	public BdyEmp getEmpById(String empId) {
		Session session = sf.openSession();
		Iterator iter = session.createCriteria(BdyEmp.class)
							   .add(Restrictions.eq("empId", empId))
							   .list()
							   .iterator();
		BdyEmp result = null;
		if (iter.hasNext()) {
			result = (BdyEmp) iter.next();
		}
		session.close();
		return result;
	}
	
	public int insert(BdyEmp emp) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		System.out.println(emp.getEmpId());
		try {
			session.save(emp);
			tx.commit();
		} catch (ConstraintViolationException e) {
			System.out.println("新增失敗 : 鍵值重複");
			session.close();
			return 0;
		}
		session.close();
		return 1;
	}
	
	public int deleteEmpById(String empId) {
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();
		
		Criteria criteria = session.createCriteria(BdyEmp.class);
		Iterator iter = criteria.add(Restrictions.eq("empId", empId)).list().iterator();
		while (iter.hasNext()) {
			BdyEmp emp = (BdyEmp) iter.next();
			session.delete(emp);
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
	
	public int update(BdyEmp emp) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyEmp.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("empId", emp.getEmpId())).list().iterator();
		if (iter.hasNext()) {
			BdyEmp tmpEmp = (BdyEmp) iter.next();
			tmpEmp.setEmpId(emp.getEmpId());
			tmpEmp.setPasswd(emp.getPasswd());
			tmpEmp.setName(emp.getName());
			tmpEmp.setSex(emp.getSex());
			tmpEmp.setBdyPriority(emp.getBdyPriority());
			tmpEmp.setComedate(emp.getComedate());
			tmpEmp.setSalary(emp.getSalary());
			tmpEmp.setPhone(emp.getPhone());
			tmpEmp.setEmpAddress(emp.getEmpAddress());
			tmpEmp.setResign(emp.getResign());
		} else {
			System.out.println("修改失敗 : 資料不存在 (empId:"+emp.getEmpId()+")");
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
	public int updateEmp(BdyEmp emp,String empId) {
		Session session = sf.openSession();
		Criteria criteria = session.createCriteria(BdyEmp.class);
		Transaction tx = session.beginTransaction();
		Iterator iter = criteria.add(Restrictions.eq("empId",empId)).list().iterator();
		if (iter.hasNext()) {
			BdyEmp tmpEmp = (BdyEmp) iter.next();
			tmpEmp.setEmpId(emp.getEmpId());
			tmpEmp.setPasswd(emp.getPasswd());
			tmpEmp.setName(emp.getName());
			tmpEmp.setSex(emp.getSex());
			tmpEmp.setBdyPriority(emp.getBdyPriority());
			tmpEmp.setComedate(emp.getComedate());
			tmpEmp.setSalary(emp.getSalary());
			tmpEmp.setPhone(emp.getPhone());
			tmpEmp.setEmpAddress(emp.getEmpAddress());
			tmpEmp.setResign(emp.getResign());
		} else {
			System.out.println("修改失敗 : 資料不存在 (empId:"+emp.getEmpId()+")");
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
