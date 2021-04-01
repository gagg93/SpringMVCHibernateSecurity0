<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Users List</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="generic-container">
		<%@include file="authheader.jsp" %>	
		<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<div class="panel-heading"><span class="lead">List of Users </span></div>
			<span class="floatRight">
					<form:form method="POST" modelAttribute="researchform" class="form-horizontal">
						<div class="form-inline">
							<label for="exampleFormControlSelect1">Ricerca</label>
							<form:select class="form-control" id="exampleFormControlSelect1" path="field">
							  <option>First name</option>
							  <option>Last name</option>
							  <option>Username</option>
							  <option>Data di nascita</option>
							</form:select>

							<form:input type="text" id="key" class="form-control input-sm" path="key"/>
							<form:errors path="key" class="help-inline"/>
							<input type="submit" value="Cerca" class="btn btn-primary btn-sm"/>
						</div>
					</form:form>
			</span><br>
			<table class="table table-hover">
	    		<thead>
		      		<tr>
				        <th>Firstname</th>
				        <th>Lastname</th>
				        <th>Username</th>
				        <th>Data di nascita</th>
						<th width="100"></th>
						<th width="100"></th>
						<th width="100"></th>

					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${users}" var="user">
					<tr>
						<td>${user.firstName}</td>
						<td>${user.lastName}</td>
						<td>${user.username}</td>
						<td>${user.dataDiNascita}</td>
						<td><a href="<c:url value='/edit-user-${user.id}' />" class="btn btn-success custom-width">edit</a></td>
						<td><a href="<c:url value='/delete-user-${user.id}' />" class="btn btn-danger custom-width">delete</a></td>
						<td><a href="<c:url value='/prenotazioni-user-${user.id}' />" class="btn btn-danger custom-width">noleggi</a></td>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
		</div>
		<sec:authorize access="hasRole('ADMIN')">
		 	<div class="well">
		 		<a href="<c:url value='/newuser' />">Add New User</a>
		 	</div>
	 	</sec:authorize>
   	</div>
</body>
</html>