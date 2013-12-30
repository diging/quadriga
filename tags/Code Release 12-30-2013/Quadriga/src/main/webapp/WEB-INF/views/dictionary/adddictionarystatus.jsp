<!--  
	Author Lohith Dwaraka  
	Used to list the items in a dictionary
	and search for items	
-->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>
<!-- Content -->
<ul>
	<li><input type=button
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries'"
		value='Okay'></li>
</ul>


<article class="is-page-content">

	<c:choose>
		<c:when test="${success=='1'}">
			<span class="byline">Dictionary created successfully.</span>
			<br />
		</c:when>

		<c:otherwise>
			<span class="byline"><c:out value="${errormsg}"></c:out></span>
			<br />
		</c:otherwise>
	</c:choose>

</article>

<!-- /Content -->