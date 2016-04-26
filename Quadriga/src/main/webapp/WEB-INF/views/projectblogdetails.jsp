<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container" style="width: 100%">
	<div class="row">
		<div>
			<!-- Blog Entries Column -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3>${latestProjectBlogEntry.title}</h3>
				</div>

				<div class="panel-body">
					<p>
						Posted on
						<fmt:formatDate type="both" dateStyle="long" timeStyle="long"
							value="${latestProjectBlogEntry.createdDate}" />
					</p>
					<p>Author: ${latestProjectBlogEntry.author.name}</p>
					<p>${latestProjectBlogEntry.description}</p>
				</div>

			</div>
		</div>
	</div>
</div>