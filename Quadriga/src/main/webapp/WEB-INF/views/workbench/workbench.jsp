<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<script type="text/javascript">
	
	$(function() {
		
		$( ".toggleBtn" ).click(function(event) {
	        var toggled =  $(this).find( "div" );
			toggled.toggle();
		});
	});
	
</script>
<style>
.tabs {
	font-size: 80%;
}
</style>
<header>
	<h2>Quadriga Workbench</h2>
	<span class="byline">Manage projects and workspaces</span>
</header>


<!-- <input type="checkbox"  id="chkboxall" checked > All  -->
<!-- <input type="checkbox" id="chkboxall" checked>
All
<div id="users">
	<input type="checkbox" id="chkboxowner" class="users" checked>
	Owner <input type="checkbox" id="chkboxcollaborator" class="users"
		checked> Collaborator <input type="checkbox"
		id="chkboxwsowner" class="users" checked> Workspace Owner <input
		type="checkbox" id="chkboxwscollaborator" class="users" checked>
	Workspace Collaborator
</div>

<div id="alljstree"></div>

<div id="asownerjstree"></div>

<div id="ascollaboratorjstree"></div>

<div id="aswsownerjstree"></div>

<div id="aswscollaboratorjstree"></div>
 -->

<div style="float:right;">
	<img style="vertical-align: middle; padding-bottom: 4px;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"> <a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject">Add Project</a>
</div>
<br/>
<c:forEach items="${projects}" var="project">
<div class="projectList">
<img style="vertical-align:middle;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/project-new.png"> 
<c:choose>
<c:when test="${accessibleProjects[project.projectId]}">
<a href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}">${project.projectName}</a> 
</c:when>
<c:otherwise>
${project.projectName} <span style="font-size: 12px"><span title="No access" class="glyphicon glyphicon-eye-close"></span></span>
</c:otherwise>
</c:choose>
	<span class="project_owner">
	Owned by: ${project.owner.name}
	</span>
	<hr style="clear: right">
	${project.description}
	
	<div class="toggleBtn">
	<img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/open.png"> <a>Show Workspaces</a>
	
	<div style="display: none" class="toggled">
	<ul class="workspaceToggleList">
	<c:forEach items="${project.projectWorkspaces}" var="ws">
		<li> <a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${ws.workspace.workspaceId}">${ws.workspace.workspaceName}</a>
			<br>${ws.workspace.description}
		</li>
	</c:forEach>
	<c:if test="${empty  project.projectWorkspaces}">
		There are workspaces.
	</c:if>
	</ul>
	</div>
	</div>
</div>
</c:forEach>

<div style="float:right;">
	<img style="vertical-align: middle; padding-bottom: 4px;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"> <a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject">Add Project</a>
</div>
<div style="clear: right;"></div>








