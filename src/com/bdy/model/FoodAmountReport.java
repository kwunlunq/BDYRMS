package com.bdy.model;

public class FoodAmountReport {
	private int amount;
	private String foodName;
	private String foodKindName;
	public FoodAmountReport() {
	}
	public FoodAmountReport(int amount,String foodName,String foodKindName) {
		this.amount = amount;
		this.foodName = foodName;
		this.foodKindName=foodKindName;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getFoodKindName() {
		return foodKindName;
	}
	public void setFoodKindName(String foodKindName) {
		this.foodKindName = foodKindName;
	}
}
