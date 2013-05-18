<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="currentPage" type="java.lang.String" scope="request"/>

<nav id="nav" class="skel-ui-fixed">
	<ul>
		<li ${currentPage == "home" ? "class=\"current_page_item\"" : ""}><a href="home">Home</a></li>
		<li ${currentPage == "workbench" ? "class=\"current_page_item\"" : ""}><a href="workbench">Workbench</a></li>
		<li ${currentPage == "conceptCollections" ? "class=\"current_page_item\"" : ""}><a href="">Concept Collections</a></li>
		<li ${currentPage == "dictionaries" ? "class=\"current_page_item\"" : ""}><a href="">Dictionaries</a></li>
		<li ${currentPage == "editing" ? "class=\"current_page_item\"" : ""}><a href="">Editing</a></li>
		<li ${currentPage == "networks" ? "class=\"current_page_item\"" : ""}><a href="">Networks</a></li>
		<li ${currentPage == "admin" ? "class=\"current_page_item\"" : ""}><a href="">Admin</a></li>
		<li class="page_item_logout"><a href="<c:url value="/j_spring_security_logout" />">Logout</a></li>
	</ul>
</nav>