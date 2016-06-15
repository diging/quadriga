<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}';
	}

	$(document).ready(function() {

		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});

	});
</script>

<h2>Modify Collaborator Roles for Concept Collection:
	${collectionname}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Concept Collection</a>
	</p>
	<hr>
</div>

<form:form commandName="collaboratorform" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/updatecollaborators"
	id="updateprojcollabform">

	<c:if test="${not empty collaboratorform.collaborators}">

		<p>Select roles for collaborators and click "Update".</p>
		<p>
			<input type="submit" class="btn btn-primary" value='Update'
				name="updateprojcollab"> 
			<a class="btn btn-default"
                href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}">Cancel</a>
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
							<td><font size="3"> <form:label
										path="collaborators[${status.index}].name">
										<c:out value="${collabuser.name}"></c:out>
									</form:label>
							</font> <form:input path="collaborators[${status.index}].userName"
									id="collaborators[${status.index}].userName" type="hidden" />
								<form:input path="collaborators[${status.index}].name"
									id="collaborators[${status.index}].name" type="hidden" /></td>

							<td align="left"><form:checkboxes
										path="collaborators[${status.index}].collaboratorRoles"
										class="roles" items="${cccollabroles}" itemValue="id"
										itemLabel="displayName" /> <form:errors
									path="collaborators[${status.index}].collaboratorRoles"
									cssClass="error"></form:errors></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${empty collaboratorform.collaborators}">
		<div>There are no collaborators for this concept collection.</div>
	</c:if>
</form:form>
