<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}';
	}

	$(document).ready(function() {

		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});
	});
</script>

<h2>Update Collaborator Permissions for Project: ${myprojectname}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Project</a>
	</p>
	<hr>
</div>


<form:form commandName="collaboratorform" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/${myprojectid}/updatecollaborators"
	id="updateprojcollabform">
	<c:if test="${not empty collaboratorform.collaborators}">

		<p>Select permissions for collaborators and click "Update".</p>
		<p>
			<input class="btn btn-primary" type="submit" value='Update'
				name="updateprojcollab"> 
			<a class="btn btn-default"
				href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}">Cancel</a>
		</p>

		<div class="panel panel-default">
			<table style="width: 100%" class="table" id="projcollablist">
				<thead>
					<tr>
						<th width="25%">Collaborator</th>
						<th width="75%">Collaborator Roles</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="collabuser"
						items="${collaboratorform.collaborators}" varStatus="status">
						<tr>
							<td><font size="3"> <form:label
										path="collaborators[${status.index}].name">
										<c:out value="${collabuser.name}"></c:out>
									</form:label>
							</font> <form:input path="collaborators[${status.index}].userName"
									id="collaborators[${status.index}].userName" type="hidden" /></td>

							<td align="left"><font size="3"> <form:checkboxes
										path="collaborators[${status.index}].collaboratorRoles"
										class="roles" items="${projectcollabroles}" itemValue="id"
										itemLabel="displayName" /></font> <form:errors
									path="collaborators[${status.index}].collaboratorRoles"
									class="ui-state-error-text"></form:errors></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${empty collaboratorform.collaborators}">
		<p>There are no collaborators for this project.</p>
		<a class="btn btn-primary"
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectid}">Cancel</a>

	</c:if>

</form:form>

