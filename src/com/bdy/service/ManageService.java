package com.bdy.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.hibernate.sql.Insert;

import com.bdy.model.BdyBill;
import com.bdy.model.BdyBillHistory;
import com.bdy.model.BdyBilldetail;
import com.bdy.model.BdyDiscount;
import com.bdy.model.BdyEmp;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyFoodkind;
import com.bdy.model.BdyMainkind;
import com.bdy.model.BdyMakearea;
import com.bdy.model.BdyOrder;
import com.bdy.model.BdyOrderlist;
import com.bdy.model.BdyOrderlistReport;
import com.bdy.model.BdyPriority;
import com.bdy.model.BdySet;
import com.bdy.model.BdySetdetail;
import com.bdy.model.BdyTable;
import com.bdy.model.CheckOut;
import com.bdy.model.FoodKindPrice;
import com.bdy.model.dao.BdyBillDao;
import com.bdy.model.dao.BdyBillHistoryDao;
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
import com.bdy.model.dao.BdyOrderlistReportDao;
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
	

	BdyBillHistoryDao billHistoryDao;
	BdyOrderlistReportDao orderlistReportDao;
	
	public void setBillHistoryDao(BdyBillHistoryDao billHistoryDao) {
		this.billHistoryDao = billHistoryDao;
	}
	public void setOrderlistReportDao(BdyOrderlistReportDao orderlistReportDao) {
		this.orderlistReportDao = orderlistReportDao;
	}
	
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
	public List<BdyFood> insertFood(String name,double price,int qty,String desc,int fkid,int foodMK){
		BdyFood food = new BdyFood();
		List<BdyFood> listFood= foodDao.getAllFood();
		
		
		food.setName(name);
		food.setPrice(price);
		food.setQty(qty);
		food.setDescript(desc);
		BdyFoodkind foodkind=foodkindDao.getFoodkind(fkid);
		food.setBdyFoodkind(foodkind);	
		BdyMainkind ma = mainkindDao.getMainkind(foodMK);
		food.setBdyMainkind(ma);
		foodDao.insert(food);
		Deque<BdyFood> foods = new LinkedList<BdyFood>(listFood);
		foods.addFirst(food);
		List<BdyFood> tempfoods = new ArrayList<BdyFood>(foods);
		
		 
		return tempfoods;
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
		List<BdyFood> bean = foodDao.getAllFood();
		
		LinkedList<BdyFood> listfood =new LinkedList<BdyFood>(bean);
		System.out.println("First size="+listfood.size());
		Iterator<BdyFood> it =  listfood.iterator();
		while(it.hasNext()){
			if(it.next().getFdId()==foodDao.getFood(fdid).getFdId()){
				it.remove();
			}
		}
		
		System.out.println("Final size="+listfood.size());
		listfood.addFirst(food);
		List<BdyFood> list = new ArrayList<>(listfood);
		
		return list;
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
	public void deleteSetDetail(int detailId){
		
		BdySet set = setDao.getSet(detailId);
		Set<BdySetdetail> setDetails = set.getBdySetdetails();
		for(BdySetdetail temp:setDetails){
			setdetailDao.delete(temp.getSdId());
		}

		
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
	public List<BdyEmp> getResignEmps(){
		List<BdyEmp> emps= empDao.getAllEmp();
		List<BdyEmp> resignEmps = new ArrayList<BdyEmp>();
		for(BdyEmp temp :emps){
			if(temp.getResign()==1){
				resignEmps.add(temp);
			}
		}
		return resignEmps;
	}
	
	public List<BdyEmp> getNotResignEmps(){
		List<BdyEmp> emps= empDao.getAllEmp();
		List<BdyEmp> notresignEmps = new ArrayList<BdyEmp>();
		for(BdyEmp temp :emps){
			if(temp.getResign()==0){
				notresignEmps.add(temp);
			}
		}
		return notresignEmps;
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
public void updateMA(int maId,String name){
	BdyMakearea ma = new BdyMakearea();
	ma.setMaId(maId);
	ma.setName(name);
	makeareaDao.update(ma);
}
//---------------------------CheckOut-------------------------------------------

public Map<BdySet, List<List<?>>> sortSetDetailMap(Set<BdyOrder> orders){
	Map<BdySet, List<List<?>>> sortDetailMap = new TreeMap<BdySet, List<List<?>>>(new Comparator<BdySet>() {

		@Override
		public int compare(BdySet o1, BdySet o2) {
			return new Integer(o1.getSetId()).compareTo(new Integer(o2.getSetId()));
		}
	});
	for(BdyOrder order:orders){//-----主餐
		Set<BdyOrderlist> orderlists = order.getBdyOrderlists();
		for(BdyOrderlist orderlist:orderlists){
			if(orderlist.getBdyFood().getBdyMainkind()!=null && orderlist.getBdySet()!=null){//-----找到唯一價錢(主餐+Setid)
				if(!sortDetailMap.containsKey(orderlist.getBdySet())){//----不包含
				List<List<?>> foodDetails = new LinkedList<List<?>>();
				List<BdyFood> mainFoods = new ArrayList<BdyFood>();
				List<BdyOrderlist> notMainFoods = new ArrayList<BdyOrderlist>();
				mainFoods.add(orderlist.getBdyFood());
				foodDetails.add(0,mainFoods);
				foodDetails.add(1,notMainFoods);
				sortDetailMap.put(orderlist.getBdySet(), foodDetails);
			}else{
				List<List<?>> foodDetails=(List<List<?>>)sortDetailMap.get(orderlist.getBdySet());
				List<BdyFood> mainFoods=(List<BdyFood>) foodDetails.get(0);
				mainFoods.add(orderlist.getBdyFood());
				foodDetails.remove(0);
				foodDetails.add(0,mainFoods);
				sortDetailMap.put(orderlist.getBdySet(), foodDetails);
			}
				}
			}
		}	
	for(BdyOrder order:orders){
		Set<BdyOrderlist> orderlists = order.getBdyOrderlists();
		for(BdyOrderlist orderlist:orderlists){
			if(orderlist.getBdyFood().getBdyMainkind()==null && orderlist.getBdySet()!=null){
				List<List<?>> foodDetails=(List<List<?>>)sortDetailMap.get(orderlist.getBdySet());
				List<BdyOrderlist> notMainFoods=(List<BdyOrderlist>) foodDetails.get(1);
				notMainFoods.add(orderlist);
				Collections.sort(notMainFoods,new Comparator<BdyOrderlist>() {

					@Override
					public int compare(BdyOrderlist o1, BdyOrderlist o2) {
						
						return new Integer(o1.getBdyFoodkind().getFkId()).compareTo(new Integer(o2.getBdyFoodkind().getFkId()));
					}
				});				
				foodDetails.remove(1);
				foodDetails.add(1, notMainFoods);
				}
			}
		
		}
	
	return sortDetailMap;
	
	
}



public Map<BdySet, List<BdyFood>> sortSetMap(Set<BdyOrder> orders){
	Map<BdySet, List<BdyFood>> sortMap = new TreeMap<BdySet, List<BdyFood>>(new Comparator<BdySet>() {

		@Override
		public int compare(BdySet o1, BdySet o2) {
			return new Integer(o1.getSetId()).compareTo(new Integer(o2.getSetId()));
		}
	});
	
	for(BdyOrder order:orders){
		Set<BdyOrderlist> orderlists = order.getBdyOrderlists();
		for(BdyOrderlist orderlist:orderlists){
			if(orderlist.getBdyFood().getBdyMainkind()!=null && orderlist.getBdySet()!=null){//-----找到唯一價錢(主餐+Setid)
				if(!sortMap.containsKey(orderlist.getBdySet())){//----不包含
					List<BdyFood> mainFoods = new ArrayList<BdyFood>();
					mainFoods.add(orderlist.getBdyFood());
					sortMap.put(orderlist.getBdySet(), mainFoods);
				}else{//-----如果已包含
				List<BdyFood> mainFoods=sortMap.get(orderlist.getBdySet());
				mainFoods.add(orderlist.getBdyFood());
				sortMap.put(orderlist.getBdySet(), mainFoods);
				}
			}
		}
	}
	return sortMap;
}

public  List<BdyTable> getUseTable(){
	List<BdyTable> usingTabls = new ArrayList<BdyTable>();
	List<BdyTable> alltable = tableDao.getAllTable();
	//判斷是使用中的桌子才放到List
	for(BdyTable temp:alltable){
		if(temp.getTableState().equals(new Integer(2))){
			usingTabls.add(temp);
		}
	}
	return usingTabls;
}
public Set<BdyOrder> getOrdersByTableId(int tableId){
	Set<BdyOrder> orders = new TreeSet<BdyOrder>(new Comparator<BdyOrder>() {//----一訂單編號排序

		@Override
		public int compare(BdyOrder o1, BdyOrder o2) {
			
			return new Integer(o1.getOdId()).compareTo(new Integer(o2.getOdId()));
		}
		
	});//--end訂單編號排序
	
	List<BdyOrder> allOrder = orderDao.getAllOrder();//----取得所有資料庫orders

	for(BdyOrder temp:allOrder){//-------判斷此桌的訂單加入orders內
		if(temp.getBdyTable().getTbId()==tableId && temp.getIsCheckout().equals(new Integer(0))){
			orders.add(temp);
		}
	}
	
	
	for(BdyOrder temp:orders){//-----------------------排序此桌的order內裡面orderList(造orderlistId排序)
		
		Collections.sort(new ArrayList<BdyOrderlist>(temp.getBdyOrderlists()), new Comparator<BdyOrderlist>() {

			@Override
			public int compare(BdyOrderlist o1, BdyOrderlist o2) {
				// TODO Auto-generated method stub
				return new Integer(o1.getOdlistId()).compareTo(new Integer(o2.getOdlistId()));
			}
			
		});
	}
	
	return orders;
	
}

public BdyTable getOrderTableName(int tableId){
	BdyTable table = tableDao.getTableById(tableId);
	return table;
}
	public Double getPrice(Set<BdyOrder> orders){
		Double singlePrice=0.0;//----------------單點的錢
		Double mealPrice=0.0;//----------------套餐的錢
		Double differ= 0.0;/////-------------------補差額的總金額
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
				if(orderlist.getAddmoney()!=null&&orderlist.getAddmoney()!=0){
					differ+=orderlist.getAddmoney();
				}
				if(orderlist.getBdyFood().getBdyMainkind()!=null && orderlist.getBdySet()!=null){
					
					mealPrice +=orderlist.getBdyFood().getPrice()+orderlist.getBdySet().getPrice(); 
				}
			}
		}
		return singlePrice+mealPrice+differ;
		
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
		
		int index=0;//------找出最後一筆
		for(BdyBill temp:billDao.getAllBill()){
			if(temp.getBillId()>index){
				index=temp.getBillId();
			}
		}
		BdyBill tempBill = billDao.getBill(index);//----insert To BillDetail table
		for(BdyOrder orders:check.getOrders()){
			BdyBilldetail detail = new BdyBilldetail();
			detail.setBdyBill(tempBill);
			detail.setBdyOrder(orders);		
			billdetailDao.insert(detail);
		}
		
		BdyTable table = tableDao.getTableById(check.getTabId());//-----change table state to 0(no use)
		table.setTableState(0);
		tableDao.updateTable(table);
		
		for(BdyOrder order:check.getOrders()){//----------------------change orders that have checkout
			int ordId=order.getOdId();
			BdyOrder temp = orderDao.getOrder(ordId);
			temp.setIsCheckout(1);
			orderDao.upDateOrder(temp);
		}
		
		BdyBillHistory history = new BdyBillHistory();
		if(check.getDiscount()==null){
		history.setBillId(index);
		history.setEndDate(check.getEndDate());
		history.setCustNum(check.getCustNum());
		history.setDisName(null);
		history.setBillEmpId(check.getEmp().getEmpId());
		history.setBillEmpName(check.getEmp().getName());
		history.setPrice(check.getPrice());
		history.setFinPrice(new Double(check.getFinalPrice()));
		history.setDiscription(check.getDiscription());
		billHistoryDao.insert(history);
		}else{
			history.setBillId(index);
			history.setEndDate(check.getEndDate());
			history.setCustNum(check.getCustNum());
			history.setDisName(check.getDiscount().getName());
			history.setBillEmpId(check.getEmp().getEmpId());
			history.setBillEmpName(check.getEmp().getName());
			history.setPrice(check.getPrice());
			history.setFinPrice(new Double(check.getFinalPrice()));
			history.setDiscription(check.getDiscription());
			billHistoryDao.insert(history);
		}
		
		int lastHistoryId=0;//------找尋最後一筆billHistoryId
		for(BdyBillHistory findLastHistory:billHistoryDao.getAllBillHistory()){
			if(findLastHistory.getBillId()>lastHistoryId){
				lastHistoryId=findLastHistory.getBillId();
			}
		}
		
		
		for(BdyOrder order:check.getOrders()){
			Set<BdyOrderlist> orderlists=order.getBdyOrderlists();
			for(BdyOrderlist orderlist:orderlists){				
				if(orderlist.getBdySet()==null){//----單點
					if(orderlist.getBdyFood().getBdyMainkind()==null){//-----無製作區(非主餐)
					BdyOrderlistReport olReport = new BdyOrderlistReport();
					olReport.setOdlistId(orderlist.getOdlistId());
					olReport.setOdId(orderlist.getBdyOrder().getOdId());
					olReport.setOdEmpName(orderlist.getBdyOrder().getBdyEmp().getName());
					olReport.setOdEmpId(orderlist.getBdyOrder().getBdyEmp().getEmpId());
					olReport.setFoodName(orderlist.getBdyFood().getName());
					olReport.setFoodPrice(orderlist.getBdyFood().getPrice());
					olReport.setSetName(null);	
					olReport.setSetPrice(null);
					olReport.setFoodkindName(orderlist.getBdyFood().getBdyFoodkind().getName());
					olReport.setMainkindName(null);
					olReport.setAddmoney(orderlist.getAddmoney());
					olReport.setBdyBillHistory(billHistoryDao.getBillHistory(lastHistoryId));
					orderlistReportDao.insert(olReport);
					}else{//------有製作區(主餐)
						BdyOrderlistReport olReport = new BdyOrderlistReport();
						olReport.setOdlistId(orderlist.getOdlistId());
						olReport.setOdId(orderlist.getBdyOrder().getOdId());
						olReport.setOdEmpName(orderlist.getBdyOrder().getBdyEmp().getName());
						olReport.setOdEmpId(orderlist.getBdyOrder().getBdyEmp().getEmpId());
						olReport.setFoodName(orderlist.getBdyFood().getName());
						olReport.setFoodPrice(orderlist.getBdyFood().getPrice());
						olReport.setSetName(null);	
						olReport.setSetPrice(null);
						olReport.setFoodkindName(orderlist.getBdyFood().getBdyFoodkind().getName());
						olReport.setMainkindName(orderlist.getBdyFood().getBdyMainkind().getName());
						olReport.setAddmoney(orderlist.getAddmoney());
						olReport.setBdyBillHistory(billHistoryDao.getBillHistory(lastHistoryId));
						orderlistReportDao.insert(olReport);
					}
				}else{//-----套餐
					if(orderlist.getBdyFood().getBdyMainkind()==null){//----無製作區套餐
					BdyOrderlistReport olReport = new BdyOrderlistReport();
					olReport.setOdlistId(orderlist.getOdlistId());
					olReport.setOdId(orderlist.getBdyOrder().getOdId());
					olReport.setOdEmpName(orderlist.getBdyOrder().getBdyEmp().getName());
					olReport.setOdEmpId(orderlist.getBdyOrder().getBdyEmp().getEmpId());
					olReport.setFoodName(orderlist.getBdyFood().getName());
					olReport.setFoodPrice(orderlist.getBdyFood().getPrice());
					olReport.setSetName(orderlist.getBdySet().getName());	
					olReport.setSetPrice(orderlist.getBdySet().getPrice());
					olReport.setFoodkindName(orderlist.getBdyFood().getBdyFoodkind().getName());
					olReport.setMainkindName(null);
					olReport.setAddmoney(orderlist.getAddmoney());
					olReport.setBdyBillHistory(billHistoryDao.getBillHistory(lastHistoryId));
					orderlistReportDao.insert(olReport);
					}else{//------有製作區的套餐(主餐)
						BdyOrderlistReport olReport = new BdyOrderlistReport();
						olReport.setOdlistId(orderlist.getOdlistId());
						olReport.setOdId(orderlist.getBdyOrder().getOdId());
						olReport.setOdEmpName(orderlist.getBdyOrder().getBdyEmp().getName());
						olReport.setOdEmpId(orderlist.getBdyOrder().getBdyEmp().getEmpId());
						olReport.setFoodName(orderlist.getBdyFood().getName());
						olReport.setFoodPrice(orderlist.getBdyFood().getPrice());
						olReport.setSetName(orderlist.getBdySet().getName());	
						olReport.setSetPrice(orderlist.getBdySet().getPrice());
						olReport.setFoodkindName(orderlist.getBdyFood().getBdyFoodkind().getName());
						olReport.setMainkindName(orderlist.getBdyFood().getBdyMainkind().getName());
						olReport.setAddmoney(orderlist.getAddmoney());
						olReport.setBdyBillHistory(billHistoryDao.getBillHistory(lastHistoryId));	
						orderlistReportDao.insert(olReport);
					}
				}
				
			}
		}
		
	}
	public BdyDiscount getDiscountById(int discountId){
		return discountDao.getDiscount(discountId);
	}
	 
	
	public int getTodayBills(Date now) throws ParseException{
		int count=0;
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd");
		List<BdyBill> bills = billDao.getAllBill();
		for(BdyBill bill:bills){
			String billCheckTime;//--yyyy-MM-dd
			String nowString;
			billCheckTime = dFormat.format(bill.getEndDate());
			 nowString= dFormat.format(now);
			if( billCheckTime.equals(nowString)){
				count++;
			};
			
		}
		return count;
	}
	
//------------------------------SetMeal----------------------------------------------
	public List<BdyFoodkind> getAllFoodKindSetMeal(){
		List<BdyFoodkind> fk = foodkindDao.getAllFoodkind();
		return fk;
	}

	public void insertSet(String setName,Double setPrice){
		BdySet set = new BdySet();
		set.setName(setName);
		set.setPrice(setPrice);
		setDao.insert(set);		
	}
	
	public int findlastSetId(){
		int setId=0;
		for(BdySet temp:setDao.getAllSet()){
			if(temp.getSetId()>setId){
				setId =temp.getSetId(); 
			}
		}
		return setId;
	}
	
	public void insertSetDetail(List<FoodKindPrice> list){
		int lastSetId=findlastSetId();
		for(FoodKindPrice temp:list){
			BdySetdetail detail = new BdySetdetail();
			detail.setBdySet(setDao.getSet(lastSetId));
			detail.setBdyFoodkind(foodkindDao.getFoodkind(temp.getFkId()));
			detail.setPrice(temp.getPirce());
			setdetailDao.insert(detail);
		}
		BdySetdetail detail = new BdySetdetail();
		detail.setBdySet(setDao.getSet(lastSetId));
		detail.setBdyFoodkind(foodkindDao.getFoodkind(6));
		detail.setPrice(0.0);
		setdetailDao.insert(detail);
	}

//setmealupdate

public void updateSetMeal(int setId,String setName,Double setPrice){
	BdySet set = new BdySet();
	set.setSetId(setId);
	set.setName(setName);
	set.setPrice(setPrice);
	setDao.update(set);		
}

public void updateSetDetail(int setId,List<FoodKindPrice> list){
	//delete	
	//update
	for(FoodKindPrice temp:list){
		BdySetdetail detail = new BdySetdetail();
		detail.setBdySet(setDao.getSet(setId));
		detail.setBdyFoodkind(foodkindDao.getFoodkind(temp.getFkId()));
		detail.setPrice(temp.getPirce());
		setdetailDao.update(detail);
	}
	//insert
	
//	BdySet sets = setDao.getSet(setId);
//	Set<BdySetdetail> setDetails = sets.getBdySetdetails();
//	List<BdyFoodkind> foodKinds = new ArrayList<BdyFoodkind>();
//	for(BdySetdetail setDetail:setDetails){
//		foodKinds.add(setDetail.getBdyFoodkind());
//	}
//	
//	for(FoodKindPrice temp:list){
//		foodKinds.add(foodkindDao.getFoodkind(temp.getFkId()));
//	}
//	
//	for(BdyFoodkind kind:foodKinds){
//		if(kind.getFkId() !=6 && Collections.frequency(foodKinds, kind.getFkId())==1){
//			System.out.println(kind.getName() + Collections.frequency(foodKinds, kind.getFkId()));
//			for(FoodKindPrice tempInsert:list){
//			if(foodkindDao.getFoodkind(tempInsert.getFkId()).equals(kind)){
//				BdySetdetail detail = new BdySetdetail();
//				detail.setBdySet(setDao.getSet(setId));
//				detail.setBdyFoodkind(foodkindDao.getFoodkind(tempInsert.getFkId()));
//				detail.setPrice(tempInsert.getPirce());
//				setdetailDao.insert(detail);
//				}
//			}
//		}
//	}
	
//	boolean b=false;
//	for(FoodKindPrice temp:list){
//		for(BdyFoodkind kind:foodKinds){
//			if(foodkindDao.getFoodkind(temp.getFkId()).equals(kind)){
//				break;
//			}else{
//				continue;
//			}
//				
//		}
//		if(b==false){
//			continue;
//		}else{
//		BdySetdetail detail = new BdySetdetail();
//		detail.setBdySet(setDao.getSet(setId));
//		detail.setBdyFoodkind(foodkindDao.getFoodkind(temp.getFkId()));
//		detail.setPrice(temp.getPirce());
//		setdetailDao.insert(detail);
//		}
//	}
	List<BdySetdetail> setDetails = setdetailDao.getAllSetdetail();
	for(BdySetdetail sd:setDetails){
		for(FoodKindPrice temp:list){
		if(sd.getSdId()==setId&&sd.getBdyFoodkind().getFkId()==temp.getFkId()){
			break;
//			BdySetdetail detail = new BdySetdetail();
//			detail.setBdySet(setDao.getSet(setId));
//			detail.setBdyFoodkind(foodkindDao.getFoodkind(temp.getFkId()));
//			detail.setPrice(temp.getPirce());
//			setdetailDao.insert(detail);
			}
		
		}
	}
	
	
}






}




