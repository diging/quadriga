<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
 	<%-- <header>
		<h2>Quadriga Workbench</h2>
		<span class="byline">Manage projects and workspaces</span>
	</header>
	
    <ol type="">
    <c:if test="${not empty projectlist}">
    <c:forEach var="project" items="${projectlist}">
	<li><a href="workbench/${project.id}">
	<c:out value="${project.name}"></c:out>  
	</a></li>
	</c:forEach>
	</c:if>
	</ol>  --%>
	
	
	<html>
	<head>
	<style>
	 ul.a {list-style-type:circle;}
	</style>
	</head>
	<header>
		<h2>Quadriga Workbench</h2>
		<span class="byline">Manage projects and workspaces</span>
		<h5>List Of ${username} projects</h5>
	</header>
	
	<body>
	
    <ul class='a'>
    <c:if test="${not empty projectlist}">
    <c:forEach var="project" items="${projectlist}">
	<li><h5><a href="workbench/${project.id}"><c:out value="${project.name}"></c:out></a></h5></li>
	<%-- <li><c:out value="${project.description}"></c:out></li> --%>
	</c:forEach></c:if>
	</ul>
	
	</body>
	</html>   
	
	
	<%-- <ul>
    <c:if test="${not empty projectlist}">
    <c:forEach var="project" items="${projectlist}">
	<li><a href="workbench/${project.id}"><c:out value="${project.name}"></c:out></a></li>
	<li><c:out value="${project.description}"></c:out></li> 
	<li><c:out value="${project.owner.name}"></c:out></li> 
	</c:forEach>
	<c:forEach var="collaborator" items="${collaboratorList}">
	<li><c:out value="${project.collaborator.name}"></c:out></li> 
	</c:forEach> 
	</c:if>
	</ul>  
	--%>
	
	
	
	
	
	
	
	
	