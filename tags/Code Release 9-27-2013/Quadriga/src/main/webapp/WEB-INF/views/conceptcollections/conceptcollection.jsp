<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize : "10"
		});
		$("ul.pagination2").quickPagination({
			pageSize : "10"
		});

	});
</script>
<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>

<header>
	<h2>Concept Collections</h2>
	<span class="byline">Manage your concept collections here.</span>
</header>
<ul>
	<li><input type=button
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/addCollectionsForm'"
		value='Add ConceptCollection'></li>
</ul>

<div
	style="float: left; width: 45%; border-radius: 5px; border: 2px solid #e3daa8; padding: 20px;">
	<h3 align="center">You own these concept collections</h3>
	<hr>
	<ul class="pagination1">
		<c:if test="${not empty conceptlist}">
			<c:forEach var="concept" items="${conceptlist}">
				<li><details>
						<summary>
							<a href="conceptcollections/${concept.id}"> <c:out
									value="${concept.name}"></c:out>
							</a>
						</summary>
						<c:out value="${concept.description}"></c:out>
					</details></li>
			</c:forEach>
		</c:if>
	</ul>

</div>

<div
	style="float: right; width: 45%; border-radius: 5px; border: 2px solid #e3daa8; padding: 20px;">
	<h3 align="center">You collaborate on these concept collections</h3>
	<hr>
	<ul class="pagination2">
		<c:if test="${not empty collaborationlist}">
			<c:forEach var="concept" items="${collaborationlist}">
				<li><details>
						<summary>
							<a href="conceptcollections/${concept.id}"> <c:out
									value="${concept.name}"></c:out>
							</a>
						</summary>
						<c:out value="${concept.description}"></c:out>
					</details></li>
			</c:forEach>
		</c:if>
	</ul>

</div>

