<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(document).ready(function() {

		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});
	});
</script>
<h2>Update Collaborator Permissions for Workspace: ${workspacename}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Workspace</a>
	</p>
	<hr>
</div>

<form:form commandName="collaboratorform" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/updatecollaborators"
	id="updatewscollabform">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<c:if test="${not empty collaboratorform.collaborators}">
		<p>Select permissions for collaborators and click "Update".</p>

        <p>
			<input class="btn btn-primary" type="submit" value='Update'
				name="updatewscollab">
			<a class="btn btn-default"
				href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}">Cancel</a>
        </p>
		<div class="panel panel-default">
			<table style="width: 100%" class="table" id="wscollablist">
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
							<td><form:label path="collaborators[${status.index}].name">
									<c:out value="${collabuser.name}"></c:out>
								</form:label> <form:input path="collaborators[${status.index}].userName"
									id="collaborators[${status.index}].userName" type="hidden" /></td>

							<td align="left"><form:checkboxes
									path="collaborators[${status.index}].collaboratorRoles"
									class="roles" items="${wscollabroles}" itemValue="id"
									itemLabel="displayName" /> <form:errors
									path="collaborators[${status.index}].collaboratorRoles"
									class="error"></form:errors></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${empty collaboratorform.collaborators}">
		<p>There are no collaborators for this workspace.</p>
        <a class="btn btn-primary"
            href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}">Cancel</a>

	</c:if>

</form:form>

