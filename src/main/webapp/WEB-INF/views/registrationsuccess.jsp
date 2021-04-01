<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Registration Confirmation Page</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>
<body>
	<div class="generic-container">
		<%@include file="authheader.jsp" %>
		
		<div class="alert alert-success lead">
	    	${success}
		</div>
		
		<span class="well floatRight">
			<sec:authorize access="hasRole('ADMIN')">
				Go to <a href="<c:url value='/${returnpage}list' />">${returnpage} List</a>
			</sec:authorize>
			<sec:authorize access="hasRole('CUSTOMER')">
				Go to <a href="<c:url value='/prenotazioni-user-0' />">your Prenotazioni</a>
			</sec:authorize>
		</span>
	</div>
</body>

</html>