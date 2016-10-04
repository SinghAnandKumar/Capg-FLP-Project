package com.flp.ems.domain;

public class Department {
	int deptId = -1;
	String deptName = "NA";
	String deptDescription = "NA";

	public Department(int deptId, String deptName, String deptDescription) {
		this.deptId = deptId;
		this.deptName = deptName;
		this.deptDescription = deptDescription;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getdeptDescription() {
		return deptDescription;
	}

	public void setdeptDescription(String deptDescription) {
		this.deptDescription = deptDescription;
	}

}
