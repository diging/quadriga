<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
 	<header>
		<span class="byline">Manage dictionary list for <c:out value="${userId}"></c:out></span>
		<a href="/quadriga/auth/dictionaries/addDictionary">Add Dictionary</a>
	</header>
	 
	<ul>
	<li>
    <c:choose>
    <c:when test="${not empty dictinarylist}">
    <b>Dictionary list:</b>
    <c:forEach var="dictionary" items="${dictinarylist}">
	<li><a href="dictionaries/${dictionary.id}">
	<c:out value="${dictionary.name}"></c:out></a> &nbsp;&nbsp;
	<c:out value="${dictionary.description}"></c:out>   
	</li>
	</c:forEach>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>
	</li>
	</ul>

	
