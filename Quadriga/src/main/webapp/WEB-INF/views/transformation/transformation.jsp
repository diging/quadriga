<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>


<!--  
	Author Jaydatta Nagarkar, Jaya-Venkata Vutukuri  
	Used to list the networks
-->

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
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
	});
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>
<script type="text/javascript">
	
	$(function() {
		
		$( ".toggleBtn" ).click(function(event) {
	        var toggled =  $(this).find( "div" );
			toggled.toggle();
		});
	});
	
</script>


<header>
	<h2>Transformations</h2>

</header>
 
   <c:choose>
	<c:when test="${not empty projects}">
		<ul class="pagination1">
	<c:forEach var="project" items="${projects}" >
			
	<div class="checkbox">
    <label>
      <input type="checkbox">
    </label>
	
	<div class="toggleBtn">
		
		<img src="/quadriga/resources/txt-layout/css/images/open.png"></img><a> ${project}</a>
	
	
	<div style="display: none" class="toggled">
	<ul class="workspaceToggleList">
	
				<li>
					<details>
						<ul>
<%-- 			 				<li><span class="byline">${project}</span></li> --%>

							<c:if test="${not empty networkMap[project]}">
								<c:forEach var="network" items="${networkMap[project]}">
									<summary>
										<a href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}">
										<c:out value="${network.networkName}"></c:out>
										</a>
									</summary>
									<li>Workspace : <c:out	value="${network.networkWorkspace.workspace.workspaceName}"></c:out></li>
									<li>Submitted by : <c:out value="${network.creator.userName}"></c:out>
									</li>
									<br>
								</c:forEach>
							</c:if>
						</ul>
					</details>
				</li>
	</ul>
	
	</div></div>
	
	</div>
	</c:forEach> 


	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>

<span class="byline">List of available Transformations.</span>
<c:choose>
	<c:when test="${not empty dummyTransformations}">
		<ul class="pagination1">
			<c:forEach var="transformations" items="${dummyTransformations}">
				<li>
					<c:out value="${transformations}"></c:out>
				</li>
			</c:forEach>
		</ul>
	</c:when>	
</c:choose>
<br />
<br />
