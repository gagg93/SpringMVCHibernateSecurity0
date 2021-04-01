<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Auto List</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
	<div class="generic-container">
		<%@include file="authheader.jsp" %>	
		<div class="panel panel-default">
			  <!-- Default panel contents -->
		  	<div class="panel-heading"><span class="lead">List of Autos </span></div>
			<span class="floatRight">
					<form:form method="POST" modelAttribute="researchform" class="form-horizontal">
						<div class="form-inline">
							<label for="research">Ricerca</label>
							<form:select class="form-control" id="research" path="field">
								<option>Targa</option>
								<option>Modello</option>
								<option>Casa Costruttrice</option>
								<option>Anno di immatricolazione</option>
								<option>Tipologia</option>
							</form:select>
							<form:input type="text" id="key" class="form-control input-sm" path="key"/>
							<input type="submit" value="Cerca" class="btn btn-primary btn-sm"/>
						</div>
					</form:form>
			</span><br>
			<table class="table table-hover">
	    		<thead>
		      		<tr>
				        <th>Targa</th>
				        <th>Casa costruttrice</th>
				        <th>Modello</th>
				        <th>Anno di immatricolazione</th>
						<th>Tipologia</th>
						<sec:authorize access="hasRole('ADMIN')">
				        	<th width="100"></th>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
				        	<th width="100"></th>
				        </sec:authorize>
				        
					</tr>
		    	</thead>
	    		<tbody>
				<c:forEach items="${autos}" var="auto">
					<tr>
						<td>${auto.targa}</td>
						<td>${auto.casaCostruttrice}</td>
						<td>${auto.modello}</td>
						<td>${auto.annoImmatricolazione}</td>
						<td>${auto.tipologia}</td>
						<sec:authorize access="hasRole('ADMIN')">
							<td><a href="<c:url value='/edit-auto-${auto.id}' />" class="btn btn-success custom-width">edit</a></td>
				        </sec:authorize>
				        <sec:authorize access="hasRole('ADMIN')">
							<td><a href="<c:url value='/delete-auto-${auto.id}' />" class="btn btn-danger custom-width">delete</a></td>
        				</sec:authorize>
					</tr>
				</c:forEach>
	    		</tbody>
	    	</table>
		</div>
		<sec:authorize access="hasRole('ADMIN')">
		 	<div class="well">
		 		<a href="<c:url value='/newauto' />">Add New Auto</a>
		 	</div>
	 	</sec:authorize>
   	</div>
</body>
</html>