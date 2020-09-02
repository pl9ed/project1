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

}
