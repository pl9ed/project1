package com.revature.data;

import java.io.FileInputStream;
import java.sql.Date;

public class Reimbursement {
	private int REIMB_ID;
	private double AMOUNT;
	private Date SUBMITTED;
	private Date RESOLVED;
	private String DESCRIPTION;
	private FileInputStream RECEIPT;
	private int AUTHOR;
	private int RESOLVER;
	private int TYPE_ID;
	private int STATUS_ID;
	
	public Reimbursement() {};
	
	public Reimbursement(int rEIMB_ID, double aMOUNT, Date sUBMITTED, Date rESOLVED, String dESCRIPTION,
			FileInputStream rECEIPT, int aUTHOR, int rESOLVER, int tYPE_ID, int sTATUS_ID) {
		super();
		REIMB_ID = rEIMB_ID;
		AMOUNT = aMOUNT;
		SUBMITTED = sUBMITTED;
		RESOLVED = rESOLVED;
		DESCRIPTION = dESCRIPTION;
		RECEIPT = rECEIPT;
		AUTHOR = aUTHOR;
		RESOLVER = rESOLVER;
		TYPE_ID = tYPE_ID;
		STATUS_ID = sTATUS_ID;
	}

	public int getREIMB_ID() {
		return REIMB_ID;
	}

	public void setREIMB_ID(int rEIMB_ID) {
		REIMB_ID = rEIMB_ID;
	}

	public double getAMOUNT() {
		return AMOUNT;
	}

	public void setAMOUNT(double aMOUNT) {
		AMOUNT = aMOUNT;
	}

	public Date getSUBMITTED() {
		return SUBMITTED;
	}

	public void setSUBMITTED(Date sUBMITTED) {
		SUBMITTED = sUBMITTED;
	}

	public Date getRESOLVED() {
		return RESOLVED;
	}

	public void setRESOLVED(Date rESOLVED) {
		RESOLVED = rESOLVED;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public FileInputStream getRECEIPT() {
		return RECEIPT;
	}

	public void setRECEIPT(FileInputStream rECEIPT) {
		RECEIPT = rECEIPT;
	}

	public int getAUTHOR() {
		return AUTHOR;
	}

	public void setAUTHOR(int aUTHOR) {
		AUTHOR = aUTHOR;
	}

	public int getRESOLVER() {
		return RESOLVER;
	}

	public void setRESOLVER(int rESOLVER) {
		RESOLVER = rESOLVER;
	}

	public int getTYPE_ID() {
		return TYPE_ID;
	}

	public void setTYPE_ID(int tYPE_ID) {
		TYPE_ID = tYPE_ID;
	}

	public int getSTATUS_ID() {
		return STATUS_ID;
	}

	public void setSTATUS_ID(int sTATUS_ID) {
		STATUS_ID = sTATUS_ID;
	}

	@Override
	public String toString() {
		return "[REIMB_ID=" + REIMB_ID + ", AMOUNT=" + AMOUNT + ", SUBMITTED=" + SUBMITTED + ", RESOLVED="
				+ RESOLVED + ", DESCRIPTION=" + DESCRIPTION + ", RECEIPT=" + RECEIPT + ", AUTHOR=" + AUTHOR
				+ ", RESOLVER=" + RESOLVER + ", TYPE_ID=" + TYPE_ID + ", STATUS_ID=" + STATUS_ID + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(AMOUNT);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + AUTHOR;
		result = prime * result + ((DESCRIPTION == null) ? 0 : DESCRIPTION.hashCode());
		result = prime * result + ((RECEIPT == null) ? 0 : RECEIPT.hashCode());
		result = prime * result + REIMB_ID;
		result = prime * result + ((RESOLVED == null) ? 0 : RESOLVED.hashCode());
		result = prime * result + RESOLVER;
		result = prime * result + STATUS_ID;
		result = prime * result + ((SUBMITTED == null) ? 0 : SUBMITTED.hashCode());
		result = prime * result + TYPE_ID;
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
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(AMOUNT) != Double.doubleToLongBits(other.AMOUNT))
			return false;
		if (AUTHOR != other.AUTHOR)
			return false;
		if (DESCRIPTION == null) {
			if (other.DESCRIPTION != null)
				return false;
		} else if (!DESCRIPTION.equals(other.DESCRIPTION))
			return false;
		if (RECEIPT == null) {
			if (other.RECEIPT != null)
				return false;
		}
		if (REIMB_ID != other.REIMB_ID)
			return false;
		if (RESOLVED == null) {
			if (other.RESOLVED != null)
				return false;
		} else if (!RESOLVED.equals(other.RESOLVED))
			return false;
		if (RESOLVER != other.RESOLVER)
			return false;
		if (STATUS_ID != other.STATUS_ID)
			return false;
		if (SUBMITTED == null) {
			if (other.SUBMITTED != null)
				return false;
		} else if (!SUBMITTED.equals(other.SUBMITTED))
			return false;
		if (TYPE_ID != other.TYPE_ID)
			return false;
		return true;
	}
	
}
