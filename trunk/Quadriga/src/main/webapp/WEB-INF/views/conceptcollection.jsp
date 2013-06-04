<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<header>
	<h2>Concept lists of  <span><b>${username}</b></span> </h2>
</header>
	<ul>
    <c:if test="${not empty conceptlist}">
    <c:forEach var="concept" items="${conceptlist}">
	<li><a href="conceptcollection/${concept.id}">
	<c:out value="${concept.name}"></c:out>  </a>
	<ul><li><c:out value="${concept.description}"></c:out></li></ul>
	</li>
	</c:forEach>
	</c:if>
	</ul>  	

</html>