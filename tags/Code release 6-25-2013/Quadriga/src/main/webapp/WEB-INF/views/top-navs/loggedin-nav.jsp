<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="currentPage" type="java.lang.String" scope="request"/>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<nav id="nav" class="skel-ui-fixed">
	<ul>
		<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_ADMIN', 'ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_RESTRICTED', 'ROLE_QUADRIGA_USER_COLLABORATOR')"><li ${currentPage == "home" ? "class=\"current_page_item\"" : ""}><a href="${pageContext.servletContext.contextPath}/auth/home">Home</a></li></sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')"><li ${currentPage == "workbench" ? "class=\"current_page_item\"" : ""}><a href="${pageContext.servletContext.contextPath}/auth/workbench">Workbench</a></li></sec:authorize>

		<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')">
		<li ${currentPage == "conceptCollections" ? "class=\"current_page_item\"" : ""}>
		<a href="${pageContext.servletContext.contextPath}/auth/conceptcollections">Concept Collections</a>
		<ul>
				<li><a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/addCollectionsForm">Add Collection</a></li>
		       
		</ul>
		</li>
		</sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')"><li ${currentPage == "dictionaries" ? "class=\"current_page_item\"" : ""}><a href="${pageContext.servletContext.contextPath}/auth/dictionaries">Dictionaries</a>
		<ul>
				<li><a href="/quadriga/auth/dictionaries/addDictionary">Add Dictionary</a></li>
		</ul>
		</li></sec:authorize>

		<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR')"><li ${currentPage == "editing" ? "class=\"current_page_item\"" : ""}><a href="">Editing</a></li></sec:authorize>
		<sec:authorize access="hasAnyRole('ROLE_QUADRIGA_USER_STANDARD', 'ROLE_QUADRIGA_USER_COLLABORATOR', 'ROLE_QUADRIGA_USER_RESTRICTED')"><li ${currentPage == "networks" ? "class=\"current_page_item\"" : ""}><a href="">Networks</a></li></sec:authorize>
		<sec:authorize access="hasRole('ROLE_QUADRIGA_USER_ADMIN')"><li ${currentPage == "admin" ? "class=\"current_page_item\"" : ""}><a href="${pageContext.servletContext.contextPath}/auth/users/manage">User Management</a></li></sec:authorize>
		<!-- <li class="page_item_logout"><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li> -->
	</ul>
</nav>