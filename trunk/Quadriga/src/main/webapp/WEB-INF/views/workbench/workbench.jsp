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
	
	$('input[type="checkbox"]').bind('click', function(){
		alert("OK");
		})
	
	$('#chkboxall').click(function() {
		alert("here");
		var opt = $(this).parent().find('input[type=checkbox]');
        opt.prop('checked', $(this).is(':checked') ? true : false);
	  
	});
	function chkAll(id){
		//alert("here");
		var opt = $("#"+id).parent().find('input[type=checkbox]');
        opt.prop('checked', $(this).is(':checked') ? true : false);
	}
	
</script>
<style>
   .tabs
   {
	font-size: 80%;
   }
</style>
	<header>
		<h2>Quadriga Workbench</h2>
		<span class="byline">Manage projects and workspaces</span>
	</header>
	<div id="users">
	<input type="checkbox"  id="chkboxall" checked > All 
	<!-- <input type="checkbox"  id="chkboxall" onclick="javascript:chkAll(this.id);" checked > All -->
	<input type="checkbox"  id="chkboxowner" checked> Owner 
	<input type="checkbox"  id="chkboxcollaborator" checked> Collaborator
	<input type="checkbox"  id="chkboxawowner" checked> Workspace Owner 
	<input type="checkbox" id="chkboxwcollaborator" checked> Workspace Collaborator 
	</div>
	<div id = "tabs" class="tabs">
	<ul>
	  <li><a href="#asowner">Owner</a></li>
	  <li><a href="#ascollaborator">Collaborator</a></li>
	  <li><a href="#aswsowner">Workspace Owner</a></li>
	  <li><a href="#aswscollaborator">Workspace Collaborator</a></li>
	</ul>
	
	<div id=asowner>
		<c:if test="${not empty projectlistasowner}">
	  You are the owner of the following projects:
	  <ul class="style2 pagination1">
				<c:forEach var="project" items="${projectlistasowner}">
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out
								value="${project.name}"></c:out></a> <br> <c:out
							value="${project.description}"></c:out></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlistasowner}">
	      You don't own any projects.
	   </c:if>
	</div>
	<div id=ascollaborator>
	  <c:if test="${not empty projectlistascollaborator}">
	  <ul class="style2 pagination1">
   	 			<c:forEach var="project" items="${projectlistascollaborator}">
					<li>
						<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out value="${project.name}"></c:out></a>
						<br><c:out value="${project.description}"></c:out>
					</li>
				</c:forEach>
			</ul>
			</c:if>
	<c:if test="${empty projectlistascollaborator}">
	   You are not collaborator to any of the projects.
	</c:if>
	</div>
	<div id=aswsowner>
	  <c:if test="${not empty projectlistaswsowner}">
	  You are the workspace owner associated with the following projects:
	  <ul class="style2 pagination1">
   	 			<c:forEach var="project" items="${projectlistaswsowner}">
					<li>
						<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out value="${project.name}"></c:out></a>
						<br><c:out value="${project.description}"></c:out>
					</li>
				</c:forEach>
			</ul>
			</c:if>
	 <c:if test="${empty projectlistaswsowner}">
	    You don't own any workspace associated with projects.
	 </c:if>
	</div>
		
	<div id=aswscollaborator>
	<c:if test="${not empty projectlistaswscollaborator}">
	  You are the workspace collaborator associated with the following projects:
	  <ul class="style2 pagination1">
   	 			<c:forEach var="project" items="${projectlistaswscollaborator}">
					<li>
						<a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out value="${project.name}"></c:out></a>
						<br><c:out value="${project.description}"></c:out>
					</li>
				</c:forEach>
			</ul>
			</c:if>
		<c:if test="${empty projectlistaswscollaborator}">
		    You are not collaborator to any workspace associated with the projects.
		</c:if>
	</div>
	</div>
	
	
	
	
	
	
	
	
	
	