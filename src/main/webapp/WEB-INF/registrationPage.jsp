<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
			rel="stylesheet" 
			integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
			crossorigin="anonymous">
			<link rel="stylesheet" href="css/registration.css" />
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div class="background">
<div class="container">
<div class="register">
    <h1>Register!</h1>
    
    <p><form:errors path="user.*"/></p>
    <p><c:out value="${error}"/></p>
    
    <form:form method="POST" action="/registration" modelAttribute="user">
    	<p>
            <form:input path="firstName" placeholder="First Name"/>
        </p>
        <p>
            <form:input path="lastName" placeholder="Last Name"/>
        </p>
        <p>
            <form:input type="email" path="email" placeholder="Email"/>
        </p>
        <p>
            <form:input path="location" placeholder="City"/>
        </p>
         <p>
            <form:input path="state" placeholder="State"/>
        </p>
        <p>
            <form:password path="password" placeholder="Password"/>
        </p>
        <p>
            <form:password path="passwordConfirmation" placeholder="Confirm Password"/>
        </p>
        <input class="button" type="submit" value="Register!"/>
    </form:form>
    </div>
    </div>
    </div>
</body>
</html>