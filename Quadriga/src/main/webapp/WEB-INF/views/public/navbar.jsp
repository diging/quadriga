<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
<c:if test="${username != null}">
	<li class="two-row-li"><div class="navbar-two-rows"><i class="fa fa-user"></i>
			${username}</div>
	    <div class="navbar-two-rows"><i class="fa fa-sign-out"></i><a href="/quadriga/logout"
		class="navbar-two-rows">
			Logout </a></div>
	</li>
</c:if>

