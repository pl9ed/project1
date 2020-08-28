package com.revature.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;

public class FMServices {
	private ReimbDAO dao;
	private static Logger devlog = Logger.getLogger(FMServices.class);
	private static Logger infolog = Logger.getLogger("infoLog");
	
	public FMServices() {
		dao = new ReimbDAO();
	}
	
	public FMServices(ReimbDAO dao) {
		this.dao = dao;
	}
	
	public boolean approveReimb(int id) {
		return dao.processReimbursement(id, true);
	}
	
	public boolean denyReimb(int id) {
		return dao.processReimbursement(id, false);
	}
	
	public Set<Reimbursement> filterBy(String s) {
		// TODO implement
		Set<Reimbursement> ret = new HashSet<Reimbursement>(); 
		return ret;
	}
	
}
