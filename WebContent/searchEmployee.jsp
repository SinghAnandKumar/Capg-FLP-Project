<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script type="text/javascript" language="JavaScript">

function radioWithText(d) {
    document.getElementById('name').style.display = "none";
    document.getElementById('email').style.display = "none";
    document.getElementById('kinId').style.display = "none";
    document.getElementById(d).style.display='inline'; 
}
</script>

</head>

<body>

<form action="controller"  method="post">
<input type="hidden" name="action" value="showAllEmployees" />

Search By : <br/>
<input type="radio" name="type" value="name" onclick="radioWithText('name')" checked>Name<br/>
<input type="radio" name="type" value="emailId" onclick="radioWithText('email')">Email Id<br/>
<input type="radio" name="type" value="kinId" onclick="radioWithText('kinId')" >Kin Id<br/><br/><br/>


<div id="name" style="display: visible;">
	Enter Name : <input type="text" name="name" >
</div>

<div id="email" style="display: none;">
	Enter Email Id: <input type="text" name="emailId" >
</div>

<div id="kinId" style="display: none;">
	Enter KinID : <input type="text" name="kinId" >
</div>

<br/>

<input type="submit" name="search" value="Search"> 
</form>

</body>
</html>