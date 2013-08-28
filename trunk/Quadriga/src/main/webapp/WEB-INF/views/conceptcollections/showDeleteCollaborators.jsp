<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
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
</script>

<form:form method="POST" commandName="collaboratorForm" 
action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/deleteCollaborator">
<input type="submit" value="Delete" />
  <%--  <input type="submit" value="Delete" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/deleteCollaborator'" > --%>
<table style="width:100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">					
	<thead>
		<tr>
		<%-- 	<th align="left"><input type="checkbox" class="selectAll" name="selected" value="check all"/>select All</th>--%>	
			<th align="left">collaborator</th>
			<th>roles</th>	
		</tr>	
	</thead>
	
	<tbody>
	<c:forEach var="collab" items="${collaboratorForm.collaborators}" varStatus="status">
		<tr>
		  <td><form:checkbox path="collaborators[${status.index}].userName" value="${collabUser.userName}" />
			<form:label path="collaborators[${status.index}].userName">
				<c:out value="${collabUser.userName}"></c:out>
			</form:label>
			<form:errors path="collaborators[${status.index}].userName" cssClass="error"></form:errors> 
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
    