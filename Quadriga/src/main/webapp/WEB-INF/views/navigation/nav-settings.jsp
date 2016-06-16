<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

<div>
	<a
		href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}"><span
		class="glyphicon glyphicon-circle-arrow-left"></span> Back to Project</a>
</div>

<br/>

<div class="panel panel-default">

	<div class="panel-heading">
		<h3 class="panel-title">Settings</h3>
	</div>

	<div class="list-group">
		<a id="mainPageTab"
			class='list-group-item ${currentTab != "mainPageSettings" ? "disabled" : ""}'
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings"><i
			class="fa fa-cog"></i> Main Page Settings</a> <a id="aboutTab"
			class='list-group-item ${currentTab != "aboutPage" ? "disabled" : ""}'
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings/editabout"><i
			class="fa fa-info-circle"></i> About Page</a>
	</div>

</div>
<%-- <a
    href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/statistics"><i
    class="fa fa-bar-chart"></i> Statistics Page</a>
--%>