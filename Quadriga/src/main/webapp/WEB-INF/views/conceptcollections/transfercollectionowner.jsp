<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

<form:form commandName="user" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/conceptcollections/transferconceptcollectionowner/${collectionid}">
	<c:choose>
		<c:when test="${success=='0'}">
			<c:if test="${not empty collaboratinguser}">
			   <h2>ConceptCollection: ${collectionname}</h2>
			   <hr>
			   <div class="user">Owned by: ${collectionowner}</div>
			   <hr>
			   <div>Assign new owner to the concept collection</div>
				<form:select path="userName">
					<form:option value="" label="--- Select ---" />
					<form:options items="${collaboratinguser}"
						itemValue="userName" itemLabel="userName" />
				</form:select>
				<form:errors path="userName" cssClass="error"></form:errors>
				<div>Note:Current owner will become concept collection admin</div>
				<td><input type="submit" value="Assign"></td>
			</c:if>
			<c:if test="${empty collaboratinguser}">
          You don't have any collaborators assigned to concept collection.
          <ul>
				<li><input type="button" onClick="submitClick(this.id);"
					value='Okay'></li>
			</ul>	   
		</c:if>
		</c:when>
		<c:otherwise>
			<span class="byline">Concept collection Ownership transferred successfully.</span>
			<br />
			<ul>
				<li><input type="button" onClick="submitClick(this.id);"
					value='Okay'></li>
			</ul>
		</c:otherwise>
	</c:choose>
</form:form>
