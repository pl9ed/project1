package com.revature.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.DAO.ReimbDAO;
import com.revature.services.LoginService;

@WebServlet(urlPatterns="/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 7912117368411759508L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String username = req.getParameter("username_input");
		String password = req.getParameter("password_input");
		
//		if(username == null || password == null || username.equals("") || password.equals("")) {
//			resp.setStatus(400);
//			// Bad Request
//			return;
//		}
		
		LoginService ls = new LoginService(new ReimbDAO("public","TEST IP"));
		int id = ls.login(username, password);
		
		PrintWriter pw = resp.getWriter();

		
		switch (id) {
		case (-1) :
			
			req.getRequestDispatcher("/index.html").forward(req,resp);
			break;
		case (0) :
			break;
		default :
			HttpSession session = req.getSession();
			
			
			break;
		}
	}
}
