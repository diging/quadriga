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
	</style>
</head>

<body>
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


