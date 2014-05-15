package com.bdy.service;

import java.util.List;

import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.model.dao.BdyBillDao;
import com.bdy.model.dao.BdyBilldetailDao;
import com.bdy.model.dao.BdyBookingDao;
import com.bdy.model.dao.BdyDiscountDao;
import com.bdy.model.dao.BdyEmpDao;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyFoodDao;
import com.bdy.model.dao.BdyFoodkindDao;
import com.bdy.model.dao.BdyMainkindDao;
import com.bdy.model.dao.BdyMakeareaDao;
import com.bdy.model.dao.BdyNewsDao;
import com.bdy.model.dao.BdyOrderDao;
import com.bdy.model.dao.BdyOrderlistDao;
import com.bdy.model.dao.BdyPriorityDao;
import com.bdy.model.dao.BdySetDao;
import com.bdy.model.dao.BdySetdetailDao;
import com.bdy.model.dao.BdyTableDao;

public class ManageService {

	BdyBillDao billDao;
	BdyDiscountDao discountDao;
	BdyEmpDao empDao;
	BdyFloorDao floorDao;
	BdyFoodDao foodDao;
	BdyFoodkindDao foodkindDao;
	BdyMakeareaDao makeareaDao;
	BdyOrderDao orderDao;
	BdyOrderlistDao orderlistDao;
	BdyPriorityDao priorityDao;
	BdySetDao setDao;
	BdySetdetailDao setdetailDao;
	BdyTableDao tableDao;
	BdyMainkindDao mainkindDao;
	BdyBilldetailDao billdetailDao;
	BdyNewsDao newsDao;
	BdyBookingDao bookingDao;
	
	
	public void setBilldetailDao(BdyBilldetailDao billdetailDao) {
		this.billdetailDao = billdetailDao;
	}
	public void setNewsDao(BdyNewsDao newsDao) {
		this.newsDao = newsDao;
	}
	public void setBookingDao(BdyBookingDao bookingDao) {
		this.bookingDao = bookingDao;
	}
	
	public BdyMainkindDao getMainkindDao() {
		return mainkindDao;
	}
	public void setMainkindDao(BdyMainkindDao mainkindDao) {
		this.mainkindDao = mainkindDao;
	}
	public BdyBillDao getBillDao() {
		return billDao;
	}
	public void setBillDao(BdyBillDao billDao) {
		this.billDao = billDao;
	}
	public BdyDiscountDao getDiscountDao() {
		return discountDao;
	}
	public void setDiscountDao(BdyDiscountDao discountDao) {
		this.discountDao = discountDao;
	}
	public BdyEmpDao getEmpDao() {
		return empDao;
	}
	public void setEmpDao(BdyEmpDao empDao) {
		this.empDao = empDao;
	}
	public BdyFloorDao getFloorDao() {
		return floorDao;
	}
	public void setFloorDao(BdyFloorDao floorDao) {
		this.floorDao = floorDao;
	}
	public BdyFoodDao getFoodDao() {
		return foodDao;
	}
	public void setFoodDao(BdyFoodDao foodDao) {
		this.foodDao = foodDao;
	}
	public BdyFoodkindDao getFoodkindDao() {
		return foodkindDao;
	}
	public void setFoodkindDao(BdyFoodkindDao foodkindDao) {
		this.foodkindDao = foodkindDao;
	}
	public BdyMakeareaDao getMakeareaDao() {
		return makeareaDao;
	}
	public void setMakeareaDao(BdyMakeareaDao makeareaDao) {
		this.makeareaDao = makeareaDao;
	}
	public BdyOrderDao getOrderDao() {
		return orderDao;
	}
	public void setOrderDao(BdyOrderDao orderDao) {
		this.orderDao = orderDao;
	}
	public BdyOrderlistDao getOrderlistDao() {
		return orderlistDao;
	}
	public void setOrderlistDao(BdyOrderlistDao orderlistDao) {
		this.orderlistDao = orderlistDao;
	}
	public BdyPriorityDao getPriorityDao() {
		return priorityDao;
	}
	public void setPriorityDao(BdyPriorityDao priorityDao) {
		this.priorityDao = priorityDao;
	}
	public BdySetDao getSetDao() {
		return setDao;
	}
	public void setSetDao(BdySetDao setDao) {
		this.setDao = setDao;
	}
	public BdySetdetailDao getSetdetailDao() {
		return setdetailDao;
	}
	public void setSetdetailDao(BdySetdetailDao setdetailDao) {
		this.setdetailDao = setdetailDao;
	}
	public BdyTableDao getTableDao() {
		return tableDao;
	}
	public void setTableDao(BdyTableDao tableDao) {
		this.tableDao = tableDao;
	}
	public ManageService(){}
	public List<BdyFood> getAllFood(){
		 return foodDao.getAllFood();
	}
	public List<BdySetdetail> getAllDetail(){
		return setdetailDao.getAllSetdetail();
	}

	public List<BdyDiscount> getAllDiscount(){
		return discountDao.getAllDiscount();
	}
	public int insertFood(String name,double price,int qty,String desc,int fkid){
		BdyFood food = new BdyFood();
		food.setName(name);
		food.setPrice(price);
		food.setQty(qty);
		food.setDescript(desc);
		BdyFoodkind foodkind=foodkindDao.getFoodkind(fkid);
		food.setBdyFoodkind(foodkind);	
		foodDao.update(food);
		
		int foodnum = foodDao.insert(food);
		return foodnum;
	}
	public List<BdyFood> updateFood(int fdid,String name,double price,int qty,String desc,int fkid){
		BdyFood food = new BdyFood();
		food.setFdId(fdid);
		food.setName(name);
		food.setPrice(price);
		food.setQty(qty);
		food.setDescript(desc);
		BdyFoodkind foodkind=foodkindDao.getFoodkind(fkid);
		food.setBdyFoodkind(foodkind);	
		foodDao.update(food);
		
		List<BdyFood> bean = null;
		bean = foodDao.getAllFood();
		return bean;
	}
	public void deleteFood(int id){
		foodDao.delete(id);
	}
	public List<BdyFoodkind> getAllFoodKind(){
		List<BdyFoodkind> resultFoodKind = foodkindDao.getAllFoodkind();
		return resultFoodKind;
		
	}
	public List<BdySet> getAllSet(){
		List<BdySet> resultSet = setDao.getAllSet();
		return resultSet;
	}
	public List<BdySetdetail> updateSet(int detailId,int setId,int fkId){
		BdySetdetail detail = new  BdySetdetail();
		detail.setSdId(detailId);
		BdySet set = setDao.getSet(setId);
		detail.setBdySet(set);
		BdyFoodkind fk = foodkindDao.getFoodkind(fkId);
		detail.setBdyFoodkind(fk);
		setdetailDao.update(detail);
		
		 List<BdySetdetail> bean = setdetailDao.getAllSetdetail();
		 return bean;
		
	}
	public void deleteSet(int detailId){
		setdetailDao.delete(detailId);
	}
	public int insertSet(int foodId,int setId){
		BdySetdetail setd = new BdySetdetail();
		BdyFoodkind fk = foodkindDao.getFoodkind(foodId);
		BdySet set = setDao.getSet(setId);
		setd.setBdyFoodkind(fk);
		setd.setBdySet(set);
		int setNum = setdetailDao.insert(setd);
		return setNum;
	}
}
