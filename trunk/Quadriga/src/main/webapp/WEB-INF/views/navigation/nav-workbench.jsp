
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<script type="text/javascript">
  $(function () {
    $('#workbenchmenu').jstree();
  });
  </script>

<h2 class="major">
			<span>Menu</span>
</h2>
<div id="workbenchmenu">
<ul>
    <li> Project
    <ul>
    <li data-jstree='{"icon":"http://jstree.com/tree.png"}'><a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject">New</a></li>
    <li><a href="${pageContext.servletContext.contextPath}/auth/workbench/deleteproject">Delete</a></li>
    </ul>
    </li>
    <li>DSpace
    <ul>
    <li><a href="${pageContext.servletContext.contextPath}/auth/workbench/keys">Manage</a></li>
    </ul>
    </li>
</ul>
</div>