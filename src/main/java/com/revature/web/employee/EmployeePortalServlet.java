package com.revature.web.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.services.EmployeeServices;

public class EmployeePortalServlet extends HttpServlet {
	private static final long serialVersionUID = 6154472678247648982L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// null check
		if (request.getParameter("id") != null) {
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
			
		} else {
			response.sendRedirect("./index");
		}
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/static/EmployeePortal.html").forward(request, response);
	}
	
	
}
