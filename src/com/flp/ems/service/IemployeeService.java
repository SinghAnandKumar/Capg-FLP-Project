package com.flp.ems.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.flp.ems.domain.Employee;

public interface IemployeeService {
	public boolean addEmployee(HashMap<String, String> employee);

	public boolean modifyEmployee(HashMap<String, String> employee);

	public boolean removeEmployee(String employeeId);

	public Employee searchEmployee(String type, String value);
	
	public ArrayList<Employee> getAllEmployees();

}
