<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<head>
<style>
.error{

color: #ff0000;
	font-style: italic;

}
</style>
</head>

<script>

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
	<%--<form:errors path="serviceName" cssClass="errors"/> --%>
</tr>
<tr>
	<td>URI</td>
	<td><form:input path="uri" size="60"/></td>
	<%--<form:errors path="uri" cssClass="errors"/>	 --%>
</tr>
<tr>
	<td><input type="submit" value="Add"></td>
</tr>
</table>
</header>
</form:form>
