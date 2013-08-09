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
</style>

<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
});


$(document).ready(function(){
	$(".selectAll").click(function(){
	if($(this).val() == "check all")
		{
			$('input:checkbox').prop("checked",true);
			$(this).val("uncheck all");
		}
	else
		{
			$('input:checkbox').prop("checked",false);
			$(this).val("check all");	
		}
	});
});	


$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {

	});
});

</script>


<form:form method="POST" name="myForm" commandName="collaborator"
 action="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/addCollaborators">
	
	<c:if test="${not empty nonCollaboratingUsers}">
	<form:select path="userObj" id="selectbox" name="userName" onchange="enableDisable()" >
	  	<form:option value="NONE" label="--- Select ---"/>
		<form:options items="${nonCollaboratingUsers}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 
	<form:errors path="userObj" cssClass="error"></form:errors>  
	<br><br>
	<form:checkboxes path="collaboratorRoles" class="roles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />	
	<td><input type="submit" value="Add"></td>
	<form:errors path="collaboratorRoles" cssClass="error"></form:errors>
	&nbsp;
	</c:if>
	
	<br><br><br>
    
    <input type="submit" value="Delete Collaborator" onclick="deleteValidate();this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/deleteCollaborators'">

	<br><br>
	
<table style="width:100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">	
	<thead>
	
		<tr>
			<th align="left"><input type="checkbox" class="selectAll" value="check all" name="selected">Select All</th>
			<th align="left">collaborator</th>
			<th align="left">roles</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="collab" items="${collaboratingUsers}">
		<tr>
		  <td><input type="checkbox" name="selected" value='<c:out value="${collab.userObj.userName}"></c:out>'/></td>
		  <td><c:out value="${collab.userObj.userName}"></c:out> </td>
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




