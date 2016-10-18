<!--  
	Author SatyaSwaroop Boddu  
	Used to add an conceptcollection	
-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(
				function(event) {
					if (!$.trim($("#conceptCollectionName").val())) {
						$.alert("Please enter a conceptcollection name",
								"Oops!");
						$("#conceptCollectionName").val("");
						event.preventDefault();
						return;
					}

					if (!$.trim($("#description").val())) {
						$.alert("Please enter a conceptcollection description",
								"Oops!");
						$("#description").val("");
						event.preventDefault();
						return;
					}

				});
	});
</script>


<form:form method="POST" action="addCollectionsForm">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<h2>Add concept collection</h2>

	<div class="back-nav">
		<hr>
		<p>
			<a
				href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}"><i
				class="fa fa-arrow-circle-left"></i> Back to Concept Collection</a>
		</p>
		<hr>
	</div>

	<table>
		<tr>
			<td>Name:</td>
			<td><form:input path="conceptCollectionName" size="30"
					id="conceptCollectionName" class="form-control" /></td>
		</tr>
		<tr>
			<td>Description:</td>
			<td><form:textarea path="description" cols="23" rows="4"
					id="description" class="form-control" /></td>
		</tr>

		<tr>
			<td></td>
			<td style="color: red;"><c:out value="${Error}"></c:out></td>
		</tr>
	</table>
	<button class="btn btn-primary" type="submit">Create Concept
		Collection</button>
</form:form>


<!-- /Content -->