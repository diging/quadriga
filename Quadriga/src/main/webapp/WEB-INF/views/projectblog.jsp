<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!-- Page Heading -->
<h1 class="page-header">Project Blog Entries</h1>

<c:if test="${isProjectAdmin || isProjectOWner}">
	<!-- Link to create blog entry -->
	<div align="right">
		<a
			href="${pageContext.servletContext.contextPath}/sites/${project.unixName}/addprojectblogentry?projectId=${project.projectId}"><i
			class="fa fa-plus-circle"></i> Add a new entry</a>
	</div>
	<br>
</c:if>

<!-- Page content when project blog entries are found -->
<c:if test="${not empty projectBlogEntryList}">
	<div class="container" style="width:100%">
		<div class="row">

			<div>
				<c:forEach var="projectBlogEntry" items="${projectBlogEntryList}">

					<!-- Blog Entries Column -->
					<div class="panel panel-default">

						<div class="panel-heading">
							<h3 style="margin-top: 10px">${projectBlogEntry.title}</h3>
						</div>

						<div class="panel-body">
							<p>
								Posted on
								<fmt:formatDate type="both" dateStyle="long" timeStyle="long"
									value="${projectBlogEntry.createdDate}" />
							</p>
							<p>Author: ${projectBlogEntry.author.name}</p>
							<p>${projectBlogEntry.description}</p>
						</div>

					</div>

				</c:forEach>
			</div>
		</div>
	</div>
</c:if>

<!-- Page content when no project blog entries are found -->
<c:if test="${empty projectBlogEntryList}">
No project blog entry found for ${project.unixName}.
</c:if>