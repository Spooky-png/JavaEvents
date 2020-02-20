<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
	<head>
		<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
			rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
		<link rel="stylesheet" type="text/css" href="/css/viewEvent.css" />
		<meta charset="ISO-8859-1">
		<title>Event Details</title>
	</head>
<body>
	<div id="background">
		<div class="container">
		<a href="/events">Dashboard</a>
		<div class="messagewall float float-right">
				<h3>Message Wall</h3>
				<c:forEach items="${ event.messages }" var="message">
					<h6>
						<c:out value="${message.writtenBy.firstName }"></c:out>
						:
						<c:out value="${message.content }"></c:out>
					</h6>
					<hr>
				</c:forEach>
				<form:form method="POST" action="/newmessage/${event.id}"
					modelAttribute="message">
					<form:hidden value="${ user.id }" path="writtenBy" />
					<form:hidden value="${ event.id }" path="event" />
					<p>
						<form:input path="content" placeholder="Add Comment" />
					</p>

					<input type="submit" value="Submit" />
				</form:form>
			</div>
			<h1>
				<c:out value="${event.name }"></c:out>
			</h1>
			<p>
				Host:
				<c:out value="${event.host.firstName }"></c:out>
			</p>
			<p>
				Date:
				<fmt:formatDate value="${event.date}" pattern="MMMM/d/yyyy" />
			</p>
			<p>
				Location:
				<c:out value="${event.location }"></c:out>
				,
				<c:out value="${event.state }"></c:out>
			</p>
			<p>
				People who are attending this event:
				<c:out value="${number}"></c:out>
			</p>
			
			<div class="attendees">
				<table class="table table-bordered table-dark">
					<thead>
						<tr>
							<th>Name</th>
							<th>Location</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${ event.attendees }" var="attendee">
							<tr>
								<td><c:out value="${attendee.firstName }"></c:out> <c:out
										value="${attendee.lastName }"></c:out></td>
								<td><c:out value="${attendee.location }"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
		</div>
	</div>
</body>
</html>