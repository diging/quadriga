<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style type="text/css">
div.wrap {
	overflow: auto;
	margin-bottom: -30px;
}

div.ex {
	width: 200px;
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
}
</style>

<script>
	$(document).ready(function() {

		$("input[type=submit]").button().click(function(event) {
		});
		$("input[type=button]").button().click(function(event) {
		});
	});
	function onSubmit() {

		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}';
	}
</script>

<h2>Add Collaborators to Workspace: ${workspacename}</h2>
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
	action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/addcollaborators">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<c:if test="${not empty noncollabusers}">

		<div class="row">

			<div class="col-md-4">
				<h4>Select collaborator:</h4>
				<form:select class="form-control" path="userObj" id="userName">
					<form:option value="NONE" label="----- Select -----" />
					<form:options items="${noncollabusers}" itemValue="userName"
						itemLabel="name" />
				</form:select>
				<form:errors path="userObj" class="error"></form:errors>
				<br>
			</div>

			<div class="col-md-8">
					<h4>Select access rights:</h4>
					
						<form:checkboxes path="collaboratorRoles" class="roles"
							items="${wscollabroles}" itemValue="DBid" itemLabel="displayName"
							element="div" />
					
					<form:errors path="collaboratorRoles" class="error"></form:errors>
					<br />
				
			</div>
		</div>
		<input class="btn btn-primary" type="submit" value="Add">
		<a class="btn btn-default"
			href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}">Done</a>

	</c:if>

	<c:if test="${empty noncollabusers}">
		<hr>
		<p>You've added all Quadriga users to this workspace as
			collaborators.</p>
		<a class="btn btn-default"
			href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}">Done</a>
	</c:if>
</form:form>

<c:if test="${not empty collaboratingusers}">
<hr>
	<H4>Current Collaborators:</H4>
	<div class="panel panel-default">
		<table class="table">
			<thead>
				<tr>
					<th align="center" width="30%">Collaborator</th>
					<th align="center" width="70%">Roles</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="workspaceCollaborator" items="${collaboratingusers}">
					<tr>
						<td><c:out
								value="${workspaceCollaborator.collaborator.userObj.name}"></c:out></td>
						<td><c:forEach var="roles"
								items="${workspaceCollaborator.collaborator.collaboratorRoles}">
								<c:out value="${roles.displayName}"></c:out> ||
		 	</c:forEach></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</c:if>
