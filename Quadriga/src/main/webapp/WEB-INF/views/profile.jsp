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

$(function() {
    $( "#dialog" ).dialog({
    buttons: {
    	OK: function(){
    		$(this).dialog("close");	
    	}
      }		
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
<header>
		<h2>Query authority file</h2>
		<span class="byline">Find yourself in an authority file</span>
	</header>
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
<c:when test="${success == '1'}">

	<c:if test="${not empty searchResultList}">
		<form:form method="POST"  modelAttribute="SearchResultBackBeanForm" 
		action="${pageContext.servletContext.contextPath}/auth/profile/${serviceid}/${term}/add">
		 <h3>Search results</h3>
		 
		 <input type="button" value="Select All" name="selectAll"> 
		 <input type="button" value="Deselect All" name="deselectAll">
		
		 <input type="submit" value="Save selected">
			 <table style="width:100%" cellpadding="0" cellspacing="0" border="0" class="display dataTable">	
				
				<thead>
					<tr>
						<th><h1>select</h1></th>
						<th><h1>Name</h1></th>
						<th><h1>ID</h1></th>
						<th><h1>Description</h1></th>
					</tr>
				</thead>
			
				<tbody>
				<c:forEach var="result" items="${SearchResultBackBeanForm.searchResultList}" varStatus="status">
					<tr>
						<form:hidden path="searchResultList[${status.index}].word" value="${result.word}"  />
						<form:hidden path="searchResultList[${status.index}].description" value="${result.description}"  />
						<form:hidden path="searchResultList[${status.index}].id" value="${result.id}"  />
						<td><form:checkbox path="searchResultList[${status.index}].isChecked"/></td>
						<form:errors path="searchResultList[${status.index}].isChecked" cssClass="error" />
						<td><c:out value="${result.word}"/></td>
						<td><c:out value="${result.id}"/></td>
						<td><c:out value="${result.description}"/></td> 
					</tr>
				</c:forEach>
				</tbody>
			</table>
		 </form:form>
	 </c:if>
</c:when>

<c:otherwise>
	<c:if test="${success == '2'}">
	
	<%--<c:if test="${not empty searchResultList}">   why after putting this condition error message gets displayed twice--%>   
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
						<th><h1>select</h1></th>
						<th><h1>Name</h1></th>
						<th><h1>ID</h1></th>
						<th><h1>Description</h1></th>
					</tr>
				</thead>
			
				<tbody>
				<c:forEach var="result" items="${SearchResultBackBeanForm.searchResultList}" varStatus="status">
					<tr>
						<form:hidden path="searchResultList[${status.index}].word" value="${result.word}"  />
						<form:hidden path="searchResultList[${status.index}].description" value="${result.description}"  />
						<form:hidden path="searchResultList[${status.index}].id" value="${result.id}"  />
						<td><form:checkbox path="searchResultList[${status.index}].isChecked"/></td>
						<form:errors path="searchResultList[${status.index}].isChecked" cssClass="error" />
						<td><c:out value="${result.word}"/></td>
						<td><c:out value="${result.id}"/></td>
						<td><c:out value="${result.description}"/></td> 
					</tr>
				</c:forEach>
				</tbody>
			</table>
		 </form:form>
	 </c:if>
	   
	<%--  <div id="dialog" title="Oops!"> 
		<p>${errmsg}</p>
	 </div> --%>
	
</c:otherwise>
</c:choose>

<c:if test="${success == '2'}">
	<font color="red">${errmsg}</font>
	<div id="dialog" title="Oops!">
		<p>${errmsg}</p>
	</div>
</c:if>

			



			
	

 



 

 
 
 
 