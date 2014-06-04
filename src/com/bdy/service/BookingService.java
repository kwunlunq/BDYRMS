package com.bdy.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.bdy.model.BdyBooking;
import com.bdy.model.BdyTable;
import com.bdy.model.dao.BdyBookingDao;
import com.bdy.model.dao.BdyFloorDao;
import com.bdy.model.dao.BdyTableDao;

public class BookingService {

	BdyBookingDao bookingDao;
	BdyTableDao tableDao;
	BdyFloorDao floorDao;

	public BdyFloorDao getFloorDao() {
		return floorDao;
	}

	public void setFloorDao(BdyFloorDao floorDao) {
		this.floorDao = floorDao;
	}

	public BdyTableDao getTableDao() {
		return tableDao;
	}

	public void setTableDao(BdyTableDao tableDao) {
		this.tableDao = tableDao;
	}

	public BdyBookingDao getBookingDao() {
		return bookingDao;
	}

	public void setBookingDao(BdyBookingDao bookingDao) {
		this.bookingDao = bookingDao;
	}
	
	public JsonObject getBookingByDate(Date date){
		System.out.println("TSS Strat to getBookingByDate(Calendar date)");
		Date startDate = startDate(date);
		Date endDate = endDate(date);
		List<BdyBooking> bookingList = bookingDao.getBookingByDate(startDate,endDate);
		JsonArrayBuilder bookingBuilder = Json.createArrayBuilder();
		for(BdyBooking bookingBean : bookingList){
			String tbName = "";
			String tbFloorName = "";
			if(bookingBean.getTbId() != -1){
				BdyTable tableBean = tableDao.getTableById(bookingBean.getTbId());
				tbName = tableBean.getName();
				tbFloorName = tableBean.getBdyFloor().getName();
			}
			bookingBuilder.add(Json.createObjectBuilder()
					.add("bkId", bookingBean.getBkId())
					.add("phone", bookingBean.getBkPhone())
					.add("empId", bookingBean.getEmpId())
					.add("tbId", bookingBean.getTbId())
					.add("tbName", tbName)
					.add("tbFloorName", tbFloorName)
					.add("custNum", bookingBean.getBkNumber())
					.add("state", bookingBean.getBkState())
					.add("content", bookingBean.getBkContent())
					.add("eatDate", bookingBean.getBkEatdate().toString())
					.add("bookingDate", bookingBean.getBkOrderdate().toString())
					.add("name", bookingBean.getBkName())
				);
		}
		JsonObjectBuilder dataBuilder = Json.createObjectBuilder();
		dataBuilder.add("data", bookingBuilder);
        System.out.println("TSE Return data :");
        JsonObject data = dataBuilder.build();
        System.out.println("TSE " +  data.toString());
        return data;
	}
	
	public void insertBooking(String bkName ,String bkPhone,String empId,int tbId,int bkNumber,int bkState,String bkContent,Date bkEatdate,Date bkOrderdate){
		System.out.println("TSS Strat to insertBooking(BdyBooking booking)");
		BdyBooking bookingBean = new BdyBooking();
		bookingBean.setBkName(bkName);
		bookingBean.setBkPhone(bkPhone);
		bookingBean.setEmpId(empId);
		bookingBean.setBkNumber(bkNumber);
		bookingBean.setTbId(tbId);
		bookingBean.setBkState(bkState);
		bookingBean.setBkContent(bkContent);
		bookingBean.setBkEatdate(bkEatdate);
		bookingBean.setBkOrderdate(bkOrderdate);
		System.out.println("TSS [ booking = "+bookingBean.toString()+" ]");
		bookingDao.insert(bookingBean);
		System.out.println("TSE insertBooking(BdyBooking booking) done");
	}
	
	public Date startDate(Date myDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	public Date endDate(Date d){
		if(d==null)return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.HOUR_OF_DAY,23);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
}
