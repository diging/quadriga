<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<h2>Add Text to Workspace: ${workspace.workspaceName}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}"><i
			class="fa fa-arrow-circle-left"></i> Back to Workspace</a>
	</p>
	<hr>
</div>

<p>Please fill in the following information:</p>

<form:form commandName="textfile" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${myProjectId}/${workspaceId}/addtext">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<table style="width: 100%">
		<tr>
			<td style="width: 170px; vertical-align:top;">File Name:</td>
			<td><form:input class="form-control" path="fileName" size="60" id="fileName" />
                <form:errors path="fileName" class="error"></form:errors></td>
		</tr>
		<tr>
            <td style="width: 170px; vertical-align:top;">Author:</td>
            <td><form:input class="form-control" path="author" size="60" id="author" />
                <form:errors path="author" class="error"></form:errors></td>
        </tr>
        <tr>
            <td style="width: 170px; vertical-align:top;">Title:</td>
            <td><form:input class="form-control" path="title" size="60" id="title" />
                <form:errors path="title" class="error"></form:errors></td>
        </tr>
        <tr>
            <td style="width: 170px; vertical-align:top;">Creation Date:</td>
            <td><form:input class="form-control" path="creationDate" size="60" id="creationDate" />
                <form:errors path="creationDate" class="error"></form:errors></td>
        </tr>
		<tr>
			<td style="width: 170px; vertical-align:top;">Reference:</td>
			<td><form:input class="form-control" path="refId" size="60" id="refID" />
        <form:errors path="refId" class="error"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top">Add File Content:</td>
			<td><form:textarea class="form-control" path="fileContent" cols="44" rows="6"
					id="fileContent" />
			<form:errors path="fileContent" class="error"></form:errors></td>
		</tr>
		<tr>
			<td style="width: 170px; vertical-align:top;">Accessibility</td>
			<td><form:select class="form-control" path="accessibility">
					<form:option value="" label="--- Select ---" />
					<form:options />
				</form:select><form:errors path="accessibility"
					class="error"></form:errors></td>
		</tr>
		<tr>
			<td colspan="2"><input class="btn btn-primary" type="submit" value="Save Textfile">
			<a class="btn btn-default"
            href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}">Cancel</a>
            </td>
		</tr>
		<tr>
			<td><input type="hidden" id="workspaceId"
				value=<c:out value="${workspaceId}"></c:out> />
			 	
			</td>
		</tr>
		<tr>
			<td><input type="hidden" id="success"
				value=<c:out value="${success}"></c:out> /></td>
		</tr>
	</table>

</form:form>

<!-- /Content -->
