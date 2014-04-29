<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function() {
	$("ul.pagination1").quickPagination({
		pageSize : "5"
	});
});
	$(function() 
			{
				    $( "#tabs" ).tabs();
			});
</script>
<style>
   .tabs
   {
	font-size: 80%;
   }
</style>
<header>
	<h2>Concept Collections</h2>
	<span class="byline">Manage your concept collections here.</span>
</header>
	<div id = "tabs" class="tabs">
	<ul>
	  <li><a href="#asowner">Owner</a></li>
	  <li><a href="#ascollaborator">Collaborator</a></li>
	</ul>
	<div id=asowner>
			<c:if test="${not empty conceptlist}">
			You own these concept collections:
			<ul class="style2 pagination1">
			<c:forEach var="concept" items="${conceptlist}">
			
			<li>
			   <a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.conceptCollectionId}">
			          <c:out value="${concept.conceptCollectionName}"></c:out>
			   </a> <br> 
			     <c:out value="${concept.description}"></c:out>
			</li>
			</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty conceptlist}">
	      You don't own any concept collections.
	   </c:if>
	</div>
	<div id=ascollaborator>
	<c:if test="${not empty collaborationlist}">
	You collaborate on these concept collections:
	<ul class="style2 pagination1">
	<c:forEach var="concept" items="${collaborationlist}">
	<li>
			   <a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.conceptCollectionId}">
			          <c:out value="${concept.conceptCollectionName}"></c:out>
			   </a> <br> 
			     <c:out value="${concept.description}"></c:out>
			     </li>
			</c:forEach>
	</ul>
		</c:if>
			<c:if test="${empty collaborationlist}">
	      You don't collaborate on any concept collections.
	   </c:if>
	</div>
	</div>