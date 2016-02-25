<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	

<!-- Content -->

<article class="is-page-content">
<head>
<script type="text/javascript" charset="utf8">
			$(document).ready(function(){
			  $('#example').dataTable({
				"sPaginationType" : "full_numbers",
				"bLengthChange": false,
				"bAutoWidth" : false
			  });
			});
		</script>
</head>
<table cellpadding="0" cellspacing="0" border="0" class="dataTable" id="example">
	<thead>
<tr>
	<th> Title </th>
	<th> Description </th>
	<th> Developers </th>
	<th> Project URL </th>
</tr>
</thead>
<tbody>
<c:forEach var="sitesList" items="${projectList}">
<tr>
	<td><c:out value="${sitesList.projectName}"></c:out></td>
	<td><c:out value="${sitesList.description}"></c:out></td>
	<td><c:out value="${sitesList.owner.name}"></c:out></td>
	<td><a href="${pageContext.servletContext.contextPath}/sites/${sitesList.unixName}">http://quadriga.asu.edu/sites/${sitesList.unixName}</a></td>
</tr>
</c:forEach>
</tbody>
</table>
</article>