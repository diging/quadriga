<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script>
	$(document).ready(function() {
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
<%-->Default uncheck the checkbox <--%>
	$("form input:checkbox").prop("checked", false);
	});

	$(function() {

		$("input[name='Back']").button().click(function(event) {
		});

		$("input[name='deletecc']").button().click(function(event) {
		});

		$("input[name='selectall']").button().click(function(event) {
			$("form input:checkbox").prop("checked", true);
			event.preventDefault();
			return;
		});

		$("input[name='deselectall']").button().click(function(event) {
			$("form input:checkbox").prop("checked", false);
			event.preventDefault();
			return;
		});
	});
</script>

<h3>Remove Collaborators from Project: ${projectname}</h3>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectId}"><i
			class="fa fa-arrow-circle-left"></i> Back to Project</a>
	</p>
	<hr>
</div>

<form:form method="POST" commandName="collaboratorForm"
	action="${pageContext.servletContext.contextPath}/auth/workbench/${myprojectId}/collaborators/delete">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<c:if test="${not empty collaboratorForm.collaborators}">
		<p>Select project collaborators to be remove and click "Remove".</p>

		<p>
			<input class="btn btn-primary" class="command" type="submit"
				value='Remove' name="deletecc"> <input
				class="btn btn-primary" type="button" value="Select All"
				name="selectall"> <input class="btn btn-primary"
				type="button" value="Deselect All" name="deselectall">
		</p>
		<div class="panel panel-default">
			<table class="table">
				<thead>
					<tr>
						<th width="4%"></th>
						<th width="21%">Collaborator</th>
						<th width="75%">Collaborator Roles</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="collabUser"
						items="${collaboratorForm.collaborators}" varStatus="status">
						<tr>
							<td><form:checkbox
									path="collaborators[${status.index}].userName"
									value="${collabUser.userName}" /></td>
							<td><font size="3"> <form:label
										path="collaborators[${status.index}].userName">
										<c:out value="${collabUser.userName}"></c:out>
									</form:label>
							</font></td>
							<td><font size="3"> <form:label
										path="collaborators[${status.index}].collaboratorRoles">
										<c:forEach var="roles" items="${collabUser.collaboratorRoles}"
											varStatus="loop">
											<c:out value="${roles.displayName}" />||
								 </c:forEach>
									</form:label>
							</font></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
	<c:if test="${empty collaboratorForm.collaborators}">
			<p>There are no collaborators for this project.</p>
			<a class="btn btn-primary"
                href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectId}">Cancel</a>

	</c:if>

</form:form>