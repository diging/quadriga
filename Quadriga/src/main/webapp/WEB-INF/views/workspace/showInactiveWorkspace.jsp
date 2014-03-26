<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<title>List of Deactivated Workspaces</title>

<table style="width: 100%" class="display dataTable" id="workspacelist">
	<thead>
		<tr>
			<th width="21%">Workspace Name</th>
			<th width="75%">Description</th>
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