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
	
	/* $("form input:checkbox").prop("checked",false); */
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
<h1>Search Your Profile In Following Services</h1>

<form:form method="GET" modelAttribute="ServiceBackBean"
action="${pageContext.servletContext.contextPath}/auth/profile/search">

<table>
	<tr>
		<td>
			Service Name
		</td>
		<td>
			<form:select path="id" items="${serviceNameIdMap}" selected="selected" />
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

 <c:choose>
	<c:when test="${not empty searchResultList}">
		<form:form method="POST"  modelAttribute="SearchResultBackBeanForm" 
		action="${pageContext.servletContext.contextPath}/auth/profile/${serviceid}/${term}/add">
		 Results of the Search
		 <br>
		 <input type="button" value="Select All" name="selectAll"> 
		 <input type="button" value="Deselect All" name="deselectAll">
		
		 <input type="submit" value="Select & Save">
			 <table style="width:100%" cellpadding="0" cellspacing="0" border="0" class="display dataTable">	
				
				<thead>
					<tr>
						<th>select</th>
						<th>Name</th>
						<th>ID</th>
						<th>Description</th>
					</tr>
				</thead>
			
				<tbody>
				<c:forEach var="result" items="${SearchResultBackBeanForm.searchResultList}" varStatus="status">
					<tr>
						<form:hidden path="searchResultList[${status.index}].word" value="${result.word}"  />
						<form:hidden path="searchResultList[${status.index}].description" value="${result.description}"  />
						<form:hidden path="searchResultList[${status.index}].id" value="${result.id}"  />
						<td><form:checkbox path="searchResultList[${status.index}].isChecked"/></td>
						<td><c:out value="${result.word}"/></td>
						<td><c:out value="${result.id}"/></td>
						<td><c:out value="${result.description}"/></td> 
					</tr>
				</c:forEach>
				</tbody>
			</table>
		 </form:form>
	 </c:when>
</c:choose>

 
<c:choose>
		<c:when test="${success == '1'}">
			<font color="blue"><spring:message code="add_profile_success"/></font>
	 	</c:when>
	 	<c:otherwise>
				<font color="red">${errmsg}</font>
		</c:otherwise>
</c:choose> 


			



			
	

 



 

 
 
 
 