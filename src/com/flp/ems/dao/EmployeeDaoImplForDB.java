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

	ArrayList<Integer> existingDepartments = null;
	ArrayList<Integer> existingProjects = null;
	ArrayList<Integer> existingRoles = null;
	Properties props = null;
	Connection dbConnection = null;
	Utils utils = null;

	public EmployeeDaoImplForDB() throws IOException, SQLException {
		existingDepartments = new ArrayList<>();
		existingProjects = new ArrayList<>();
		existingRoles = new ArrayList<>();
		utils = new Utils();
		
		props = utils.getProperties();

		//READ EXISTING DEPARTMENT,PROJECT,ROLE IDs
		
		
		//GETS DATABASE CONNECTION FROM UTILS CLASS
		String url = props.getProperty("jdbc.url");
		dbConnection = DriverManager.getConnection(url);
		System.out.println("Connection succesfull ? " + (dbConnection != null));

		// TO INSERT DUMMY DATA FOR DEPARTMENT, ROLE AND PROJECT TABLES
		utils.insertDummyData(dbConnection);
	}

	@Override
	public boolean addEmployee(Employee employee) {
		String insertQuery = props.getProperty("jdbc.query.insertEmployee");
		String email,newEmail= "";
		int row = 0;
		boolean status = false;

		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery)) {

			insertStatement.setString(1, employee.getKinId());
			insertStatement.setString(2, employee.getName());
			
			//REGENERATEs EMAIL IF ALREADY EXISTS
			email = employee.getEmailId();
			if(!utils.ifEmailNotAssigned(email, dbConnection)){
				newEmail = utils.regenerateEmail(email);
			}
			
			insertStatement.setString(3, employee.getEmailId());
			insertStatement.setString(4, Long.toString(employee.getPhoneNo()));
			insertStatement.setString(5,employee.getDateOfBirth());
			insertStatement.setString(6,employee.getDateOfJoining());
			insertStatement.setString(7, employee.getAddress());
			insertStatement.setInt(8, employee.getDeptId());
			insertStatement.setInt(9, employee.getProjectId());
			insertStatement.setInt(10, employee.getRoleId());

			row = insertStatement.executeUpdate();
			if (row > 0)
				status = true;
			System.out.println(row + " rows added in Employee database");

		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException){
				System.err.println("SQL Integrity Constraint violation. While adding employee");
				e.printStackTrace();
			}
			else {
				System.out.println("Error while adding new employee");
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		//TODO Multiple employee with same name. return array list of employees 
		
		
		if (type.equals(Constants.kinId)) {
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeOnKinId");
		} else if (type.equals(Constants.emailId)) {
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeOnEmailId");
		} else if (type.equals(Constants.name)) {
			value="%"+value+"%";
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
				tempEmp.setDateOfBirth(rs.getString(5));
				tempEmp.setDateOfJoining(rs.getString(6));
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
				tempEmp.setDateOfBirth(rs.getString(5));
				tempEmp.setDateOfJoining(rs.getString(6));
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
