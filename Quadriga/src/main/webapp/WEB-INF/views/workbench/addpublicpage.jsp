<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
	$(document).ready(function() {

		$("#unixName").keyup(function(event) {
			var keyedInput = $("#unixName").val();
			$("#UnixURL").text('${unixnameurl}' + $.trim(keyedInput));
		});
	});

	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});
	});
</script>
<article class="is-page-content">
	<form:form commandName="project" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/addpublicpage">
		<header>
			<h2>Editing Text Contents to be shown</h2>
			<span class="byline">Please fill in the following information:</span>
		</header>
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Heading 1:</td>
				<td style="width: 400px"><form:input path="projectName"
						size="60" id="projectName" /></td>
				<td><form:errors path="projectName" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Heading 2:</td>
				<td style="width: 400px"><form:input path="projectName"
						size="60" id="projectName" /></td>
				<td><form:errors path="projectName" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Heading 3:</td>
				<td style="width: 400px"><form:input path="projectName"
						size="60" id="projectName" /></td>
				<td><form:errors path="projectName" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE"></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->