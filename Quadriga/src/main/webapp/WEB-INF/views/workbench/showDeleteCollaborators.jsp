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
} );

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
	
function onSubmit(){
	
	location.href='${pageContext.servletContext.contextPath}/auth/workbench/${projectid}';
}

</script> 

<style>

.error {
	color: #ff0000;
	font-style: italic;
}

</style>

<input type="submit" value="Back" onClick="onSubmit()">
<br><br>

<form:form method="POST" commandName="collaboratorForm" 
action="${pageContext.servletContext.contextPath}/auth/workbench/${projectId}/deletecollaborator">

<c:choose>
<c:when test="${success == '0'}">
<c:if test="${not empty collaboratorForm.collaborators}">

<input type="submit" value="delete">

<table style="width: 100%" class="display dataTable">
<thead>
					<tr>
						<th width="25%">Collaborator</th>
						<th width="75%">Collaborator Roles</th> 
					</tr>
</thead>
<tbody>
	<c:forEach var="collabUser" items="${collaboratorForm.collaborators}" varStatus="status">
	<tr>
		<td>
			<form:checkbox path="collaborators[${status.index}].userName" value="${collabUser.userName}" />
			<form:label path="collaborators[${status.index}].userName">
				<c:out value="${collabUser.userName}"></c:out>
			</form:label>
			<form:errors path="collaborators[${status.index}].userName" cssClass="error"></form:errors> 
		</td>
		<td>
			<c:forEach var="roles" items="${collabUser.collaboratorRoles}">
				<c:out value="${roles.displayName}" />||
			</c:forEach>
		</td> 	
	</tr>
	</c:forEach>

</tbody>
</table>
</c:if>
</c:when>
</c:choose>
</form:form>




<%-- <input type="submit" value="Delete Collaborator">
<br><br>

<table style="width:100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">					
	<thead>
		<tr>
		  <th align="left"><input type="checkbox" class="selectAll" name="selected" value="check all"/>select All</th>	
			
			<th align="left">collaborator</th>
			<th align="left">roles</th>	
		</tr>
	</thead>
	
	<tbody>
	<c:forEach items="${collaboratingUsers}" varStatus="loop"> 
	<tr>
		<td><form:checkboxes items="collaboratingUsers[${loop.index}]" path="collaboratorList" itemValue="userObj.userName" itemLabel="userObj.userName"/>
		</td>
		
		<td>
		<form:checkboxes path="collaboratorRoleList" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />||
		<%-- <c:out value="${roles.displayName}"></c:out>|| 
		</td>
	</tr>
	</c:forEach>
	</tbody>
	
</table>
</form:form> --%>
    