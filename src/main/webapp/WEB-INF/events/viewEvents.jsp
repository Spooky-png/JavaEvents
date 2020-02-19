<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
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
</body>
</html>