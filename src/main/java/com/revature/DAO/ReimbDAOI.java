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
	public boolean processReimbursement(int id, boolean b);
	
	public boolean createUser(User u);
	public boolean createReimbursement(Reimbursement r);

}
