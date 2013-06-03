

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<header>
	<h2>List of projects for <span><b>${username}</b></span> </h2>
</header>
		
<article class = "page content"> 	
	
	<section class = "links">
	
		<ol>
    	<c:if test="${not empty projectlist}">
    	<c:forEach var="project" items="${projectlist}">
		<li><a href = '<c:out value="'projectContent' ${project.name}"></c:out>'>${project.name}</a></li>
		</c:forEach>
		</c:if>
		</ol>
		
	</section>
	
</article>
	
	
	
	
	
	
	
	
	
	
	<%--- <header>
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
			--%>
	