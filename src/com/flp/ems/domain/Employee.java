package com.flp.ems.domain;

import com.flp.ems.util.Constants;

public class Employee implements Cloneable {
	
	private int empId = 0;
	private String kinId = "NA";
	private String name = "NA";
	private String emailId = "NA";
	private long phoneNo = 00_0000_0000;
	private String dateOfBirth = "dd/MM/yyyy";
	private String dateOfJoining = "dd/MM/yyyy";
	private String address = "NA";
	private int deptId = -1;
	private int projectId = -1;
	private int roleId = -1;

	public Employee() {
	}

	//IF EMAIL IS AUTO GENERATED CALL THIS CONSTRUCTOR
	public Employee(String kinId, String name, long phoneNo, String dateOfBirth, String dateOfJoining,
			String address, int deptId, int projectId, int roleId) {
		this.kinId = kinId;
		this.name = name;
		this.emailId = generateEmailId(name);
		this.phoneNo = phoneNo;
		this.dateOfBirth = dateOfBirth;
		this.dateOfJoining = dateOfJoining;
		this.address = address;
		this.deptId = deptId;
		this.projectId = projectId;
		this.roleId = roleId;
	}

	
	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getKinId() {
		return kinId;
	}
	
	public void setKinId(String kinId) {
		this.kinId = kinId;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		//change in name will change the email id
		this.emailId = generateEmailId(name);
	}

	public String getEmailId() {
		return emailId;
	}

	//EMAIL ID WILL BE BASED ON NAME , ONE CAN'T SET IT MANUALLY
	/*public void setEmailId(String emailId) {
		this.emailId = emailId;
	}*/

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
		Employee emp = (Employee)obj;
		return (this.kinId.equals(emp.kinId) && this.emailId.equals(emp.emailId));
	}
	
	@Override
	public int hashCode() {
		
		return super.hashCode();
	}
	
	@Override
	public Employee clone() throws CloneNotSupportedException {

	    return (Employee)super.clone();
	}

	private String generateEmailId(String name){
		String email = name.replaceAll("\\s+","").toLowerCase()+"@"+Constants.emailSuffix+".com";
		return email;
	}
}
