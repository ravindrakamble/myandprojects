<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.r2.appservices.dao.TrackerDAO" %>
<%@ page import="com.r2.appservices.dao.User" %>
<!DOCTYPE html>
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/normalize.css"/>
<link rel="stylesheet" type="text/css" href="css/login.css"/>
<title>Hello Achanak Team</title>
</head>

<body>
	<h1 id = "header" name="header" style="text-align:center">Welcome to Achanak Team Tracking System</h1>

	<section class="loginform cf">
	<form name="login" action="/login" method="post"
		accept-charset="utf-8">
		<ul>
			<li><label for="usermail">Email</label> <input type="email"
				name="username" placeholder="yourname@email.com" required></li>
			<li><label for="password">Password</label> <input
				type="password" name="password" placeholder="password" required></li>
			<li><input type="submit" value="Login"></li>
		</ul>
		<div id="error" name="error">${message}</div> 
	</form>
	</section>
</body>
</html>
