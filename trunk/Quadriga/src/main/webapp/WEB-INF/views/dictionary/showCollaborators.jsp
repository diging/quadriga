<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
});


function validate()
{
	ValidateSelectBox('userName','text');
}

function ValidateSelectBox(selectbox,returntype)
{
	var retMessage = "";
	
	if(getSelectedValue(selectbox) == 'NONE'){
		
		retMessage = 'you must select a value';
		
		if(returntype == 'text')
			{
				return retMessage;
			}
		
	}
}


function alertbox(){
	
	
}
</script>

<form:form modelAttribute="collaborator" method="POST">
	<form:select path="userObj" id="userName" name="userName">
		    <form:option value="NONE" label="--- Select ---"/>
		   	<form:options items="${nonCollaboratingUsers}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 
	
	<br><br>
	<form:checkboxes path="collaboratorRoles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />	
	
	<%-- <c:choose> --%>
	<%-- 	<c:when test="${not empty nonCollaboratingUsers}">
			<input id="submit_btn" type="submit" value="Add Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/addCollaborators'">
		</c:when>  --%>
		
	<%-- 	<c:otherwise>
			<input id="submit_btn" type="submit" value="Add Collaborator" onclick="myfunction()">
		</c:otherwise>
	</c:choose> --%>
	
	<input id="submit_btn" type="submit" value="Add Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/addCollaborators';validate()">
    
</form:form>
<br><br>
<table style="width: 100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">					
	<thead>
		<tr>
			<th>collaborator</th>	
		</tr>
	</thead>
	<tbody>
	<c:forEach var="collab" items="${collaboratingUsers}">
		<tr>
		  <td><c:out value="${collab.userName}"></c:out> </td>
		</tr>
	</c:forEach>
	</tbody>
</table>




