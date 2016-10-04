package com.flp.ems.domain;

public class Role {
	private int roleId = -1;
	private String roleName = "NA";
	private String roleDescription = "NA";

	public Role(int roleId, String roleName, String roleDescription) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDescription = roleDescription;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getroleDescription() {
		return roleDescription;
	}

	public void setroleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

}
