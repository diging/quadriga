<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
	$(document).ready(function() {
	});
	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>
<article class="is-page-content">
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/addpublicpage">
		<header>
			<h2>Editing Text Contents to be shown</h2>
			<span class="byline">Please fill in the following information:</span>
		</header>
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title 1</td>
				<td style="width: 400px"><form:input path="title1"
						size="60" id="title1" /></td>
				<td><form:errors path="title1" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description 1</td>
				<td><form:textarea path="description1" cols="60" rows="6"
						id="description1" /></td>
				<td><form:errors path="description1" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE"></td>
			</tr>
			<tr>
				<td style="width: 170px">Title 2</td>
				<td style="width: 400px"><form:input path="title2"
						size="60" id="title2" /></td>
				<td><form:errors path="title2" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description 2</td>
				<td><form:textarea path="description2" cols="60" rows="6"
						id="description2" /></td>
				<td><form:errors path="description2" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE"></td>
			</tr>
			<tr>
				<td style="width: 170px">Title 3</td>
				<td style="width: 400px"><form:input path="title3"
						size="60" id="title3" /></td>
				<td><form:errors path="title3" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description 3</td>
				<td><form:textarea path="description3" cols="60" rows="6"
						id="description3" /></td>
				<td><form:errors path="description3" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE"></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->