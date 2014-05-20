package com.bdy.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bdy.model.BdyBill;
import com.bdy.model.BdyFood;
import com.bdy.model.BdyMainkind;
import com.bdy.model.MonthReport;
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
import com.bdy.model.dao.MonthReportDaoJdbc;

public class ReportService {

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
	MonthReportDaoJdbc monthReportDao;
	
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

	public void setBillDao(BdyBillDao billDao) {
		this.billDao = billDao;
	}

	public void setDiscountDao(BdyDiscountDao discountDao) {
		this.discountDao = discountDao;
	}

	public void setEmpDao(BdyEmpDao empDao) {
		this.empDao = empDao;
	}

	public void setFloorDao(BdyFloorDao floorDao) {
		this.floorDao = floorDao;
	}

	public void setFoodDao(BdyFoodDao foodDao) {
		this.foodDao = foodDao;
	}

	public void setFoodkindDao(BdyFoodkindDao foodkindDao) {
		this.foodkindDao = foodkindDao;
	}

	public void setMakeareaDao(BdyMakeareaDao makeareaDao) {
		this.makeareaDao = makeareaDao;
	}

	public void setOrderDao(BdyOrderDao orderDao) {
		this.orderDao = orderDao;
	}

	public void setOrderlistDao(BdyOrderlistDao orderlistDao) {
		this.orderlistDao = orderlistDao;
	}

	public void setPriorityDao(BdyPriorityDao priorityDao) {
		this.priorityDao = priorityDao;
	}

	public void setSetDao(BdySetDao setDao) {
		this.setDao = setDao;
	}

	public void setSetdetailDao(BdySetdetailDao setdetailDao) {
		this.setdetailDao = setdetailDao;
	}

	public void setTableDao(BdyTableDao tableDao) {
		this.tableDao = tableDao;
	}
	
	public void setMonthReportDao(MonthReportDaoJdbc monthReportDao) {
		this.monthReportDao = monthReportDao;
	}

	public ReportService() {
	}

	public List<BdyBill> getDayRevenueDetails(java.util.Date date) {

		List<BdyBill> beans = new ArrayList<BdyBill>();
		beans = billDao.getDayRevenueDetailsDB(date);
		return beans;

	}
		
	@SuppressWarnings("unchecked")
	public JSONObject getSingleDayJSON(java.util.Date date) {
		
		JSONObject obj = new JSONObject();
		/*
		 *目標 :
		 *	{
		 *		"主餐名稱":["牛排", "披薩", ...,"燉飯"],
		 *		"主餐數量":["", "", ..., ""]
 		 *  }
 		 *  
		 */
		
		
		
		List<BdyMainkind> mainkindBeans = new ArrayList<BdyMainkind>();;
		mainkindBeans = mainkindDao.getAllMainkind();
		JSONArray mainkindList = new JSONArray();
		for (BdyMainkind mainkind : mainkindBeans) {
			mainkindList.add(mainkind.getName());
		}
		obj.put("mainkindName", mainkindList);
		
		/*
		 *目標 :
		 *	{
		 *		"開胃菜":["", "", ...,""],
		 *		"主餐數量":["", "", ..., ""]
 		 *  }
 		 *  
		 */
		
		List<BdyFood> foodkindBeans1 = new ArrayList<BdyFood>();;
		foodkindBeans1 = foodDao.getFoodsByFkId(1);
		JSONArray foodKindList1 = new JSONArray();
		for (BdyFood foodkind1 : foodkindBeans1) {
			foodKindList1.add(foodkind1.getName());
		}
		obj.put("saladName", foodKindList1);
		
		List<BdyFood> foodkindBeans2 = new ArrayList<BdyFood>();;
		foodkindBeans2 = foodDao.getFoodsByFkId(2);
		JSONArray foodKindList2 = new JSONArray();
		for (BdyFood foodkind2 : foodkindBeans2) {
			foodKindList2.add(foodkind2.getName());
		}
		obj.put("appetizerName", foodKindList2);
		
		List<BdyFood> foodkindBeans3 = new ArrayList<BdyFood>();;
		foodkindBeans3 = foodDao.getFoodsByFkId(3);
		JSONArray foodKindList3 = new JSONArray();
		for (BdyFood foodkind3 : foodkindBeans3) {
			foodKindList3.add(foodkind3.getName());
		}
		obj.put("soupName", foodKindList3);
		
		/*
		 *目標 : 
		 *	{ 
		 * 		"來客數":["", "", "", "", "", ..., ""], 
		 *		"平均消費金額":["", "", "", "", ..., ""] 
		 *	}
		 */		
		
		List<BdyBill> billBeans = new ArrayList<BdyBill>();
		billBeans = billDao.getDayRevenueDetailsDB(date);

		
		JSONArray list1 = new JSONArray();
		JSONArray list2 = new JSONArray();
		
		DecimalFormat df = new DecimalFormat(".00");
		
		for (int i=8;i<24;i++){
			double billPrice=0;
			int billCustNum=0;
			for (BdyBill bill : billBeans) {
				java.util.Date billEndDate = bill.getEndDate();
				java.util.Calendar calendar = java.util.Calendar.getInstance();
				calendar.setTime(billEndDate);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				if(i==hour){
					billPrice = billPrice+bill.getFinPrice();
					billCustNum = billCustNum+bill.getCustNum();
				}
			}
			list1.add(billPrice);
			if(billPrice==0){
				list2.add(0);
			}else{
				list2.add(Double.parseDouble(df.format(billPrice/billCustNum)));
			}
		}

		obj.put("sumCustNumByhour", list1);
		obj.put("avgPriceDividedByCustNumByhour", list2);
		System.out.println(obj);
		return obj;
	}
	
	public List<MonthReport> getMonthRevenueDetails(int year,int month) throws NamingException {

		List<MonthReport> beans = new ArrayList<MonthReport>();
		beans = monthReportDao.getMonthRevenueDetailsDB(year,month);
		return beans;

	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getSingleMonthJSON(int year,int month){
		
		List<MonthReport> beans = new ArrayList<MonthReport>();
		beans = monthReportDao.getMonthRevenueDetailsDB(year,month);
		
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month-1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		/*
		 *目標 :
		 *	{
		 *		"單日來客數":["", "", ...,""],
		 *		"單日營收":["", "", ..., ""],
		 *		"日期":[1,2,...,]
 		 *  }
 		 *  
		 */
		
		JSONArray dayInMonthList = new JSONArray();
		JSONArray dayTatolCustNumList = new JSONArray();
		JSONArray dayTatolFinPriceList = new JSONArray();
		
		for(int i=1;i<=maxDay;i++){
			for(MonthReport monthreport : beans){
				if(monthreport.getDayInMonth()==i){
					dayInMonthList.add(monthreport.getDayInMonth());
					dayTatolCustNumList.add(monthreport.getDayTatolCustNum());
					dayTatolFinPriceList.add(monthreport.getDayTatolFinPrice());
				}
			}
			if(dayInMonthList.size()!=i){
				dayInMonthList.add(i);
				dayTatolCustNumList.add(0);
				dayTatolFinPriceList.add(0);
			}
		}
		
		JSONObject obj = new JSONObject();

		obj.put("dayInMonth", dayInMonthList);
		obj.put("dayTatolCustNum", dayTatolCustNumList);
		obj.put("dayTatolFinPrice", dayTatolFinPriceList);
		
		System.out.println(obj);
		return obj;
	}
}
