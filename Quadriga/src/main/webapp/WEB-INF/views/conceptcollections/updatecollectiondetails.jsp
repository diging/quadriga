<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(document).ready(function() {

		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});

	});

</script>
<h2>Modify Concept Collection</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Concept Collection</a>
	</p>
	<hr>
</div>

<form:form commandName="collection" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/conceptcollections/updatecollection/${collection.conceptCollectionId}">

	<p>Modify the concept collection details and click "Update".</p>
	<table style="width: 100%">
		<tr>
			<td style="width: 170px">Name:</td>
			<td><form:input class="form-control"
					path="conceptCollectionName" size="60" id="conceptCollectionName" /></td>
			<td><form:errors path="conceptCollectionName" cssClass="error"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top">Description:</td>
			<td><form:textarea class="form-control" path="description"
					id="description" /></td>
			<td><form:errors path="description" cssClass="error"></form:errors></td>
		</tr>
		<tr>
			<td><form:input path="conceptCollectionId" type="hidden" /></td>
		</tr>
		<tr>
			<td><input class="btn btn-primary" type="submit" value="Update">
			<a class="btn btn-default"
            href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}">Cancel</a>
            </td>
		</tr>
	</table>


</form:form>


<!-- /Content -->