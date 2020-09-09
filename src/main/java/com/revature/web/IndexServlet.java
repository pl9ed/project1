package com.revature.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 4326640463364320856L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException, ServletException {
		request.getRequestDispatcher("/static/index.html").forward(request, resp);
	}

}
