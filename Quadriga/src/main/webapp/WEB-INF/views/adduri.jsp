<%@ page language="java" contentType="text/html;"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>
<style>
.errors{

color: #ff0000;
	font-style: italic;

}
</style>
</head>

<script>
function goBack(){
	
	location.href = '${pageContext.servletContext.contextPath}/auth/profile/search' ;
}

</script>

<h1>profile added successfully</h1>
<input type="button" value="Okay" onClick="goBack()"/>


<%--<script>


$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {
	
	});
});


function goBack(){
	
	location.href="${pageContext.servletContext.contextPath}/auth/profile";
}

</script>

<input type="submit" value="Go Back" onClick="goBack();">
<br><br>
<form:form method="POST" commandName="serviceUri"
action="${pageContext.servletContext.contextPath}/auth/profile/adduri">
<header>
<h2>Create New Service</h2>
<span class="byline">Please Fill in Service Name and URIs</span>

<table style="width: 100%">

<tr>
	<td>Service Name</td>
	<td><form:input path="serviceName" size="60"/></td>
	<td><form:errors path="serviceName" cssClass="errors"/></td> 
</tr>
<tr>
	<td>URI</td>
	<td><form:input path="uri" size="60"/></td>
	<td><form:errors path="uri" cssClass="errors"/></td>	 
</tr>
<tr>
	<td><input type="submit" value="Add"></td>
</tr>
</table>
</header>
</form:form> --%>
