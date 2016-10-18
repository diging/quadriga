<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});
		$("input[type=button]").button().click(function(event) {
		});
	});

</script>

<form:form commandName="dictionary" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/dictionaries/updatedictionary/${dictionaryid}">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<h2>Update Dictionary</h2>
	<div class="back-nav">
		<hr>
		<p>
			<a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}"><i
				class="fa fa-arrow-circle-left"></i> Back to Dictionary</a>
		</p>
		<hr>
	</div>
	<p>Please update the the following
		information:</p>
	<table style="width: 100%">
		<tr>
			<td style="width: 170px">Name:</td>
			<td><form:input class="form-control" path="dictionaryName"
					size="60" id="dictionaryName" /></td>
			<td><form:errors path="dictionaryName" cssClass="error"></form:errors></td>
		</tr>
		<tr>
			<td style="vertical-align: top">Description:</td>
			<td><form:textarea class="form-control" path="description"
					cols="44" rows="6" id="description" /></td>
			<td><form:errors path="description" cssClass="error"></form:errors></td>
		</tr>
		<tr>
			<td><input class="btn btn-primary" type="submit" value="Update">
				<a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}"
				class="btn btn-default">Cancel</a></td>
		</tr>
	</table>

</form:form>


<!-- /Content -->