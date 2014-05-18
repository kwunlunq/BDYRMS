package com.bdy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.model.dao.BdyNewsDao;
import com.bdy.model.BdyNews;

@WebServlet("/news")
public class NewsServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private WebApplicationContext context;
	
	@Override
	public void init() throws ServletException {
		context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String action = req.getParameter("act");
		BdyNewsDao newsDao = (BdyNewsDao) context.getBean("BdyNewsDao");
		
		switch(action){
			case "postNews":
				String newsTitle = req.getParameter("newsTitle");
				String newsType = req.getParameter("newsType");
				String newsContent = req.getParameter("newsContent");
				String newsPostname = req.getParameter("newsPostname");
				//DateFormat fullFormat24 = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss.sss" );
				Calendar calendarTW = new GregorianCalendar( Locale.TAIWAN );

				BdyNews news = new BdyNews();
				news.setNewsTitle(newsTitle);
				news.setNewsContent(newsContent);
				news.setNewsPostdate(calendarTW.getTime());
				news.setNewsPostname(newsPostname);
				news.setNewsType(newsType);
				newsDao.insert(news);
				System.out.println(news.getNewsPostname() +" at " + news.getNewsPostdate() + " psoted a news.");
				System.out.println("Title: "+news.getNewsTitle()+" Type: "+news.getNewsType());
				System.out.println("Content: "+ news.getNewsContent());
				//resp.sendRedirect("mainpage.jsp");
				break;
			case "getAllNews":
				List<BdyNews> newsList = newsDao.getAllNewsSortByDateDESC();
				JsonArrayBuilder newsBuilder = Json.createArrayBuilder();
				for(BdyNews newsBean : newsList){
					newsBuilder.add(Json.createObjectBuilder()
							.add("newsId",newsBean.getNewsId())
							.add("newsTitle", newsBean.getNewsTitle())
							.add("newsType", newsBean.getNewsType())
							.add("newsContent", newsBean.getNewsContent())
							.add("newsPostdate", newsBean.getNewsPostdate().toString())
							.add("newsPostname", newsBean.getNewsPostname())
							.build());
				}
				out.write(newsBuilder.build().toString());
				break;
			case "delNews":
				int newsId = Integer.parseInt(req.getParameter("newsId"));
				newsDao.deleteNewsById(newsId);
				break;
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
}
