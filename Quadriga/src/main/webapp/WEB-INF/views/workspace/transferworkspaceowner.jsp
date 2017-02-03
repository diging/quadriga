<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/projects/${projectid}';
	}

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});

	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>

<h2>Transfer Ownership of Workspace: ${wsname}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Workspace</a>
	</p>
	<hr>
</div>



<form:form commandName="user" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}/transfer">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<c:if test="${not empty collaboratinguser}">
		<div class="alert alert-info" role="alert">This workspace is
			currently owned by: ${wsowner}</div>
			
		<p>
		<form:select path="userName" class="form-control">
			<form:option value="" label="--- Select ---" />
			<form:options items="${collaboratinguser}" itemValue="userName"
				itemLabel="userName" />
		</form:select>
		<form:errors path="userName" class="error"></form:errors>
		</p>

		<div class="alert alert-warning" role="alert">Note: The current
			owner of this workspace will become a workspace admin and will not be
			able to undo the ownership transfer.</div>

		<td><input class="btn btn-primary" type="submit" value="Transfer"></td>
	</c:if>
	<c:if test="${empty collaboratinguser}">
		<p>You don't have any collaborators assigned to this workspace. To
			transfer ownership of this workspace, first add another user as
			collaborator to the workspace.</p>
		<p>
			<a class="btn btn-primary"
				href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceid}">Back</a>
		</p>
	</c:if>


</form:form>