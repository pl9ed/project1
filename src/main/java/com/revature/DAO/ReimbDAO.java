package com.revature.DAO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.revature.data.Reimbursement;
import com.revature.data.User;

public class ReimbDAO implements ReimbDAOI {
	private static Logger devlog = Logger.getLogger(ReimbDAO.class);
	private static Logger infolog = Logger.getLogger("infoLogger");
	private String schema = "project1";
	private String ip = "N/A";

	public ReimbDAO() {
		try {
			URL whatismyip = new URL("http://checkip.amazonaws.com");
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				String ip = in.readLine();
				this.ip = ip;
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	};

	
	public ReimbDAO(String schema, String ip) {
		this.ip = ip;
		this.schema = schema;

	};
	
	public ReimbDAO(String ip) {
		this.ip = ip;
	}

	public String getIP() {
		return this.ip;
	}

	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@Override
	public Reimbursement getReimbursement(int id) {
		PreparedStatement stmt;

		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "SELECT * FROM " + schema + ".ERS_REIMBURSEMENT_FULL WHERE REIMB_ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			devlog.info("[" + ip + "] Query: " + stmt);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				devlog.info("[" + ip + "] SUCCESS - Got Reimbursement with ID: " + id);
				return createReimbursementObject(rs);
			} else {
				devlog.error("[" + ip + "] FAILURE - No Reimbursement found for ID: " + id);
				return null;
			}

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when getting reimbursement");
			devlog.trace(this, e);
		}
		return null;
	}

	@Override
	public Reimbursement getReimbursement(Reimbursement r) {
		return getReimbursement(r.getREIMB_ID());
	}

	@Override
	public User getUser(String username) {
		PreparedStatement stmt;

		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "SELECT * FROM " + schema + ".ERS_USERS_FULL WHERE USERNAME=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);

			devlog.info("[" + ip + "] Query: " + stmt);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				devlog.info("[" + ip + "] SUCCESS - Got User with USERNAME: " + username);
				return createUserObject(rs);
			} else {
				devlog.error("[" + ip + "] FAILURE - No User found for USERNAME: " + username);
				return null;
			}

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when getting user by username");
			devlog.trace(this, e);
		}
		return null;
	}
	
	@Override
	public User getUser(int id) {
		PreparedStatement stmt;

		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "SELECT * FROM " + schema + ".ERS_USERS_FULL WHERE USER_ID=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			devlog.info("[" + ip + "] Query: " + stmt);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				devlog.info("[" + ip + "] SUCCESS - Got User with ID: " + id);
				return createUserObject(rs);
			} else {
				devlog.error("[" + ip + "] FAILURE - No User found for ID: " + id);
				return null;
			}

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when getting user");
			devlog.trace(this, e);
		}
		return null;
	}

	@Override
	public User getUser(User u) {
		return getUser(u.getUSER_ID());
	}

	@Override
	public Set<User> getAllUsers() {
		PreparedStatement stmt;

		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "SELECT * FROM " + schema + ".ERS_USERS_FULL";
			stmt = conn.prepareStatement(sql);

			devlog.info("[" + ip + "] Query: " + stmt);

			ResultSet rs = stmt.executeQuery();

			Set<User> ret = new HashSet<User>();
			int i = 0;
			while (rs.next()) {
				ret.add(createUserObject(rs));
				i++;
			}

			devlog.info("[" + ip + "] Returned " + i + " users");
			return ret;

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when getting all users");
			devlog.trace(this, e);
		}
		return null;
	}

	@Override
	public Set<Reimbursement> getAllReimbursements() {
		PreparedStatement stmt;

		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "SELECT * FROM " + schema + ".ERS_REIMBURSEMENT_FULL";
			stmt = conn.prepareStatement(sql);

			devlog.info("[" + ip + "] Query: " + stmt);

			ResultSet rs = stmt.executeQuery();

			Set<Reimbursement> ret = new HashSet<Reimbursement>();
			int i = 0;
			while (rs.next()) {
				ret.add(createReimbursementObject(rs));
				i++;
			}

			devlog.info("[" + ip + "] Returned " + i + " Reimbursements");
			return ret;

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when getting all reimbursements");
			devlog.trace(this, e);
		}
		return null;
	}

	@Override
	public boolean updateUser(User u) {
		String sql;
		PreparedStatement stmt;

		sql = "UPDATE " + schema + ".ERS_USERS SET "
				+ "USERNAME=?, PASSHASH=?, FIRST_NAME=?, LAST_NAME=?,EMAIL=?,ROLE_ID=? "
				+ "WHERE USER_ID=?";

		try (Connection conn = DAOUtilities.getConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, u.getUSERNAME());
			stmt.setString(2, u.getPASSHASH());
			stmt.setString(3, u.getFIRST_NAME());
			stmt.setString(4, u.getLAST_NAME());
			stmt.setString(5, u.getEMAIL());
			stmt.setInt(6, u.getROLE_ID());
			stmt.setInt(7, u.getUSER_ID());

			
			devlog.info("[" + ip + "] Query: " + stmt);

			int rowcount = stmt.executeUpdate();

			if (rowcount == 1) {
				infolog.info("[" + ip + "] SUCCESS - UPDATED USER WITH ID: " + u.getUSER_ID() + ", USERNAME: "
						+ u.getUSERNAME());
				devlog.info("[" + ip + "] SUCCESS: " + stmt);
				return true;
			} else {
				infolog.info("[" + ip + "] FAILURE - COULD NOT UPDATE USER WITH ID: " + u.getUSER_ID() + ", USERNAME: "
						+ u.getUSERNAME());
				devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				return false;
			}

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when updating user");
			devlog.trace(this, e);
		}
		return false;
	}

	@Override
	public boolean updateReimbursement(Reimbursement r) {
		try (Connection conn = DAOUtilities.getConnection()) {
			
			PreparedStatement stmt;
			
			if (r.getRESOLVED() == null) {
				String sql = "UPDATE " + schema + ".ERS_REIMBURSEMENT SET "
						+ "AMOUNT=?, SUBMITTED=?, DESCRIPTION=?,RECEIPT=?,AUTHOR=?,"
						+ "TYPE_ID=?,STATUS_ID=? WHERE REIMB_ID=?";
				stmt = conn.prepareStatement(sql);
				
				stmt.setDouble(1, r.getAMOUNT());
				stmt.setDate(2, Date.valueOf(r.getSUBMITTED()));
				stmt.setString(3, r.getDESCRIPTION());
				stmt.setBinaryStream(4, r.getRECEIPT());
				stmt.setInt(5, r.getAUTHOR());
				stmt.setInt(6, r.getTYPE_ID());
				stmt.setInt(7, r.getSTATUS_ID());
				stmt.setInt(8, r.getREIMB_ID());
				
			} else {
				String sql = "UPDATE " + schema + ".ERS_REIMBURSEMENT SET "
						+ "AMOUNT=?, SUBMITTED=?, RESOLVED=?, DESCRIPTION=?,RECEIPT=?,AUTHOR=?,"
						+ "RESOLVER=?,TYPE_ID=?,STATUS_ID=? WHERE REIMB_ID=?";
				stmt = conn.prepareStatement(sql);
				
				stmt.setDouble(1, r.getAMOUNT());
				stmt.setDate(2, Date.valueOf(r.getSUBMITTED()));
				stmt.setDate(3, Date.valueOf(r.getRESOLVED()));
				stmt.setString(4, r.getDESCRIPTION());
				stmt.setBinaryStream(5, r.getRECEIPT());
				stmt.setInt(6, r.getAUTHOR());
				stmt.setInt(7, r.getRESOLVER());
				stmt.setInt(8, r.getTYPE_ID());
				stmt.setInt(9, r.getSTATUS_ID());
				stmt.setInt(10, r.getREIMB_ID());
			}
			
			devlog.info("[" + ip + "] Query: " + stmt);
			int rowcount = stmt.executeUpdate();
			
			if (rowcount == 1) {
				infolog.info("[" + ip + "] SUCCESS - UPDATED REIMBURSEMENT WITH ID: " + r.getREIMB_ID());
				devlog.info("[" + ip + "] SUCCESS: " + stmt);
				return true;
			} else {
				infolog.info("[" + ip + "] FAILURE - COULD NOT UPDATE REIMBURSEMENT WITH ID: " + r.getREIMB_ID());
				devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				return false;
			}
		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when updating reimbursement");
			devlog.trace(this, e);
		}
		return false;
	}

	@Override
	public boolean createUser(User u) {
		String sql;
		PreparedStatement stmt;

		sql = "INSERT INTO ".concat(schema).concat(".ERS_USERS(" + "USER_ID," + "USERNAME," + "PASSHASH,"
				+ "FIRST_NAME," + "LAST_NAME," + "EMAIL," + "ROLE_ID) VALUES(?,?,?,?,?,?,?)");

		try (Connection conn = DAOUtilities.getConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, u.getUSER_ID());
			stmt.setString(2, u.getUSERNAME());
			stmt.setString(3, u.getPASSHASH());
			stmt.setString(4, u.getFIRST_NAME());
			stmt.setString(5, u.getLAST_NAME());
			stmt.setString(6, u.getEMAIL());
			stmt.setInt(7, u.getROLE_ID());

			devlog.info("[" + ip + "] Query: " + stmt);

			int rowcount = stmt.executeUpdate();

			if (rowcount == 1) {
				infolog.info("[" + ip + "] SUCCESS - CREATED USER WITH ID: " + u.getUSER_ID() + ", USERNAME: "
						+ u.getUSERNAME());
				devlog.info("[" + ip + "] SUCCESS: " + stmt);
				return true;
			} else {
				infolog.info("[" + ip + "] FAILURE - COULD NOT CREATE USER WITH ID: " + u.getUSER_ID() + ", USERNAME: "
						+ u.getUSERNAME());
				devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				return false;
			}

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when creating user");
			devlog.trace(this, e);
		}
		return false;
	}

	public boolean deleteUser(int id) {
		String sql;
		PreparedStatement stmt;

		sql = "DELETE FROM ".concat(schema).concat(".ERS_USERS WHERE USER_ID=?");

		try (Connection conn = DAOUtilities.getConnection()) {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			devlog.info("[" + ip + "] Query: " + stmt);

			int rowcount = stmt.executeUpdate();

			if (rowcount == 1) {
				infolog.info("[" + ip + "] SUCCESS - USER WITH ID: " + id + " DELETED");
				devlog.info("[" + ip + "] SUCCESS: " + stmt);
				return true;
			} else {
				infolog.info("[" + ip + "] FAILURE - COULD NOT DELETE USER: " + id);
				devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				return false;
			}

		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when deleting user");
			devlog.trace(this, e);
		}
		return false;
	}

	public boolean deleteUser(User u) {
		return deleteUser(u.getUSER_ID());
	}

	@Override
	public boolean createReimbursement(Reimbursement r) {
		try (Connection conn = DAOUtilities.getConnection()) {
			String sql;
			PreparedStatement stmt;

			if (r.getRESOLVED() == null || r.getRESOLVER() == 0) {
				sql = "INSERT INTO ".concat(schema)
						.concat(".ERS_REIMBURSEMENT(" + "REIMB_ID, " + "AMOUNT," + "SUBMITTED," + "DESCRIPTION,"
								+ "RECEIPT," + "AUTHOR," + "TYPE_ID," + "STATUS_ID) VALUES(?,?,?,?,?,?,?,?)");

				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, r.getREIMB_ID());
				stmt.setDouble(2, r.getAMOUNT());
				stmt.setDate(3, Date.valueOf(r.getSUBMITTED()));
				stmt.setString(4, r.getDESCRIPTION());
				stmt.setBinaryStream(5, r.getRECEIPT());
				stmt.setInt(6, r.getAUTHOR());
				stmt.setInt(7, r.getTYPE_ID());
				stmt.setInt(8, r.getSTATUS_ID());

				devlog.info("[" + ip + "] Query: " + stmt);

				int rowcount = stmt.executeUpdate();

				if (rowcount == 1) {
					infolog.info("[" + ip + "] SUCCESS - REIMBURSEMENT CREATED WITH ID: " + r.getREIMB_ID());
					devlog.info("[" + ip + "] SUCCESS: " + stmt);
					return true;
				} else {
					infolog.info("[" + ip + "] FAILURE - COULD NOT CREATE REIMBURSEMENT WITH ID: " + r.getREIMB_ID());
					devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
					return false;
				}

			} else {
				sql = "INSERT INTO "+ schema +".ERS_REIMBURSEMENT(" + "REIMB_ID, " 
						+ "AMOUNT," + "SUBMITTED," + "RESOLVED,"
						+ "DESCRIPTION," + "RECEIPT," + "AUTHOR," + "RESOLVER," + "TYPE_ID,"
						+ "STATUS_ID) VALUES(?,?,?,?,?,?,?,?,?,?)";

				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, r.getREIMB_ID());
				stmt.setDouble(2, r.getAMOUNT());
				stmt.setDate(3, Date.valueOf(r.getSUBMITTED()));
				stmt.setDate(4, Date.valueOf(r.getRESOLVED()));
				stmt.setString(5, r.getDESCRIPTION());
				stmt.setBinaryStream(6, r.getRECEIPT());
				stmt.setInt(7, r.getAUTHOR());
				stmt.setInt(8, r.getRESOLVER());
				stmt.setInt(9, r.getTYPE_ID());
				stmt.setInt(10, r.getSTATUS_ID());

				devlog.info("[" + ip + "] Query: " + stmt);

				int rowcount = stmt.executeUpdate();

				if (rowcount == 1) {
					infolog.info("[" + ip + "] SUCCESS - REIMBURSEMENT CREATED WITH ID: " + r.getREIMB_ID());
					devlog.info("[" + ip + "] SUCCESS: " + stmt);
					return true;
				} else {
					infolog.info("[" + ip + "] FAILURE - COULD NOT CREATE REIMBURSEMENT WITH ID: " + r.getREIMB_ID());
					devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with 1 expected.");
					return false;
				}
			}
		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when creating reimbursement");
			devlog.trace(this, e);
		}
		return false;
	}

	@Override
	public boolean processReimbursement(int id, boolean b) {
		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "UPDATE " + schema + ".ERS_REIMBURSEMENT SET STATUS_ID=? WHERE REIMB_ID=?";
			PreparedStatement stmt = conn.prepareStatement(sql);

			int decision = b ? 1 : -1;

			stmt.setInt(1, decision);
			stmt.setInt(2, id);

			devlog.info("[" + ip + "] Query: " + stmt);

			int rowcount = stmt.executeUpdate();

			if (rowcount == 1) {
				infolog.info("[" + ip + "] SUCCESS - PROCESSED REIMBURSEMENT WITH ID: " + id);
				devlog.info("[" + ip + "] SUCCESS: " + stmt);
				return true;
			} else {
				infolog.info("[" + ip + "] FAILURE - COULD NOT PROCESS REIMBURSEMENT WITH ID: " + id);
				devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				return false;
			}

			// maybe add error popups for exceptions?
		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when processing reimbursement");
			devlog.trace(this, e);
		}
		return false;
	}

	public boolean deleteReimbursement(int id) {
		try (Connection conn = DAOUtilities.getConnection()) {
			String sql = "DELETE FROM ".concat(schema).concat(".ERS_REIMBURSEMENT WHERE REIMB_ID=?");

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);

			devlog.info("[" + ip + "] Query: " + stmt);

			int rowcount = stmt.executeUpdate();

			if (rowcount == 1) {
				infolog.info("[" + ip + "] SUCCESS - DELETED REIMBURSEMENT WITH ID: " + id);
				devlog.info("[" + ip + "] SUCCESS: " + stmt);
				return true;
			} else {
				infolog.info("[" + ip + "] FAILURE - COULD NOT DELETE REIMBURSEMENT WITH ID: " + id);
				devlog.info("[" + ip + "] FAILURE. " + rowcount + " rows updated with query: " + stmt + " with 1 expected.");
				return false;
			}

			// maybe add error popups for exceptions?
		} catch (SQLException e) {
			devlog.trace(this, e);
		} catch (NullPointerException e) {
			devlog.error("Null pointer exception when deleting reimbursement");
			devlog.trace(this, e);
		}
		return false;
	}

	// ----------------- STATIC METHODS FOR CREATING OBJECT FROM RESULTSET
	// ----------------------

	private static User createUserObject(ResultSet rs) throws SQLException, NullPointerException {
		User u = new User();

		u.setUSER_ID(rs.getInt("USER_ID"));
		u.setUSERNAME(rs.getString("USERNAME"));
		u.setPASSHASH(rs.getString("PASSHASH"));
		u.setFIRST_NAME(rs.getString("FIRST_NAME"));
		u.setLAST_NAME(rs.getString("LAST_NAME"));
		u.setEMAIL(rs.getString("EMAIL"));
		u.setROLE_ID(rs.getInt("ROLE_ID"));
		u.setUSER_ROLE(rs.getString("USER_ROLE"));

		return u;
	}

	private static Reimbursement createReimbursementObject(ResultSet rs) throws SQLException, NullPointerException {
		Reimbursement r = new Reimbursement();

		r.setREIMB_ID(rs.getInt("REIMB_ID"));
		r.setAMOUNT(rs.getDouble("AMOUNT"));
		r.setSUBMITTED(rs.getDate("SUBMITTED").toLocalDate());
		r.setDESCRIPTION(rs.getString("DESCRIPTION"));
		r.setRECEIPT(rs.getBinaryStream("RECEIPT"));
		r.setAUTHOR(rs.getInt("AUTHOR"));
		r.setTYPE_ID(rs.getInt("TYPE_ID"));
		r.setREIMB_TYPE(rs.getString("REIMB_TYPE"));
		r.setSTATUS_ID(rs.getInt("STATUS_ID"));
		r.setStatus(rs.getString("STATUS"));

		if (rs.getDate("RESOLVED") != null) {
			r.setRESOLVED(rs.getDate("RESOLVED").toLocalDate());
			r.setRESOLVER(rs.getInt("RESOLVER"));
		}

		return r;
	}

}
