
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<script type="text/javascript">
  $(function () {
   $('#workbenchmenu').jstree().on("select_node.jstree", function (e, data) {
       document.location = data.instance.get_node(data.node, true).children('a').attr('href');
   });
  });
  </script>
<h2 class="major">
			<span>Menu</span>
</h2>
<div id="workbenchmenu">
	<ul>
		<li  style="font-size:5px">Project

			<ul>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/images/new.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/addproject">New</a></li>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/images/delete.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/deleteproject">Delete</a></li>
			</ul>
		</li>
		<li>DSpace
			<ul>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/images/edit.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/keys">Manage</a></li>
			</ul>
		</li>
	</ul>
</div>