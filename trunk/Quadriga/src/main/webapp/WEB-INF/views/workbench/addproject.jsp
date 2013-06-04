<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
$(function() {
	$("input[type=submit]").button().click(function(event) {
	});
});
</script>

<article class="is-page-content">

	<form:form method="POST" action="/auth/workbench/addproject">
		<table>
			<tr>
				<td>Name:</td>
				<td><form:input path="name" size="30" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><form:textarea path="description" cols="23" rows="4" /></td>
			</tr>
			<tr>
				<td>Project Access:</td>
				<td><form:select path="projectAccess">
						<form:options />
					</form:select>
			</tr>
		</table>
		<input type="submit" value="Create Project">
	</form:form>

</article>

<!-- /Content -->