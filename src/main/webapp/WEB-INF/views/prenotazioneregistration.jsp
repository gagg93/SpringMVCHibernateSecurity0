<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User Registration Form</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
 	<div class="generic-container">
		<%@include file="authheader.jsp" %>

		<div class="well lead">Prenotazione Registration Form</div>
	 	<form:form method="POST" modelAttribute="prenotazioneDto" class="form-horizontal">
			<form:input type="hidden" path="id" id="id"/>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="username">Username</label>
					<div class="col-md-7">
						<form:input type="text" path="username" id="username" value="${loggedinuser}" readonly="true" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="username" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="targa">Targa</label>
					<div class="col-md-7">
						<form:input type="text" path="targa" id="targa" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="targa" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="dataDiInizio">Data di fine</label>
					<div class="col-md-7">
						<form:input type="text" path="dataDiInizio" id="dataDiInizio" class="form-control input-sm" readonly="true"/>
						<div class="has-error">
							<form:errors path="dataDiInizio" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>



			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="dataDiFine">Data di fine</label>
					<div class="col-md-7">
						<form:input type="text" path="dataDiFine" id="dataDiFine" class="form-control input-sm" readonly="true"/>
						<div class="has-error">
							<form:errors path="dataDiFine" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/prenotazionelist' />">Cancel</a>
						</c:when>
						<c:otherwise>
							<sec:authorize access="hasRole('ADMIN')">
								<input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/prenotazionelist' />">Cancel</a>
							</sec:authorize>
							<sec:authorize access="hasRole('CUSTOMER')">
								<input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/prenotazioni-user-0' />">Cancel</a>
							</sec:authorize>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

		</form:form>
	</div>
</body>
</html>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.11.3.min.js"></script>

<!-- Include Date Range Picker -->
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/css/bootstrap-datepicker3.css"/>

<script>
	$(document).ready(function(){
		var date_input=$('input[name="dataDiInizio"]'); //our date input has the name "date"
		var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		date_input.datepicker({
			format: 'dd/mm/yyyy',
			container: container,
			todayHighlight: true,
			autoclose: true,
			startDate: '+2d'
		})
	})
</script>
<script>
	$(document).ready(function(){
		var date_input=$('input[name="dataDiFine"]'); //our date input has the name "date"
		var container=$('.bootstrap-iso form').length>0 ? $('.bootstrap-iso form').parent() : "body";
		date_input.datepicker({
			format: 'dd/mm/yyyy',
			container: container,
			todayHighlight: true,
			autoclose: true,
			startDate: '+2d'
		})
	})
</script>