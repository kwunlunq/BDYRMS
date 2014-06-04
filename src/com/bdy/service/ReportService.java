package com.bdy.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import javax.naming.NamingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.bdy.model.BdyBillHistory;
import com.bdy.model.BdyOrderlistReport;
import com.bdy.model.FoodAmountReport;
import com.bdy.model.MonthReport;
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
import com.bdy.model.dao.ReportDaoJdbc;

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
	ReportDaoJdbc reportDao;
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

	public void setReportDao(ReportDaoJdbc reportDao) {
		this.reportDao = reportDao;
	}

	public ReportService() {
	}

	/*
	 * --------------------------日報表專區-------------------------------
	 */

	public List<BdyBillHistory> getDayRevenue(java.util.Date date) {
		List<BdyBillHistory> billBeans = new ArrayList<BdyBillHistory>();
		billBeans = billHistoryDao.getDayBillHistoryList(date);
		return billBeans;
	}

	public List<BdyOrderlistReport> getMainkindNameByDate(java.util.Date date) {
		List<BdyOrderlistReport> mainKindNames = new ArrayList<BdyOrderlistReport>();
		mainKindNames = reportDao.getDayMainKindName(date);
		return mainKindNames;
	}
	
	public List<BdyOrderlistReport> getFoodkindNameByDate(java.util.Date date) {
		List<BdyOrderlistReport> foodKindNames = new ArrayList<BdyOrderlistReport>();
		foodKindNames = reportDao.getDayFoodKindName(date);
		return foodKindNames;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getSingleDayJSON(java.util.Date date) {

		JSONObject obj = new JSONObject();

		/*
		 * 目標 : { "來客數":["", "", "", "", "", ..., ""], "平均消費金額":["", "", "", "",
		 * ..., ""] }
		 */

		List<BdyBillHistory> billBeans = new ArrayList<BdyBillHistory>();
		billBeans = billHistoryDao.getDayBillHistoryList(date);
		JSONArray list1 = new JSONArray();
		JSONArray list2 = new JSONArray();
		DecimalFormat df = new DecimalFormat(".00");
		for (int i = 8; i < 24; i++) {
			double billPrice = 0;
			int billCustNum = 0;
			for (BdyBillHistory bill : billBeans) {
				java.util.Date billEndDate = bill.getEndDate();
				java.util.Calendar calendar = java.util.Calendar.getInstance();
				calendar.setTime(billEndDate);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				if (i == hour) {
					billPrice = billPrice + bill.getFinPrice();
					billCustNum = billCustNum + bill.getCustNum();
				}
			}
			list1.add(billCustNum);
			if (billPrice == 0) {
				list2.add(0);
			} else {
				list2.add(Double.parseDouble(df.format(billPrice / billCustNum)));
			}
		}
		obj.put("sumCustNumByhour", list1);
		obj.put("avgPriceDividedByCustNumByhour", list2);
		
		/*
		 * {"mainkindName":[{"foodName":[ {"name":[...],"amount":[...]},
		 * {"name":[...],"amount":[...]}, . . . {"name":[...],"amount":[...]} ]
		 * }] }
		 */

		List<BdyOrderlistReport> mainKindNames = new ArrayList<BdyOrderlistReport>();
		mainKindNames = reportDao.getDayMainKindName(date);
		JSONArray mainkindNameList = new JSONArray();
		JSONArray mainkindNames = new JSONArray();
		JSONObject mainkindNameobj = new JSONObject();
		JSONArray mainkindList = new JSONArray();
		for (BdyOrderlistReport mainKindName : mainKindNames) {
			List<FoodAmountReport> foodAmounts = new ArrayList<FoodAmountReport>();
			foodAmounts = reportDao.getDayMainAmount(
					mainKindName.getMainkindName(), date);
			JSONObject mainkindNameObj = new JSONObject();
			JSONArray mainNameList = new JSONArray();
			JSONArray mainAmountList = new JSONArray();
			for (FoodAmountReport foodAmount : foodAmounts) {
				mainNameList.add(foodAmount.getFoodName());
				mainAmountList.add(foodAmount.getAmount());
			}
			mainkindNames.add(mainKindName.getMainkindName());
			mainkindNameObj.put("foodName", mainNameList);
			mainkindNameObj.put("foodAmount", mainAmountList);
			mainkindList.add(mainkindNameObj);

		}
		mainkindNameobj.put("mainkindName", mainkindList);
		mainkindNameList.add(mainkindNameobj);
		obj.put("mainkind", mainkindNameList);
		obj.put("mainkindNames", mainkindNames);
		
		/*
		 * {"foodkindName":[{"foodName":[ {"name":[...],"amount":[...]},
		 * {"name":[...],"amount":[...]}, . . . {"name":[...],"amount":[...]} ]
		 * }] }
		 */

		List<BdyOrderlistReport> foodKindNames = new ArrayList<BdyOrderlistReport>();
		foodKindNames = reportDao.getDayFoodKindName(date);
		JSONArray foodkindNameList = new JSONArray();
		JSONArray foodkindNames = new JSONArray();
		JSONObject foodkindNameobj = new JSONObject();
		JSONArray foodkindList = new JSONArray();
		for (BdyOrderlistReport foodKindName : foodKindNames) {

			List<FoodAmountReport> foodAmounts = new ArrayList<FoodAmountReport>();
			foodAmounts = reportDao.getDayFoodAmount(
					foodKindName.getFoodkindName(), date);
			JSONObject foodkindNameObj = new JSONObject();
			JSONArray foodNameList = new JSONArray();
			JSONArray foodAmountList = new JSONArray();
			for (FoodAmountReport foodAmount : foodAmounts) {
				foodNameList.add(foodAmount.getFoodName());
				foodAmountList.add(foodAmount.getAmount());
			}
			foodkindNames.add(foodKindName.getFoodkindName());
			foodkindNameObj.put("foodName", foodNameList);
			foodkindNameObj.put("foodAmount", foodAmountList);
			foodkindList.add(foodkindNameObj);

		}
		foodkindNameobj.put("foodkindName", foodkindList);
		foodkindNameList.add(foodkindNameobj);
		obj.put("foodkind", foodkindNameList);
		obj.put("foodkindNames", foodkindNames);
		
		
		//System.out.println(obj);
		return obj;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getOrderDetailByBillIdJSON(int billId) {
		JSONObject obj = new JSONObject();
		JSONArray odId = new JSONArray();
		JSONArray foodName = new JSONArray();
		JSONArray foodPrice = new JSONArray();
		JSONArray foodSet = new JSONArray();
		JSONArray foodAddMoney = new JSONArray();

		List<BdyOrderlistReport> orderlistReports = new ArrayList<BdyOrderlistReport>();
		orderlistReports = orderlistReportDao
				.getOrderlistReportByBillId(billId);
		for (BdyOrderlistReport orderlistReport : orderlistReports) {
			odId.add(orderlistReport.getOdId());
			foodName.add(orderlistReport.getFoodName());
			foodPrice.add(orderlistReport.getFoodPrice());
			if (orderlistReport.getSetName() != null) {
				foodSet.add(orderlistReport.getSetName());
			} else {
				foodSet.add("單點");
			}
			foodAddMoney.add(orderlistReport.getAddmoney());
		}
		obj.put("orderId", odId);
		obj.put("foodName", foodName);
		obj.put("foodPrice", foodPrice);
		obj.put("foodSet", foodSet);
		obj.put("foodAddMoney", foodAddMoney);
		// System.out.println(obj+"---------------");
		return obj;
	}

	/*
	 * -------------------------月報表專區 --------------------------------
	 */

	public List<MonthReport> getMonthRevenue(int year, int month)
			throws NamingException {
		List<MonthReport> beans = new ArrayList<MonthReport>();
		beans = reportDao.getMonthBillHistoryList(year, month);
		return beans;
	}
	
	public List<BdyOrderlistReport> getMainkindNameByMonth(int year, int month) {
		List<BdyOrderlistReport> mainKindNames = new ArrayList<BdyOrderlistReport>();
		mainKindNames = reportDao.getMonthMainKindName(year, month);
		return mainKindNames;
	}
	
	public List<BdyOrderlistReport> getFoodkindNameByMonth(int year, int month) {
		List<BdyOrderlistReport> foodKindNames = new ArrayList<BdyOrderlistReport>();
		foodKindNames = reportDao.getMonthFoodKindName(year, month);
		return foodKindNames;
	}

	@SuppressWarnings("unchecked")
	public JSONObject getSingleMonthJSON(int year, int month) {
		JSONObject obj = new JSONObject();

		/*
		 * 目標 : { "單日來客數":["", "", ...,""], "單日營收":["", "", ..., ""],
		 * "日期":[1,2,...,] }
		 */

		List<MonthReport> beans = new ArrayList<MonthReport>();
		beans = reportDao.getMonthBillHistoryList(year, month);

		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		JSONArray dayInMonthList = new JSONArray();
		JSONArray dayTatolCustNumList = new JSONArray();
		JSONArray dayTatolFinPriceList = new JSONArray();

		for (int i = 1; i <= maxDay; i++) {
			for (MonthReport monthreport : beans) {
				if (monthreport.getDayInMonth() == i) {
					dayInMonthList.add(monthreport.getDayInMonth());
					dayTatolCustNumList.add(monthreport.getDayTatolCustNum());
					dayTatolFinPriceList.add(monthreport.getDayTatolFinPrice());
				}
			}
			if (dayInMonthList.size() != i) {
				dayInMonthList.add(i);
				dayTatolCustNumList.add(0);
				dayTatolFinPriceList.add(0);
			}
		}
		obj.put("dayInMonth", dayInMonthList);
		obj.put("dayTatolCustNum", dayTatolCustNumList);
		obj.put("dayTatolFinPrice", dayTatolFinPriceList);
		
		/*
		 * {"mainkindName":[{"foodName":[ {"name":[...],"amount":[...]},
		 * {"name":[...],"amount":[...]}, . . . {"name":[...],"amount":[...]} ]
		 * }] }
		 */

		List<BdyOrderlistReport> mainKindNames = new ArrayList<BdyOrderlistReport>();
		mainKindNames = reportDao.getMonthMainKindName(year, month);
		JSONArray mainkindNameList = new JSONArray();
		JSONArray mainkindNames = new JSONArray();
		JSONObject mainkindNameobj = new JSONObject();
		JSONArray mainkindList = new JSONArray();
		for (BdyOrderlistReport mainKindName : mainKindNames) {
			List<FoodAmountReport> foodAmounts = new ArrayList<FoodAmountReport>();
			foodAmounts = reportDao.getMonthMainAmount(
					mainKindName.getMainkindName(), year, month);
			JSONObject mainkindNameObj = new JSONObject();
			JSONArray mainNameList = new JSONArray();
			JSONArray mainAmountList = new JSONArray();
			for (FoodAmountReport foodAmount : foodAmounts) {
				mainNameList.add(foodAmount.getFoodName());
				mainAmountList.add(foodAmount.getAmount());
			}
			mainkindNames.add(mainKindName.getMainkindName());
			mainkindNameObj.put("foodName", mainNameList);
			mainkindNameObj.put("foodAmount", mainAmountList);
			mainkindList.add(mainkindNameObj);

		}
		mainkindNameobj.put("mainkindName", mainkindList);
		mainkindNameList.add(mainkindNameobj);
		obj.put("mainkind", mainkindNameList);
		obj.put("mainkindNames", mainkindNames);
		
		/*
		 * {"foodkindName":[{"foodName":[ {"name":[...],"amount":[...]},
		 * {"name":[...],"amount":[...]}, . . . {"name":[...],"amount":[...]} ]
		 * }] }
		 */

		List<BdyOrderlistReport> foodKindNames = new ArrayList<BdyOrderlistReport>();
		foodKindNames = reportDao.getMonthFoodKindName(year, month);
		JSONArray foodkindNameList = new JSONArray();
		JSONArray foodkindNames = new JSONArray();
		JSONObject foodkindNameobj = new JSONObject();
		JSONArray foodkindList = new JSONArray();
		for (BdyOrderlistReport foodKindName : foodKindNames) {
			List<FoodAmountReport> foodAmounts = new ArrayList<FoodAmountReport>();
			foodAmounts = reportDao.getMonthFoodAmount(foodKindName.getFoodkindName(),year, month);
			JSONObject foodkindNameObj = new JSONObject();
			JSONArray foodNameList = new JSONArray();
			JSONArray foodAmountList = new JSONArray();
			for (FoodAmountReport foodAmount : foodAmounts) {
				foodNameList.add(foodAmount.getFoodName());
				foodAmountList.add(foodAmount.getAmount());
			}
			foodkindNames.add(foodKindName.getFoodkindName());
			foodkindNameObj.put("foodName", foodNameList);
			foodkindNameObj.put("foodAmount", foodAmountList);
			foodkindList.add(foodkindNameObj);
		}
		foodkindNameobj.put("foodkindName", foodkindList);
		foodkindNameList.add(foodkindNameobj);
		obj.put("foodkind", foodkindNameList);
		obj.put("foodkindNames", foodkindNames);
		System.out.println(obj);
		return obj;
	}

}
