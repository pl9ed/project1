package com.revature.data;

import java.io.FileInputStream;
import java.sql.Date;

public class Reimbursement {
	private int REIMB_ID=0;
	private double AMOUNT;
	private Date SUBMITTED;
	private Date RESOLVED;
	private String DESCRIPTION;
	private FileInputStream RECEIPT;
	private int AUTHOR=0;
	private int RESOLVER=0;
	private int TYPE_ID=0;
	private String type;
	private int STATUS_ID=0;
	private String status;
	private int SET_ID=0;
	
	public Reimbursement() {};
	
	public Reimbursement(int rEIMB_ID, double aMOUNT, Date sUBMITTED, Date rESOLVED, String dESCRIPTION,
			FileInputStream rECEIPT, int aUTHOR, int rESOLVER, int tYPE_ID, String type, int sTATUS_ID, String status,
			int SET_ID) {
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
		this.type = type;
		STATUS_ID = sTATUS_ID;
		this.status = status;
		this.setSET_ID(SET_ID);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getSET_ID() {
		return SET_ID;
	}

	public void setSET_ID(int sET_ID) {
		SET_ID = sET_ID;
	}

	
	
}
