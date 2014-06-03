package com.bdy.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.bdy.model.BdyBooking;
import com.bdy.model.dao.BdyBookingDao;

public class BookingService {

	BdyBookingDao bookingDao;

	public BdyBookingDao getBookingDao() {
		return bookingDao;
	}

	public void setBookingDao(BdyBookingDao bookingDao) {
		this.bookingDao = bookingDao;
	}
	
	public JsonObject getBookingByDate(Date date){
		System.out.println("TSS Strat to getBookingByDate(Calendar date)");
		List<BdyBooking> bookingList = bookingDao.getBookingByDate(date);
		JsonArrayBuilder bookingBuilder = Json.createArrayBuilder();
		for(BdyBooking bookingBean : bookingList){
			bookingBuilder.add(Json.createObjectBuilder()
					.add("bkId", bookingBean.getBkId())
					.add("phone", bookingBean.getBkPhone())
					.add("empId", bookingBean.getEmpId())
					.add("tbId", bookingBean.getTbId())
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
}
