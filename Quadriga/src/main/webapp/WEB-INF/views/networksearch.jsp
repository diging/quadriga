<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
		 pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<h1>Search Networks</h1>
<p>Search networks of project <em><c:out value="${project.projectName}"/></em>.</p>

<div class="container">
	<div class="row">
		<div class="col-sm-6 search-wrapper" style="position: relative">
			<form action="<c:url value="/sites/${project.projectName}/search" />">
				<div class="form-group">
					<label for="search-term">Enter the search term</label>
					<input type="text" class="form-control" id="search-term">
				</div>
			</form>
			<div class="row">
				<div class="col-sm-12 search-results">
					<div class="list-group">
						<a href="#" class="list-group-item">
							Cras justo odio
						</a>
						<a href="#" class="list-group-item">Dapibus ac facilisis in</a>
						<a href="#" class="list-group-item">Morbi leo risus</a>
						<a href="#" class="list-group-item">Porta ac consectetur ac</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	function init() {
		var $searchInput = $('#search-term');
	}
	window.onload = init;
</script>