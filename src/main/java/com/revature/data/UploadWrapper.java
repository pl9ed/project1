package com.revature.data;

import java.io.InputStream;

public class UploadWrapper {
	private Reimbursement data;
	private InputStream upload;
	
	public UploadWrapper() {}

	public UploadWrapper(Reimbursement r, InputStream upload) {
		super();
		this.data = r;
		this.upload = upload;
	}

	public Reimbursement getR() {
		return data;
	}

	public void setR(Reimbursement r) {
		this.data = r;
	}

	public InputStream getUpload() {
		return upload;
	}

	public void setUpload(InputStream upload) {
		this.upload = upload;
	};
	
}
