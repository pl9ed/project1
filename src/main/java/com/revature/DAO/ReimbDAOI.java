package com.revature.DAO;

import java.util.Set;

import com.revature.data.Reimbursement;
import com.revature.data.User;

public interface ReimbDAOI {
	public Reimbursement getReimbursement(int id);
	public Reimbursement getReimbursement(Reimbursement r);
	
	public User getUser(int id);
	public User getUser(User u);
	public User getUser(String username);
	
	public Set<User> getAllUsers();
	public Set<Reimbursement> getAllReimbursements();
	
	public boolean updateUser(User u);
	public boolean updateReimbursement(Reimbursement r);
	boolean processReimbursement(int id, int resolver, int decision);
	
	public boolean createUser(User u);
	public boolean createReimbursement(Reimbursement r);
	
	public Set<Reimbursement> filterByIntField(String col_name, int n);
	public Set<Reimbursement> filterByExactStringField(String col_name, String s);
	public Set<Reimbursement> filterByStringField(String col_name, String s);
	
	public Set<Reimbursement> filterByExactDoubleField(String col_name, double n);
	public Set<Reimbursement> filterByGreaterThanDoubleField(String col_name, double n);
	public Set<Reimbursement> filterByLessThanDoubleField(String col_name, double n);


}
