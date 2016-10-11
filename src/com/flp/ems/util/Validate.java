package com.flp.ems.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

//	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
//		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	private static final String EMAIL_PATTERN =
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String PHONE_NO = "^[7-9]{2}[0-9]{8}$"; 
	
	
	
	//EMAIL IS AUTO GENERATED, NO NEED TO VALIDATE
	public static boolean validateEmailId(String emailId){
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(emailId);
        return matcher.matches();
	}
	
	public static boolean validatePhoneNo(Long phone){
		Pattern pattern = Pattern.compile(PHONE_NO);
		Matcher matcher = pattern.matcher(Long.toString(phone));
		return matcher.matches();
	}
	
	public static boolean validateDate(String date){
		boolean validation = false;
		//TODO
		return validation;
	}
}
