<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
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
	<form:form commandName="publicpage" method="GET"
		action="${pageContext.servletContext.contextPath}/auth/workbench/addpublicpage">
		<header>
			<h2>Editing Text Contents to be shown</h2>
			<span class="byline">Please fill in the following information:</span>
		</header>
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title" /></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference</td>
				<td style="width: 1px"><form:input path="order" size="60"
						id="order" /></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE"></td>
			</tr>
			<tr>
				<td style="width: 170px">Title</td>
				<td style="width: 1px"><form:input path="title" size="60"
						id="title" /></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference</td>
				<td style="width: 1px"><form:input path="order" size="60" id="order" /></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE"></td>
			</tr>
			<tr>
				<td style="width: 170px">Title</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title" /></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference</td>
				<td style="width: 1px"><form:input path="order" size="60" id="order" />
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE"></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->