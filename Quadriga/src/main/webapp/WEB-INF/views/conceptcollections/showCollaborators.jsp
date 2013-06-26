<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

 <form:form modelAttribute="collaborator" method="POST"> 
 
		
	<form:select path="userObj" id="userName">
	    <form:option value="NONE" label="--- Select ---"/>
	   	<form:options items="${noncollaboratorList}"  itemValue="userName" itemLabel="userName" /> 
	</form:select> 

	 <br><br>
	 <form:checkboxes path="collaboratorRoles" items="${possibleCollaboratorRoles}" itemValue="roleid" itemLabel="roleid" />	
	<input id="submit_btn" type="submit" value="Add Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/${conceptcollection.id}/addcollaborators'"/> 

</form:form>  