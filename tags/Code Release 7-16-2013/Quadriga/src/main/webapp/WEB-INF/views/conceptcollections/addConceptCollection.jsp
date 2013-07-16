<!--  
	Author SatyaSwaroop Boddu  
	Used to add an conceptcollection	
-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
			if (!$.trim($("#name").val())) {
					$.alert("Please enter a conceptcollection name","Oops !!!");
					$("#name").val("");		            
					event.preventDefault();
					return;
			}
			
			if (!$.trim($("#description").val())) {
				$.alert("Please enter a conceptcollection description","Oops !!!");
				$("#description").val("");
				event.preventDefault();
				return;
			}

		});
	});
</script>

<article class="is-page-content">

	<form:form method="POST" action="addCollectionsForm">
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
				<td></td>
				<td style="color: red;"><c:out value="${Error}"></c:out></td>
			</tr>
		</table>
		<input type="submit" value="Create conceptcollection">
	</form:form>

</article>

<!-- /Content -->