<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

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



 <form action="${pageContext.servletContext.contextPath}/auth/profile/showadduri">
 <table>
	 <tr>
		 <td width="50%"><span style="font-weight:bold;">Name:&nbsp</span><c:out value="${user.name}"/></td>
		 <td width="50%"><span style="font-weight:bold;">Email:&nbsp</span><c:out value="${user.email}"/></td>
	 </tr>
 </table>
 
 <table width="100%" cellpadding="0" cellspacing="0" border="0" class="display dataTable">
 <thead>
 	<tr>
 		<th align="left">ServiceName</th>
 		<th align="left">URI</th>
 	</tr>
 </thead>
 <tbody>
 <c:forEach var="profile" items="${profileList}">
	 <tr>
		 <td width="50%"><c:out value="${profile.serviceName}"/></td>
		 <td width="50%"><c:out value="${profile.uri}"/></td>
	 </tr>
 </c:forEach>
 </tbody>
 </table>
 <input type="submit" value="Add uri">
 </form>
 
 
 <%-- <form action="${pageContext.servletContext.contextPath}/auth/profile/showadduri">
<ul>
<li><h1 class="heading">Name:</h1><c:out value="${user.name}"></c:out></li>
<li><h1 class="heading">Email:</h1><c:out value="${user.email}"></c:out></li>
<c:forEach var="profile" items="${profileList}">
<li class="withborder"><h1 class="heading">ServiceUri:</h1><c:out value="${profile.serviceName}"/>
<br><br><h1 class="heading">URI:</h1><c:out value="${profile.uri}" /></li>
</c:forEach>
</ul>
<input type="submit" value="Add URI">
</form>--%>