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

</script> 


<form method="POST">
<input type="submit" value="Delete Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/workbench/${projectid}/deletecollaborator'" >
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
		 <td><c:out value="${collab.userObj.userName}"></c:out></td>
		 <td>
			<c:forEach var="roles" items="${collab.collaboratorRoles}">
		 	<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach>		 
		 </td>
		</tr>
	</c:forEach>
	</tbody>
	
</table>
</form>
    