<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page errorPage="WEB-INF/showErrorMessage.jsp" %>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>   *******   Employee Management System  ******* </title>
</head>

<body>

<h3>Choose option : </h3>

<a href="controller?action=addEmployee">Add Employee</a><br/>
<a href="controller?action=searchEmployee">Search Employee</a><br/>
<a href="controller?action=deleteEmployee">Delete Employee</a><br/>
<a href="controller?action=showAllEmployees">Show all employees</a>

</body>
</html>