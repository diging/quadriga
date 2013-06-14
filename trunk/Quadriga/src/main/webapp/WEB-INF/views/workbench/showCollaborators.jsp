<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


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



<form action="Quadriga/src/views/workbench/project-right.jsp" onclick="submitAll()"  method="get">
<input id="submit_btn" type="submit" value="Add Collaborator">
</form>


<input id="checkbox_1" value="ADMIN" onclick ="enableSubmit()" 
name="admin" value="collaborator_role1" type="checkbox"/>
<label for="checkbox_1" class="css-label">ADMIN</label>

<input id="checkbox_2" value="PROJECT_ADMIN" onclick ="enableSubmit()" 
name="project_admin" value="collaborator_role2" type="checkbox" />
<label for="checkbox_2" class="css-label">PROJECT_ADMIN</label>

<input id="checkbox_3" value="CONTRIBUTOR" onclick ="enableSubmit()" 
name="contributor" value="collaborator_role3" type="checkbox" />
<label for="checkbox_3" onclick="fun()">CONTRIBUTOR</label>

<input id="checkbox_4" value="EDITOR" onclick ="enableSubmit()" 
name="editor" value="collaborator_role4" type="checkbox" />
<label for="checkbox_4" class="css-label">EDITOR</label>


<select id="myOptions" >

   <c:forEach var="collab" items="${project1.collaborators}">
   <option><c:out value="${collab.userObj.name}"></c:out></option>
   </c:forEach>
</select> 


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

























<%-- 
<table>
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


