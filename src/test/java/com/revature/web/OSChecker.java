package com.revature.web;

public class OSChecker {
	private static String OS = System.getProperty("os.name").toLowerCase();
	public OSChecker() {
		
	}
	
	public static boolean isWindows() {
		return OS.indexOf("win") >= 0;
	}
	
}
