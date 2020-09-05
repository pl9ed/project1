package com.revature.data;

public class Application {
	private int REIMB_ID = 0;
	private int isApproved = 0;
	private int RESOLVER = 0;
	
	public Application() {};
	
	public Application(int rEIMB_ID, int isApproved) {
		super();
		REIMB_ID = rEIMB_ID;
		this.isApproved = isApproved;
	}

	public int getREIMB_ID() {
		return REIMB_ID;
	}

	public void setREIMB_ID(int rEIMB_ID) {
		REIMB_ID = rEIMB_ID;
	}

	public int getIsApproved() {
		return isApproved;
	}

	public void setSTATUS_ID(int isApproved) {
		this.isApproved = isApproved;
	}

	public int getRESOLVER() {
		return RESOLVER;
	}

	public void setRESOLVER(int rESOLVER) {
		RESOLVER = rESOLVER;
	}
	
	

}
