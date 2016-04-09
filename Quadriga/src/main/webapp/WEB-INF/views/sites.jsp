<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>


<script>
	$(document).ready(function() {
		$('#example').DataTable();
	});

	function handle(e) {
		if (e.keyCode === 13) {
			document.getElementById("sites").submit();
		}
		return false;
	}
</script>
<!-- Content -->
<form name='sites' id="sites"
	action="${pageContext.servletContext.contextPath}/sites/searchTerm"
	method='POST'>
	<input type="text" class="form-control"
		placeholder="Search Public Sites Repository" name="searchTerm"
		tabindex="1" onkeypress="handle(event)" autocapitalize="off">

	</br>
	<table id="example" class="table table-striped table-bordered"
		cellspacing="0" width="100%">
		<thead>
			<tr>
				<th>Title</th>
				<th>Description</th>
				<th>Project Lead</th>
				<th>Project URL</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="sitesList" items="${projectList}">
				<tr>
					<td style="width: 15%"><c:out value="${sitesList.projectName}"></c:out></td>
					<td style="width: 30%"><span class="comment more"> <c:out
								value="${sitesList.description}"></c:out></span></td>
					<td style="width: 20%"><c:out value="${sitesList.owner.name}"></c:out></td>
					<td style="width: 35%"><a
						href="${pageContext.servletContext.contextPath}/sites/${sitesList.unixName}">http://quadriga.asu.edu/sites/${sitesList.unixName}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>