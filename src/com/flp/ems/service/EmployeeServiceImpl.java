package com.flp.ems.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.flp.ems.dao.EmployeeDaoImplForDB;
import com.flp.ems.domain.Employee;
import com.flp.ems.util.Constants;

public class EmployeeServiceImpl implements IemployeeService {

	Employee employee = null;
	// EmployeeDaoImplForList employeeDao = new EmployeeDaoImplForList();
	EmployeeDaoImplForDB employeeDao = null;
	// employeeDao = new EmployeeDaoImplForDB();

	public EmployeeServiceImpl() {
		try {
			employeeDao = new EmployeeDaoImplForDB();
			
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean addEmployee(HashMap<String, String> employeeData) {
		employee = new Employee();
		boolean status = false;
		// UNIQUNESS OF KINID and EMAILID

		// DATA POPULATION IN EMPLOYEE OBJECT
		employee.setName(employeeData.get(Constants.name));

		//Auto generating email ID based on name of employee
		//employee.setEmailId(employeeData.get(Constants.emailId));
		
		employee.setPhoneNo(Long.parseLong(employeeData.get(Constants.phoneNo)));
		employee.setDateOfBirth(employeeData.get(Constants.dateOfBirth));
		employee.setDateOfJoining(employeeData.get(Constants.dateOfJoining));
		employee.setAddress(employeeData.get(Constants.address));
		employee.setDeptId(Integer.parseInt(employeeData.get(Constants.departmentId)));
		employee.setProjectId(Integer.parseInt(employeeData.get(Constants.projectId)));
		employee.setRoleId(Integer.parseInt(employeeData.get(Constants.roleId)));

		status = employeeDao.addEmployee(employee);

		return status;
	}

	@Override
	public boolean modifyEmployee(HashMap<String, String> employee) {
		Employee emp = employeeDao.searchEmployee(Constants.kinId, employee.get(Constants.kinId));

		Set<Map.Entry<String, String>> mapItr = employee.entrySet();
		for (Map.Entry<String, String> e : mapItr) {

			// USER CAN'T MODIFY KINID, IT IS SET BY THE SYSTEM
			if (e.getKey().equals(Constants.name))
				emp.setName(e.getValue());
			/*else if (e.getKey().equals(Constants.emailId))
				emp.setEmailId(e.getValue());*/
			else if (e.getKey().equals(Constants.phoneNo))
				emp.setPhoneNo(Long.parseLong(e.getValue()));
			else if (e.getKey().equals(Constants.dateOfBirth))
				emp.setDateOfBirth(e.getValue());
			else if (e.getKey().equals(Constants.dateOfJoining))
				emp.setDateOfJoining(e.getValue());
			else if (e.getKey().equals(Constants.address))
				emp.setAddress(e.getValue());
			else if (e.getKey().equals(Constants.departmentId))
				emp.setDeptId(Integer.parseInt(e.getValue()));
			else if (e.getKey().equals(Constants.projectId))
				emp.setProjectId(Integer.parseInt(e.getValue()));
			else if (e.getKey().equals(Constants.roleId))
				emp.setRoleId(Integer.parseInt(e.getValue()));
			else
				System.out.println("Invalid Modification ");
		}

		boolean status = employeeDao.modifyEmployee(emp);
		return status;
	}

	@Override
	public boolean removeEmployee(String kinId) {
		boolean status = employeeDao.removeEmployee(kinId);
		return status;
	}

	@Override
	public Employee searchEmployee(String type, String value) {
		return employeeDao.searchEmployee(type, value);
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		employees = employeeDao.getAllEmployees();

		return employees;
	}

}
