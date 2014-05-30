package Filter;

import java.io.IOException;
import java.text.ParseException;

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

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bdy.service.ManageService;
@WebFilter(
		urlPatterns={"/*"}
		)
public class EncodingCheckFilter implements Filter {
	private FilterConfig config;
	private ManageService service;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {	
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		 request.setCharacterEncoding("utf-8");
		 response.setCharacterEncoding("utf-8");
		 HttpSession session = request.getSession();
		 int billsCount = 0;
		try {
			billsCount = service.getTodayBills(new java.util.Date(System.currentTimeMillis()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 session.setAttribute("billsCount", billsCount);
		 chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		service = (ManageService)context.getBean("ManageService");
		this.config = config;
	}
	
	@Override
	public void destroy() {
		

	}
}
