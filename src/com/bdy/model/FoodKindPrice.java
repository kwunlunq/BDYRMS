package com.bdy.model;

public class FoodKindPrice {
	Integer sdId;
	Integer fkId;
	Double pirce;
	BdySetdetail setDetail;
	
	
	public BdySetdetail getSetDetail() {
		return setDetail;
	}
	public void setSetDetail(BdySetdetail setDetail) {
		this.setDetail = setDetail;
	}
	public Integer getSdId() {
		return sdId;
	}
	public void setSdId(Integer sdId) {
		this.sdId = sdId;
	}
	public Integer getFkId() {
		return fkId;
	}
	public void setFkId(Integer fkId) {
		this.fkId = fkId;
	}
	public Double getPirce() {
		return pirce;
	}
	public void setPirce(Double pirce) {
		this.pirce = pirce;
	}
	
}
