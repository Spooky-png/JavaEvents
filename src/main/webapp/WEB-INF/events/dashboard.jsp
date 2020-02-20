<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt" %>  
<!DOCTYPE html>
<html>
	<head>
		<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
			rel="stylesheet" 
			integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" 
			crossorigin="anonymous">
		<link rel="stylesheet" href="css/dashboard.css" />
		<meta charset="ISO-8859-1">
	<title>Event Planner</title>
</head>
	<body>
	<div id="background">
		<div class="container">
			<h2>Welcome, ${ user.firstName }</h2>
			<a href="/logout">Logout</a>
			<hr />
			<div class="new-event float float-right">
				<p>Plan an Event:</p>
				<p><form:errors path="event.*"/></p>
				<p><c:out value="${error}"/></p>
				<form:form action="/newevent" method="post" modelAttribute="event">
					<form:hidden value="${ user.id }" path="host"/>
					<div class="form-group">
						<form:label path="name">Name</form:label>
						<form:errors path="name"></form:errors>
						<form:input class="form-control" path="name"></form:input>
					</div>
					<div class="form-group">
						<form:label path="date">Date</form:label>
						<form:errors path="date"></form:errors>
						<form:input class="form-control" type="date" value="${ now }" path="date"></form:input>
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
				    <button>Create Event</button>
			</form:form>
			</div>
			<div class="float float-left">
				<h3>Here are some events in your state:</h3>
				<table class="table table-hover table-dark">
					<thead>
						<tr>
							<th>Name</th>
							<th>Date</th>
							<th>City</th>
							<th>Host</th>
							<th>Action/Status</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${ eventsInState }" var="event">
						<tr>
							<td><a href="/events/event/${ event.id }">${ event.name }</a></td>
							<td><fmt:formatDate value="${event.date}" pattern="MMMM/d/yyyy" /></td>
							<td>${ event.location }</td>
							<td>${ event.host.firstName }</td>
							<td>
							<c:choose>
								<c:when test="${ event.host.id == user.id }">
									<a class="myButton" href="/events/edit/${ event.id }">Edit</a> |
									<form class="delete-form" action="/events/event/${ event.id }" method="post">
										<input type="hidden" name="_method" value="delete" />
										<button class="deleteButton">Delete</button>
									</form>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ event.attendees.contains(user) }">
											<span>Joining <a href="/events/${ event.id }/a/cancel">Cancel</a></span>
										</c:when>
										<c:otherwise>
											<a href="/events/${ event.id }/a/join">Join</a>								
										</c:otherwise>
									
									</c:choose>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<h3>Here are some events in other states:</h3>
				<table class="table table-hover table-dark">
					<thead>
						<tr>
							<th>Name</th>
							<th>Date</th>
							<th>City</th>
							<th>State</th>
							<th>Host</th>
							<th>Action/Status</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${ otherEvents }" var="event">
						<tr>
							<td><a href="/events/event/${ event.id }">${ event.name }</a></td>
							<td><fmt:formatDate value="${event.date}" pattern="MMMM/d/yyyy" /></td>
							<td>${ event.location }</td>
							<td>${ event.state }</td>
							<td>${ event.host.firstName }</td>
							<td>
							<c:choose>
								<c:when test="${ event.host.id == user.id }">
									<a href="/events/edit/${event.id}">Edit</a> |
									<form class="delete-form" action="/events/${ event.id }" method="post">
										<input type="hidden" name="_method" value="delete" />
										<button>Delete</button>
									</form>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ event.attendees.contains(user) }">
											<span>Joining <a href="/events/${ event.id }/a/cancel">Cancel</a></span>
										</c:when>
										<c:otherwise>
											<a href="/events/${ event.id }/a/join">Join</a>								
										</c:otherwise>
									
									</c:choose>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		</div>
	</body>
</html>