package com.revature.web;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.services.EmployeeServices;

public class ApplyServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(ApplyServlet.class);
	private ObjectMapper om = new ObjectMapper();
	private static Reimbursement r;
	private static EmployeeServices es;

	private static final long serialVersionUID = -5106547048754670654L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/static/apply.html").forward(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		BufferedReader reader = request.getReader();
		r = om.readValue(reader, Reimbursement.class);

		// needs amount, submitted, receipt, author, type_id, status_id
		// contains amount, type_ID, receipt, and author
		// submitted and status id needed:

		LocalDate submitted = LocalDate.now();

		r.setSUBMITTED((submitted));
		r.setSTATUS_ID(0);// new reimbursements always pending

		es = new EmployeeServices(r.getAUTHOR(), new ReimbDAO());

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		if(!ServletFileUpload.isMultipartContent(request)) {
			response.setStatus(400);
			request.getRequestDispatcher("/static/EmployeePortal.html").forward(request, response);
		}

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(new ServletRequestContext(request));

			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				r.setFileName(item.getName());

				// write to stream
				InputStream is = item.getInputStream();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();

				int n;
				byte[] data = new byte[1024];
				while ((n = is.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, n);
				}
				buffer.flush();

				r.setRECEIPT(buffer.toByteArray());
				buffer.close();
				es.submitReimb(r);
			}
		} catch (FileUploadException e) {
			log.trace(this, e);
		}
	}
}
