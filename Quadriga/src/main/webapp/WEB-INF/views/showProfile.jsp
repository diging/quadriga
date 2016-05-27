<%@ page language="java" contentType="text/html;"%>
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
 
$(function(){
	
	$("#dialog").dialog({
	  buttons:{
		  OK:function(){
			 $(this).dialog("close");
		  }  
	   }	
	});
});

</script> 

<header>
		<h2>Your Profile</h2>
		<span class="byline">You linked your profile to the following authority file entries</span>
	</header>

<br>

<form:form method="GET" action="${pageContext.servletContext.contextPath}/auth/profile/addnew" >
 
<input type="submit" value="Add new Authority File Entry" style="float:left;">

</form:form>
<br>

<%-- if no record is selected --%>

<c:if test="${success == '0'}">

<div id="dialog" title="Oops!">
 <p>${errmsg}</p>
</div>

<br>
<input type="button" value="Select All" name="selectAll"> <input type="button" value="Deselect All" name="deselectAll">
<form:form method="POST" modelAttribute="SearchResultBackBeanForm" 
action="${pageContext.servletContext.contextPath}/auth/profile/delete">

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
						<form:hidden path="searchResultList[${status.index}].word" value="${entry.word}"  />
						<form:hidden path="searchResultList[${status.index}].description" value="${entry.description}"  />
						<form:hidden path="searchResultList[${status.index}].id" value="${entry.id}"  />
						<td><form:checkbox path="searchResultList[${status.index}].isChecked" /></td>
						<td><c:out value="${entry.word}" /></td>
						<td><c:out value="${entry.id}" /></td>
						<td><c:out value="${entry.description}" /></td>
					</tr>
				</c:forEach>
			</tbody>
	</table>	

<input type="submit" value="Delete" id='ischeck'>
</form:form>

</c:if>
<br>
<%-- if no record selected END --%>



<%-- if at least one record is selected --%>
<c:if test="${success == '1'}">
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
					    <form:hidden path="searchResultList[${status.index}].word" value="${entry.word}"  />
						<form:hidden path="searchResultList[${status.index}].description" value="${entry.description}"  />
						<form:hidden path="searchResultList[${status.index}].id" value="${entry.id}"  />
						<td><form:checkbox path="searchResultList[${status.index}].isChecked" /></td>
						<td><c:out value="${entry.word}" /></td>
						<td><c:out value="${entry.id}" /></td>
						<td><c:out value="${entry.description}" /></td>
					</tr>
				</c:forEach>
			</tbody>
	</table>	

<input type="submit" value="Delete" id='ischeck'>
</form:form>
</c:if>

<%-- at least one record is selected to delete END --%>








 
 
 
 



 

 
 
 
 