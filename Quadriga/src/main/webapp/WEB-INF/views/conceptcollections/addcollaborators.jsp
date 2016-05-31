<%@ page language="java" contentType="text/html;"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<style>
div.wrap {
	overflow: auto;
	margin-bottom: -30px;
}

div.ex {
	width: 250px;
	float: left;
}

div.ex1 {
	width: 200px;
	margin-top: -29px;
	float: left;
	overflow: auto;
}

div.rolesError {
	float: left;
	margin-top: -45px;
}
</style>
<script type="text/javascript">
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
		$("input[type=button]").button().click(function(event) {

		});
	});

	function onSubmit() {
		location.href = '${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}';
	}
</script>
<form:form method="POST" name="myForm" commandName="ccCollaborator"
	action="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}/addcollaborators">
	<h2>Add Collaborators to Concept Collection: ${collectionname}</h2>

	<div class="back-nav">
		<hr>
		<p>
			<a
				href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}"><i
				class="fa fa-arrow-circle-left"></i> Back to Concept Collection</a>
		</p>
		<hr>
	</div>

	<c:if test="${not empty nonCollaboratorList}">

		<div class="row">
			<div class="col-md-4">
				<h4>Select collaborator</h4>
				<form:select path="userObj" id="userName" class="form-control">
					<form:option value="NONE" label="----- Select -----" />
					<form:options items="${nonCollaboratorList}" itemValue="userName"
						itemLabel="userName" />
				</form:select>
				<br>
				<form:errors path="userObj" cssClass="error"></form:errors>
			</div>
			<div class="col-md-8">
				<h4>Select access rights:</h4>
				<form:checkboxes element="div" path="collaboratorRoles"
					items="${collaboratorRoles}" itemValue="id" itemLabel="displayName" />
				<p>
					<form:errors path="collaboratorRoles" cssClass="error"></form:errors>
				</p>
			</div>
		</div>
		<input class="btn btn-primary" type="submit" value="Add">
		<a class="btn btn-default"
			href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}">Done</a>
	</c:if>
	<c:if test="${empty nonCollaboratorList}">
		<p>You have added all Quadriga users to the concept collection as
			collaborators.</p>
		<a class="btn btn-primary"
			href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}">Back</a>
	</c:if>
	<c:if test="${not empty ccCollaboratingUsers}">
		<hr>
		<h4>Current collaborators:</h4>
		<div class="panel panel-default">
			<table style="width: 100%" class="table">
				<thead>
					<tr>
						<th align="left">Collaborator</th>
						<th align="left">Roles</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="collab" items="${ccCollaboratingUsers}">
						<tr>
							<td><c:out value="${collab.collaborator.userObj.name}"></c:out></td>
							<td><c:forEach var="roles"
									items="${collab.collaborator.collaboratorRoles}">
									<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</form:form>

