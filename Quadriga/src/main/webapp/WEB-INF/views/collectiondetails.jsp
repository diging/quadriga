<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<header>
		<h2>Collection </h2>
		<span class="byline"> ${concept.name}</span>
	</header>
	<article>
	<c:out value="${concept.description }"></c:out>
	 <c:forEach var="items" items="${concept.items}">
	<li>
	jiji
	<c:out value="${items.name}"></c:out>
	<c:out value="${items.description}"></c:out>   
	<c:out value="${items.pos}"></c:out> 
	</li>
	</c:forEach>
	</article>
	
</html>