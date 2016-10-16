<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page errorPage="WEB-INF/showErrorMessage.jsp" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a New Employee</title>
</head>
<body>

<form method="post" action="controller">
<input type="hidden" name="action" value="saveEmployee">
<input type="hidden" name="empId" value='${emp.getEmpId()}'>

<p><a href="controller?action=showAllEmployees">[Return to List]</a></p>
<p><a href="controller?action=homePage">[Go To Home page]</a></p>

Enter/Modify Employee data :
<table>

	<tr>
		<td>Employee ID : </td>
		<td><input type="text" name="empId" value="${emp.getEmpId()}"  readonly="readonly" disabled="disabled"/></td>
	</tr>
	
	<tr>
		<td>Kin ID : </td>
		<td><input type="text" name="kinId" value="${emp.getKinId()}" readonly="readonly" disabled="disabled"/></td>
	</tr>

	<tr>
		<td>Name : </td>
		<td><input type="text" name="name" value="${emp.getName()}" /></td>
	</tr>
	
	<tr>
		<td>Email ID : </td>
		<td><input type="text" name="emailId" value="${emp.getEmailId()}" readonly="readonly" disabled="disabled"/></td>
	</tr>
	
	<tr>
		<td>Phone No. : </td>
		<td><input type="text" name="phoneNo" value="${emp.getPhoneNo()}" /></td>
	</tr>

	<tr>
		<td>Date Of Birth : </td>
		<td><input type="text" name="dateOfBirth" value="${emp.getDateOfBirth()}" /></td>
	</tr>
	
	<tr>
		<td>Date Of Joining : </td>
		<td><input type="text" name="dateOfJoining" value="${emp.getDateOfJoining()}" /></td>
	</tr>
	
	<tr>
		<td>Address : </td>
		<td><input type="text" name="address" value="${emp.getAddress()}" /></td>
	</tr>
	
	<tr>
		<td>Department ID : </td>
		<td><input type="text" name="deptId" value="${emp.getDeptId()}"/></td>
	</tr>
	
	<tr>
		<td>Project ID : </td>
		<td><input type="text" name="projectId" value="${emp.getProjectId()}"/></td>
	</tr>
	
	<tr>
		<td>Role ID : </td>
		<td><input type="text" name="roleId" value="${emp.getRoleId()}"/></td>
	</tr>

    <tr>
      <td colspan="2">
        <input type="submit" name="save" value="Save" /> 
        &nbsp;&nbsp;
        <input type="reset" name="reset" value="Reset" />
      </td>
    </tr>
      
</table>

</form>

</body>
</html>