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

	function submitClick(id) {
		location.href = "${pageContext.servletContext.contextPath}/auth/workbench/projects/${wsprojectid}";
	}
</script>

<h2>Create Workspace in Project: ${project.projectName}</h2>

<div class="back-nav">
    <hr>
    <p>
        <a
            href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${wsprojectid}"><i
            class="fa fa-arrow-circle-left"></i> Back to Project</a>
    </p>
    <hr>
</div>

<form:form commandName="workspace" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/${wsprojectid}/workspace/add">
	<p>Please fill in the following information:</p>
	<table style="width: 100%">
		<tr>
			<td style="width: 170px; vertical-align:top;">Name:</td>
			<td><form:input path="workspaceName" class="form-control"
					size="60" id="workspaceName" />
			<form:errors path="workspaceName"
					class="error"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top; vertical-align:top;">Description:</td>
			<td><form:textarea class="form-control" path="description"
					cols="44" rows="6" id="description" /><form:errors path="description"
					class="error"></form:errors></td>
		</tr>
		<tr>
			<td colspan="2">
			    <input class="btn btn-primary" type="submit" value="Create Workspace">
				<a class="btn btn-default"
                     href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${wsprojectid}">Cancel</a>
            
			</td>
		</tr>
		<tr>
			<td>
			     <input type="hidden" id="wsprojectid"
				value=<c:out value="${wsprojectid}"></c:out> />
		    </td>
		</tr>
	</table>
</form:form>

<!-- /Content -->