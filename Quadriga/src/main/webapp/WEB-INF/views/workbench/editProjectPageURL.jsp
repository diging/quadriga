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
		<form:form id="target" commandName="project" method="POST"
			action="${pageContext.servletContext.contextPath}/auth/workbench/editProjectPageURL/${project.projectId}">

			<script>
				function funConfirmChanges() {

					var name = $.trim($('#unixName').val());
					if (name === '') {
						$("#dialog-confirm").dialog({
							resizable : false,
							modal : true,
							title : "Custom URL cannot be empty.",
							height : 140,
							width : 400,
							buttons : {
								"Ok" : function() {
									$(this).dialog('close');
									return false;
								}
							}
						});
						return false;
					} else {
						// Define the Dialog and its properties.
						$("#dialog-confirm").dialog({
							resizable : false,
							modal : true,
							title : "Old URL will not work anymore. Continue?",
							height : 140,
							width : 500,
							buttons : {
								"Yes" : function() {
									$(this).dialog('close');
									$("#target").submit();
									loca
									return false;
								},
								"No" : function() {
									$(this).dialog('close');
									return false;
								}
							}
						});
					}
				}
			</script>

			<div id="dialog-confirm" title="Confirm ?"></div>

			<header>
				<h2>Edit Project URL</h2>
				<span class="byline">Please fill in the following
					information:</span>
			</header>
			<table style="width: 100%">
				<tr>
					<td style="width: 170px">Name:</td>
					<td style="width: 400px"><c:out
							value="${project.projectName }" /> <form:hidden
							path="projectName" /></td>
				</tr>
				<tr>
					<td>Custom URL:</td>
					<td><form:input path="unixName" size="60" id="unixName" /></td>
					<td><form:errors path="unixName" class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td>Public URL: <!--<form:input path="unixName" type="hidden" />-->
						<form:input path="projectId" type="hidden" />
					</td>
					<td><div id="UnixURL"></div></td>
				</tr>
				<tr>
					<td><input class="command" type="button"
						onclick="return funConfirmChanges();" value="Edit Project URL"></td>
					<td><input type="button" value="Cancel"
						onclick="location.href='${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}'"></td>
				</tr>
			</table>
		</form:form>
	</div>
</article>


<!-- /Content -->