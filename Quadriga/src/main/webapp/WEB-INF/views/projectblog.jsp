<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>



<h1>Project Blog </h2>
<p>You are viewing contents of project  <i>${project.projectName}</i>.</p>

<div>
<c:if test="${not empty blockentrylist}">
You own these concept collections:
   <table>   
   <c:forEach var="blockentry" items="${blockentrylist}">
       	<tr>    
        <td> <b> <c:out value="${blockentry.title}"></c:out> </b> </td>
        <td> <c:out value="${blockentry.text}"></c:out> </td>
        <td> <c:out value="${blockentry.date}"></c:out> </td>
        <td> <c:out value="${blockentry.author}"></c:out> </td>
   		</tr>
   </c:forEach>
   </table>
</c:if>
<c:if test="${empty blockentrylist}">
You don't own any concept collections.
</c:if>
</div>
