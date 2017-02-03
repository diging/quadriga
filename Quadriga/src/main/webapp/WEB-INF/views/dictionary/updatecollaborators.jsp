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
<h2>Update Collaborator Permissions for Dictionary: ${dictionaryname}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Dictionary</a>
	</p>
	<hr>
</div>

<form:form commandName="collaboratorform" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/collaborators/update"
	id="updateprojcollabform">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<c:if test="${not empty collaboratorform.collaborators}">

		<p>Select permissions for collaborators and click "Update".</p>
		<p>
			<input class="btn btn-primary" type="submit" value='Update'
				name="updateprojcollab"> 
			<a class="btn btn-default"
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}">Cancel</a>
		</p>
		<div class="panel panel-default">
			<table style="width: 100%" class="table" id="cccollablist">
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
									id="collaborators[${status.index}].userName" type="hidden" />
								<form:input path="collaborators[${status.index}].name"
									id="collaborators[${status.index}].name" type="hidden" /></td>

							<td align="left"><form:checkboxes
									path="collaborators[${status.index}].collaboratorRoles"
									class="roles" items="${dictcollabroles}" itemValue="id"
									itemLabel="displayName" /> <form:errors
									path="collaborators[${status.index}].collaboratorRoles"
									cssClass="ui-state-error-text"></form:errors></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${empty collaboratorform.collaborators}">
		<p>There are no collaborators for this dictionary.</p>
		<a class="btn btn-primary"
			href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}">Back</a>
	</c:if>

</form:form>
