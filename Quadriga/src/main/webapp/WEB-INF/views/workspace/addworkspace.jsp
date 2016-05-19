<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
$(function() {
	$("input[type=submit]").button().click(function(event){
	});
	$("input[type=button]").button().click(function(event){
	});
});

function submitClick(id){
	location.href = "${pageContext.servletContext.contextPath}/auth/workbench/projects/${wsprojectid}";
}
</script>
<article class="is-page-content">
	<form:form commandName="workspace" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${wsprojectid}/workspace/add">
		  	<header>
					<h2>Create new Workspace</h2>
					<span class="byline">Please fill in the following
						information:</span>
				</header>
				<table style="width: 100%">
					<tr>
						<td style="width: 170px">Name:</td>
						<td><form:input path="workspaceName" size="60" id="workspaceName" /></td>
						<td><form:errors path="workspaceName" class="ui-state-error-text"></form:errors></td>
					</tr>
					<tr>
						<td style="vertical-align: top">Description:</td>
						<td><form:textarea path="description" cols="44" rows="6"
								id="description" /></td>
						<td style="vertical-align:top;"><form:errors path="description" class="ui-state-error-text"></form:errors></td>
					</tr>
					<tr>
						<td><input type="submit" value="Create Workspace"></td>
					</tr>
					<tr>
						<td><input type="hidden" id="wsprojectid"
							value=<c:out value="${wsprojectid}"></c:out> /></td>
					</tr>
				</table>
		  </form:form>
</article>

<!-- /Content -->