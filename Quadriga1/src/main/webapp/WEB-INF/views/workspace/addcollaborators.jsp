<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style type="text/css">

div.wrap {
		 overflow: auto;
		 margin-bottom:-30px; 
	}
	
div.ex {	
		width:200px;
		float:left;
	}
	
div.ex1 {
		width:200px;
		margin-top:-29px;
		float:left;
		overflow: auto;
	}
	
div.rolesError{	
	float:left;
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
	$("input[type=button]").button().click(function(event) {
	});
});
function onSubmit(){
	
	location.href='${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspaceid}';
}
</script>
	<form:form commandName="collaborator" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/addcollaborators">
  <h2>Project collaborators:</h2>
<h3>Workspace: ${workspacename}</h3>
<div>${workspacedesc}</div>
		<c:if test="${not empty noncollabusers}">
		<hr>
		
	<div class="wrap">
		<div class="ex">
	        <h5>select collaborator</h5>
			<form:select path="userObj" id="userName">
		    	<form:option value="NONE" label="----- Select -----"/>
		   		<form:options items="${noncollabusers}"  itemValue="userName" itemLabel="name" /> 
			</form:select>
			<form:errors path="userObj" class="ui-state-error-text"></form:errors>
			<br>
		</div>
		<br/>
		<div class="ex1">
			<h5>select access rights</h5>	
			<ul><form:checkboxes path="collaboratorRoles" class="roles" items="${wscollabroles}" itemValue="DBid" itemLabel="displayName" element="li" /></ul>
			<form:errors path="collaboratorRoles" class="ui-state-error-text"></form:errors>
			<br/>
		</div>
	</div>
	<td><input type="submit" value="Add"></td>
	<input type="button" value="Cancel" onClick="onSubmit()">
		</c:if>
		
		<c:if test="${empty noncollabusers}">
		<hr>
          <span class="byline">All Quadriga users are already added to workspace</span>	
          <input type="button" value="Done" onClick="onSubmit()">	   
		</c:if>
		</form:form>
		
		<c:if test="${not empty collaboratingusers}">
		<hr>
		<span class="byline">List of workspace collaborators:</span>
		<table style="width: 100%" class="display dataTable" id="collabaratinguserlist">
		   <thead>
		<tr>	
			<th align="center" width="30%">Collaborator</th>
			<th align="center" width="70%">Roles</th>	
		</tr>
	</thead>
		<tbody>
	<c:forEach var="workspaceCollaborator" items="${collaboratingusers}">
		<tr>
		 <td><c:out value="${workspaceCollaborator.collaborator.userObj.name}"></c:out></td>
		 <td>
			<c:forEach var="roles" items="${workspaceCollaborator.collaborator.collaboratorRoles}">
		 	<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach>		 
		 </td>
		</tr>
	</c:forEach>
	</tbody>
		</table>
		</c:if>
