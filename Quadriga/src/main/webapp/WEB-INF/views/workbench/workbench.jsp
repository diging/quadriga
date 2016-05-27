<%@ page language="java" contentType="text/html;"%>
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


<!--  if there are projects -->
<c:if test="${not empty projects}">
<div style="float:right; margin-bottom: 10px;">
    <a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject"><i class="fa fa-plus-circle"></i> Add Project</a>
</div>
<div style="clear: right;"></div>

<c:forEach items="${projects}" var="project">
<div class="panel panel-default">
<div class="panel-body">


<c:choose>
<c:when test="${accessibleProjects[project.projectId]}">
<a href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}"><i class="ion-planet" ></i> ${project.projectName}</a> 
</c:when>
<c:otherwise>
<i class="ion-planet" ></i> ${project.projectName} <span style="font-size: 12px"><span title="No access" class="glyphicon glyphicon-eye-close"></span></span>
</c:otherwise>
</c:choose>

	<div class="project_owner pull-right" style="font-size: 14px;">
	Owned by: ${project.owner.name}
	</div>
	<hr />
	${project.description}
	
	<div class="toggleBtn" style=" margin-top: 15px;">
	<img src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/open.png"> <a>Show Workspaces</a>
	
	<div style="display: none" class="toggled">
	<ul class="workspaceToggleList">
	<c:forEach items="${project.projectWorkspaces}" var="ws">
		<li> <a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${ws.workspace.workspaceId}"><i class="ion-filing icons"></i> ${ws.workspace.workspaceName}</a>
			<br>${ws.workspace.description}
		</li>
	</c:forEach>
	<c:if test="${empty  project.projectWorkspaces}">
		There are no workspaces.
	</c:if>
	</ul>
	</div>
	</div>

	</div>
</div>
</c:forEach>

<div style="float:right;">
	<a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject"><i class="fa fa-plus-circle"></i> Add Project</a>
</div>
<div style="clear: right;"></div>
</c:if>
<!--  end if there are projects -->

<!--  if there are no projects -->
<c:if test="${empty projects}">

    <p>
    You don't have any projects yet. You should <a href="${pageContext.servletContext.contextPath}/auth/workbench/addproject"><i class="fa fa-plus-circle"></i> create one</a>!
    </p>
</c:if>





