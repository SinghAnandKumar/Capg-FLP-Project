package com.flp.ems.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.flp.ems.domain.Employee;
import com.flp.ems.util.Constants;
import com.flp.ems.util.Utils;

public class EmployeeDaoImplForDB implements IemployeeDao {

	Properties props = null;
	Connection dbConnection = null;
	Utils utils = null;

	public EmployeeDaoImplForDB() throws IOException, SQLException {
		utils = new Utils();
		props = utils.getProperties();

		// TODO : GET DATABASE CONNECTION USING JDBC URL
		String url = props.getProperty("jdbc.url");
		dbConnection = DriverManager.getConnection(url);
		System.out.println("Connection succesfull ? " + (dbConnection != null));

		// TO INSERT DUMMY DATA FOR DEPARTMENT, ROLE AND PROJECT TABLES
		utils.insertDummyData(dbConnection);
	}

	@Override
	public boolean addEmployee(Employee employee) {
		String insertQuery = props.getProperty("jdbc.query.insertEmployee");
		int row = 0;
		boolean status = false;

		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)) {

			insertStatement.setString(1, employee.getKinId());
			insertStatement.setString(2, employee.getName());
			insertStatement.setString(3, employee.getEmailId());
			insertStatement.setString(4, Long.toString(employee.getPhoneNo()));
			insertStatement.setDate(5,(Date) Utils.getSqlDateFromJavaDate(utils.getDateFromString(employee.getDateOfBirth())));
			// insertStatement.setDate(5, (Date)utils.getDateFromString(employee.getDateOfBirth()));//error
			insertStatement.setDate(6,(Date) Utils.getSqlDateFromJavaDate(utils.getDateFromString(employee.getDateOfJoining())));
			insertStatement.setString(7, employee.getAddress());
			insertStatement.setInt(8, employee.getDeptId());
			insertStatement.setInt(9, employee.getProjectId());
			insertStatement.setInt(10, employee.getRoleId());

			row = insertStatement.executeUpdate();
			if (row > 0)
				status = true;
			System.out.println(row + " rows added in Employee database");

		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException)
				System.err.println("SQL Integrity Constraint violation. Duplicate entry While adding employee");
			else {
				System.out.println("Error while adding new employee");
				e.printStackTrace();
			}
		}

		return status;
	}

	@Override
	public boolean modifyEmployee(Employee employee) {
		boolean status = false;
		// UPDATE QUERY NOT USED AS GIVEN IN DOC
		status = removeEmployee(employee.getKinId());

		if (status) {
			status = false;
			status = addEmployee(employee);
		}

		return status;
	}

	@Override
	public boolean removeEmployee(String kinId) {
		String deleteQuery = props.getProperty("jdbc.query.deleteEmployee");
		int row = 0;
		boolean status = false;

		try (PreparedStatement deleteStatement = dbConnection.prepareStatement(deleteQuery)) {

			deleteStatement.setString(1, kinId);
			row = deleteStatement.executeUpdate();

			if (row > 0)
				status = true;
		} catch (SQLException e) {
			System.out.println("Error while removing employee");
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Employee searchEmployee(String type, String value) {
		String selectQuery = "";
		Employee tempEmp = null;

		if (type.equals(Constants.kinId)) {
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeOnKinId");
		} else if (type.equals(Constants.emailId)) {
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeOnEmailId");
		} else if (type.equals(Constants.name)) {
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeOnName");
		} else {
			return null;
		}

		try (PreparedStatement selectStatement = dbConnection.prepareStatement(selectQuery)) {
			
			selectStatement.setString(1, value);
			ResultSet rs = selectStatement.executeQuery();

			while (rs.next()) {
				tempEmp = new Employee(rs.getString(1),rs.getString(3));
				tempEmp.setName(rs.getString(2));
//				tempEmp.setEmailId(rs.getString(3));
				tempEmp.setPhoneNo(Long.parseLong(rs.getString(4)));
				tempEmp.setDateOfBirth(Utils.getJavaDateFromSqlDate(rs.getDate(5)).toString());// **Error
																								// prone
				tempEmp.setDateOfJoining(Utils.getJavaDateFromSqlDate(rs.getDate(6)).toString());// **Error
																									// prone
				tempEmp.setAddress(rs.getString(7));
				tempEmp.setDeptId(rs.getInt(8));
				tempEmp.setProjectId(rs.getInt(9));
				tempEmp.setRoleId(rs.getInt(10));
			}
		} catch (SQLException e) {
			System.out.println("Error while searching employee");
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		return tempEmp;
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<>();

		String selectQuery = props.getProperty("jdbc.query.readAllEmployee");

		try (Statement selectStatement = dbConnection.createStatement()) {

			ResultSet rs = selectStatement.executeQuery(selectQuery);

			while (rs.next()) {
				Employee tempEmp = new Employee(rs.getString(1),rs.getString(3));
				tempEmp.setName(rs.getString(2));
//				tempEmp.setEmailId(rs.getString(3));
				tempEmp.setPhoneNo(Long.parseLong(rs.getString(4)));
				tempEmp.setDateOfBirth(Utils.getJavaDateFromSqlDate(rs.getDate(5)).toString());// **Error
																								// prone
				tempEmp.setDateOfJoining(Utils.getJavaDateFromSqlDate(rs.getDate(6)).toString());// **Error
																									// prone
				tempEmp.setAddress(rs.getString(7));
				tempEmp.setDeptId(rs.getInt(8));
				tempEmp.setProjectId(rs.getInt(9));
				tempEmp.setRoleId(rs.getInt(10));

				employees.add(tempEmp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employees;
	}

}
