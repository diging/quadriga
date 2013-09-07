<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>

$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {
	
	});
});

</script>


<form:form method="POST" commandName="serviceUri"
action="${pageContext.servletContext.contextPath}/auth/profile/adduri">
<header>
<h2>Create New Service</h2>
<span class="byline">Please Fill in Service Name and URIs</span>

<table style="width: 100%">

<tr>
	<td>Service Name</td>
	<td><form:input path="serviceName" size="60"/></td>
</tr>

<tr>
<td>URI</td>
<td><form:input path="uri" size="60"/></td>
</tr>

<tr>
<td><input type="submit" value="add"></td>
</tr>
</table>
</header>
</form:form>