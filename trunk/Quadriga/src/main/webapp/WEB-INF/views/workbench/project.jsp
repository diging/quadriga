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
		<td style="width: 90%">
			<h2>Project: ${project.projectName}</h2>
			<div>${project.description}</div>
			<div style="text-align:right">
			<a href="modifyproject/${project.projectId}"> <img style="vertical-align:text-top;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/edit.png"> Edit Project
			</a>
			</div>
			<hr>
			<div class="user">Owned by: ${project.owner.name} (<a href="${pageContext.servletContext.contextPath}/auth/workbench/transferprojectowner/${project.projectId}">Change</a>)</div>
					
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
				<li class="ws">
					<a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.workspaceId}"><c:out
							value="${workspace.workspaceName}"></c:out></a> (Owner) <br> 
						
					<c:out
					value="${workspace.description}"></c:out>
				</li>
			</c:forEach>
			
			<c:forEach var="workspace" items="${collabworkspacelist}">
			<li  class="ws"><a
					href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.id}"><c:out
					value="${workspace.name}"></c:out></a> (Collaborator)<br> <c:out
					value="${workspace.description}"></c:out></li>
			</c:forEach>
			</ul>
			<c:if test="${empty workspaceList}">
			There are no workspaces yet. You should create one!
			</c:if>
			<c:if test="${empty collabworkspacelist}">
				You are not collaborating on any workspace.
			</c:if>
			
			<!-- <div id = "tabs" class="tabs">
			<ul>
			  	  <li><a href="#asowner">Owner</a></li>
	              <li><a href="#ascollaborator">Collaborator</a></li>
			</ul>
			<div id="asowner">
						<c:if test="${not empty workspaceList}">
    	              Project associates these workspaces:
    		<ul class="style2 pagination1">
								<c:forEach var="workspace" items="${workspaceList}">
									<li><a
										href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.workspaceId}"><c:out
												value="${workspace.workspaceName}"></c:out></a> <br> <c:out
											value="${workspace.description}"></c:out></li>
								</c:forEach>
							</ul>
						</c:if>
						<c:if test="${empty workspaceList}">
			No workspaces are associated yet. You should create one!
		</c:if>
					</div>
				<div id="ascollaborator">
						<c:if test="${not empty collabworkspacelist}">
    	              Project associates these workspaces:
    		<ul class="style2 pagination1">
								<c:forEach var="workspace" items="${collabworkspacelist}">
									<li><a
										href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspace.id}"><c:out
												value="${workspace.name}"></c:out></a> <br> <c:out
											value="${workspace.description}"></c:out></li>
								</c:forEach>
							</ul>
						</c:if>
						<c:if test="${empty collabworkspacelist}">
			You are not collaborator to any workspace.
		</c:if>
					</div> -->
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
						<img style="vertical-align:middle;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/archive.png"> Show Inactive Workspace
						</a> 
													
					</c:when>
				
				</c:choose>
			</div>
		</td>
		<!-- Display collaborators -->
		<td style="width: 10%">
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
			</section>
		</td>
	</tr>
	<tr>
	<td></td>
	</tr>
</table>
