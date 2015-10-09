<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}';
	}
</script>

<style>
div.projectDiv {
	position: relative;
	width: 300px;
	height: 30px;
	border: 1px solid;
	text-align: center;
}

input {
	position: relative;
	width: 125px;
	height: 30px;
}
</style>

<article class="is-page-content">
	<div id="projectDiv">
		<form:form commandName="project" method="POST"
			action="${pageContext.servletContext.contextPath}/auth/workbench/modifyproject/${project.projectId}">
			<header>
				<h2>Modify Project</h2>
				<span class="byline">Please fill in the following
					information:</span>
			</header>
			<table style="width: 100%">
				<tr>
					<td style="width: 170px">Name:</td>
					<td style="width: 400px"><form:input path="projectName"
							size="60" id="projectName" /></td>
					<td><form:errors path="projectName"
							class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td style="vertical-align: top">Description:</td>
					<td><form:textarea path="description" cols="44" rows="6"
							id="description" /></td>
					<td><form:errors path="description"
							class="ui-state-error-text"></form:errors> <form:input
							path="projectId" type="hidden" /></td>
				</tr>
				<tr>
					<td>Project Public Access:</td>
					<td><form:select path="projectAccess">
							<form:option value="" label="--- Select ---" />
							<form:options />
						</form:select>
					<td><form:errors path="projectAccess"
							class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td><input class="command" type="submit"
						value="Modify Project"></td>
					<td><input type="button" value="Cancel"
						onclick="location.href='${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}'"></td>
				</tr>
			</table>
		</form:form>
	</div>
</article>
<!-- /Content -->
