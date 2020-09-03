package com.revature.web.FM;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.DAO.ReimbDAO;
import com.revature.services.LoginService;

public class FMLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 4667449093533831211L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/static/FMLogin.html").forward(request,response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		if (username == null || password == null || username.equals("") || password.equals("")) {
			response.setStatus(400);
			request.getRequestDispatcher("/InvalidLogin").forward(request, response);
		}

		LoginService ls = new LoginService(new ReimbDAO());
		int id = ls.FMlogin(username, password);

		if (id > 0) {
			// make cookie
			HttpSession session = request.getSession();

			session.setAttribute("currentUser", username);
			request.getRequestDispatcher("/FMPortal").forward(request, response);
		} else if (id == -2) {
			response.setStatus(401);
			request.getRequestDispatcher("/401").forward(request, response);
		} else {
			response.setStatus(200);
			request.getRequestDispatcher("/InvalidLogin").forward(request, response);
		}

	}

}
