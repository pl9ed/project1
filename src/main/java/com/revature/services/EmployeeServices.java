package com.revature.services;

import java.util.Set;

import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.exceptions.SQLSecurityException;

public class EmployeeServices {
	private int user;
	private ReimbDAO dao;

	public EmployeeServices(int user) {
		dao = new ReimbDAO();
		this.user = user;
	}
	
	public EmployeeServices(int user, ReimbDAO dao) {
		this.user = user;
		this.dao = dao;
	}
	
	public boolean submitReimb(Reimbursement r) {
		return dao.createReimbursement(r);
	}
	
	public Set<Reimbursement> myReimbursements() {
		try {
			return dao.filterByIntField("AUTHOR", user);
		} catch (SQLSecurityException e) {
			// don't need to do anything, exception is logged in DAO
			return null;
		}
	}

}
