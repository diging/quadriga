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

.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<script>
	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});
</script>
<article class="is-page-content">
	<form:form commandName="collaboratorform" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${projectid}/updatecollaborators"
		id="updateprojcollabform">

		<c:if test="${not empty collaboratorform.collaborator}">
			<span class="byline">Select roles for the collaborator:</span>
			<input type="submit" value='Update' name="updateprojcollab">
			<table style="width: 100%" class="display dataTable"
				id="projcollablist">
				<thead>
					<tr>
						<th width="20%">Collaborator Name</th>
						<th width="75%">Collaborator Roles</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="collabuser"
						items="${collaboratorform.collaborator}" varStatus="status">
						<tr>
							<td><font size="3">
									<div name="collaborator[${status.index}].userObj.userName">
										<c:out value="${collabuser.userObj.userName}"></c:out>
									</div>
							</font></td>
							 
							<td><font size="3"> <form:checkboxes
										path="collaborator[${status.index}].collaboratorRoles"
										class="roles" items="${projcollabroles}" itemValue="roleid"
										itemLabel="displayName" /></font></td>
										
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</form:form>
</article>
