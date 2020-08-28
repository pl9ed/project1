package com.revature.services;

import java.util.HashSet;
import java.util.Set;

import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.data.User;

public class EmployeeServices {
	private User u;
	private ReimbDAO dao;
	
	public EmployeeServices() {
		dao = new ReimbDAO();
	}
	
	public EmployeeServices(ReimbDAO dao) {
		this.dao = dao;
	}
	
	public boolean submitReimb(Reimbursement r) {
		// TODO impl
		return false;
	}
	
	public Set<Reimbursement> myReimbursements() {
		// TODO imp
		Set<Reimbursement> ret = new HashSet<Reimbursement>();
		return ret;
	}

}
