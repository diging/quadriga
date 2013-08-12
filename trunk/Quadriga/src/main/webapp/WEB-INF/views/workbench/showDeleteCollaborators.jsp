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

<input type="submit" value="Back" onClick="onSubmit()">
<br><br>

<form method="POST" modelAttribute="collaboratorBackingBean" 
action="${pageContext.servletContext.contextPath}/auth/workbench/${projectid}/deletecollaborator">
<input type="submit" value="Delete Collaborator">
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
	<tr>
		<td><form:checkboxes items="${collaboratingUsers}" path="collaboratorList" itemValue="userObj.userName"/></td>
	
	 </tr>
	</tbody>
	
</table>
</form>
    