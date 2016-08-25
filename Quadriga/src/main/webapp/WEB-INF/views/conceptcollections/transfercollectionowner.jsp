<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<style>
.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<script>
	function submitClick(id) {
		location.href = '${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}';
	}

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {
		});
	});

	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
		});
	});
</script>

<h2>Transfer Ownership of Concept Collection: ${collectionname}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}"><i
			class="fa fa-arrow-circle-left"></i> Back to Concept Collection</a>
	</p>
	<hr>
</div>


<form:form commandName="user" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/conceptcollections/transferconceptcollectionowner/${collectionid}">
	<c:if test="${not empty collaboratinguser}">
		<div class="alert alert-info" role="alert">Concept Collection
			currently owned by: ${collectionowner}</div>

		<p>Select a new owner for the concept collection:</p>
		<p>
			<form:select class="form-control" path="userName">
				<form:option value="" label="--- Select ---" />
				<form:options items="${collaboratinguser}" itemValue="userName"
					itemLabel="name" />
			</form:select>
			<form:errors path="userName" cssClass="error"></form:errors>
		</p>
		<div class="alert alert-warning" role="alert">Note: The current
			owner of this concept collection will become a concept collection
			admin and will not be able to undo ownership transfer.</div>

		<input class="btn btn-primary" type="submit" value="Assign">
		<a class="btn btn-default"
			href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${collectionid}">Cancel</a>
	</c:if>
	<c:if test="${empty collaboratinguser}">
		<p>You don't have any collaborators assigned to concept
			collection.</p>
		<input class="btn btn-primary" type="button"
			onClick="submitClick(this.id);" value='Okay'>
	</c:if>
</form:form>
