<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
	$(document).ready(function() {
<%-->Default uncheck the checkbox <--%>
	$("form input:checkbox").prop("checked", false);
	});

	$(function() {

		$("input[name='Back']").button().click(function(event) {
		});

		$("input[name='deletedict']").button().click(function(event) {
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

<h2>Remove Collaborator from Dictionary: ${dictionaryname}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Dictionary</a>
	</p>
	<hr>
</div>

<form:form method="POST" commandName="collaboratorForm"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/collaborators/delete">

	<c:if test="${not empty collaboratorForm.collaborators}">

    <p>Select dictionary collaborators to be remove and click "Remove".</p>
        
		<p>
			<input class="btn btn-primary btn-sm" class="command" type="submit"
				value='Remove' name="deletedict"> <input
				class="btn btn-primary btn-sm" type="button" value="Select All"
				name="selectall"> <input class="btn btn-primary btn-sm"
				type="button" value="Deselect All" name="deselectall">

		</p>
		<div class="panel panel-default">
			<table style="width: 100%" class="table">
				<thead>
					<tr>
						<th width="4%">Action</th>
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
							<td><form:label
									path="collaborators[${status.index}].userName">
									<c:out value="${collabUser.userName}"></c:out>
								</form:label></td>
							<td><form:label
									path="collaborators[${status.index}].collaboratorRoles">
									<c:forEach var="roles" items="${collabUser.collaboratorRoles}"
										varStatus="loop">
										<c:out value="${roles.displayName}" />||
								 </c:forEach>
								</form:label></td>

						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<p>
			<input class="btn btn-primary btn-sm" class="command" type="submit"
				value='Remove' name="deletedict"> <input
				class="btn btn-primary btn-sm" type="button" value="Select All"
				name="selectall"> <input class="btn btn-primary btn-sm"
				type="button" value="Deselect All" name="deselectall">
		</p>
	</c:if>
	<c:if test="${empty collaboratorForm.collaborators}">
		<p>There are no collaborators for this dictionary.</p>

		<p>
			<a class="btn btn-primary"
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}">Back</a>
		</p>


	</c:if>

</form:form>