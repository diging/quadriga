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

<form:form modelAttribute="collaborator" method="POST">
	<form:select path="userObj" id="userName" name="userName">
		    <form:option value="NONE" label="--- Select ---"/>
		   	<form:options items="${nonCollaboratingUsers}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 
	
	<br><br>
	<form:checkboxes path="collaboratorRoles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />	
	
	<input id="submit_btn" type="submit" value="Add Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/addCollaborators';validate()">
    
</form:form>

<br><br>

<form method="POST">
<input type="submit" value="Delete Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/deleteCollaborators'">
<br><br>
<table style="width:100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">
					
	<thead>
		<tr>
			<th align="left"><input type="checkbox" class="selectAll" value="check all" name="selected">Select All</th>
			<th align="left">collaborator</th>	
		</tr>
	</thead>
	<tbody>
	<c:forEach var="collab" items="${collaboratingUsers}">
		<tr>
		  <td><input type="checkbox" name="selected" value='<c:out value="${collab.userName}"></c:out>'/></td>
		  <td><c:out value="${collab.userName}"></c:out> </td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</form>




