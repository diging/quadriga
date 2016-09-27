<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<li ${currentPage == "sites" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/">Home</a></li>
<li ${currentPage == "network" ? "class=\"active\"" : ""}><a
	href="${pageContext.servletContext.contextPath}/sites/network">Public Networks</a></li>

