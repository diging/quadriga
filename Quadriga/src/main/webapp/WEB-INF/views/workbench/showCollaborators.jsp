<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script>

function enableSubmit()
{
		if(document.getElementById("checkbox_1").checked || document.getElementById("checkbox_2").checked || 
			document.getElementById("checkbox_3").checked || document.getElementById("checkbox_4").checked)
			{
				document.getElementById("submit_btn").disabled = false;
				
			}
		else
			{
				document.getElementById("submit_btn").disabled = true;

			}
}

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

</script> 


 <form:form name="myForm" commandName="collaborator" method="POST" 
  action="${pageContext.servletContext.contextPath}/auth/workbench/${projectid}/addcollaborators">
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
 	<c:if test="${not empty nonCollaboratingUsers}">
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${notCollaboratingUsers}"  itemValue="userName" itemLabel="name" /> 
	</form:select> 
	<form:errors path="userObj" class="ui-state-error-text"></form:errors>  
	
	<br><br>
	
	<form:checkboxes path="collaboratorRoles" class="roles" items="${possibleCollaboratorRoles}" itemValue="id" itemLabel="id" />	
	<td><input type="submit" value="Add"></td>
	<form:errors path="collaboratorRoles" class="ui-state-error-text"></form:errors>
	&nbsp;
	</c:if>
	<br><br><br>
	<input type="submit" value="Delete Collaborator" onclick="deleteValidate();this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/deleteCollaborators'">
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
		<c:forEach var="collab" items="${collaboratingUsers}">
			<tr>
			 <td><input type="checkbox" name="selected" value='<c:out value="${collab.userObj.userName}"></c:out>'/></td>
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
</form:form>












