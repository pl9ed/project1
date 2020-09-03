package com.revature.web.FM;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.services.FMServices;

@WebServlet(urlPatterns="/FMPortal")
public class FMPortalServlet extends HttpServlet {
	
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
}
