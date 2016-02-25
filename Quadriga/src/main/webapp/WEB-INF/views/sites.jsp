<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	

<!-- Content -->

<article class="is-page-content">
<head>
<style>
table, td, th {
    border: 2px solid black;
}

table {
    border-collapse: collapse;
    width: 100%;
}

td {
    height: 10px;
    vertical-align: middle;
    padding-left: 3px;
}

th {
    height: 20px;
    vertical-align: middle;
    text-align: center;
    font-weight: bold;
}
</style>
</head>
<table >
<tr>
	<th> Title </th>
	<th> Description </th>
	<th> Developers </th>
	<th> Project URL </th>
</tr>
<c:forEach var="sitesList" items="${projectList}">
<tr>
	<td><c:out value="${sitesList.projectName}"></c:out></td>
	<td><c:out value="${sitesList.description}"></c:out></td>
	<td><c:out value="${sitesList.owner.name}"></c:out></td>
	<td><a href="${pageContext.servletContext.contextPath}/sites/${sitesList.unixName}">http://quadriga.asu.edu/sites/${sitesList.unixName}</a></td>
</tr>
</c:forEach>

</table>
</article>