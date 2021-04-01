<div class="authbar">
		<span>Dear <strong>${loggedinuser}</strong>, Welcome to CrazyUsers.</span> <span class="floatRight"><a href="<c:url value="/logout" />">Logout</a></span><br>

		<sec:authorize access="hasRole('ADMIN')">
			<a href="<c:url value='/userlist' />">Users </a> <a href="<c:url value='/autolist' />">Auto </a> <a href="<c:url value='/prenotazionelist' />">Prenotazioni</a>
		</sec:authorize>
		<sec:authorize access="hasRole('CUSTOMER')">
			<a href="<c:url value='/edit-profile' />">Profilo </a> <a href="<c:url value='/autolist' />">Auto </a> <a href="<c:url value='/prenotazioni-user-0' />">Prenotazioni </a>
		</sec:authorize>
</div>
