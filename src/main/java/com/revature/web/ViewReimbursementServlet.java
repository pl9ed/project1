package com.revature.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.services.EmployeeServices;

@WebServlet(urlPatterns="/view")
public class ViewReimbursementServlet extends HttpServlet {
	private ObjectMapper om = new ObjectMapper();
	private Reimbursement r;
	private ReimbDAO dao;
	private static Logger devlog = Logger.getLogger(ViewReimbursementServlet.class);
	private String imgPath = "C:\\Users\\pl9ed\\scoop\\apps\\sts\\current";
	private static final long serialVersionUID = 604233471689635408L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {				
		try {
			int reimb_ID = Integer.parseInt(request.getParameter("reimb_ID"));
			dao = new ReimbDAO();
			r = dao.getReimbursement(reimb_ID);
			
			
			// write to file for view in JS
//			File f = new File(r.getFileName());
//			try (FileOutputStream fos = new FileOutputStream(f)) {
//				fos.write(r.getRECEIPT());
//				System.out.println("Wrote to " + System.getProperty("user.dir"));
//			} catch (IOException e) {
//				devlog.trace(this,e);
//			}
			
			PrintWriter pw = response.getWriter();
			pw.println(om.writeValueAsString(r));
		} catch (NumberFormatException e) {
			System.out.println("error");
			devlog.error(request.getParameter("reimb_ID") + " could not be parsed to an integer");
		}
	}
}
