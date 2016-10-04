package com.flp.ems.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

import sun.security.pkcs11.Secmod.DbMode;

public class Utils {

	Properties props = null;
	/*
	 * static Properties props = null; static Connection dbConnection;
	 * 
	 * public static void main(String[] args) throws SQLException, IOException {
	 * props = getProperties(); String url = props.getProperty("jdbc.url");
	 * dbConnection = DriverManager.getConnection(url); System.out.println(
	 * "Connection succesfull ? " + (dbConnection != null));
	 * insertDummyData(dbConnection); }
	 */

	// CONVERTS STRING TO JAVA DATE FORMAT
	public Date getDateFromString(String dateString) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date date = null;
		try {
			date = df.parse(dateString);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		return date;
	}

	// CONVERTS SQl DATE TO JAVA DATE
	public static Date getJavaDateFromSqlDate(java.sql.Date date) {

		java.util.Date utilDate = new java.util.Date(date.getTime());

		return utilDate;
	}

	// CONVERTS JAVA DATE TO SQL DATE
	public static Date getSqlDateFromJavaDate(Date date) {

		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		return sqlDate;
	}

	// GET PROPERTIES FILE REFERENCE
	public Properties getProperties() throws IOException {
		// LOAD PROPERTIES FILE
		props = new Properties();

		FileInputStream fis = new FileInputStream("dbDetails.properties");

		props.load(fis);

		return props;

	}

	// VERIFYING THE AUTO ASSIGNED EMAIL IS UNIQUE, IF NOT SUFFIX SOME NUMBER
	public boolean ifEmailNotAssigned(String email, Connection dbConnection) throws IOException, SQLException {
		boolean status = false;
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
					status = true;
					break;
				}
			}

		}

		if (status)
			regenerateEmail(tempEmail);

		return status;
	}

	private String regenerateEmail(String email) {
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

	public void insertDummyData(Connection dbConnection) throws SQLException {
		// INSERTING # DUMMY DEPARTMENT DATA
		String insertQuery = props.getProperty("jdbc.query.insertDepartment");
		int deptId = 100, rows = 0;
		String deptName[] = { "Development Dept", "Testing Dept", "Marketing Dept" };
		String deptDesc[] = { "To develop softwares", "To test the products", "Market exposure" };
		
		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)) {

			for (int i = 0; i < deptName.length; i++) {
				insertStatement.setInt(1, deptId++);
				insertStatement.setString(2, deptName[i]);
				insertStatement.setString(3, deptDesc[i]);
				try {
					rows = insertStatement.executeUpdate();
				} catch (Exception e) {
					if (e instanceof SQLIntegrityConstraintViolationException)
						System.out.println("Integrity Violation");
				} finally {
					System.out.println(rows + " rows inserted in Department");
				}
			}
		}

		// INSERTING 3 DUMMY PROJECT DATA
		insertQuery = props.getProperty("jdbc.query.insertProject");
		int projectId = 10;
		deptId = 100; rows = 0;
		String projectName[] = { "Web Project", ".NET Project", "JAVA Project" };
		String projectDesc[] = { "To develop websites", ".NET based project development",
				"Java based Project development" };

		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)) {

			for (int i = 0; i < projectName.length; i++) {
				insertStatement.setInt(1, projectId++);
				insertStatement.setString(2, projectName[i]);
				insertStatement.setString(3, projectDesc[i]);
				insertStatement.setInt(4, deptId++);
				try {
					rows = insertStatement.executeUpdate();
				} catch (Exception e) {
					if (e instanceof SQLIntegrityConstraintViolationException)
						System.out.println("Integrity Violation");
				} finally {
					System.out.println(rows + " rows inserted in Project");
				}
			}
		}//END OF TRY

		// INSERTING 3 DUMMY ROWS IN ROLE
		insertQuery = props.getProperty("jdbc.query.insertRole");
//		insertQuery = "insert into role values()";
		int roleId = 1;
		rows = 0;
		String roleName[] = { "Developer", "Tester", "Designer" };
		String roleDesc[] = { "To develop applications", "Testing of applications", "Designing applications" };
		
		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)) {

			for (int i = 0; i < roleName.length; i++) {
				insertStatement.setInt(1, roleId++);
				insertStatement.setString(2, roleName[i]);
				insertStatement.setString(3, roleDesc[i]);
				try {
					rows = insertStatement.executeUpdate();
				} catch (Exception e) {
					if (e instanceof SQLIntegrityConstraintViolationException)
						System.out.println("Integrity Violation");
				} finally {
					System.out.println(rows + " rows inserted in Role");
				}
			}

		} // END OF TRY

		// INSERTING 3 DUMMY ROWS IN EMPLOYEE for TESTING
		insertQuery = props.getProperty("jdbc.query.insertEmployee");
		rows = 0;
		String kinId[]={"EMS01","EMS02","EMS03"};
		String name[] ={"Anand Kumar Singh","Swapnil Ingale", "Rohit Singh"};
		String email[] ={"anandkumarsingh@ems.com","swapnilingale@ems.com", "rohitsingh@ems.com"};
		String phone[] ={"8856076895","8965324512","7894561230"};
		String dob[] = {"22/08/1994","18/12/1992","05/02/1991"};
		String doj[] = {"10/02/2016","02/05/2014","31/06/2013"};
		String addr[] = {"Pimpri Chinchwad","Eon Kharadi","Hinjewadi"};
		int dept[] = {101,102,100};
		int project[] = {10, 11, 12};
		int role[] = {1, 2, 3};

		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)) {
			
			for(int i=0;i<name.length;i++){
				insertStatement.setString(1, kinId[i]);
				insertStatement.setString(2,name[i] );
				insertStatement.setString(3, email[i]);
				insertStatement.setString(4, phone[i]);
				insertStatement.setDate(5, (java.sql.Date)getSqlDateFromJavaDate(getDateFromString(dob[i])));
				insertStatement.setDate(6, (java.sql.Date)getSqlDateFromJavaDate(getDateFromString(doj[i])));
				insertStatement.setString(7, addr[i]);
				insertStatement.setInt(8, dept[i]);
				insertStatement.setInt(9, project[i]);
				insertStatement.setInt(10, role[i]);
				try {
					rows = insertStatement.executeUpdate();
				} catch (Exception e) {
					if (e instanceof SQLIntegrityConstraintViolationException)
						System.out.println("Integrity Violation");
				} finally {
					System.out.println(rows + " rows inserted in Employee");
				}
			}
		}

	}// end of insertDummy method

}
