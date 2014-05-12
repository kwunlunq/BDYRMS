package com.bdy.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com.bdy.model.BdyBill;
import com.bdy.model.dao.BdyBillDao;
import com.bdy.model.dao.BdyDiscountDao;
import com.bdy.model.dao.BdyEmpDao;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyFoodDao;
import com.bdy.model.dao.BdyFoodkindDao;
import com.bdy.model.dao.BdyMainkindDao;
import com.bdy.model.dao.BdyMakeareaDao;
import com.bdy.model.dao.BdyOrderDao;
import com.bdy.model.dao.BdyOrderlistDao;
import com.bdy.model.dao.BdyPriorityDao;
import com.bdy.model.dao.BdySetDao;
import com.bdy.model.dao.BdySetdetailDao;
import com.bdy.model.dao.BdyTableDao;

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
	
	public ReportService(){}
	
	public List<BdyBill> getDayRevenueDetails(java.util.Date date){
		
		List<BdyBill> beans = new ArrayList<BdyBill>();
		beans = billDao.getDayRevenueDetailsDB(date);
		return beans;
		
	}
	

	@SuppressWarnings("unchecked")
	public JSONObject getSingleDayJSON(java.util.Date date) {
	
		/*
		  	目標 :
		 	{
				"來客數":["", "", "", "", "", ..., ""], 
 		   		"平均消費金額":["", "", "", "", "", ..., ""]
 		   	}
		*/
	
		List<BdyBill> beans = new ArrayList<BdyBill>();
		beans = billDao.getDayRevenueDetailsDB(date);
		double sumPrice8 = 0;
		int sumCustNum8 = 0;
		double sumPrice9 = 0;
		int sumCustNum9 = 0;
		double sumPrice10 = 0;
		int sumCustNum10 = 0;
		double sumPrice11 = 0;
		int sumCustNum11 = 0;
		double sumPrice12 = 0;
		int sumCustNum12 = 0;
		double sumPrice13 = 0;
		int sumCustNum13 = 0;
		double sumPrice14 = 0;
		int sumCustNum14 = 0;
		double sumPrice15 = 0;
		int sumCustNum15 = 0;
		double sumPrice16 = 0;
		int sumCustNum16 = 0;
		double sumPrice17 = 0;
		int sumCustNum17 = 0;
		double sumPrice18 = 0;
		int sumCustNum18 = 0;
		double sumPrice19 = 0;
		int sumCustNum19 = 0;
		double sumPrice20 = 0;
		int sumCustNum20 = 0;
		double sumPrice21 = 0;
		int sumCustNum21 = 0;
		double sumPrice22 = 0;
		int sumCustNum22 = 0;
		double sumPrice23 = 0;
		int sumCustNum23 = 0;
		
		for (BdyBill bills : beans) {
			
			java.util.Date billEndDate = bills.getEndDate();
			java.util.Calendar calendar = java.util.Calendar
					.getInstance();
			calendar.setTime(billEndDate);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			if(hour == 8) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice8 = sumPrice8 + billPrice;
				sumCustNum8 = sumCustNum8 + billCustNum;
			}else if(hour == 9) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice9 = sumPrice9 + billPrice;
				sumCustNum9 = sumCustNum9 + billCustNum;
			} else if (hour == 10) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice10 = sumPrice10 + billPrice;
				sumCustNum10 = sumCustNum10 + billCustNum;
			} else if (hour == 11) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice11 = sumPrice11 + billPrice;
				sumCustNum11 = sumCustNum11 + billCustNum;
			} else if (hour == 12) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice12 = sumPrice12 + billPrice;
				sumCustNum12 = sumCustNum12 + billCustNum;
			} else if (hour == 13) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice13 = sumPrice13 + billPrice;
				sumCustNum13 = sumCustNum13 + billCustNum;
			} else if (hour == 14) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice14 = sumPrice14 + billPrice;
				sumCustNum14 = sumCustNum14 + billCustNum;
			} else if (hour == 15) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice15 = sumPrice15 + billPrice;
				sumCustNum15 = sumCustNum15 + billCustNum;
			} else if (hour == 16) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice16 = sumPrice16 + billPrice;
				sumCustNum16 = sumCustNum16 + billCustNum;
			} else if (hour == 17) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice17 = sumPrice17 + billPrice;
				sumCustNum17 = sumCustNum17 + billCustNum;
			} else if (hour == 18) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice18 = sumPrice18 + billPrice;
				sumCustNum18 = sumCustNum18 + billCustNum;
			} else if (hour == 19) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice19 = sumPrice19 + billPrice;
				sumCustNum19 = sumCustNum19 + billCustNum;
			} else if (hour == 20) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice20 = sumPrice20 + billPrice;
				sumCustNum20 = sumCustNum20 + billCustNum;
			} else if (hour == 21) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice21 = sumPrice21 + billPrice;
				sumCustNum21 = sumCustNum21 + billCustNum;
			} else if (hour == 22) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice22 = sumPrice22 + billPrice;
				sumCustNum22 = sumCustNum22 + billCustNum;
			} else if (hour == 23) {
				double billPrice = bills.getPrice();
				int billCustNum = bills.getCustNum();
				sumPrice23 = sumPrice23 + billPrice;
				sumCustNum23 = sumCustNum23 + billCustNum;
			}
		}
		
		JSONArray list1 = new JSONArray();
		JSONArray list2 = new JSONArray();
		
		list1.add(sumCustNum8);
		list1.add(sumCustNum9);
		list1.add(sumCustNum10);
		list1.add(sumCustNum11);
		list1.add(sumCustNum12);
		list1.add(sumCustNum13);
		list1.add(sumCustNum14);
		list1.add(sumCustNum15);
		list1.add(sumCustNum16);
		list1.add(sumCustNum17);
		list1.add(sumCustNum18);
		list1.add(sumCustNum19);
		list1.add(sumCustNum20);
		list1.add(sumCustNum21);
		list1.add(sumCustNum22);
		list1.add(sumCustNum23);
		list2.add(sumPrice8/sumCustNum8);
		list2.add(sumPrice9/sumCustNum9);
		list2.add(sumPrice10/sumCustNum10);
		list2.add(sumPrice11/sumCustNum11);
		list2.add(sumPrice12/sumCustNum12);
		list2.add(sumPrice13/sumCustNum13);
		list2.add(sumPrice14/sumCustNum14);
		list2.add(sumPrice15/sumCustNum15);
		list2.add(sumPrice16/sumCustNum16);
		list2.add(sumPrice17/sumCustNum17);
		list2.add(sumPrice18/sumCustNum18);
		list2.add(sumPrice19/sumCustNum19);
		list2.add(sumPrice20/sumCustNum20);
		list2.add(sumPrice21/sumCustNum21);
		list2.add(sumPrice22/sumCustNum22);
		list2.add(sumPrice23/sumCustNum23);
		
		JSONObject obj = new JSONObject();
		
		obj.put("sumCustNumByhour", list1);
		obj.put("avgPriceDividedByCustNumByhour", list2);
		
		return obj;
	}
}
