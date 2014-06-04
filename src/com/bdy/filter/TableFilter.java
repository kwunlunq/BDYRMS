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
@WebFilter("/table/*")
public class TableFilter implements Filter {
    public TableFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
//		System.out.println(" -- table filter -- ");
		HttpServletRequest request = (HttpServletRequest) req;
//		System.out.println(request.getRequestURI());
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpSession session = request.getSession();
		
		String contextPath = request.getContextPath();
		BdyEmp emp = (BdyEmp) session.getAttribute("empData");
		if (emp == null) {
			response.sendRedirect(contextPath+"/index.jsp");
		} else {
			int prio = emp.getBdyPriority().getPrio();
			if ("/table/tableset.jsp".equals(request.getServletPath())) {
				if (prio == 1) {
					chain.doFilter(req, resp);
				} else {
					response.sendRedirect(contextPath+"/index.jsp");
					System.out.println("權限不足");
				}
			} else if ("/table/opentable.jsp".equals(request.getServletPath())) {
				if (prio == 1 || prio == 2) {
					chain.doFilter(req, resp);
				} else {
					response.sendRedirect(contextPath+"/index.jsp");
					System.out.println("權限不足");
				}
			} else {
				chain.doFilter(req, resp);
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
