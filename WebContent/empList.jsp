<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page errorPage="WEB-INF/showErrorMessage.jsp" %> 
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of All Employees In the Organisation</title>
</head>
<body>

 	<p><a href="controller?action=addNewEmp">[Add New Employee]</a></p>
 	<p><a href="controller?action=homePage">[Go To Home page]</a></p>
  
  <form name="deleteForm" method="post" action="controller">
  		<input type="hidden" name="action" value="deleteEmp" />
  	<table>
    	<tr>
    	  	<th><a href="javascript:checkAll(document.deleteForm.id)">Select All</a></th>
    	  	<th>Emp ID</th>
	      	<th>Kin ID</th>
      		<th>Name </th>
      		<th>Email ID</th>
      		<th>Phone No</th>
      		<th>Date Of Birth</th>
      		<th>Date Of Joining</th>
      		<th>Address </th>
      		<th>Department ID</th>
      		<th>Project ID</th>
      		<th>Role ID</th>
			<th>Action</th>      		
      		
    	</tr>
    
    
    <c:forEach items='${empList}' var='emp' >
      <tr>
      <td><input type="checkbox" name="empId" value="${emp.getEmpId()}"></td>
      <td>${emp.getKinId()}</td>
      <td>${emp.getName()}</td>
      <td>${emp.getEmailId()}</td>
      <td>${emp.getPhoneNo()}</td>
      <td>${emp.getDateOfBirth()}</td>
      <td>${emp.getDateOfJoining()}</td>
      <td>${emp.getAddress()}</td>
      <td>${emp.getDeptId()}</td>
      <td>${emp.getProjectId()}</td>
      <td>${emp.getRoleId()}</td>
      <td><a href="controller?action=modifyEmployee&id=${emp.getEmpId()}">Modify</a></td>
      </tr>
    </c:forEach>
    
    <tr>
      <td colspan="5">
        <input type="submit" name="Delete Checked" value="Delete Checked" />
        &nbsp;&nbsp;
        <input type="reset" name="Reset" value="Reset" />
      </td>
    </tr>
    
  </table>
  </form>

</body>
</html>