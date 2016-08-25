<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
 	<header>
		<h2>Quadriga dictionaries</h2>
		<span class="byline">Manage dictionaries</span>
	</header>
	 


	
	<h1></h1><b>Dictionary list as per biology</b></h1>
	<br><br>
	<ul>
	<li>
    <c:if test="${not empty dictinarylist}">
    
    <c:forEach var="dictionary" items="${dictinarylist}">
	<li><a href="workbench/${dictionary.id}">
	<c:out value="${dictionary.name}"></c:out></a> &nbsp;&nbsp;
	<c:out value="${dictionary.description}"></c:out>   
	</li>
	</c:forEach>
	</c:if>
	</li>
	</ul>
