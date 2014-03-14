<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<header>
		<h2>Quadriga Workbench</h2>
		<span class="byline">Manage projects and workspaces</span>
	</header>

    <ol>
    <c:if test="${not empty projectlist}">
    <c:forEach var="project" items="${projectlist}">
	<li><c:out value="${project.name}"></c:out></li>
	<li><c:out value="${project.description}"></c:out></li>
	</c:forEach></c:if>
	</ol>
	
	