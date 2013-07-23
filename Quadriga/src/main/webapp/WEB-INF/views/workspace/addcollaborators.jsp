<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

	<form:form commandName="collaborator" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/addCollaborators">
		
		<c:if test="${not empty noncollabusers}">
		    
		   	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${noncollabusers}"  itemValue="userName" itemLabel="userName" /> 
	</form:select>
	<br><br>
	<form:checkboxes path="collaboratorRoles" class="roles" items="${wscollabroles}" itemValue="roleid" itemLabel="roleid" />	
	<input type="submit" value="Add Collaborator">
		</c:if>
		<c:if test="${empty noncollabusers}">
          All active users are collaborators to the workspace.		   
		</c:if>
		
		</form:form>