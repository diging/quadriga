<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div>
	<c:if test="${not empty blockentrylist}">
		<c:forEach var="blockentry" items="${blockentrylist}">
			<div>
				<h3>
					<b>${blockentry.title}</b>
				</h3>
				<h4>
					<i> Posted by ${blockentry.author} on ${blockentry.date} </i>
				</h4>
				<p align="justify">
					<c:out value="${blockentry.text}" escapeXml="false"></c:out>
				</p>
			</div>
		</c:forEach>
	</c:if>

	<c:if test="${empty blockentrylist}">
You don't own any public blogs.
</c:if>

	<div style="float: right;">
		<a href="${project.projectId}/addprojectblog"><i
			class="fa fa-plus-circle"></i> Add a new entry</a>
	</div>