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
@WebFilter("/order/*")
public class OrderFilter implements Filter {
    public OrderFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		System.out.println("login filter");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = ((HttpServletRequest) request).getSession();
		BdyEmp emp = (BdyEmp) session.getAttribute("empData");
		BdyPriority prio = emp.getBdyPriority();
		if (prio.getPrio() == 1 || prio.getPrio() == 2) {
			chain.doFilter(req, response);
		} else {
			response.sendRedirect("/index.jsp");
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
