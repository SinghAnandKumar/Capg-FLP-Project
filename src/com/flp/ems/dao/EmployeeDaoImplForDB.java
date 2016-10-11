package com.flp.ems.dao;

import java.io.IOException;
import java.sql.Connection;
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
		loadData();
	}

	@Override
	public boolean addEmployee(Employee employee) {
		String insertQuery = props.getProperty("jdbc.query.insertEmployee");
		String email, newEmail = "";
		int row = 0;
		boolean status = false;
		
		int a = getLatestAutoKey();
		String suffix = String.format ("%05d", (a+1));
		employee.setKinId(Constants.Prefix+suffix);

		String autoColumns[]={"empId"};
		try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertQuery,autoColumns)) {

			insertStatement.setString(1, employee.getKinId());
			insertStatement.setString(2, employee.getName());

			// REGENERATEs EMAIL IF ALREADY EXISTS
			email = employee.getEmailId();
			if (!utils.ifEmailNotAssigned(email, dbConnection)) {
				newEmail = utils.regenerateEmail(email);
				email = newEmail;
			}

			insertStatement.setString(3, email);
			insertStatement.setString(4, Long.toString(employee.getPhoneNo()));
			insertStatement.setString(5, employee.getDateOfBirth());
			insertStatement.setString(6, employee.getDateOfJoining());
			insertStatement.setString(7, employee.getAddress());
			insertStatement.setInt(8, employee.getDeptId());
			insertStatement.setInt(9, employee.getProjectId());
			insertStatement.setInt(10, employee.getRoleId());

			row = insertStatement.executeUpdate();
			if (row > 0)
				status = true;
			System.out.println(row + " rows added in Employee database");

		} catch (SQLException e) {
			if (e instanceof SQLIntegrityConstraintViolationException) {
				System.err.println("SQL Integrity Constraint violation. While adding employee");
				e.printStackTrace();
			} else {
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
	public Employee searchEmployeeById(int empId) {
		String selectQuery = props.getProperty("jdbc.query.searchEmloyeeByEmpId");
		Employee tempEmp = null;

		try (PreparedStatement selectStatement = dbConnection.prepareStatement(selectQuery)) {

			selectStatement.setInt(1, empId);
			ResultSet rs = selectStatement.executeQuery();

			while (rs.next()) {
				tempEmp = new Employee();
				tempEmp.setEmpId(rs.getInt(1));
				tempEmp.setKinId(rs.getString(2));
				tempEmp.setName(rs.getString(3));
				tempEmp.setEmailId(rs.getString(4));
				tempEmp.setPhoneNo(Long.parseLong(rs.getString(5)));
				tempEmp.setDateOfBirth(rs.getString(6));
				tempEmp.setDateOfJoining(rs.getString(7));
				tempEmp.setAddress(rs.getString(8));
				tempEmp.setDeptId(rs.getInt(9));
				tempEmp.setProjectId(rs.getInt(10));
				tempEmp.setRoleId(rs.getInt(11));
			}
		} catch (SQLException e) {
			System.out.println("Error while searching employee");
			e.printStackTrace();
		}

		return tempEmp;
	}
	
	
	@Override
	public Employee searchEmployee(String type, String value) {
		String selectQuery = "";
		Employee tempEmp = null;

		// TODO Multiple employee with same name. return array list of employees

		if (type.equals(Constants.kinId)) {
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeByKinId");
		} else if (type.equals(Constants.emailId)) {
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeByEmailId");
		} else if (type.equals(Constants.name)) {
			value = "%" + value + "%";
			selectQuery = props.getProperty("jdbc.query.searchEmloyeeByName");
		} else {
			return null;
		}

		try (PreparedStatement selectStatement = dbConnection.prepareStatement(selectQuery)) {

			selectStatement.setString(1, value);
			ResultSet rs = selectStatement.executeQuery();

			while (rs.next()) {
				tempEmp = new Employee();
				tempEmp.setEmpId(rs.getInt(1));
				tempEmp.setKinId(rs.getString(2));
				tempEmp.setName(rs.getString(3));
				tempEmp.setEmailId(rs.getString(4));
				tempEmp.setPhoneNo(Long.parseLong(rs.getString(5)));
				tempEmp.setDateOfBirth(rs.getString(6));
				tempEmp.setDateOfJoining(rs.getString(7));
				tempEmp.setAddress(rs.getString(8));
				tempEmp.setDeptId(rs.getInt(9));
				tempEmp.setProjectId(rs.getInt(10));
				tempEmp.setRoleId(rs.getInt(11));
			}
		} catch (SQLException e) {
			System.out.println("Error while searching employee");
			e.printStackTrace();
		}

		return tempEmp;
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<>();
		Employee tempEmp = null;
		
		String selectQuery = props.getProperty("jdbc.query.readAllEmployee");

		try (Statement selectStatement = dbConnection.createStatement()) {

			ResultSet rs = selectStatement.executeQuery(selectQuery);

			while (rs.next()) {
				tempEmp = new Employee();
				tempEmp.setEmpId(rs.getInt(1));
				tempEmp.setKinId(rs.getString(2));
				tempEmp.setName(rs.getString(3));
				tempEmp.setEmailId(rs.getString(4));
				tempEmp.setPhoneNo(Long.parseLong(rs.getString(5)));
				tempEmp.setDateOfBirth(rs.getString(6));
				tempEmp.setDateOfJoining(rs.getString(7));
				tempEmp.setAddress(rs.getString(8));
				tempEmp.setDeptId(rs.getInt(9));
				tempEmp.setProjectId(rs.getInt(10));
				tempEmp.setRoleId(rs.getInt(11));

				employees.add(tempEmp);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employees;
	}
	
	
	// Return the auto generated number to be assigned to new employee
	private int getLatestAutoKey(){
		int key=0;
		
		String selectQuery=props.getProperty("jdbc.query.lastEmpId");
		try(Statement selectStatement = dbConnection.createStatement()){
			
			ResultSet rs = selectStatement.executeQuery(selectQuery);
			if(rs.next()){
				key = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return key;
	}

	// LOADS EXISTING DATA
	private void loadData() throws IOException, SQLException{
		existingDepartments = new ArrayList<>();
		existingProjects = new ArrayList<>();
		existingRoles = new ArrayList<>();
		utils = new Utils();
		
		props = utils.getProperties();

		//GETS DATABASE CONNECTION FROM UTILS CLASS
		String url = props.getProperty("jdbc.url");
		dbConnection = DriverManager.getConnection(url,"root","anand@MYSQL02");
		System.out.println("Connection succesfull ? " + (dbConnection != null));

		// TO INSERT DUMMY DATA FOR DEPARTMENT, ROLE AND PROJECT TABLES
		//utils.insertDummyData(dbConnection);

		
		
		//READ EXISTING DEPARTMENT,PROJECT,ROLE IDs
		String selectQuery=props.getProperty("jdbc.query.selectDeptIds");
		try(Statement selectStatement = dbConnection.createStatement()){
			
			ResultSet rs = selectStatement.executeQuery(selectQuery);
			while(rs.next()){
				existingDepartments.add(rs.getInt(1));
			}
		}
		
				
		selectQuery=props.getProperty("jdbc.query.selectProjectIds");
		try(Statement selectStatement = dbConnection.createStatement()){
			
			ResultSet rs = selectStatement.executeQuery(selectQuery);
			while(rs.next()){
				existingProjects.add(rs.getInt(1));
			}
		}

		
		selectQuery=props.getProperty("jdbc.query.selectRoleIds");
		try(Statement selectStatement = dbConnection.createStatement()){
			
			ResultSet rs = selectStatement.executeQuery(selectQuery);
			while(rs.next()){
				existingRoles.add(rs.getInt(1));
			}
		}

		
	}

	// GETTERS AND SETTERS
	public ArrayList<Integer> getExistingDepartments() {
		return existingDepartments;
	}

	public void setExistingDepartments(ArrayList<Integer> existingDepartments) {
		this.existingDepartments = existingDepartments;
	}

	public ArrayList<Integer> getExistingProjects() {
		return existingProjects;
	}

	public void setExistingProjects(ArrayList<Integer> existingProjects) {
		this.existingProjects = existingProjects;
	}

	public ArrayList<Integer> getExistingRoles() {
		return existingRoles;
	}

	public void setExistingRoles(ArrayList<Integer> existingRoles) {
		this.existingRoles = existingRoles;
	}

}
