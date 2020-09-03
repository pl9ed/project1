package com.revature.web.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.data.User;
import com.revature.services.EmployeeServices;

public class EmployeePortalServlet extends HttpServlet {
	private ObjectMapper om = new ObjectMapper();

	private static final long serialVersionUID = 6154472678247648982L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		response.setStatus(401);
//		request.getRequestDispatcher("/401").forward(request, response);
		
		int id = Integer.parseInt(request.getParameter("id"));
		ReimbDAO dao = new ReimbDAO();
		EmployeeServices es = new EmployeeServices(id, dao);
		Set<Reimbursement> ret = es.myReimbursements();
		
		PrintWriter pw = response.getWriter();
		
		String json = "";
		// don't need entire object for the menu, only get entire object incl image when we request it
		for (Reimbursement r : ret) {
			json += "{"
					+ "\"REIMB_ID\":" + r.getREIMB_ID()
					+ ", \"AMOUNT\":" + r.getAMOUNT()
					+ ", \"SUBMITTED\":\"" + r.getSUBMITTED() + "\""
					+ ", \"STATUS\":\"" + r.getStatus() + "\"}@@@";
		}
		
		pw.print(json);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/static/EmployeePortal.html").forward(request, response);
	}
	
	
}
