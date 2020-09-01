package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.DAO.ReimbDAO;
import com.revature.data.User;
import com.revature.services.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;


public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 4326640463364320856L;
	private static ObjectMapper om = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
		request.getRequestDispatcher("/static/index.html").forward(request, resp);
	}

//	@Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		
//		if(username == null || password == null || username.equals("") || password.equals("")) {
//			response.setStatus(400);
//			// Bad Request
//			return;
//		}
//		
//		LoginService ls = new LoginService(new ReimbDAO("public","TEST IP"));
//		int id = ls.login(username, password);
//		
//		PrintWriter pw = response.getWriter();
//		
//		// test 
//		HttpSession session = request.getSession();
//		session.setAttribute("currentUser", id);
//		ReimbDAO dao = new ReimbDAO("public", "TEST IP");
//		//ReimbDAO dao = new ReimbDAO();
//		User u = dao.getUser(id);
//		
//		pw.println(om.writeValueAsString(u));
//		response.setStatus(200);
////		response.sendRedirect("/EmployeePortal.html");
//		request.getRequestDispatcher("/EmployeePortal").forward(request, response);
//	}
}
