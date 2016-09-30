<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>

<h2>Modify Workspace</h2>
<div class="back-nav">
    <hr>
    <p>
        <a
            href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspace.workspaceId}"><i
            class="fa fa-arrow-circle-left"></i> Back to Workspace</a>
    </p>
    <hr>
</div>

<form:form commandName="workspace" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspace.workspaceId}/update">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

	<p>Please update the the following information:</p>
	<table style="width: 100%">
		<tr>
			<td style="width: 170px; vertical-align: top;">Name:</td>
			<td><form:input class="form-control" path="workspaceName"
					id="workspaceName" /> <form:errors path="workspaceName"
					class="error"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top">Description:</td>
			<td><form:textarea class="form-control" path="description"
					rows="6" id="description" /> <form:errors path="description"
					class="error"></form:errors></td>
		</tr>
		<tr>
			<td colspan="2">
			     <input class="btn btn-primary" type="submit" value="Update Workspace"> 
				<a class="btn btn-default"
				    href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspace.workspaceId}">Cancel</a>
			</td>
		</tr>
	</table>
	</header>
</form:form>
</article>

<!-- /Content -->