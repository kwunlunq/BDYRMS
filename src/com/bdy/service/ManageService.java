package com.bdy.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bdy.model.BdyBill;
import com.bdy.model.BdyBilldetail;
import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyEmp;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyMainkind;
import com.bdy.model.BdyMakearea;
import com.bdy.model.BdyOrder;
import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdyPriority;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.model.BdyTable;
import com.bdy.model.CheckOut;
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
	public int insertFood(String name,double price,int qty,String desc,int fkid,int foodMK){
		BdyFood food = new BdyFood();
		food.setName(name);
		food.setPrice(price);
		food.setQty(qty);
		food.setDescript(desc);
		BdyFoodkind foodkind=foodkindDao.getFoodkind(fkid);
		food.setBdyFoodkind(foodkind);	
		BdyMainkind ma = mainkindDao.getMainkind(foodMK);
		food.setBdyMainkind(ma);
		foodDao.update(food);
		
		int foodnum = foodDao.insert(food);
		return foodnum;
	}
	public List<BdyFood> updateFood(int fdid,String name,double price,int qty,String desc,int fkid,int fmk){
		BdyFood food = new BdyFood();
		food.setFdId(fdid);
		food.setName(name);
		food.setPrice(price);
		food.setQty(qty);
		food.setDescript(desc);
		BdyFoodkind foodkind=foodkindDao.getFoodkind(fkid);
		food.setBdyFoodkind(foodkind);	
		if(fmk!=0){
		BdyMainkind ma = mainkindDao.getMainkind(fmk);
		food.setBdyMainkind(ma);
		}
		foodDao.update(food);
		
		List<BdyFood> bean = null;
		bean = foodDao.getAllFood();
		return bean;
	}
	public int deleteFood(int id){
		int foodState = foodDao.delete(id);
		return foodState;
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
	public int insertSet(int foodId,int setId,Double setDetailPrice){
		BdySetdetail setd = new BdySetdetail();
		BdyFoodkind fk = foodkindDao.getFoodkind(foodId);
		BdySet set = setDao.getSet(setId);
		setd.setBdyFoodkind(fk);
		setd.setBdySet(set);
		setd.setPrice(setDetailPrice);
		int setNum = setdetailDao.insert(setd);
		return setNum;
	}
	public int deleteFoodKind(int fkId){
		int  foodKindState= foodkindDao.delete(fkId);
		return foodKindState;
	}
	
	
	public List<BdyFoodkind> updateFoodKind(int fkId,String fkname,Double fkperiod,int fkma,int fkseq){
		BdyFoodkind foodkind = new BdyFoodkind();
		foodkind.setFkId(fkId);
		foodkind.setName(fkname);
		foodkind.setPeriod(fkperiod);
		foodkind.setSeq(fkseq);
		BdyMakearea ma = new BdyMakearea();
		ma.setMaId(fkma);
		foodkind.setBdyMakearea(ma);
		foodkindDao.update(foodkind);
		List<BdyFoodkind> bean = foodkindDao.getAllFoodkind();
		return bean;
	}
	public int insertFoodKind(BdyFoodkind fk){
		int fkState = foodkindDao.insert(fk);
		return fkState;
	}
	
	//----------------------Emp--------------------------
	public List<BdyEmp> getAllEmps(){
		return empDao.getAllEmp();
	}
	public List<BdyPriority> getAllPri(){
		return priorityDao.getAllProiority();
	}
	public  List<BdyMakearea> getAllMakeArea(){
		 List<BdyMakearea> allma = makeareaDao.getAllMakeArea();
		 return allma;		
	}
	public BdyEmp findEmpById(String empId){
		return empDao.getEmpById(empId);
	}
	public int updateEmp(BdyEmp emp,String empId){
		return empDao.updateEmp(emp, empId);
	}
	public int updateEmps(BdyEmp emp){
		return empDao.update(emp);
	}
	public int insertEmp(BdyEmp emp){
		return empDao.insert(emp);
	}
	public List<BdyMainkind> getAllMainKind(){
		List<BdyMainkind> ma = mainkindDao.getAllMainkind();
		return ma;
	}
//------------------------ManageInside----------------------------------
public List<BdyDiscount> insideInsertDiscount(String name,Double price){
	BdyDiscount dis = new BdyDiscount();
	dis.setName(name);
	dis.setDisPrice(price);
	discountDao.insert(dis);
	List<BdyDiscount> bean = discountDao.getAllDiscount();
	return bean;
}
public int deleteDisc(int disId){
	int disState = discountDao.delete(disId);
	return disState;
}

public List<BdySet> insideInsertSet(String name , Double price ){
	BdySet set = new BdySet();
	set.setName(name);
	set.setPrice(price);
	setDao.insert(set);
	List<BdySet> bean = setDao.getAllSet();
	return bean;
}
public int deleteSetInside(int setId){
	int setState = setDao.delete(setId);
	return setState;
}
public List<BdyMakearea> insideInsertMA(String name){
	BdyMakearea ma = new BdyMakearea();
	ma.setName(name);
	makeareaDao.insert(ma);
	List<BdyMakearea> bean = makeareaDao.getAllMakeArea();
	return bean;
}
public int deleteMA(int maId){
	int maState = makeareaDao.deleteMakeareaById(maId);
	return maState;
}
public List<BdyMainkind> insideInsertMK(String name){
	BdyMainkind mk = new BdyMainkind();
	mk.setName(name);
	mainkindDao.insert(mk);
	List<BdyMainkind> bean = mainkindDao.getAllMainkind();
	return bean;
}
public int deleteMK(int mkId){
	int mkState = mainkindDao.delete(mkId);
	return mkState;
}
//---------------------------CheckOut-------------------------------------------
public  List<BdyTable> getUseTable(){
	List<BdyTable> usingTabls = new ArrayList<BdyTable>();
	List<BdyTable> alltable = tableDao.getAllTable();
	for(BdyTable temp:alltable){
		if(temp.getTableState().equals(new Integer(1))){
			usingTabls.add(temp);
		}
	}
	return usingTabls;
}
public Set<BdyOrder> getOrdersByTableId(int tableId){
	Set<BdyOrder> orders = new HashSet<BdyOrder>();
	List<BdyOrder> allOrder = orderDao.getAllOrder();
	for(BdyOrder temp:allOrder){
		if(temp.getBdyTable().getTbId()==tableId && temp.getIsCheckout().equals(new Integer(0))){
			orders.add(temp);
		}
	}
	return orders;
	
}
	public Double getPrice(Set<BdyOrder> orders){
		Double singlePrice=0.0;
		Double mealPrice=0.0;
		for(BdyOrder order:orders){
		Set<BdyOrderlist> orderlists=order.getBdyOrderlists();
		for(BdyOrderlist orderlist:orderlists){
			if(orderlist.getBdySet()==null){
				singlePrice +=orderlist.getBdyFood().getPrice();
			}
		 }
		}
		for(BdyOrder order:orders){
			Set<BdyOrderlist> orderlists = order.getBdyOrderlists();
			for(BdyOrderlist  orderlist:orderlists){
				if(orderlist.getBdyFood().getBdyMainkind()!=null && orderlist.getBdySet()!=null){
					
					mealPrice +=orderlist.getBdyFood().getPrice()+orderlist.getBdySet().getPrice(); 
				}
			}
		}
		return singlePrice+mealPrice;
		
	}
	
	
	public void insertBill(CheckOut check){
		BdyBill bill = new BdyBill();
		bill.setEndDate(check.getEndDate());
		bill.setCustNum(check.getCustNum());
		bill.setBdyDiscount(check.getDiscount());
		bill.setBdyEmp(check.getEmp());
		bill.setPrice(check.getPrice());
		bill.setFinPrice((double)check.getFinalPrice());
		billDao.insert(bill);
		
		int index=0;
		for(BdyBill temp:billDao.getAllBill()){
			if(temp.getBillId()>index){
				index=temp.getBillId();
			}
		}
		BdyBill tempBill = billDao.getBill(index);
		for(BdyOrder orders:check.getOrders()){
			BdyBilldetail detail = new BdyBilldetail();
			detail.setBdyBill(tempBill);
			detail.setBdyOrder(orders);		
			billdetailDao.insert(detail);
		}
		
		BdyTable table = tableDao.getTableById(check.getTabId());
		table.setTableState(0);
		tableDao.updateTable(table);
		
		for(BdyOrder order:check.getOrders()){
			int ordId=order.getOdId();
			BdyOrder temp = orderDao.getOrder(ordId);
			temp.setIsCheckout(1);
			orderDao.upDateOrder(temp);
		}
		
	}
	public BdyDiscount getDiscountById(int discountId){
		return discountDao.getDiscount(discountId);
	}
	
	
}











