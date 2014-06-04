package com.bdy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bdy.model.BdyEmp;
import com.bdy.model.BdyPriority;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/booking/*")
public class BookingFilter implements Filter {
    public BookingFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//		System.out.println(" -- booking filter -- ");
		HttpServletRequest request = (HttpServletRequest) req;
//		System.out.println(request.getRequestURL());
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		
		String contextPath = request.getContextPath();
		BdyEmp emp = (BdyEmp) session.getAttribute("empData");
		if (emp == null) {
			response.sendRedirect(contextPath+"/index.jsp");
		} else {
			int prio = emp.getBdyPriority().getPrio();
			if (prio == 1 || prio == 2) {
				chain.doFilter(req, resp);
			} else {
				response.sendRedirect(contextPath+"/index.jsp");
				System.out.println("權限不足");
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
