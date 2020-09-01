package com.revature.data;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;

public class Reimbursement implements Comparable<Reimbursement> {
	private int REIMB_ID=0;
	private double AMOUNT;
	private LocalDate SUBMITTED;
	private LocalDate RESOLVED;
	private String DESCRIPTION = "Missing description!";
	private InputStream RECEIPT = new ByteArrayInputStream(new byte[0]);
	private int AUTHOR=0;
	private int RESOLVER=0;
	private int TYPE_ID=0;
	private String REIMB_TYPE;
	private int STATUS_ID=0;
	private String status;
	
	public Reimbursement() {};

	public Reimbursement(int rEIMB_ID, double aMOUNT, LocalDate sUBMITTED, LocalDate rESOLVED, String dESCRIPTION,
			FileInputStream rECEIPT, int aUTHOR, int rESOLVER, int tYPE_ID, String rEIMB_TYPE,
			int sTATUS_ID, String status) {
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
		REIMB_TYPE = rEIMB_TYPE;
		STATUS_ID = sTATUS_ID;
		this.status = status;
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

	public LocalDate getSUBMITTED() {
		return SUBMITTED;
	}

	public void setSUBMITTED(LocalDate sUBMITTED) {
		SUBMITTED = sUBMITTED;
	}

	public LocalDate getRESOLVED() {
		return RESOLVED;
	}

	public void setRESOLVED(LocalDate rESOLVED) {
		RESOLVED = rESOLVED;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public InputStream getRECEIPT() {
		return RECEIPT;
	}

	public void setRECEIPT(InputStream rECEIPT) {
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

	
	public String getREIMB_TYPE() {
		return REIMB_TYPE;
	}

	public void setREIMB_TYPE(String rEIMB_TYPE) {
		REIMB_TYPE = rEIMB_TYPE;
	}

	public int getSTATUS_ID() {
		return STATUS_ID;
	}

	public void setSTATUS_ID(int sTATUS_ID) {
		STATUS_ID = sTATUS_ID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "[REIMB_ID=" + REIMB_ID + ", AMOUNT=" + AMOUNT + ", SUBMITTED=" + SUBMITTED + ", RESOLVED="
				+ RESOLVED + ", DESCRIPTION=" + DESCRIPTION + ", AUTHOR=" + AUTHOR + ", RESOLVER=" + RESOLVER
				+ ", TYPE_ID=" + TYPE_ID  + ", STATUS_ID=" + STATUS_ID + ", status=" + status + "]";
	}

	@Override
	public int compareTo(Reimbursement r) {
		return this.REIMB_ID - r.REIMB_ID;
	}

	
	
}
