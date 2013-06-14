<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<style type="text/css">
table,td,th,caption {
	border: 1px solid black;
}

th {
	background-color: #E9EEF6;
	color: black;
	font-weight: bold;
}

td {
	background-color: white;
	color: black;
	white-space: wrap;
	overflow: wrap;
	text-overflow: ellipsis;
}
</style>
<script>
	$(document).ready(function() {
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
	});
	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});
	$(document).ready(function() {
		$("#deletechk").is(":checked")(function(event) {
			$.alert("Select record to delete", "Oops !!!");
		});
	});
</script>
<article class="is-page-content">
	<form:form modelAttribute="project" method="POST"
		action="/auth/workbench/deleteproject">
		<c:if test="${not empty projectlist}">
			<span class="byline">Select the projects to be deleted:</span>
			<input class="command" type="submit" value='Delete'>
			<table style="width: 100%" class="display dataTable" id="projectlist">
				<thead>
					<tr>
						<th width="4%" align="center">Action</th>
						<th width="21%">Project Name</th>
						<th width="75%">Description</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="project" items="${projectlist}">
						<tr>
							<td><input type="checkbox" value="${project.internalid}" id="deletechk">
							</td>
							<td><font size="3"> <c:out value="${project.name}"></c:out>
							</font></td>
							<td><font size="3"> <c:out
										value="${project.description}"></c:out>
							</font></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<input class="command" type="submit" value='Delete'>
		</c:if>
		<c:if test="${empty projectlist}">
			You don't have any projects to delete.
		</c:if>
	</form:form>
</article>