<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>

$(function() {
	$("input[type=submit]").button().click(function(event){
	});
});
</script>
<style>
.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<header>
	<h2>Update Workspace</h2>
	<span class="byline">Please update the the following
		information:</span>
</header>

<article class="is-page-content">

	<form:form commandName="workspace" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/updateworkspacedetails/${workspace.id}">
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Name:</td>
				<td><form:input path="name" size="60" id="name" /></td>
				<td><form:errors path="name" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><form:textarea path="description" cols="44" rows="6"
						id="description" /></td>
				<td><form:errors path="description" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="Update Workspace"></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->