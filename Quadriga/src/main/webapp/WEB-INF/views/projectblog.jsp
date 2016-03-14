<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h1>Project Blog </h1>
<p>You are viewing contents of project  <i>${project.projectName}</i>.</p>

<div>
<c:if test="${not empty blockentrylist}">
   <c:forEach var="blockentry" items="${blockentrylist}">
	<div>
    		<h3><b><c:out value="${blockentry.title}"></c:out></b></h3>
    		<h4><i> Posted by <c:out value="${blockentry.author}"></c:out> on <c:out value="${blockentry.date}"></c:out> </i></h4>
    		<p align="justify"><c:out value="${blockentry.text}" escapeXml="false"></c:out>  </p>
    		    		
	</div>
	
   </c:forEach>
</c:if>

<c:if test="${empty blockentrylist}">
You don't own any concept collections.
</c:if>
</div>
