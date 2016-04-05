<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- Bootstrap Core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- Page Heading -->
<h1 class="page-header">Project Entries</h1>

<!-- Page content when project blog entries are found -->
<c:if test="${not empty projectBlogList}">
	<div class="container">
		<div class="row">

			<div>
				<c:forEach var="projectBlog" items="${projectBlogList}">

					<!-- Blog Entries Column -->
					<div class="panel panel-default">

						<div class="panel-heading">
							<h3>${projectBlog.title}</h3>
						</div>

						<div class="panel-body">
							<p>
								Posted on
								<fmt:formatDate type="both" dateStyle="long" timeStyle="long"
									value="${projectBlog.createdDate}" />
							</p>
							<p>Author: ${projectBlog.author}</p>
							<p>${projectBlog.description}</p>
						</div>
						
					</div>
					
					<!-- Link to create blog entry -->
					<div align="right">
						<a
							href="${pageContext.servletContext.contextPath}/sites/${project.projectId}/addprojectblog"><i
							class="fa fa-plus-circle"></i> Add a new entry</a>
					</div>
					
					<br>
				</c:forEach>
			</div>
		</div>
	</div>
</c:if>

<!-- Page content when no project blog entries are found -->
<c:if test="${empty projectBlogList}">
No project blog entry found for ${project.unixName}.
<div align="right">
		<a
			href="${pageContext.servletContext.contextPath}/sites/${project.projectId}/addprojectblog"><i
			class="fa fa-plus-circle"></i> Add a new entry</a>
	</div>
</c:if>