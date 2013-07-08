<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
function submitClick(id){
	location.href = '${pageContext.servletContext.contextPath}/auth/workbench/${wsprojectid}';
}

$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {
		event.preventDefault();
	});
});

</script>
<article class="is-page-content">
	<c:choose>
		<c:when test="${success=='1'}">
			<span class="byline">Workspace deleted successfully.</span>
			<br />
		</c:when>

		<c:otherwise>
			<span class="byline"><c:out value="${errormsg}"></c:out></span>
			<br />
		</c:otherwise>
	</c:choose>
	<ul>
		<li><input type="submit"
			onClick="submitClick(this.id);"
			value='Back'></li>
	</ul>
</article>

<!-- /Content -->