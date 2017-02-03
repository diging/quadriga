<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
$(function() {
	$("input[type=submit]").button().click(function(event){
	});
	$("input[type=button]").button().click(function(event){
	});
});

function submitClick(id){
	location.href = "${pageContext.servletContext.contextPath}/auth/workbench/projects/${wsprojectid}";
}
</script>
<article class="is-page-content">
	<form:form commandName="textfile" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${wsprojectid}/addtextfile">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<c:choose>
		  <c:when test="${success == '0'}">
				<header>
					<h2>Add a new Textfile</h2>
					<span class="byline">Please fill in the following
						information:</span>
				</header>
				<table style="width: 100%">
					<tr>
						<td style="width: 170px">Filename:</td>
						<td><form:input path="FileName" size="60" id="fileName" /></td>
						<td><form:errors path="fileName" class="ui-state-error-text"></form:errors></td>
					</tr>
					<tr>
						<td style="vertical-align: top">Content:</td>
						<td><form:textarea path="description" cols="44" rows="6"
								id="description" /></td>
						<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
					</tr>
					<tr>
						<td><input type="submit" value="Save Textfile"></td>
					</tr>
					<tr>
						<td><input type="hidden" id="textrefid"
							value=<c:out value="${wsprojectid}"></c:out> /></td>
					</tr>
				</table>
			</c:when>
		  <c:when test="${success == '1'}">
		  <span class="byline">Textfile saved successfully.</span>
		<ul>
					<li><input type="button" onClick="submitClick(this.id);"
						value='Okay'></li>
				</ul>
		  </c:when>
		</c:choose>
	</form:form>
</article>
