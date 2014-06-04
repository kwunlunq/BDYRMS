package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.service.BookingService;


@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	BookingService BS;

	@Override
	public void init() throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(this.getServletContext());
		BS = (BookingService)context.getBean("BookingService");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		JsonReader jsonReader = Json.createReader(req.getReader());
		JsonObject object = jsonReader.readObject();
		String action = object.getString("act");
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		switch(action)
		{
			case "searchByDate":
				String input_date = object.getString("date");
				Date date = null;
				try {
					date = (Date)formatter.parse(input_date);
					JsonObject bookingArray = BS.getBookingByDate(date);
					out.write(bookingArray.toString());
				} catch (ParseException e) {
					System.out.println("input_date change to date fialed");
				} 
				break;
			case "insertBooking":
				String bkName = object.getString("name"); 
				String bkPhone = object.getString("phone"); 
				String empId = object.getString("empId");
				int tbId = Integer.parseInt(object.getString("tbId"));
				int bkNumber = Integer.parseInt(object.getString("number")); 
				int bkState = 0;
				String bkContent = object.getString("content");
				String eatDate_str = object.getString("eatDate");
				String hour = object.getString("hour");
				String min = object.getString("min");
				Date eatDate = null;
				try {
					eatDate = (Date)formatter.parse(eatDate_str);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar eatDateTime = new GregorianCalendar();
				eatDateTime.setTime(eatDate);
				eatDateTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour)); //anything 0 - 23
				eatDateTime.set(Calendar.MINUTE, Integer.parseInt(min));
				System.out.println(eatDateTime.getTime());
				Date bkEatdate = eatDateTime.getTime();
				Calendar c = new GregorianCalendar();
				Date bkOrderdate = c.getTime();
				BS.insertBooking(bkName, bkPhone, empId, tbId, bkNumber, bkState, bkContent, bkEatdate, bkOrderdate);
				break;
		}
	}
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
}
