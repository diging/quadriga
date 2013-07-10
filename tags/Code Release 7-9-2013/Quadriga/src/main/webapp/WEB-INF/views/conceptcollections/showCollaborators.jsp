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
</script>

 <form:form modelAttribute="collaborator" method="POST"> 
 
		
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${noncollaboratorList}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 

	 <br><br>
	 <form:checkboxes path="collaboratorRoles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="displayName" />	
	<input id="submit_btn" type="submit" value="Add Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/${conceptcollection.id}/addcollaborators'"/> 

</form:form>  
<br></br>
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
		  <c:forEach var="roles" items="collaboratingUsers">
		  <td><c:out value="${roles.roleDBid}"></c:out></td>
		  </c:forEach>
		</tr>
	</c:forEach>
	</tbody>
</table>