<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
<style>

div.ex {color:#01A9DB;
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
} );

$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {

	});
	$("input[type=button]").button().click(function(event) {

	});
});

function onSubmit(){
	
	location.href='${pageContext.servletContext.contextPath}/auth/workbench/${projectid}';
}

</script>

<form:form method="POST" name="myForm" commandName="collaborator" 
  action="${pageContext.servletContext.contextPath}/auth/workbench/${projectid}/addcollaborators">
  <h2>Associate collaborators to project:</h2>
<h3>Project: ${projectname}</h3>
<div>${projectdesc}</div>
<c:if test="${not empty notCollaboratingUsers}">
<hr>
<div class="ex" style="float:left;">select collaborator</div>
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${notCollaboratingUsers}"  itemValue="userName" itemLabel="name" /> 
	</form:select> 
 	<form:errors path="userObj" class="ui-state-error-text"></form:errors>  
<br/>
<div class="ex" style="float:left;">select access rights</div>
	<form:checkboxes path="collaboratorRoles" class="roles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />	
	<form:errors path="collaboratorRoles" class="ui-state-error-text"></form:errors>
<br/>
<input type="submit" value="Add">
<input type="button" value="Cancel" onClick="onSubmit()">
</c:if>
<c:if test="${empty notCollaboratingUsers}">
<hr>
 <span class="byline">All collaborators are associated to the project</span>
 <input type="button" value="Done" onClick="onSubmit()">
</c:if>
<br>
<c:if test="${not empty collaboratingUsers}">
<hr>
<span class="byline">Associated project collaborators :</span>
<table style="width:100%" class="display dataTable">					
	<thead>
		<tr>	
			<th align="left">collaborator</th>
			<th align="left">roles</th>	
		</tr>
	</thead>
	
	<tbody>
	<c:forEach var="collab" items="${collaboratingUsers}">
		<tr>
		 <td><c:out value="${collab.userObj.name}"></c:out></td>
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
</form:form> 
<br>
