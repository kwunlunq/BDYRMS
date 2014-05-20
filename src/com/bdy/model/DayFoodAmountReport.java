package com.bdy.model;

public class DayFoodAmountReport {
	private int amount;
	private String name;
	public DayFoodAmountReport() {
	}
	public DayFoodAmountReport(int amount,String name) {
		this.amount = amount;
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
