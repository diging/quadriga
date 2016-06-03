
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<div class="panel panel-default">
	<div class="panel-heading">
		<h3 class="panel-title">Tools</h3>
	</div>
	<div class="list-group">
		<a class="list-group-item"
			href="${pageContext.servletContext.contextPath}/auth/dictionaries/delete">
			<i class="fa fa-ban"></i> Delete Dictionaries
		</a>
		<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD')">
			<a class="list-group-item"
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/add"><i
				class="fa fa-plus-circle"></i> Add Dictionary</a>
		</sec:authorize>
	</div>

</div>
