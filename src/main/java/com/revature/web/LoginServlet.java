package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ReimbDAO;
import com.revature.data.User;
import com.revature.services.LoginService;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 7912117368411759508L;
	private static ObjectMapper om = new ObjectMapper();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		String username = (String) request.getParameter("username").toUpperCase();
		ReimbDAO dao = new ReimbDAO("public","TEST IP");
		User u = dao.getUser(username);
		
		PrintWriter pw = response.getWriter();
		pw.println(om.writeValueAsString(u));
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(username == null || password == null || username.equals("") || password.equals("")) {
			response.setStatus(400);
			request.getRequestDispatcher("/InvalidLogin").forward(request, response);
		}
		
		LoginService ls = new LoginService(new ReimbDAO("public","TEST IP"));
		int id = ls.login(username, password);
		
		if (id > 0) {
			request.getRequestDispatcher("/EmployeePortal").forward(request, response);
		} else {
			response.setStatus(200);
			request.getRequestDispatcher("/InvalidLogin").forward(request, response);
		}
		
	}
}
