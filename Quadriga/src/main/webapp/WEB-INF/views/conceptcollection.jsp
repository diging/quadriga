<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
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


<header>
	<h2>Concept Collections</h2>
	<span class="byline">Manage your concept collections here.</span>
</header>



<div
	style="float: left; width: 45%; border-radius: 5px; border: 2px solid #e3daa8; padding: 20px;">
	<%-- <display:table class="its" name="conceptlist" keepStatus="true" requestURI="/auth/conceptcollections" uid="1" pagesize = "10">
	<display:column property="name" sortable="false" title="You own these concept collections"
	maxLength="25"   href="conceptdetails" paramId="name"/>
	</display:table> --%>
	<h3 align="center">You own these concept collections</h3>
	<hr>
	<ul class="pagination1">
		<c:if test="${not empty conceptlist}">
			<c:forEach var="concept" items="${conceptlist}">
				<li><details>
						<summary>
							<a href="conceptdetails/?name=${concept.name}"> <c:out
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
	<%-- <display:table class="its" name="collaborationlist" requestURI="/auth/conceptcollections" pagesize = "10" keepStatus="true" uid="2">
	<display:column property="name" sortable="false" title="You also participate in these concept collections"
	maxLength="25"  href="conceptdetails" paramId="name"/>
	</display:table> --%>
	<h3 align="center">You collaborate on these concept collections</h3>
	<hr>
	<ul class="pagination2">
		<c:if test="${not empty conceptlist}">
			<c:forEach var="concept" items="${collaborationlist}">
				<li><details>
						<summary>
							<a href="conceptdetails/?name=${concept.name}"> <c:out
									value="${concept.name}"></c:out>
							</a>
						</summary>
						<c:out value="${concept.description}"></c:out>
					</details></li>
			</c:forEach>
		</c:if>
	</ul>

</div>



</html>