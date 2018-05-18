<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="currentPage" type="java.lang.String" scope="request" />
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize
	access="hasAnyRole('ROLE_QUADRIGA_USER_ADMIN', 'ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_RESTRICTED', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
	<li ${currentPage == "home" ? "class=\"active\"" : ""}><a
		href="${pageContext.servletContext.contextPath}/auth/home">Home</a></li>
</sec:authorize>
<sec:authorize
	access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
	<li ${currentPage == "workbench" ? "class=\"active\"" : ""}><a
		href="${pageContext.servletContext.contextPath}/auth/workbench">Workbench</a></li>
</sec:authorize>

<sec:authorize
	access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
	<li class="${currentPage == "conceptCollections" ? "active" : ""}">
		<a class="dropdown-main"
		href="${pageContext.servletContext.contextPath}/auth/conceptcollections">Concept
			Collections</a>
	</li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD')">
	<li class="${currentPage == "conceptCollections" ? "active" : ""}">
		<a class="dropdown-caret dropdown-toggle" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <span class="caret"></span>
			<span class="sr-only">Toggle Dropdown</span>
	</a>
		<ul class="dropdown-menu">
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/conceptcollections/addCollectionsForm">Add
					Collection</a></li>

		</ul>
	</li>
</sec:authorize>

<sec:authorize
	access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
	<li ${currentPage == "dictionaries" ? "class=\"active\"" : ""}><a
		class="dropdown-main"
		href="${pageContext.servletContext.contextPath}/auth/dictionaries">Dictionaries</a>
	</li>
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD')">
	<li ${currentPage == "dictionaries" ? "class=\"active\"" : ""}><a
		class="dropdown-caret dropdown-toggle" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <span class="caret"></span>
			<span class="sr-only">Toggle Dropdown</span>
	</a>
		<ul class="dropdown-menu">
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/add">Add
					Dictionary</a></li>
		</ul></li>
</sec:authorize>

<sec:authorize
	access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
	<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a
		class="dropdown-main"
		href="${pageContext.servletContext.contextPath}/auth/networks">Networks</a>
	</li>
	<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a
		class="dropdown-caret dropdown-toggle" data-toggle="dropdown"
		aria-haspopup="true" aria-expanded="false"> <span class="caret"></span>
			<span class="sr-only">Toggle Dropdown</span>
	</a>
		<ul class="dropdown-menu">
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/editing">Editing Workbench</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/transformation">Transformations</a></li>
			<li><a
				href="${pageContext.servletContext.contextPath}/auth/transformation/selectTransformationFiles">Upload Transformation</a></li>
		</ul></li>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_QUADRIGA_USER_STANDARD')">
    <li ${currentPage == "resolvers" ? "class=\"active\"" : ""}><a
        href="${pageContext.servletContext.contextPath}/auth/resolvers">Resolvers</a></li>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_QUADRIGA_USER_ADMIN')">
	<li ${currentPage == "admin" ? "class=\"active\"" : ""}><a
		href="${pageContext.servletContext.contextPath}/auth/users/manage">User
			Management</a></li>
    <li ${currentPage == "manageprojects" ? "class=\"active\"" : ""}><a
		href="${pageContext.servletContext.contextPath}/auth/admin/projects">Project
			Management</a></li>
</sec:authorize>


