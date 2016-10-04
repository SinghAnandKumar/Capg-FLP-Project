package com.flp.ems.dao;

import java.util.ArrayList;

import com.flp.ems.domain.Employee;

public interface IemployeeDao {
	public boolean addEmployee(Employee employee);

	public boolean modifyEmployee(Employee employee);

	public boolean removeEmployee(String kinId);

	public Employee searchEmployee(String type,String value);
	
	//public void insertDummyData() throws SQLException;
	
	public ArrayList<Employee> getAllEmployees();
}
