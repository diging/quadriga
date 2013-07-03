<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
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

</script> 


<head>
	<style>
		table
		{
		width:50%;
		}
		table, td, th
		{
		border:1px solid white;
		border-style:outset;
		border-width:medium;
		}
		th
		{
		background-color:black;
		color:white;
		}
		td
		{
		padding:10px;
		color:black;
		}
  }	
	</style>
</head>


 <form:form modelAttribute="collaborator" method="POST" >
 
		<!-- <input type="checkbox" id="checkbox_1" onclick ="enableSubmit()" 
		name="roleitem" value="collaborator_role1" />
		<label for="checkbox_1">ADMIN</label>
		
		<input type="checkbox" id="checkbox_2" onclick ="enableSubmit()" 
		name="roleitem" value="collaborator_role2"  />
		<label for="checkbox_2">PROJECT_ADMIN</label>
		
		<input type="checkbox" id="checkbox_3" onclick ="enableSubmit()" 
		name="roleitem" value="collaborator_role3" />
		<label for="checkbox_3">CONTRIBUTOR</label>
		
		<input type="checkbox" id="checkbox_4" onclick ="enableSubmit()" 
		name="roleitem" value="collaborator_role4"  />
		<label for="checkbox_4">EDITOR</label> -->
		
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${notCollaboratingUsers}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 

	<br><br>
	<form:checkboxes path="collaboratorRoles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="roleid" />	
	<input id="submit_btn" type="submit" value="Add Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}/addcollaborator'">

</form:form> 

<%-- <table style="width: 100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">
<thead>					
<tr>
	<th>Collaborators</th>
	<th>Roles</th>	
</tr>
</thead>

<tbody>

</tbody>
</table>--%>








<%-- <table>
	<tr>
	<th>Existing collaborators</th>
	<th>Roles</th>
	</tr>

		<c:forEach var="collab" items="${project1.collaborators}">

	<tr>
		<td>
			<c:out value="${collab.userObj.name}"></c:out>
			<c:forEach var="roles"  items="${collab.collaboratorRoles}">
				<td><c:out value="${roles.roleid}"></c:out></td>
			</c:forEach>
		</td> 
	</tr>
			</c:forEach>
	

</table>

<h1> non existing collaborators </h1>

<ul>
  <c:forEach var="collab" items="${project.collaborators}">
     <li> 
    	<c:out value="${collab.userObj.name}"></c:out>
     </li>
  </c:forEach>
</ul>
--%>


