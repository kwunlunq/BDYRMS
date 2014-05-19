package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.directwebremoting.ServerContext;
import org.directwebremoting.ServerContextFactory;

import utils.Service;
@WebServlet("/TestServlet")
public class TestServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {

 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   Service service = new Service();
   
   ServerContext wctx = ServerContextFactory.get(this.getServletContext());
   for (int i = 0; i < 10; i++) {
     service.update();
     System.out.println(i);
     try {
       Thread.sleep(1000);
     }
     catch (InterruptedException e) {
       e.printStackTrace();
     }
   }
   PrintWriter writer = response.getWriter();
   writer.println("Done");
   writer.close();

 }}