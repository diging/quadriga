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
</form:form>  


	<c:when test="${success=='0'}">
		<form:form method="POST"  modelAttribute="SearchResultBackBeanForm" 
		action="${pageContext.servletContext.contextPath}/auth/profile/${serviceid}/${term}/add">
		 Results of the Search
		 <input type="submit" value="Select & Save">
			 <table style="width:100%" cellpadding="0" cellspacing="0" border="0" class="display dataTable">	
				
				<thead>
					<tr>
						<th>select</th>
						<th>ID</th>
						<th>Description</th>
					</tr>
				</thead>
			
				<tbody>
				
				<c:forEach var="result" items="${SearchResultBackBeanForm.searchResultList}" varStatus="status">
					<tr>
						<td><form:checkbox path="searchResultList[${status.index}].id" value="${result.id}" /></td>
						<td><c:out value="${result.id}"></c:out></td>
						<td><c:out value="${result.description}"></c:out></td>
					</tr>
				</c:forEach>
				
				</tbody>
			</table>
			
			<c:choose>
				<c:when test="${success == '1'}">
	 				<font color="blue"><spring:message code="add_profile_success"/></font>
	 			</c:when>
 			</c:choose>
		
		 </form:form>
	 </c:when>
	

 
 <%--<form:select path="serviceIdNameMap" items="${serviceNameIdMap}" multiple="true" /> --%>



 

 
 
 
 