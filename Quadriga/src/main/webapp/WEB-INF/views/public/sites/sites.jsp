<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<input type="text" class="form-control"
		placeholder="Search Public Sites" name="searchTerm"
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
						href="${pageContext.servletContext.contextPath}/sites/${sitesList.unixName}">${project_baseurl}/sites/${sitesList.unixName}</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</form>