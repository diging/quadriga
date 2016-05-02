<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(document).ready(function() {
		var oldUnixName = $("#unixName").val();
		$("#editProject").prop("disabled",true);
		
		$("#unixName").keyup(function(event) {
			var keyedInput = $("#unixName").val();
			$("#UnixURL").text('${unixnameurl}' + keyedInput);
			
			if(keyedInput != oldUnixName) {
				$("#editProject").prop("disabled",false);
			}
			else {
				$("#editProject").prop("disabled",true);
			}
		});
		$("#dialog-confirm").dialog({
			autoOpen : false,
		});

		$('#editProject').click(funConfirmChanges);
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
}
</style>

<article class="is-page-content">
	<div id="projectDiv">
		<form:form id="target" commandName="project" method="POST"
			action="${pageContext.servletContext.contextPath}/auth/workbench/editProjectPageURL/${project.projectId}">

			<script>
				function funConfirmChanges() {
					// Define the Dialog and its properties.
					$("#dialog-confirm").dialog({
						resizable : false,
						height : 210,
						width : 400,
						buttons : {
							"Yes" : function() {
								$("#dialog-confirm").dialog('close');
								$("#target").submit();
							},
							"No" : function() {
								$("#dialog-confirm").dialog('close');
							}
						}
					});

					$("#dialog-confirm").dialog('open');

				}
			</script>


			<div id="dialog-confirm" title="Warning">Old URL will not work
				anymore. Do you wish to continue?</div>

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
					<td><input id="editProject" class="command" type="button"
						value="Edit Project URL"></td>
					<td><input type="button" value="Cancel"
						onclick="location.href='${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}'"></td>
				</tr>
			</table>
		</form:form>
	</div>
</article>


<!-- /Content -->