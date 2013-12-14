<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>

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
	//alert($("form input:checkbox").is(':checked'));
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

<h1>CURRENT ADDED PROFILES</h1>
<br>

<c:choose>
	<c:when test="${success == '1'}">
		<font color="blue" style="font-family: inherit;"><c:out value="${result}" /></font>
	</c:when>
</c:choose>
<br>

<input type="button" value="Select All" name="selectAll"> <input type="button" value="Deselect All" name="deselectAll">
<form:form method="POST" modelAttribute="SearchResultBackBeanForm" 
action="${pageContext.servletContext.contextPath}/auth/profile/delete">

<%-- <c:if test="${not empty resultLists}"> --%>
 	<table class="display dataTable" cellpadding="0" cellspacing="0" border="0" style="width:100%">
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
						<td><form:checkbox path="searchResultList[${status.index}].id" value="${entry.id}"/></td>
						<td><c:out value="${entry.word}" /></td>
						<td><c:out value="${entry.id}" /></td>
						<td><c:out value="${entry.description}" /></td>
					</tr>
				</c:forEach>
			</tbody>
	</table>	
<%--  </c:if> --%> 
<input type="submit" value="Delete" id='ischeck' style="float:left;">

</form:form>
	
<form:form method="GET" action="${pageContext.servletContext.contextPath}/auth/profile/addnew" >
 
<input type="submit" value="Add new Entries" style="float:left;">

</form:form>




 
 
 
 



 

 
 
 
 