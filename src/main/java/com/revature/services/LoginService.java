package com.revature.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.log4j.Logger;

import com.revature.DAO.ReimbDAO;
import com.revature.data.User;

public class LoginService {
	private static Logger log = Logger.getLogger("infoLogger");
	private String ip = "N/A";
	private ReimbDAO dao;
	
	public LoginService() {
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
		dao = new ReimbDAO(ip);
	}
	
	public LoginService(ReimbDAO dao) {
		this.dao = dao;
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
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return Returns the ID of the login if successful, 0 if password is incorrect, -1 if no account with username found
	 */
	public int login(String username, String password) {
		User u = dao.getUser(username);
		
		if (u == null) {
			return -1;
		}
		
		return u.passMatch(password) ? u.getUSER_ID() : 0;
		
	}
	
}
