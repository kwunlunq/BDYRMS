package com.bdy.model;

import java.util.Date;

public class BdyNews {

	private int newsId;
	private String newsTitle;
	private String newsContent;
	private Date newsPostdate;
	private String newsPostname;
	private String newsType;

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public BdyNews() {
	}

	public BdyNews(int newsId) {
		this.newsId = newsId;
	}

	public BdyNews(int newsId, String newsTitle,
			String newsContent, Date newsPostdate, String newsPostname ,String newsType) {
		this.newsId = newsId;
		this.newsTitle = newsTitle;
		this.newsContent = newsContent;
		this.newsPostdate = newsPostdate;
		this.newsPostname = newsPostname;
		this.newsType = newsType;
	}

	public int getNewsId() {
		return this.newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return this.newsTitle;
	}

	public void setNewsTitle(String newsTittle) {
		this.newsTitle = newsTittle;
	}

	public String getNewsContent() {
		return this.newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public Date getNewsPostdate() {
		return this.newsPostdate;
	}

	public void setNewsPostdate(Date newsPostdate) {
		this.newsPostdate = newsPostdate;
	}

	public String getNewsPostname() {
		return this.newsPostname;
	}

	public void setNewsPostname(String newsPostid) {
		this.newsPostname = newsPostid;
	}

}
