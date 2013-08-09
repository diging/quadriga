<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

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

 <form:form  method="POST" name="myForm" commandName="collaborator"
 action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/addcollaborators"> 
 
<!--<c:if test="${not empty nonCollaboratorList}">-->	
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${noncollaboratorList}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 
	<form:errors path="userObj" cssClass="error"></form:errors>  
	
	<br><br>
	
	<form:checkboxes path="collaboratorRoles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />	
	<td><input type="submit" value="Add"></td>
	<form:errors path="collaboratorRoles" cssClass="error"></form:errors>
	&nbsp;
<!-- </c:if>-->
<br></br>


<input type="submit" value="Delete Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/${conceptcollection.id}/deleteCollaborator'" >
<br><br>
<table style="width:100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">					
	<thead>
		<tr>
			<th align="left"><input type="checkbox" class="selectAll" name="selected" value="check all"/>select All</th>	
			<th align="left">collaborator</th>
			<th>roles</th>	
		</tr>
		
	</thead>
	
	<tbody>
	<c:forEach var="collab" items="${collaborators}">
		<tr>

		  <td><input type="checkbox" name="selected" value='<c:out value="${collab.userObj.userName}"></c:out>'/></td>
		  <td><c:out value="${collab.userObj.userName}"></c:out> </td>
		  <td>
		  	<c:forEach var="roles" items="${collab.collaboratorRoles}">
		  	<c:out value="${roles.displayName}"></c:out>|
		  	</c:forEach>
		  </td>

		</tr>
	</c:forEach>
	</tbody>
</table>
</form:form>  
