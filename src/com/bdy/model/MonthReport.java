package com.bdy.model;


public class MonthReport {
	private int dayTatolCustNum;
	private double dayTatolFinPrice;
	private int dayInMonth;
	
	public MonthReport() {
	}
	public MonthReport(int dayTatolCustNum,double dayTatolFinPrice,int dayInMonth) {
		this.dayTatolCustNum = dayTatolCustNum;
		this.dayTatolFinPrice = dayTatolFinPrice;
		this.dayInMonth = dayInMonth;
	}
	public int getDayTatolCustNum() {
		return dayTatolCustNum;
	}
	public void setDayTatolCustNum(int dayTatolCustNum) {
		this.dayTatolCustNum = dayTatolCustNum;
	}
	public double getDayTatolFinPrice() {
		return dayTatolFinPrice;
	}
	public void setDayTatolFinPrice(double dayTatolFinPrice) {
		this.dayTatolFinPrice = dayTatolFinPrice;
	}
	public int getDayInMonth() {
		return dayInMonth;
	}
	public void setDayInMonth(int dayInMonth) {
		this.dayInMonth = dayInMonth;
	}
}
