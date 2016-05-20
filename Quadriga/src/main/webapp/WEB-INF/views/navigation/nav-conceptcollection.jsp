
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>
<script type="text/javascript">
$(function () {
 $('#conceptcollection').jstree().on("select_node.jstree", function (e, data) {
	   var i, j = [];
	   for(i = 0, j = data.selected.length; i < j; i++) {
		   $('#conceptcollection').jstree().toggle_node(data.instance.get_node(data.selected[i]));
		}
     document.location = data.instance.get_node(data.node, true).children('a').attr('href');
 });
});
</script>

<div>
<a href="${pageContext.servletContext.contextPath}/auth/conceptcollections"><span class="fa fa-arrow-circle-left"></span> All Concept Collections</a>
</div>




    