<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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

<script>
	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {

		});

		$("input[type=button]").button().click(function(event) {

		});
	});

	function onSubmit() {

		location.href = '${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}';
	}
</script>

<form:form method="POST" name="myForm" commandName="collaborator"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/collaborators/add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<h2>Add collaborators to dictionary: ${dictionaryname}</h2>
	<div class="back-nav">
		<hr>
		<p>
			<a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}"><i
				class="fa fa-arrow-circle-left"></i> Back to Dictionary</a>
		</p>
		<hr>
	</div>


	<c:if test="${not empty nonCollaboratingUsers}">
		<div class="row">

			<div class="col-md-4">
				<h4>Select collaborator:</h4>
				<form:select path="userObj" id="selectbox" name="userName" class="form-control"
					onchange="enableDisable()">
					<form:option value="NONE" label="----- Select -----" />
					<form:options items="${nonCollaboratingUsers}" itemValue="userName"
						itemLabel="userName" />
				</form:select>
				<br>
				<form:errors path="userObj" cssClass="error"></form:errors>
			</div>
			<div class="col-md-8">
				<h4>Select access rights:</h4>
				<form:checkboxes element="div" path="collaboratorRoles" 
						class="roles" items="${possibleCollaboratorRoles}" itemValue="id"
						itemLabel="displayName" />
				<p><form:errors path="collaboratorRoles"
                        cssClass="error"></form:errors></p>
				
			</div>

		</div>
		<br>
		<input type="submit" value="Add" class="btn btn-primary">
		<a class="btn btn-default"
			href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}">Done</a>

	</c:if>
	<c:if test="${empty nonCollaboratingUsers}">
		<p>You have added all Quadriga users to the dictionary as collaborators.</p>
		<a class="btn btn-primary"
            href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}">Back</a>

	</c:if>
	<c:if test="${not empty collaboratingUsers}">
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
				<c:forEach var="collab" items="${collaboratingUsers}">
					<tr>
						<td><c:out value="${collab.collaborator.userObj.userName}" />
						</td>
						<td><c:forEach var="roles"
								items="${collab.collaborator.collaboratorRoles}">
								<c:out value="${roles.displayName}" />||
		</c:forEach></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
	</c:if>

</form:form>

