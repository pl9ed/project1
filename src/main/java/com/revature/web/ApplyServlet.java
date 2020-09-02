package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.postgresql.util.ReaderInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.services.EmployeeServices;

public class ApplyServlet extends HttpServlet {
	private ObjectMapper om = new ObjectMapper();
	private Reimbursement r;
	private EmployeeServices es;

	private static final long serialVersionUID = -5106547048754670654L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.getRequestDispatcher("/static/apply.html").forward(request, response);
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BufferedReader reader = request.getReader();
		r = om.readValue(reader, Reimbursement.class);
		
		// needs amount, submitted, receipt, author, type_id, status_id
		// contains amount, type_ID, receipt, and author
		// submitted and status id needed:
		
		LocalDate submitted = LocalDate.now();
		
		r.setSUBMITTED((submitted));
		r.setSTATUS_ID(0);// new reimbursements always pending
		
		es = new EmployeeServices(r.getAUTHOR(), new ReimbDAO());
		System.out.println(r);
	}
	
	@Override 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Part filePart = request.getPart("file");
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
		
		r.setRECEIPT(filePart.getInputStream());
		r.setFileName(fileName);
		
		//BufferedReader reader = request.getReader();

		
		System.out.println(es.submitReimb(r));

	}
}
