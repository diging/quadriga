<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>


<div>
    <ul>
        <li><a
            href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}"><span
                class="glyphicon glyphicon-circle-arrow-left"></span> Back to Project
        </a></li>
    </ul>
</div>

<h2 class="major" href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/settings">
	<span>Settings</span>
</h2>
<a
    href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings"><i class="fa fa-cog"></i> Main Page Settings</a>
   
<br>
<a
	href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings/editabout"><i
	class="fa fa-info-circle"></i> About Page</a>
<br>
<a
    href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/statistics"><i
    class="fa fa-bar-chart"></i> Statistics Page</a>
