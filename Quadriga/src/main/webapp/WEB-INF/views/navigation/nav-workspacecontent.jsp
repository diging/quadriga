<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('#workspacemenu').jstree().on(
				"select_node.jstree",
				function(e, data) {
					var i, j = [];
					for (i = 0, j = data.selected.length; i < j; i++) {
						$('#workspacemenu').jstree().toggle_node(
								data.instance.get_node(data.selected[i]));
					}
					document.location = data.instance.get_node(data.node, true)
							.children('a').attr('href');
				});
	});
</script>
<h2 class="major">
	<span>Menu</span>
</h2>
<div id="workspacemenu">
	<ul>
		<li
			data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/down.png"}'>Workspace
			<ul>
				<li
					data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${myprojectid}/addworkspace">Add</a></li>
				<li
					data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/minus.png"}'>
					<c:if test="${isDeactivated == true}">
						<a href="#" onclick="return funConfirmDeletion();">Delete</a>
					</c:if> <c:if test="${isDeactivated == false }">
						<font color="#CCCCCC"
							title="Only deactivated workspaces can be deleted.">Delete
							Workspace</font>
					</c:if>
				</li>
				<li
					data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'>

					<c:if test="${isDeactivated == false}">
						<a href="#" onclick="return confirmWorkspaceDeactivation();">Deactivate</a>


					</c:if> <c:if test="${isDeactivated == true }">
						<font color="#CCCCCC"
							title="The workspace is already deactivated."> Deactivate
							Workspace</a>&nbsp;&nbsp;
						</font>

					</c:if>

				</li>

				<li
					data-jstree='{"icon":"${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/right.png"}'>

					<c:if test="${isDeactivated == true}">
						<a href="#" onclick="return confirmWorkspaceActivation();">Activate</a>
					</c:if> <c:if test="${isDeactivated == false }">
						<font color="#CCCCCC" title="The workspace is already activated.">
							Activate Workspace</a>&nbsp;&nbsp;
						</font>
					</c:if>

				</li>
			</ul>
		</li>


		<li
			data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/down.png"}'>Dictionary
			<ul>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/plus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/adddictionary">Add</a></li>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/list.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/dictionaries">List</a></li>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/minus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletedictionary">Delete</a></li>
			</ul>
		</li>
		<li
			data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/down.png"}'>Collections
			<ul>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/plus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addconceptcollection">Add</a></li>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/list.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/conceptcollections">List</a></li>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/minus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deleteconceptcollections">Delete</a></li>
			</ul>
		</li>
		<!-- 
		<li
			data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/down.png"}'>Collaborators
			<ul>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/plus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/addcollaborators">Add</a></li>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/minus.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/deletecollaborators">Delete</a></li>
				<li
					data-jstree='{"icon":"/quadriga/resources/txt-layout/css/images/pen.png"}'><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.workspaceId}/updatecollaborators">Update</a></li>
			</ul>
		</li>
		-->
	</ul>
</div>