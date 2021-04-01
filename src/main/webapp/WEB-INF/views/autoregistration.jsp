<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Auto Registration Form</title>
	<link href="<c:url value='/static/css/bootstrap.css' />" rel="stylesheet"></link>
	<link href="<c:url value='/static/css/app.css' />" rel="stylesheet"></link>
</head>

<body>
 	<div class="generic-container">
		<%@include file="authheader.jsp" %>

		<div class="well lead">Auto Registration Form</div>
	 	<form:form method="POST" modelAttribute="auto" class="form-horizontal">
			<form:input type="hidden" path="id" id="id"/>
			
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="modello">Modello</label>
					<div class="col-md-7">
						<form:input type="text" path="modello" id="modello" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="modello" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="casaCostruttrice">Casa Costruttrice</label>
					<div class="col-md-7">
						<form:input type="text" path="casaCostruttrice" id="casaCostruttrice" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="casaCostruttrice" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="targa">Targa</label>
					<div class="col-md-7">
						<c:choose>
							<c:when test="${edit}">
								<form:input type="text" path="targa" id="targa" class="form-control input-sm" readonly="true"/>
							</c:when>
							<c:otherwise>
								<form:input type="text" path="targa" id="targa" class="form-control input-sm" />
								<div class="has-error">
									<form:errors path="targa" class="help-inline"/>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="annoImmatricolazione">Anno di immatricolazione</label>
					<div class="col-md-7">
						<form:input type="text" path="annoImmatricolazione" id="annoImmatricolazione" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="annoImmatricolazione" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>
	
			<div class="row">
				<div class="form-group col-md-12">
					<label class="col-md-3 control-lable" for="tipologia">Tipologia</label>
					<div class="col-md-7">
						<form:input type="text" path="tipologia" id="tipologia" class="form-control input-sm" />
						<div class="has-error">
							<form:errors path="tipologia" class="help-inline"/>
						</div>
					</div>
				</div>
			</div>


	
			<div class="row">
				<div class="form-actions floatRight">
					<c:choose>
						<c:when test="${edit}">
							<input type="submit" value="Update" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/autolist' />">Cancel</a>
						</c:when>
						<c:otherwise>
							<input type="submit" value="Register" class="btn btn-primary btn-sm"/> or <a href="<c:url value='/autolist' />">Cancel</a>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

		</form:form>
	</div>
</body>
</html>

