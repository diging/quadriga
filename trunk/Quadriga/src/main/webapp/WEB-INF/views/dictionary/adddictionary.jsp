<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
			if (!$.trim($("#name").val())) {
					$.alert("Please enter a dictionary name","Oops !!!");
					$("#name").val("");		            
					event.preventDefault();
					return;
			}
			
			if (!$.trim($("#description").val())) {
				$.alert("Please enter a dictionary description","Oops !!!");
				$("#description").val("");
				event.preventDefault();
				return;
			}
			

			if (!$.trim($("#id").val())) {
				$.alert("Please enter a dictionary ID","Oops !!!");
				$("#id").val("");
				event.preventDefault();
				return;
			}
		});
	});
</script>

<article class="is-page-content">

	<form:form method="POST" action="/auth/dictionaries/addDictionary">
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
				<td>Dictionary Id:</td>
				<td><form:input path="id" size="30" id="id"/></td>
			</tr>
		</table>
		<input type="submit" value="Create Dictionary">
	</form:form>

</article>

<!-- /Content -->