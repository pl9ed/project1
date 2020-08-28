package com.revature.DAO;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.data.Reimbursement;
import com.revature.data.User;

public class ReimbDAO implements ReimbDAOI {
	private static Logger devlog = Logger.getLogger(ReimbDAO.class);
	private static Logger infolog = Logger.getLogger("infoLog");
	private String schema = "project1";
	
	public ReimbDAO() {};
	
	public ReimbDAO(String schema) {
		this.schema = schema;
	};
	
	
	public String getSchema() {
		return this.schema;
	}
	public void setSchema(String schema) {
		this.schema=schema;
	}
	
	@Override
	public Reimbursement getReimbursement(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reimbursement getReimbursement(Reimbursement r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<User> getAllReimbursements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateReimbursement(Reimbursement r) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createUser(User u) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean createReimbursement(Reimbursement r) {
		// TODO Auto-generated method stub
		try (Connection conn = DAOUtilities.getConnection()) {
			String sql;
			PreparedStatement stmt;

			if (r.getRESOLVED() == null || r.getRESOLVER() == 0) {
				sql = "INSERT INTO ";
				sql = sql.concat(schema).concat(".ERS_REIMBURSEMENT("
						+ "REIMB_ID, "
						+ "AMOUNT,"
						+ "SUBMITTED,"
						+ "DESCRIPTION,"
						+ "RECEIPT,"
						+ "AUTHOR,"
						+ "TYPE_ID,"
						+ "STATUS_ID,"
						+ "SET_ID) VALUES(?,?,?,?,?,?,?,?,?)");
				
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, r.getREIMB_ID());
				stmt.setDouble(2, r.getAMOUNT());
				stmt.setDate(3, r.getSUBMITTED());
				stmt.setString(4, r.getDESCRIPTION());
				stmt.setBinaryStream(5, r.getRECEIPT());
				stmt.setInt(6, r.getAUTHOR());
				stmt.setInt(7, r.getTYPE_ID());
				stmt.setInt(8, r.getSTATUS_ID());
				stmt.setInt(9, r.getSET_ID());
				
				devlog.info("Query: " + stmt);
				
				int rowcount = stmt.executeUpdate();
				
				if (rowcount == 1) {
					infolog.info("SUCCESS");
					return true;
				} else {
					infolog.info("FAILURE. " + rowcount + " rows updated with 1 expected");
					return false;
				}
				
			} else {
				sql = "INSERT INTO ?.ERS_REIMBURSEMENT("
						+ "REIMB_ID, "
						+ "AMOUNT,"
						+ "SUBMITTED,"
						+ "RESOLVED"
						+ "DESCRIPTION,"
						+ "RECEIPT,"
						+ "AUTHOR,"
						+ "RESOLVER,"
						+ "TYPE_ID,"
						+ "STATUS_ID,"
						+ "SET_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?)";

				stmt = conn.prepareStatement(sql);
				stmt.setString(1, schema);
				stmt.setInt(2, r.getREIMB_ID());
				stmt.setDouble(3, r.getAMOUNT());
				stmt.setDate(4, r.getSUBMITTED());
				stmt.setDate(5, r.getRESOLVED());
				stmt.setString(6, r.getDESCRIPTION());
				stmt.setBinaryStream(7, r.getRECEIPT());
				stmt.setInt(8, r.getAUTHOR());
				stmt.setInt(9, r.getRESOLVER());
				stmt.setInt(10, r.getTYPE_ID());
				stmt.setInt(11, r.getSTATUS_ID());
				stmt.setInt(12, r.getSET_ID());
				
				devlog.info("Query: " + stmt);
				
				int rowcount = stmt.executeUpdate();
				
				if (rowcount == 1) {
					infolog.info("SUCCESS: " + stmt);
					devlog.info("SUCCESS: " + stmt);
					return true;
				} else {
					infolog.info("FAILURE. " + rowcount + " rows updated with 1 expected.");
					devlog.info("FAILURE. " + rowcount + " rows updated with 1 expected.");
					return false;
				}
			}
		} catch (SQLException e) {
			devlog.trace(this,e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when approving reimbursement");
			devlog.trace(this,e);
		}
		return false;
	}
	@Override
	public boolean processReimbursement(int id, boolean b) {
		// TODO Auto-generated method stub
		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "UPDATE ?.ERS_REIMBURSEMENT STATUS_ID=? WHERE REIMB_ID=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			int decision = b ? 1 : -1;
			
			stmt.setString(1, schema);
			stmt.setInt(2, decision);
			stmt.setInt(3, id);
			
			int rowcount = stmt.executeUpdate();
			
			if (rowcount == 1) {
				infolog.info("SUCCESS: " + stmt);
				devlog.info("SUCCESS: " + stmt);
				return true;
			} else {
				infolog.info("FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				devlog.info("FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				return false;
			}

			
		// maybe add error popups for exceptions?
		} catch (SQLException e) {
			devlog.trace(this,e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when approving reimbursement");
			devlog.trace(this,e);
		}
		return false;
	}

}
