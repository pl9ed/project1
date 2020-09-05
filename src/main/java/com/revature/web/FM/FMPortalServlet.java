package com.revature.web.FM;

import java.io.BufferedReader;
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
import com.revature.data.Application;
import com.revature.data.Reimbursement;
import com.revature.services.FMServices;

@WebServlet(urlPatterns="/FMPortal")
public class FMPortalServlet extends HttpServlet {
	private Application application;
	private static final long serialVersionUID = -5569151079124455619L;
	private static ObjectMapper om = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// null check
		if (request.getParameter("id") != null) {
			int id = Integer.parseInt(request.getParameter("id"));
			ReimbDAO dao = new ReimbDAO();
			FMServices fs = new FMServices(id, dao);
			Set<Reimbursement> ret = fs.getAllReimbursements();
			
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
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/static/FMPortal.html").forward(request,response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BufferedReader reader = request.getReader();
		application = om.readValue(reader, Application.class);
		FMServices fm = new FMServices(application.getRESOLVER(),new ReimbDAO());
		boolean success = false;
		success = fm.processReimb(application.getREIMB_ID(), application.getIsApproved());
				
		PrintWriter pw = response.getWriter();
		if (success) {
			pw.println("SUCCESS");
		} else {
			pw.print("");
		}
		
//		request.getRequestDispatcher("/FMPortal").forward(request, response);
		
	}
}
