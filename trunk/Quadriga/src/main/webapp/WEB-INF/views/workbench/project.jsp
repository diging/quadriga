<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<h2>welcome to: ${project.id}</h2>
<%--<c:forEach var = "project" items = "${project}">  --%>
<ul>
<li><c:out value="${project.description}"></c:out>
<li><c:out value="${project.owner.name}"></c:out>

</ul>

