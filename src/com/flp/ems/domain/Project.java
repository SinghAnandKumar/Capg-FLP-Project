package com.flp.ems.domain;

public class Project {
	private int projectId = -1;
	private String projectName = "NA";
	private String description = "NA";
	private int departmentId = -1;
	

	public Project(int projectId, String projectName, String description, int departmentId) {
		this.projectId = projectId;
		this.projectName = projectName;
		this.description = description;
		this.departmentId = departmentId;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getprojectName() {
		return projectName;
	}

	public void setprojectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getdepartmentId() {
		return departmentId;
	}

	public void setdepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

}
