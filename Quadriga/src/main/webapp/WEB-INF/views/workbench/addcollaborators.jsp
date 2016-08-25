<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
<style>

	div.wrap {
	  
		 overflow: auto;
		 margin-bottom:-30px; 
	}
	
	div.ex {
	
		width:250px;
		float:left;
	}
	
	div.ex1{
	
		width:200px;
		margin-top:-29px;
		float:left;
		overflow: auto;
	}
	
	div.rolesError{
	float:left;
	margin-top:-45px;
	}
	
</style>

<script>
$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {

	});
	$("input[type=button]").button().click(function(event) {

	});
});
</script>

<h2>Add Collaborator to Project: ${projectname}</h2>
<div class="back-nav">
        <hr>
        <p>
            <a
                href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}"><i
                class="fa fa-arrow-circle-left"></i> Back to Project</a>
        </p>
        <hr>
    </div>
<form:form method="POST" name="myForm" commandName="collaborator" 
  action="${pageContext.servletContext.contextPath}/auth/workbench/${myprojectid}/addcollaborators">
  
<c:if test="${not empty notCollaboratingUsers}">

<div class="row">

<div class="col-md-4">
    <h4>Select collaborator:</h4>
	<form:select class="form-control" path="userObj" id="userName">
	    <form:option value="NONE" label="----- Select -----"/>
	   	<form:options items="${notCollaboratingUsers}"  itemValue="userName" itemLabel="name" /> 
	</form:select>
	 
 	<div class="userError"><form:errors path="userObj" class="error"></form:errors></div>
</div> 	 

<div class="col-md-8">
    <h4>Select access rights:</h4>
	<form:checkboxes path="collaboratorRoles" class="roles" items="${possibleCollaboratorRoles}" itemValue="id" itemLabel="displayName" element="div" /> 	
	<div class="error"><form:errors path="collaboratorRoles" class="ui-state-error-text"></form:errors></div>
</div>

</div>
<br/>
<input class="btn btn-primary" type="submit" value="Add">
<a class="btn btn-default"
            href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}">Done</a>

</c:if>
<c:if test="${empty notCollaboratingUsers}">

 <p>You've added all Quadriga users to this project as collaborators.</p><br>
 <a class="btn btn-primary"
            href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}">Done</a>

</c:if>
<br>
<c:if test="${not empty projectCollaborators}">
<hr>
<h4>Current collaborators:</h4>
<div class="panel panel-default">
        <table class="table">					
	<thead>
		<tr>	
			<th align="left">Collaborator</th>
			<th align="left">Roles</th>	
		</tr>
	</thead>
	
	<tbody>
	<c:forEach var="collab" items="${projectCollaborators}">
		<tr>
		 <td><c:out value="${collab.collaborator.userObj.userName}"></c:out></td>
		 <td>
			<c:forEach var="roles" items="${collab.collaborator.collaboratorRoles}">
		 	<c:out value="${roles.displayName}"></c:out>||
		 	</c:forEach>		 
		 </td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</c:if>
</form:form> 
<br>

