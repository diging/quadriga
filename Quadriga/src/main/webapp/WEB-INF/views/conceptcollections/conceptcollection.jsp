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

<div>
<c:if test="${not empty conceptlist}">
You own these concept collections:
<ul>
   <c:forEach var="concept" items="${conceptlist}">
   
   <li class="cc with-icon">
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

<div style="float:right;">
	<img style="vertical-align: middle; padding-bottom: 4px;" src="${pageContext.servletContext.contextPath}/resources/txt-layout/css/images/plus.png"> <a href="${pageContext.servletContext.contextPath}/auth/conceptcollections/addCollectionsForm">Add Concept Collection</a>
</div>
<br/>

<div>
<c:if test="${not empty collaborationlist}">
    You collaborate on these concept collections:
    <ul>
    <c:forEach var="concept" items="${collaborationlist}">
    <li class="cc with-icon">
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