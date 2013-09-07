<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
    
<style>
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

div.ex {color:blue;
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
});

function onSubmit(){
	
	location.href='${pageContext.servletContext.contextPath}/auth/workbench/${projectid}';
}

</script>

<input type="submit" value="Back" onClick="onSubmit()">
<br><br>
<form:form method="POST" name="myForm" commandName="collaborator" 
  action="${pageContext.servletContext.contextPath}/auth/workbench/${projectid}/addcollaborators">

<c:if test="${not empty notCollaboratingUsers}">
<div class="ex">select collaborator</div>
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${notCollaboratingUsers}"  itemValue="userName" itemLabel="name" /> 
	</form:select> 
 	<form:errors path="userObj" cssClass="error"></form:errors>  
	
	<br><br>
<div class="ex">select access rights</div>	
	<form:checkboxes path="collaboratorRoles" class="roles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />	
	<td><input type="submit" value="Add"></td>
	<form:errors path="collaboratorRoles" cssClass="error"></form:errors>
	&nbsp;
</c:if>


<br><br>
<c:if test="${not empty collaboratingUsers}">
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

