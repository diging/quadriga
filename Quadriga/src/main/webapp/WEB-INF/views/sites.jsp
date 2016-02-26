<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
	
<article class="is-page-content">
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
	<td style="width:15%"><c:out value="${sitesList.projectName}"></c:out></td>
	<td style="width:30%"><span class="comment more">	<c:out value="${sitesList.description}"></c:out></span></td>
	<td style="width:20%"><c:out value="${sitesList.owner.name}"></c:out></td>
	<td style="width:35%"><a href="${pageContext.servletContext.contextPath}/sites/${sitesList.unixName}">http://quadriga.asu.edu/sites/${sitesList.unixName}</a></td>
</tr>
</c:forEach>
</tbody>
</table>
</article>