<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
	
	$(function() 
			{
				    $( "#tabs" ).tabs();
			});
	
	/* inactiveWS()
	{
		location.href='${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/showInactiveWorkspace';
	} */
	
</script>

<style>
   .tabs
   {
	font-size: 80%;
   }
</style>

<table style="width: 100%">
	<tr>
		<!-- Display project details -->
		<td>
			<h2>Project: ${project.projectName}</h2>
			<div>${project.description}</div>
			<div style="text-align:right">
			<a href="${pageContext.servletContext.contextPath}/auth/workbench/modifyproject/${project.projectId}"> <img style="vertical-align:text-top;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/edit.png"> Edit Project
			</a>
			</div>
			
			<div style="text-align:right">
			<a href="${pageContext.servletContext.contextPath}/auth/workbench/editProjectPageURL/${project.projectId}"> <img style="vertical-align:text-top;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/edit.png"> Edit Project URL
			</a>
			</div>
			
			<hr>
			<div class="user">Owned by: ${project.owner.name} <c:if test="${owner=='1'}">(<a href="${pageContext.servletContext.contextPath}/auth/workbench/transferprojectowner/${project.projectId}">Change</a>)</c:if></div>
					
					<c:if test="${owner=='1' and editoraccess=='0' }">
					<img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses-no.png"> You are not an Editor on this Project 
						(<a href="${pageContext.servletContext.contextPath}/auth/workbench/assignownereditor/${project.projectId}">Become an Editor</a>)
						
						
					</c:if>
					
					<c:if test="${owner=='1' and editoraccess=='1' }">
					<img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/glasses.png"> You are an Editor on this Project
					(<a href="${pageContext.servletContext.contextPath}/auth/workbench/deleteownereditor/${project.projectId}">Remove me as Editor</a>)
					</c:if>
			
			<hr> <!--  Display associated workspace -->
			
			<strong>Workspaces in this project:</strong>
			<ul>
			<c:forEach var="workspace" items="${workspaceList}">
				<li class="ws with-icon">
					<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.workspaceId}"><c:out
							value="${workspace.workspaceName}"></c:out></a> (Owner) <br> 
						
					<c:out
					value="${workspace.description}"></c:out>
				</li>
			</c:forEach>
			
			<c:forEach var="workspace" items="${collabworkspacelist}">
			<li  class="ws with-icon"><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.workspaceId}"><c:out
					value="${workspace.workspaceName}"></c:out></a> (Collaborator)<br> <c:out
					value="${workspace.description}"></c:out></li>
			</c:forEach>
			</ul>
			
			<div style="float:right;">
				<img style="vertical-align: middle; padding-bottom: 4px;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"> <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addworkspace">Add Workspace</a>
			</div>
			
			<div style="clear:right;">
			<c:if test="${empty workspaceList}">
			There are no workspaces yet. You should create one!
			</c:if>
			<c:if test="${empty collabworkspacelist}">
				You are not collaborating on any workspace.
			</c:if>
			</div>
			</div>
			<c:choose>
				<c:when test="${AssignEditorSuccess=='1'}">
					<font color="blue"> <spring:message
							code="project.assign.owner.editor.success" /></font>

				</c:when>
				<c:when test="${AssignEditorSuccess=='0'}">
					<font color="red"> <spring:message
							code="project.assign.owner.editor.failure" /></font>
				</c:when>
				<c:when test="${AssignEditorSuccess=='2'}">
					<font color="red"> <spring:message
							code="project.assign.owner.editor.assigned" /></font>
				</c:when>
			</c:choose>
			
			<c:choose>
				<c:when test="${DeleteEditorSuccess=='1'}">
					<font color="blue"> <spring:message
							code="project.delete.owner.editor.success" /></font>

				</c:when>
				<c:when test="${DeleteEditorSuccess=='0'}">
					<font color="red"> <spring:message
							code="project.delete.owner.editor.failure" /></font>
				</c:when>
				<c:when test="${DeleteEditorSuccess=='2'}">
					<font color="red"> <spring:message
							code="project.delete.owner.editor.assigned" /></font>
				</c:when>
			</c:choose>
			<div align="left">
				<hr>
				To go to the public site, click this link <a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">http://quadriga.asu.edu/sites/${project.unixName}</a>
				<hr>
				<c:choose>
					<c:when test="${owner=='1'}">
						<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/showinactiveworkspace">
						<img style="vertical-align:middle;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/archive.png">
                            Show Inactive Workspace <span><c:out value="(${deactivatedWSSize})" /></span>
						</a> 
													
					</c:when>
				
				</c:choose>
				
			</div>
			<div align="right">
				<c:if test="${owner=='1'}">
					

					<span class="glyphicon glyphicon-ban-circle"></span>
					<a href="#" onclick="return confirmProjectDeletion()">Delete
						Project</a>
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
										position: 'top',
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
			<div id="dialog-delete-project-confirm" title="Confirm Delete?">
				You are about to delete a project, this is not reversible.</br> Do you want to proceed?</div>
		</td>
		
		<!-- Display collaborators -->
		<td style="width: 200px">
			<section>
				<h3 class="major">
					<span>Collaborators</span>
				</h3>
				<c:if test="${not empty project.projectCollaborators}">
					<ul class="collaborators">
						<c:forEach var="projectcollaborator"
							items="${project.projectCollaborators}">
							<li><c:out value="${projectcollaborator.collaborator.userObj.name}"></c:out>
							</li>
						</c:forEach>
					</ul>
				</c:if>
				<div style="border-top: dashed 1px #e7eae8; padding: 5px;"> 
				<ul class="colltools">
					<li><img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png" style="vertical-align: middle; padding-bottom: 2px;"> <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addcollaborators">Add</a></li>
					<li><img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/minus.png" style="vertical-align: middle; padding-bottom: 2px;"> <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletecollaborators">Delete</a></li>
					<li><img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/pen.png" style="vertical-align: middle; padding-bottom: 2px;"> <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/updatecollaborators">Update</a></li>
				</ul>
				</div>
			</section>
		</td>
	</tr>
	<tr>
	<td></td>
	</tr>
</table>
