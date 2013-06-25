<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>

$(function() {
	$("input[type=submit]").button().click(function(event){
		if (!$.trim($("#name").val())) {
			$.alert("Please enter a project name", "Oops !!!");
			$("#name").val("");
			event.preventDefault();
			return;
		}

		if (!$.trim($("#description").val())) {
			$.alert("Please enter a project description", "Oops !!!");
			$("#description").val("");
			event.preventDefault();
			return;
		}
	});
});

</script>


<header>
	<h2>Create new Workspace</h2>
	<span class="byline">Please fill in the following information:</span>
</header>

<article class="is-page-content">

	<form:form modelAttribute="workspace" method="POST"
		action="/auth/workbench/workspace/addworkspace">
		<table style="width: 100%">
			<c:choose>
				<c:when test="${success=='0'}">
					<span class="byline" style="color: #f00;"><c:out
							value="${errormsg}"></c:out></span>
					<br />
				</c:when>
			</c:choose>
			<tr>
				<td style="width: 170px">Name:</td>
				<td><form:input path="name" size="80" id="name" /></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
			</tr>
			<tr>
		    <td>  <input class="command" type="submit" value="Create Workspace"> </td>
		    </tr>
		</table>
	</form:form>

</article>

<!-- /Content -->