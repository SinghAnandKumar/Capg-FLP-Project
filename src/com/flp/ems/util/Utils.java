package com.flp.ems.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.sun.corba.se.impl.ior.GenericTaggedComponent;

public class Utils {

	Properties props = null;

	// GET PROPERTIES FILE REFERENCE
	public Properties getProperties() throws IOException {
		// LOAD PROPERTIES FILE
		props = new Properties();

		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("dbDetails.properties");
		props.load(input);

		return props;

	}

	// VERIFYING THE AUTO ASSIGNED EMAIL IS UNIQUE, IF NOT SUFFIX SOME NUMBER
	public boolean ifEmailNotAssigned(String email, Connection dbConnection) throws IOException, SQLException {
		boolean status = true;
		// ArrayList<String> mails = new ArrayList<>();
		props = (new Utils()).getProperties();
		String tempEmail = "";
		String selectQuery = props.getProperty("jdbc.query.selectEmails");

		try (Statement selectStatement = dbConnection.createStatement()) {
			ResultSet rs = selectStatement.executeQuery(selectQuery);

			while (rs.next()) {
				// mails.add(rs.getString(1));
				tempEmail = rs.getString(1);
				if (email.equals(tempEmail)) {
					status = false;
					break;
				}
			}

		}

//		if (status)
//			regenerateEmail(tempEmail);

		return status;
	}

	public String regenerateEmail(String email) {
		String tmp = "";
		String name = email.substring(0, email.indexOf("@"));
		// INCREASE THE NUMBER AT THE END OF EMAIL eg: abc@ems.com ->
		// abc1@ems.com
		char last = name.charAt(name.length() - 1);
		if (Character.isDigit(last))
			tmp = name + ((int) last + 1) + "@" + Constants.emailSuffix + ".com";
		else
			tmp = name + 1 + "@" + Constants.emailSuffix + ".com";

		return tmp;
	}

	
	public ArrayList<Integer> getIDs(String field){
		ArrayList<Integer> ids = new ArrayList<>();
		String selectQuery ;
		
		if(field.equals("department")){
			selectQuery=props.getProperty("jdbc.query.selectDeptIds");
		}else if(field.equals("project")){
			selectQuery=props.getProperty("jdbc.query.selectProjectIds");
		}else if(field.equals("role")){
			selectQuery=props.getProperty("jdbc.query.selectRolesIds");
		}else
			return null;
		
		return ids;
	}
	

}
