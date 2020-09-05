package com.revature.services;

import java.time.LocalDate;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.data.User;
import com.revature.exceptions.SQLSecurityException;

public class FMServices {
	private int user;
	private ReimbDAO dao;
	private static Logger devlog = Logger.getLogger(FMServices.class);

	public FMServices(int user) {
		this.user = user;
		dao = new ReimbDAO();
	}

	public FMServices(int user, ReimbDAO dao) {
		this.user = user;
		this.dao = dao;
	}

	public boolean approveReimb(int id) {
		return dao.processReimbursement(id, user, 1);
	}

	public boolean denyReimb(int id) {
		return dao.processReimbursement(id, user, -1);
	}
	
	public boolean processReimb(int id, int decision) {
		return dao.processReimbursement(id, user, decision);
	}

	/**
	 * 
	 * @param status 1: approved, 0:pending, -1: denied
	 * @return Set of reimbursements for the specified filter
	 */
	public Set<Reimbursement> findByStatus(int status) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();
		try {
			ret = dao.filterByIntField("STATUS_ID", status);
		} catch (SQLSecurityException e) {
			devlog.trace(this, e);
		}
		return ret;
	}

	public Set<Reimbursement> getAllReimbursements() {
		return dao.getAllReimbursementsNoReceipt();
	}

	public Set<Reimbursement> amountGreaterThan(double amt) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();

		try {
			ret = dao.filterByGreaterThanDoubleField("AMOUNT", amt);
		} catch (SQLSecurityException e) {
			devlog.trace(this, e);
		}
		return ret;
	}

	public Set<Reimbursement> amountLessThan(double amt) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();

		try {
			ret = dao.filterByLessThanDoubleField("AMOUNT", amt);
		} catch (SQLSecurityException e) {
			devlog.trace(this, e);
		}
		return ret;
	}

	public Set<Reimbursement> amountExactly(double amt) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();

		try {
			ret = dao.filterByExactDoubleField("AMOUNT", amt);
		} catch (SQLSecurityException e) {
			devlog.trace(this, e);
		}
		return ret;
	}

	public Set<Reimbursement> findByUser(int id) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();

		try {
			ret = dao.filterByIntField("AUTHOR", id);
		} catch (SQLSecurityException e) {
			devlog.trace(this, e);
		}
		return ret;
	}

	public Set<Reimbursement> findByUser(User u) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();

		try {
			ret = dao.filterByIntField("AUTHOR", u.getUSER_ID());
		} catch (SQLSecurityException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.trace(this, e);
		}
		return ret;
	}

	public Set<Reimbursement> findByFirstName(String fn) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();
		if (fn == null) {
			return ret;
		} else if (fn.length() > 0 ) {
			try {
				ret = dao.filterByStringField("FIRST_NAME", fn);
			} catch (SQLSecurityException e) {
				devlog.trace(this,e);
			}
		}
	
		return ret;
	}

	public Set<Reimbursement> findByLastName(String ln) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();
		if (ln == null) {
			return ret;
		} else if (ln.length() > 0) {
			try {
				ret = dao.filterByStringField("LAST_NAME", ln);
			} catch (SQLSecurityException e) {
				devlog.trace(this, e);
			}
		}
		return ret;
	}

	public Set<Reimbursement> findBy(String col_name, String s) {
		Set<Reimbursement> ret = new TreeSet<Reimbursement>();
		if (col_name == null  || s == null) {
			return ret;
		} else if (s.length() > 0 && col_name.length() > 1) {
			try {
				ret = dao.filterByStringField(col_name, s);
			} catch (SQLSecurityException e) {
				devlog.trace(this, e);
			}
		}
		return ret;
	}

}
