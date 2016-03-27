<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
$(document).ready(function(){
	
		$("input[type=submit]").button().click(function(event) {
		});
		
		$("input[type=button]").button().click(function(event) {
		});
		
	});
	
	function submitClick(conceptCollectionId) {
		location.href = '${pageContext.servletContext.contextPath}/auth/conceptcollections/${collection.conceptCollectionId}';
	}
</script>
<style>
.error {
	color: #ff0000;
	font-style: italic;
}
</style>
<article class="is-page-content">

	<form:form commandName="collection" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/conceptcollections/updatecollection/${collection.conceptCollectionId}">
				<c:choose>
			<c:when test="${success == '0'}">
			<header>
	<h2>Modify Concept Collection</h2>
	<span class="byline">Please fill in the following information:</span>
</header>
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Name:</td>
				<td><form:input path="conceptCollectionName" size="60" id="conceptCollectionName" /></td>
				<td><form:errors path="conceptCollectionName" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><form:textarea path="description" cols="44" rows="6"
						id="description" /></td>
						<td><form:errors path="description" cssClass="error"></form:errors></td>
			</tr>
			<tr>
				<td><form:input path="conceptCollectionId" type="hidden" /> </td>
			</tr>
			<tr>
			<td><input class="command" type="submit" value="Update"> </td>
			</tr>
		</table>
			</c:when>
				<c:when test="${success == '1'}">
				<span class="byline">Concept collection modified successfully.</span>
				<ul>
					<li><input type="button" onClick="submitClick(this.conceptCollectionName);"
						value='Okay'></li>
				</ul>
			</c:when>
			</c:choose>

	</form:form>

</article>

<!-- /Content -->