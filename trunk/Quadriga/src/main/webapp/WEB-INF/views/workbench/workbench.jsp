<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
 
	
	
	<header>
		<h2>Quadriga Workbench</h2>
		<span class="byline">Manage projects and workspaces</span>
	</header>

	
	<h5>You are the owner of the following projects:</h5>
    
    	<c:if test="${not empty projectlist}">
    		<ul class='a'>
   	 			<c:forEach var="project" items="${projectlist}">
					<li>
						<a href="workbench/${project.id}"><c:out value="${project.name}"></c:out></a>
						<br><c:out value="${project.description}"></c:out>
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlist}">
			You don't have any projects yet.
		</c:if>
	
	
	
	
	
	
	
	
	
	
	