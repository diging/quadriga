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
	
	$("form input:checkbox").prop("checked",false);
});

$(function(){
$("input[name='selectAll']").button().click(function(){
		
		$("form input:checkbox").prop("checked",true);
		event.preventDefault();
		return;
	});
	
$("input[name='deselectAll']").button().click(function(){
	
	$("form input:checkbox").prop("checked",false);
	event.preventDefault();
	return;
	
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


<%-- <c:if test="${not empty userProfile}">
		<h3>What you have added</h3>
		<form:form method="GET"  action="${pageContext.servletContext.contextPath}/auth/profile/addnew">
		<table cellpadding="0" cellspacing="0" border="0"
			class="display dataTable" width="100%">
			<thead>
				<tr>
					<th>Service</th>
					<th>Link</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="entry" items="${userProfile}">
					<tr>
						<td><c:out value="${entry.name}"></c:out></td>
						<td><c:out value="${entry.id}"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="submit" value="Add New Entries" name="addNew"/>
		</form:form>
	</c:if>
 --%>
 

 
 
 
 
<form:form method="POST" modelAttribute="SearchResultBackBeanForm" 
action="${pageContext.servletContext.contextPath}/auth/profile/delete">

<%-- <c:if test="${not empty resultLists}"> --%>
 	<table cellpadding="0" cellspacing="0" border="0"
			class="display dataTable" width="100%">
			<thead>
				<tr>
					<th>Select</th>
					<th>Name</th>
					<th>ID</th>
					<th>Description</th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach var="entry" items="${SearchResultBackBeanForm.searchResultList}" varStatus="status">
					<tr>
						<td><form:checkbox path="searchResultList[${status.index}].id" value="${entry.id}" /></td>
						<td><c:out value="${entry.word}" /></td>
						<td><c:out value="${entry.id}" /></td>
						<td><c:out value="${entry.description}" /></td>
					</tr>
				</c:forEach>
			</tbody>
	</table>	
<%--  </c:if> --%> 
<input type="submit" value="delete">

</form:form>
	
<form:form method="GET" action="${pageContext.servletContext.contextPath}/auth/profile/addnew" >
 
<input type="submit" value="Add new Entries">

</form:form>
 



 

 
 
 
 