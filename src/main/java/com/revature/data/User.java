package com.revature.data;

import org.mindrot.jbcrypt.BCrypt;

public class User {
	private int USER_ID;
	private String USERNAME;
	private String PASSHASH;
	private String FIRST_NAME;
	private String LAST_NAME;
	private String EMAIL;
	private int ROLE_ID;
	
	public User() {
		super();
	}

	public User(int uSER_ID, String uSERNAME, String password, String fIRST_NAME, String lAST_NAME, String eMAIL,
			int rOLE_ID) {
		super();
		USER_ID = uSER_ID;
		USERNAME = uSERNAME;
		setPASSHASH(password);
		FIRST_NAME = fIRST_NAME;
		LAST_NAME = lAST_NAME;
		EMAIL = eMAIL;
		ROLE_ID = rOLE_ID;
	}

	public int getUSER_ID() {
		return USER_ID;
	}

	public void setUSER_ID(int uSER_ID) {
		USER_ID = uSER_ID;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSHASH() {
		return PASSHASH;
	}

	public void setPASSHASH(String password) {
		PASSHASH = BCrypt.hashpw(password,BCrypt.gensalt());
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String fIRST_NAME) {
		FIRST_NAME = fIRST_NAME;
	}

	public String getLAST_NAME() {
		return LAST_NAME;
	}

	public void setLAST_NAME(String lAST_NAME) {
		LAST_NAME = lAST_NAME;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	public int getROLE_ID() {
		return ROLE_ID;
	}

	public void setROLE_ID(int rOLE_ID) {
		ROLE_ID = rOLE_ID;
	}

	@Override
	public String toString() {
		return "[USER_ID=" + USER_ID + ", USERNAME=" + USERNAME + ", PASSHASH=" + PASSHASH + ", FIRST_NAME="
				+ FIRST_NAME + ", LAST_NAME=" + LAST_NAME + ", EMAIL=" + EMAIL + ", ROLE_ID=" + ROLE_ID + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EMAIL == null) ? 0 : EMAIL.hashCode());
		result = prime * result + ((FIRST_NAME == null) ? 0 : FIRST_NAME.hashCode());
		result = prime * result + ((LAST_NAME == null) ? 0 : LAST_NAME.hashCode());
		result = prime * result + ((PASSHASH == null) ? 0 : PASSHASH.hashCode());
		result = prime * result + ROLE_ID;
		result = prime * result + ((USERNAME == null) ? 0 : USERNAME.hashCode());
		result = prime * result + USER_ID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (EMAIL == null) {
			if (other.EMAIL != null)
				return false;
		} else if (!EMAIL.equals(other.EMAIL))
			return false;
		if (FIRST_NAME == null) {
			if (other.FIRST_NAME != null)
				return false;
		} else if (!FIRST_NAME.equals(other.FIRST_NAME))
			return false;
		if (LAST_NAME == null) {
			if (other.LAST_NAME != null)
				return false;
		} else if (!LAST_NAME.equals(other.LAST_NAME))
			return false;
		if (PASSHASH == null) {
			if (other.PASSHASH != null)
				return false;
		} 
		if (ROLE_ID != other.ROLE_ID)
			return false;
		if (USERNAME == null) {
			if (other.USERNAME != null)
				return false;
		} else if (!USERNAME.equals(other.USERNAME))
			return false;
		if (USER_ID != other.USER_ID)
			return false;
		return true;
	}

}
