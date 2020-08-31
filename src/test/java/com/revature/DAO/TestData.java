package com.revature.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import com.revature.data.Reimbursement;
import com.revature.data.User;

public class TestData {
	public User employee;
	public User fm;
	public Reimbursement r1;
	public Reimbursement r2;
	
	public TestData() {
		employee = new User(1,"employee","hunter2","John","Doe","jdoe@mail.com",-1, "EMPLOYEE");
		fm = new User(2,"fm","hunter1","Bob","Smith","bsmith@mail.com",0, "FINANCE MANAGER");
		r1 = new Reimbursement();
		r2 = new Reimbursement();

		File f;
		f = new File("placeholder.jpg");
		// C:\Users\pl9ed\Documents\workspace-spring-tool-suite-4-4.7.1.RELEASE\project1-paili
		
		try {
			//System.out.println(System.getProperty("user.dir"));
			FileInputStream fis = new FileInputStream(f);
			r1.setRECEIPT(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		try {
			//System.out.println(System.getProperty("user.dir"));
			FileInputStream fis = new FileInputStream(f);
			r2.setRECEIPT(fis);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		
		// r1: pending, 
		r1.setREIMB_ID(1);
		r1.setAMOUNT(10.50);
		r1.setSUBMITTED(LocalDate.now());
		r1.setDESCRIPTION("DESCRIPTION GOES HERE");
		r1.setAUTHOR(employee.getUSER_ID());
		r1.setTYPE_ID(1);
		r1.setREIMB_TYPE("LODGING");
		r1.setSTATUS_ID(0);
		r1.setStatus("PENDING");
		
		// r2: approved
		r2.setREIMB_ID(2);
		r2.setAMOUNT(100.99);
		r2.setSUBMITTED(LocalDate.of(2020, 8, 20));
		r2.setDESCRIPTION("APPROVED TEST");
		r2.setAUTHOR(employee.getUSER_ID());
		r2.setTYPE_ID(2);
		r2.setREIMB_TYPE("TRAVEL");
		r2.setSTATUS_ID(1);
		r2.setStatus("APPROVED");
		r2.setRESOLVED(LocalDate.now());
		r2.setRESOLVER(fm.getUSER_ID());
		
	}
	
	public static void resetDB() {
		String sql = "CALL p1_db_reset()";
		
		try (Connection conn = DAOUtilities.getConnection()) {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
