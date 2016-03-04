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
    		
    		<%-- Try escapeXML using attribute --%>
    		<c:out value="${'<b>This is a <c:out> example </b>'}" escapeXml="false"></c:out>
    		<c:set var="string1" value="This is first String."/>
			<c:set var="string2" value="This <em>is second String.</em>"/>

			<%-- Try escapeXML using function--%>
			<p>With escapeXml() Function:</p>
			<p>string (1) : ${fn:escapeXml(string1)}</p>	
			<p>string (2) : ${fn:escapeXml(string2)}</p>
    		
	</div>
	
   </c:forEach>
</c:if>

<c:if test="${empty blockentrylist}">
You don't own any concept collections.
</c:if>
</div>
