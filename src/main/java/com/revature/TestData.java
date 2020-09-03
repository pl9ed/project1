package com.revature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.commons.io.IOUtils;

import com.revature.DAO.DAOUtilities;
import com.revature.DAO.ReimbDAO;
import com.revature.data.Reimbursement;
import com.revature.data.User;

public class TestData {
	public User employee;
	public User fm;
	public User dummy;
	public Reimbursement r1, r2, r3;

	
	public TestData() {
		employee = new User(1,"EMPLOYEE","hunter2","John","Doe","jdoe@mail.com",-1, "EMPLOYEE");
		fm = new User(2,"FM","hunter1","Bob","Smith","bsmith@mail.com",0, "FINANCE MANAGER");
		r1 = new Reimbursement();
		r2 = new Reimbursement();
		r3 = new Reimbursement();
		
		dummy = new User(3,"pl","password","Pai","Li","pl@email.com",0,"FINANCE MANAGER");

		File f;
		f = new File("placeholder.jpg");
		// C:\Users\pl9ed\Documents\workspace-spring-tool-suite-4-4.7.1.RELEASE\project1-paili
		
		try {
			//System.out.println(System.getProperty("user.dir"));
			FileInputStream fis = new FileInputStream(f);
			r1.setRECEIPT(IOUtils.toByteArray(fis));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			//System.out.println(System.getProperty("user.dir"));
			FileInputStream fis = new FileInputStream(f);
			r2.setRECEIPT(IOUtils.toByteArray(fis));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		f = new File("large.jpg");
		try {
			//System.out.println(System.getProperty("user.dir"));
			FileInputStream fis = new FileInputStream(f);
			r3.setRECEIPT(IOUtils.toByteArray(fis));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		r1.setFileName("r1.png");
		
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
		r2.setFileName("r2.png");
		
		r3.setAMOUNT(50);
		r3.setAUTHOR(1);
		r3.setDESCRIPTION("Large Image");
		r3.setSUBMITTED(LocalDate.now());
		r3.setTYPE_ID(2);
		r3.setSTATUS_ID(0);
		
	}
	
	public static void resetDB() {
		String sql = "CALL project1.p1_db_setup(); CALL project1.p1_db_reset();";
		
		try (Connection conn = DAOUtilities.getConnection()) {
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.execute();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		sql = "CREATE TRIGGER PENDING_TRIGGER BEFORE UPDATE OF status_id ON ERS_REIMBURSEMENT \r\n" + 
				"FOR EACH ROW WHEN (OLD.STATUS_ID != 0) EXECUTE PROCEDURE PENDING_CHECK();\r\n" + 
				"\r\n" + 
				"CREATE OR REPLACE FUNCTION PENDING_CHECK()\r\n" + 
				"RETURNS TRIGGER\r\n" + 
				"AS \r\n" + 
				"$$\r\n" + 
				"BEGIN \r\n" + 
				"	RAISE 'ONLY UPDATES TO PENDING REIMBURSEMENTS ARE ALLOWED';\r\n" + 
				"END;\r\n" + 
				"$$ LANGUAGE plpgsql;";
		
		try (Connection conn = DAOUtilities.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
