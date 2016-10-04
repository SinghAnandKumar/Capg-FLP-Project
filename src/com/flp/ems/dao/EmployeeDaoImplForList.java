package com.flp.ems.dao;

import java.util.ArrayList;
import java.util.Iterator;

import com.flp.ems.domain.Employee;
import com.flp.ems.util.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class EmployeeDaoImplForList implements IemployeeDao, Cloneable {

	private ArrayList<Employee> employees = new ArrayList<Employee>();
	Iterator<Employee> itr = null;

	@Override // ADDING A NEW EMPLOYEE
	public boolean addEmployee(Employee employee) {
		boolean status = false;
		status = employees.add(employee);

		return status;
	}

	@Override // MODIFYING AN EMPLOYEE
	public boolean modifyEmployee(Employee employee) {
		int index = indexOf(Constants.kinId, employee.getKinId());
		if (index != -1) {
			employees.set(index, employee);
			return true;
		}
		return false;
	}

	@Override /// REMOVING AN EMPLOYEE
	public boolean removeEmployee(String kinId) {
		int index = indexOf(Constants.kinId, kinId);
		if (index != -1) {
			employees.remove(index);
			return true;
		}
		return false;
	}

	@Override
	public ArrayList<Employee> getAllEmployees() {
		ArrayList<Employee> emp = new ArrayList<>();

		for (Employee e : employees) {
			emp.add(e);
		}

		return emp;
	}

	/*
	 * @Override public Employee searchEmployee(String type, String value) {
	 * getEmployee(type, value) return emp; }
	 */

	@Override
	public Employee searchEmployee(String type, String value) {
		int index = indexOf(type, value);
		Employee emp = null;
		
		if(index != -1){
			emp = employees.get(index);
			try {
				return emp.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	private int indexOf(String type, String value) {
		itr = employees.iterator();
		int index = -1;
		boolean found = false;

		if (type.equals(Constants.kinId)) {
			while (itr.hasNext()) {
				Employee emp = (Employee) itr.next();
				index++;
				if (emp.getKinId().equals(value)) {
					found = true;
					break;
				} // end of if
			} // end of while
		}
		else if(type.equals(Constants.emailId)){
			while (itr.hasNext()) {
				Employee emp = (Employee) itr.next();
				index++;
				if (emp.getEmailId().equals(value)) {
					found = true;
					break;
				} // end of if
			} // end of while
		}
		else if(type.equals(Constants.name)){
			while (itr.hasNext()) {
				Employee emp = (Employee) itr.next();
				index++;
				if (emp.getName().equals(value)) {
					found = true;
					break;
				} // end of if
			} // end of while
		}
		else
			return -1;

		if (found == true)
			return index;

		return -1;
	}

}
