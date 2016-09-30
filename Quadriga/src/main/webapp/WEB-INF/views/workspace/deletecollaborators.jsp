<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {

		$("input[name='selectall']").button().click(function(event) {
			$("input[name='wscollabchecked']").prop("checked", true);
			event.preventDefault();
			return;
		});

		$("input[name='deselectall']").button().click(function(event) {
			$("input[name='wscollabchecked']").prop("checked", false);
			event.preventDefault();
			return;
		});
	});
</script>

<h3>Remove Collaborators from Workspace: ${workspacename}</h3>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Workspace</a>
	</p>
	<hr>
</div>

<form:form commandName="collaborator" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/deletecollaborators"
	id="deletewscollabform">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<c:if test="${not empty collaboratingusers}">
		<p>Select project collaborators to be remove and click "Remove".</p>


		<p>
			<input class="btn btn-primary" class="command" type="submit"
				value='Delete' name="deletecollab"> <input
				class="btn btn-primary" type="button" value="Select All"
				name="selectall"> <input class="btn btn-primary"
				type="button" value="Deselect All" name="deselectall">
		</p>

		<table style="width: 100%" class="table" id="wscollablist">
			<thead>
				<tr>
					<th width="5%"></th>
					<th width="25%">Collaborator</th>
					<th width="70%">Roles</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="workspaceCollaborator" items="${collaboratingusers}">
					<tr>
						<td><input type="checkbox" name="wscollabchecked"
							value="${workspaceCollaborator.collaborator.userObj.userName}"></td>
						<td><c:out
								value="${workspaceCollaborator.collaborator.userObj.name}"></c:out>
						</td>
						<td><c:forEach var="roles"
								items="${workspaceCollaborator.collaborator.collaboratorRoles}">
								<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

	</c:if>
	<c:if test="${empty collaboratingusers}">

		<p>There are no collaborators for this workspace.</p>
		<a class="btn btn-primary"
			href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}">Cancel</a>

	</c:if>
</form:form>




