<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<script type="text/javascript" charset="utf8">
			$(document).ready(function() {
				$("input[type=button]").button().click(function(event) {
					event.preventDefault();
				});
			});
		</script>
	

	
		
	<H3>Manage dictionary list for User : <c:out value="${userId}"></c:out></H3>
    				<hr>
    				<br>
    	
    <ul>
		<li>
			<input type=button onClick="location.href='/quadriga/auth/dictionaries/addDictionary'" value='Add Dictionary'>
		</li>
	</ul>		
<!-- 
<div class="example4">	<a href="/quadriga/auth/dictionaries/addDictionary">Add Dictionary</a></div>-->
	<c:choose>
    	<c:when test="${success=='1'}">
    		<span class="byline"><c:out value="${successMsg}"></c:out></span>
    	</c:when>
		<c:otherwise> 
			<span class="byline"><c:out value="${errormsg}"></c:out></span>
		</c:otherwise>
	</c:choose>
	
	<div class="container">
    <c:choose>
    <c:when test="${not empty dictinarylist}">
			<ul class="pagination1">
		    	<c:forEach var="dictionary" items="${dictinarylist}">
		    	
			    	
						<li>
							<a href="dictionaries/${dictionary.id}">
								<c:out value="${dictionary.name}"></c:out>
							</a>

							<br>&nbsp;<c:out value="${dictionary.description}"></c:out>   
						</li>
				</c:forEach>
				</ul>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>
</div>
