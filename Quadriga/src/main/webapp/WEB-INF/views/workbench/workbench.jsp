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


<c:forEach items="${projects}" var="project">
<div class="projectList">
<img style="vertical-align:middle;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/project-new.png"> 
<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}">${project.projectName}</a> 
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
<!-- 
<div id="tabs" class="tabs">
	<ul>
		<li><a href="#asowner">Owner</a></li>
		<li><a href="#ascollaborator">Collaborator</a></li>
		<li><a href="#aswsowner">Workspace Owner</a></li>
		<li><a href="#aswscollaborator">Workspace Collaborator</a></li>
	</ul>

	<div id=asowner>
		<c:if test="${not empty projectlistasowner}">
	  You are the owner of the following projects:
	  <ul class="style2 pagination1">
				<c:forEach var="project" items="${projectlistasowner}">
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}"><c:out
								value="${project.projectName}"></c:out></a> <br> <c:out
							value="${project.description}"></c:out></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlistasowner}">
	      You don't own any projects.
	   </c:if>
	</div>
	<div id=ascollaborator>
		<c:if test="${not empty projectlistascollaborator}">
			<ul class="style2 pagination1">
				<c:forEach var="project" items="${projectlistascollaborator}">
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}"><c:out
								value="${project.projectName}"></c:out></a> <br>
					<c:out value="${project.description}"></c:out></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlistascollaborator}">
	   You are not collaborator to any of the projects.
	</c:if>
	</div>
	<div id=aswsowner>
		<c:if test="${not empty projectlistaswsowner}">
	  You are the workspace owner associated with the following projects:
	  <ul class="style2 pagination1">
				<c:forEach var="project" items="${projectlistaswsowner}">
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}"><c:out
								value="${project.projectName}"></c:out></a> <br>
					<c:out value="${project.description}"></c:out></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlistaswsowner}">
	    You don't own any workspace associated with projects.
	 </c:if>
	</div>

	<div id=aswscollaborator>
		<c:if test="${not empty projectlistaswscollaborator}">
	  You are the workspace collaborator associated with the following projects:
	  <ul class="style2 pagination1">
				<c:forEach var="project" items="${projectlistaswscollaborator}">
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}"><c:out
								value="${project.projectName}"></c:out></a> <br>
					<c:out value="${project.description}"></c:out></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlistaswscollaborator}">
		    You are not collaborator to any workspace associated with the projects.
		</c:if>
	</div>
</div>
 -->








