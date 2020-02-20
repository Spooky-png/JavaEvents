<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
			rel="stylesheet" 
			integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
			crossorigin="anonymous">
			<link rel="stylesheet" type="text/css" href="/css/editEvents.css" />
<meta charset="ISO-8859-1">
<title>Edit Event</title>
</head>
<body>
<div class="container">
<div class="edit-event float float-left">
				<h1>Edit <c:out value="${event.name }"></c:out></h1>
				<p><form:errors path="event.*"/></p>
				<p><c:out value="${error}"/></p>
			<form:form action="/editevent/${event.id}" method="post" modelAttribute="event">
				<input type="hidden" name="_method" value="put">
				<form:hidden value="${ userID}" path="host"/>
				<div class="form-group">
					<form:label path="name">Name</form:label>
					<form:errors path="name"></form:errors>
					<form:input class="form-control" path="name"></form:input>
				</div>
				<div class="form-group">
					<form:label path="date">Date</form:label>
					<form:errors path="date"></form:errors>
					<form:input class="form-control" type="date" path="date"></form:input>
				</div>
				 <div class="form-group">
			        <form:label path="location">City</form:label>
			        <form:errors path="location"/>
			        <form:input class="form-control" path="location" />
			    </div>
			    <div class="form-group">
			        <form:label path="state">State</form:label>
			        <form:errors path="state"/>
					<form:input class="form-control" path="state"/>
			    </div>
			    <button class="myButton">Edit Event</button>
			</form:form>
			</div>
			</div>
</body>
</html>