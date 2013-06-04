<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
			if (!$.trim($("#name").val())) {
					$.alert("Please enter a project name","Oops !!!");
					$("#name").val("");		            
					event.preventDefault();
					return;
			}
			
			if (!$.trim($("#description").val())) {
				$.alert("Please enter a project description","Oops !!!");
				$("#description").val("");
				event.preventDefault();
				return;
			}
			

			if (!$.trim($("#projId").val())) {
				$.alert("Please enter a project ID","Oops !!!");
				$("#projId").val("");
				event.preventDefault();
				return;
			}
		});
	});
</script>

<article class="is-page-content">

	<form:form method="POST" action="/auth/workbench/addproject">
		<table>
			<tr>
				<td>Name:</td>
				<td><form:input path="name" size="30" id="name"/></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><form:textarea path="description" cols="23" rows="4"
						id="description" /></td>
			</tr>
			<tr>
				<td>Project Access:</td>
				<td><form:select path="projectAccess">
						<form:options />
					</form:select>
			</tr>
			<tr>
				<td>Project Id:</td>
				<td><form:input path="id" size="30" id="projId"/></td>
			</tr>
		</table>
		<input type="submit" value="Create Project">
	</form:form>

</article>

<!-- /Content -->