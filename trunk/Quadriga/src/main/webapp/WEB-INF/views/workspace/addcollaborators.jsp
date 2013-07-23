<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style type="text/css">
table,td,th,caption {
	border: 1px solid black;
}

th {
	background-color: #E9EEF6;
	color: black;
	font-weight: bold;
}

td {
	background-color: white;
	color: black;
	white-space: wrap;
	overflow: wrap;
	text-overflow: ellipsis;
}

.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<script>
$(document).ready(function() {
	
	activeTable = $('.dataTable').dataTable({
    	"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
    });
	
	$("input[type=submit]").button().click(function(event) {
	});
});
</script>
	<form:form commandName="collaborator" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/addcollaborators">
		
		<c:if test="${not empty noncollabusers}">
		    
		   	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${noncollabusers}"  itemValue="userName" itemLabel="userName" /> 
	</form:select>
	<td><form:errors path="userObj" cssClass="error"></form:errors></td>
	<br><br>
	<form:checkboxes path="collaboratorRoles" class="roles" items="${wscollabroles}" itemValue="roleDBid" itemLabel="displayName" />
	<td><form:errors path="collaboratorRoles" cssClass="error"></form:errors></td>
	<br>	
	<input type="submit" value="Add Collaborator">
		</c:if>
		<c:if test="${empty noncollabusers}">
          All active users are collaborators to the workspace.		   
		</c:if>
		</form:form>
		<c:if test="${not empty collaboratingusers}">
		<br>
		<br>
		<table style="width: 100%" class="display dataTable" id="collabaratinguserlist">
		   <thead>
		<tr>	
			<th align="center" width="30%">Collaborator</th>
			<th align="center" width="70%">Roles</th>	
		</tr>
	</thead>
		<tbody>
	<c:forEach var="collab" items="${collaboratingusers}">
		<tr>
		 <td><c:out value="${collab.userObj.userName}"></c:out></td>
		 <td>
			<c:forEach var="roles" items="${collab.collaboratorRoles}">
		 	<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach>		 
		 </td>
		</tr>
	</c:forEach>
	</tbody>
		</table>
		</c:if>
