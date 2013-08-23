<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
   
<style>
.error {
	color: #ff0000;
	font-style: italic;
}

div.ex {
	color:blue;
	font-style: italic
}
</style>

<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
});

$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {

	});
});

function goBack(){
	
	location.href = '${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}';
}
</script>

<input type="submit" value="Back" onClick="goBack()"/> 
<br><br>
<form:form method="POST" commandName="collaboratorForm"
action="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/deleteCollaborators">
    
    <input type="submit" value="Delete">	
	<table style="width:100%" cellpadding="0" cellspacing="0" border="0" class="display dataTable">	
	
	<thead>
		<tr>
			<th align="left">collaborator</th>
			<th align="left">roles</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="collab" items="${collaboratorForm.collaborators}" varStatus="status">
		<tr>
		  <td><form:checkbox path="collaborators[${status.index}].userName" value="${collab.userName}" />
		  <form:label path="collaborators[${status.index}].userName">
		  <c:out value="${collab.userName}"/>
		  </form:label>
		  <form:errors path="collaborators[${status.index}].userName" cssClass="error"/>
		  </td>
		  <td>
			  <c:forEach var="roles" items="${collab.collaboratorRoles}">
			  	<c:out value="${roles.displayName}"></c:out>||
			  </c:forEach>
		  </td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</form:form>