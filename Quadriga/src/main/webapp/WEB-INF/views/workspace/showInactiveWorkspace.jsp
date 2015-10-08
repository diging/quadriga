<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<title>List of Deactivated Workspaces</title>
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



</script>

<h2>List of Inactive Workspaces</h2>
<br>
<input type="button" value="Go Back" onclick="
location.href='${pageContext.servletContext.contextPath}/auth/workbench/projects/${projectid}'"/>
<table style="width: 100%" class="display dataTable" id="workspacelist">
	<thead>
		<tr>
			<th width="21%"><h1>Workspace Name</h1></th>
			<th width="75%"><h1>Description</h1></th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="workspace" items="${deactivatedWSList}">
	<tr>
		<td width="30%"><c:out value="${workspace.name}" /></td>
		<td width="70%"><c:out value="${workspace.description}" /></td>
	</tr>
	</c:forEach>
	</tbody>
</table>
