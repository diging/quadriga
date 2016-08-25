<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});

	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>

<h2>Transfer Ownership of Project: ${projectname}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectId}"><i
			class="fa fa-arrow-circle-left"></i> Back to Project</a>
	</p>
	<hr>
</div>



<form:form commandName="user" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectId}/transfer">

	<c:if test="${not empty collaboratinguser}">
		<div class="alert alert-info" role="alert">This project is
			currently owned by: ${projectowner}</div>

		<p>Select a new owner for the project:</p>

		<p>
			<form:select class="form-control" path="userName">
				<form:option value="" label="--- Select ---" />
				<form:options items="${collaboratinguser}" itemValue="userName"
					itemLabel="userName" />
			</form:select>
			<form:errors path="userName" class="error"></form:errors>
		</p>

		<div class="alert alert-warning" role="alert">Note: The current
			owner of this project will become a project admin and will not be
			able to undo the ownership transfer.</div>

		<input type="submit" value="Transfer" class="btn btn-primary">
		<a class="btn btn-default"
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectId}">
			Cancel</a>
	</c:if>
	<c:if test="${empty collaboratinguser}">
		<p>You don't have any collaborators assigned to this project. To
			transfer ownership of this project, first add another user as
			collaborator to the project.</p>
		<p>
			<a class="btn btn-primary"
				href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${myprojectId}">Back</a>
		</p>
	</c:if>
</form:form>
