<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin Login Page</title>
<link rel="icon" href="../views/images/favicon.ico" type="image/x-icon">
<style>
</style>
<link rel="stylesheet" type="text/css" href="../views/css/main.css">
<script src="/ekataBookStore/views/js/jquery.js" type="text/javascript"></script>
<script src="/ekataBookStore/views/js/ajax.js" type="text/javascript"></script>
</head>
<body>
<div class="background">
  <div class="transbox" align="center">

<h3>Admin User LogIn</h3>
<table>
<tr>
<td>UserName:</td><td><input type="text" name="username" id="username"> </td>
</tr>
<tr>
<td>PassWord:</td><td><input type="password" name="userpwd" id="userpwd"></td>
</tr>
<tr>				
<td></td><td><input type="submit" value="submit" id="loginbtn" name="loginbtn"></td>
</tr>
</table>

<div id="ajaxResponse">

</div>
</div>
</div>
</body>
</html>