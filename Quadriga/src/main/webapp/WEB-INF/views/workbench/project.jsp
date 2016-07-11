<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script>
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			return;
		});
		$("ul.pagination1").quickPagination({
			pageSize : "3"
		});
	});

	$(function() {
		$("#tabs").tabs();
	});
</script>

<style>
.tabs {
	font-size: 80%;
}
</style>

<div class="row">


	<!-- Display project details -->
	<div class="col-md-9">
		<h2>
			<i class="ion-planet"></i> Project: ${project.projectName}
		</h2>
		<div>${project.description}</div>
		<c:if test="${owner=='1' || isProjectAdmin==true}">
		<div style="text-align: right">
			<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/modifyproject/${project.projectId}">
				<i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
				Project
			</a>
		</div>
		<div style="text-align: right">
			<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/editProjectPageURL/${project.projectId}">
				<i class="fa fa-pencil-square-o" aria-hidden="true"></i> Edit
				Project URL
			</a>
		</div>
		<div class="text-right">
		  <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/resolvers/add">Add Project Handle Resolver</a>
		</div>
		</c:if>
		<hr>
		<div class="user">
			Owned by: ${project.owner.name}
			<c:if test="${owner=='1'}">(<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/transfer/">Change</a>)</c:if>
		</div>

		<c:if test="${owner=='1' and editoraccess=='0' }">
			<img
				src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses-no.png"> You are not an Editor on this Project 
                        (<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/assignownereditor/${project.projectId}">Become
				an Editor</a>)
                        
                        
                    </c:if>

		<c:if test="${owner=='1' and editoraccess=='1' }">
			<img
				src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses.png"> You are an Editor on this Project
                    (<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/deleteownereditor/${project.projectId}">Remove
				me as Editor</a>)
                    </c:if>


		<hr>
		<!--  Display associated workspace -->

		<h4>Workspaces in this project:</h4>

		<c:forEach var="workspace" items="${workspaceList}">
			<div class="panel panel-default">
				<div class="panel-body">
					<a
						href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspace.workspaceId}"><i
						class="ion-filing icons"></i> <c:out
							value="${workspace.workspaceName}"></c:out></a> (Owner) <br>

					<c:out value="${workspace.description}"></c:out>
				</div>
			</div>
		</c:forEach>

		<c:forEach var="workspace" items="${collabworkspacelist}">
			<div class="panel panel-default">
				<div class="panel-body">
					<a
						href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspace.workspaceId}"><i
						class="ion-filing icons"></i> <c:out
							value="${workspace.workspaceName}"></c:out></a> (Collaborator)<br>
					<c:out value="${workspace.description}"></c:out>
				</div>
			</div>
		</c:forEach>


		<div style="float: right;">
			<a
				href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/workspace/add"><i
				class="fa fa-plus-circle" aria-hidden="true"></i> Add Workspace</a>
		</div>

		<div style="clear: right;">
			<c:if test="${empty workspaceList and empty collabworkspacelist}">
                There are no workspaces yet. You should create one!
            </c:if>
		</div>

		<div align="left">
			<hr>
			<c:choose>
				<c:when test="${owner=='1'}">
					<a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/showinactiveworkspace">
						<i class="ion-ios-box-outline icons"></i> Show Inactive Workspaces
					</a>

				</c:when>

			</c:choose>

		</div>
		<div align="right">
			<c:if test="${owner=='1'}">



				<a href="#" onclick="return confirmProjectDeletion()"><i
					class="fa fa-trash" aria-hidden="true"></i> Delete Project</a>
			</c:if>
		</div>
		<script>
			function confirmProjectDeletion() {
				// Define the Dialog and its properties.
				$("#dialog-delete-project-confirm")
						.dialog(
								{
									resizable : false,
									modal : true,
									title : "Delete Project",
									position : 'top',
									height : 300,
									width : 500,
									buttons : {
										"Yes" : function() {
											$(this).dialog('close');
											location.href = '${pageContext.servletContext.contextPath}/auth/workbench/deleteproject/${project.projectId}';
											return false;
										},
										"No" : function() {
											$(this).dialog('close');
											return false;
										}
									}
								});
			}
		</script>
		<div id="dialog-delete-project-confirm" title="Confirm Delete?"
			style="display: none;">
			You are about to delete a project, this is not reversible.</br> Do you
			want to proceed?
		</div>
	</div>

	<!-- Display collaborators -->
	<div class="col-md-3">
		<h3 class="major">
			<span>Collaborators</span>
		</h3>
		<c:if test="${not empty project.projectCollaborators}">
			<div style="padding: 5px;">
				<c:forEach var="projectcollaborator"
					items="${project.projectCollaborators}">
					<i class="fa fa-user" aria-hidden="true"></i>
					<c:out value="${projectcollaborator.collaborator.userObj.name}"></c:out>
					<br>
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${owner=='1' || isProjectAdmin==true}">
			<div style="border-top: dashed 1px #e7eae8; padding: 5px;">
				<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addcollaborators"><i
					class="fa fa-user-plus" aria-hidden="true"></i> Add</a><br> <a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/collaborators/delete"><i
					class="fa fa-user-times" aria-hidden="true"></i> Remove</a><br> <a
					href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/updatecollaborators"><i
					class="fa fa-users" aria-hidden="true"></i> Update</a>
				</ul>
			</div>
		</c:if>
	</div>
</div>