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
	<li><a class="fa fa-user"
		style="margin: 0px 10px 0px 0px; padding: 0px 0px 0px 0px; font-size: 80%">
			${username} </a> <a href="/quadriga/logout"
		style="margin: 2px 10px; padding: 0px 0px 0px 0px; font-size: 80%">
			Logout </a></li>
</c:if>

