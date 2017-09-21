<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<li ${currentPage == "home" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">Home</a></li>
<li ${currentPage == "about" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/about">About</a></li>
<li ${currentPage == "projectblog" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/projectblog">Project
		Blog</a></li>
<li ${currentPage == "networks" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/browsenetworks">Networks</a></li>
<li ${currentPage == "exploregraph" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks">Explore</a></li>
<li ${currentPage == "statistics" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/statistics">Statistics</a></li>
<li ${currentPage == "networksearch" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/search">Search</a></li>
<sec:authorize access="isAuthenticated()">
	<li class="two-row-li">
	   <div class="navbar-two-rows"><i class="fa fa-user"></i>
	       <sec:authentication property="principal.username" />
	   </div>
	   <div class="navbar-two-rows">
	   <form action="<c:url value='/logout' />" method='POST'
			class="pull-right">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div>
				<button type="submit" class="btn btn-link logout-button"><i
			class="fa fa-sign-out" aria-hidden="true"></i> Logout</button>
			</div>
		</form>
		</div>
</sec:authorize>

