<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
			rel="stylesheet" 
			integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
			crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Event Details</title>
</head>
<body>
<h1><c:out value="${event.name }"></c:out></h1>
<p><c:out value="${event.host.firstName }"></c:out></p>
<p><c:out value="${event.date }"></c:out></p>
<p><c:out value="${event.location }"></c:out><c:out value="${event.state }"></c:out></p>
<c:forEach items="${ event.attendees }" var="attendee">
<p>People Attending<c:out value="${attendee.firstName }"></c:out><c:out value="${attendee.lastName }"></c:out><c:out value="${attendee.location }"></c:out></p>
</c:forEach>
<c:out value="${number}"></c:out>
<form:form method="POST" action="/newmessage/${event.id}" modelAttribute="message">
<form:hidden value="${ user.id }" path="writtenBy"/>
<form:hidden value="${ event.id }" path="event"/>
    	<p>
            <form:label path="content">Add Comment:</form:label>
            <form:input path="content"/>
        </p>

        <input type="submit" value="Submit"/>
    </form:form>
    <c:forEach items="${ event.messages }" var="message">
    <p><c:out value="${message.writtenBy.firstName }"></c:out><c:out value="${message.content }"></c:out></p>
    </c:forEach>
</body>
</html>