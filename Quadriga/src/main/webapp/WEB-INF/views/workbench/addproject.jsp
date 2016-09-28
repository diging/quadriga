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

<form:form commandName="project" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/addproject">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<h2>Create new Project</h2>
	<p>Please fill in the following information:</p>
	<table style="width: 100%">
		<tr>
			<td style="width: 170px; vertical-align: top;">Name:</td>
			<td><form:input class="form-control"
					path="projectName" size="60" id="projectName" />
			<form:errors path="projectName" class="error"></form:errors>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top">Description:</td>
			<td><form:textarea class="form-control" path="description"
					cols="44" rows="6" id="description" />
            <form:errors path="description" class="error"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top">Project Public Access:</td>
			<td><form:select class="form-control" path="projectAccess">
					<form:option value="" label="--- Select ---" />
					<form:options />
				</form:select>
			<form:errors path="projectAccess"
					class="error"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top">Custom URL:</td>
			<td>
				<div class="input-group">
					<div class="input-group-addon">${unixnameurl}</div>
					<input type="hidden" name="projectId" value="${project.projectId}">
					<input type="text" class="form-control" id="unixName"
						name="unixName" placeholder="Custom project URL">
				
                </div>
                <form:errors path="unixName" class="error"></form:errors>
                </td>
		</tr>

		<tr>
			<td><input class="btn btn-primary" type="submit" value="Create">
			<a class="btn btn-default" href="${pageContext.servletContext.contextPath}/auth/workbench">Cancel</a>
			</td>
		</tr>
	</table>
</form:form>

<!-- /Content -->