package com.revature;

import com.revature.DAO.ReimbDAO;

public class Main {

	public static void main(String[] args) {
		TestData td = new TestData();
//		TestData.resetDB();
		TestData.setupTrigger();
		ReimbDAO dao = new ReimbDAO();
		dao.createUser(td.employee);
		dao.createUser(td.fm);
		dao.createUser(td.dummy);
		dao.createReimbursement(td.r1);
		dao.createReimbursement(td.r2);
		dao.createReimbursement(td.r3);
		System.out.println("Done with setup!");
	}

}
