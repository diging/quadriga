<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<script type="text/javascript">

$(function() {
	$("input[type=submit]").button().click(function(event) {
	});
	
	$("input[type=button]").button().click(function(event) {
	});
});

$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
});

</script> 

<head>
<style type="text/css">
h1{
	font-size:50x;
}

</style>
</head>

<form:form method="GET" modelAttribute="ServiceBackBean"
action="${pageContext.servletContext.contextPath}/auth/profile/search">

<table>
	<tr>
	
		<td>
		Service Name
		</td>
		<td>
		<form:select path="id" items="${serviceNameIdMap}"/>
		</td>
	</tr>	
	<tr>
		<td>
		Search Term
		</td>
		<td>
		<form:input path="term" label="search term"/>
		</td>
	</tr>
	<tr>
		<td>
		<input type="submit" value="search"/> 
		</td>
	</tr>
</table>

<c:if test="${not empty searchResults}">
<table style="width:100%" cellpadding="0" cellspacing="0" border="0" class="display dataTable">	
	<thead>
		<tr>
			<th>ID</th>
			<th>Description</th>
		</tr>
	</thead>
	
	<tbody>
	
	<c:forEach var="result" items="${searchResults}">
		<tr>
			<td><c:out value="${result.id}"></c:out></td>
			<td><c:out value="${result.description}"></c:out></td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</c:if>
</form:form>  

<form:form method="POST"  modelAttribute="ServiceBackBean" 
action="${pageContext.servletContext.contextPath}/auth/profile/additem">
 Results of the Search
 <input type="submit" value="Select & Save">
 <table>
 <tr>
 <td></td>
 
 </tr>
 
 
 </table>
 
 
 
 </form:form>
 
 
 <%--<form:select path="serviceIdNameMap" items="${serviceNameIdMap}" multiple="true" /> --%>



 

 
 
 
 