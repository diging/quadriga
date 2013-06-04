<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	
 	<header>
		<span class="byline">Manage dictionary list for <c:out value="${userId}"></c:out></span>
	</header>
	 


	
	
	<br>
	<ul>
	<li>
    <c:choose>
    <c:when test="${not empty dictinarylist}">
    <b>Dictionary list:</b>
    <c:forEach var="dictionary" items="${dictinarylist}">
	<li><a href="workbench/${dictionary.id}">
	<c:out value="${dictionary.name}"></c:out></a> &nbsp;&nbsp;
	<c:out value="${dictionary.description}"></c:out>   
	</li>
	</c:forEach>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>
	</li>
	</ul>
	<form name='f' action="<c:url value='dictionaries.jsp' />"
		method='GET'>
	<table>
			<tr>			
				<td colspan='2'><input name="ClickHere" type="submit"
					value="Click to add a new dictionary" />
				</td>
			</tr>
	</table>
	</form>