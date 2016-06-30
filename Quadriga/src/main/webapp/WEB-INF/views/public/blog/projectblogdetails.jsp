<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container" style="width: 100%">
	<div class="row">
		<div>
			<!-- Blog Entries Column -->
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3>${projectBlogEntry.title}</h3>
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
		</div>
	</div>
</div>