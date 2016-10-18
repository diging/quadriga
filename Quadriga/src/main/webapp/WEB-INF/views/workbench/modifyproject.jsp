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
}
</style>

<h2>Modify Project</h2>
<div class="back-nav">
    <hr>
    <p>
        <a
            href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}"><i
            class="fa fa-arrow-circle-left"></i> Back to Project</a>
    </p>
    <hr>
</div>
<p>Please update the following information:</p>

<form:form commandName="project" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/modifyproject/${project.projectId}">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<table style="width: 100%">
		<tr>
			<td style="width: 170px">Name:</td>
			<td><form:input path="projectName" class="form-control"
					 id="projectName" /></td>
			<td><form:errors path="projectName" class="ui-state-error-text"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top">Description:</td>
			<td><form:textarea path="description" cols="44" rows="6"  class="form-control"
					id="description" /></td>
			<td><form:errors path="description" class="ui-state-error-text"></form:errors>
				<form:input path="projectId" type="hidden" /></td>
		</tr>
		<tr>
			<td>Project Public Access:</td>
			<td><form:select path="projectAccess"  class="form-control">
					<form:option value="" label="--- Select ---" />
					<form:options />
				</form:select>
			<td><form:errors path="projectAccess"
					class="ui-state-error-text"></form:errors></td>
		</tr>
		<tr>
			<td colspan=2><input class="btn btn-primary" class="command" type="submit" value="Modify Project">
			<a class="btn btn-default" href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}">Cancel</a></td>
		</tr>
	</table>
</form:form>

<!-- /Content -->
