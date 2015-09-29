<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(document).ready(function() {
		$("#unixName").keyup(function(event) {
			var keyedInput = $("#unixName").val();
			$("#UnixURL").text('${unixnameurl}' + keyedInput);
		});
	});

	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});

		if ($.trim($("#unixName").val())) {
			var keyedInput = $("#unixName").val();
			$("#UnixURL").text('${unixnameurl}' + keyedInput);
		}
	});

	function submitClick(id) {

		location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}';
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
			<c:choose>
				<c:when test="${success == '0'}">
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
									class="ui-state-error-text"></form:errors></td>
						</tr>
						<tr>
							<td>Public URL: <!--<form:input path="unixName" type="hidden" />-->
								<form:input path="projectId" type="hidden" /> <form:input
									path="unixName" id="unixName" type="hidden" /> <form:input
									path="projectAccess" id="projectAccess" type="hidden" />

							</td>
							<td><div id="UnixURL"></div></td>
						</tr>
						<tr>
							<td><input class="command" type="submit"
								value="Modify Project"></td>
							<td><input type="button" value="Cancel"
								onclick="location.href='${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}'"></td>
						</tr>
					</table>
				</c:when>
				<c:when test="${success == '1'}">
					<span class="byline">Project modified successfully.</span>
					<ul>
						<li><input type="button" onClick="submitClick();"
							value='Okay'></li>
					</ul>
				</c:when>
			</c:choose>
		</form:form>
	</div>
</article>


<!-- /Content -->
