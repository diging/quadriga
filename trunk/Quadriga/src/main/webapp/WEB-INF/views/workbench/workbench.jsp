<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize : "5"
		});
	});
	$(function() 
	{
		  $( "#tabs" ).tabs();
		  
		  allprojects();
		  ownerprojects();
		  collaboratorprojects();
		  wsownerprojects();
		  wscollaboratorprojects();
		  
		    $("#chkboxall").click(function() {
		    	$("#chkboxall").trigger("change");
		   
				allprojects();
			});
		   
		   $("#chkboxowner").click(function() {
		    	//alert("here");
		    	$("#chkboxowner").trigger("change");
		    	ownerprojects();
		    	
			});
		   
		   
		   $("#chkboxcollaborator").click(function() {
				$("#chkboxcollaborator").trigger("change");
				collaboratorprojects();
		    	
		    	
			});
		   $("#chkboxwsowner").click(function() {
				$("#chkboxwsowner").trigger("change");
				wsownerprojects();
			});
			
			
			$("#chkboxwscollaborator").click(function() {
				$("#chkboxwscollaborator").trigger("change");
				wscollaboratorprojects();
				
			});
	});
	
	function allprojects(){
		if ($('#chkboxall').prop('checked')){
			  $("#chkboxall").trigger("change");
			  var data = ${allprojects};
		    	console.log(data);
		    	//$('#alljstree').show();
		    	$('#alljstree').jstree(data);
		    	$('#alljstree').show();
		  } else {
			  $('#alljstree').hide();
			  $("#chkboxall").trigger("change");
		  
		  }
	}
	function ownerprojects(){
		 if ($('#chkboxowner').prop('checked')){
			  $("#chkboxowner").trigger("change");
			  var data = ${allprojects};
		    	console.log(data);
		    	//$('#alljstree').show();
		    	$('#asownerjstree').jstree(data);
		    	$('#asownerjstree').show();
		  } else {
			  $('#asownerjstree').hide();
			  $("#chkboxowner").trigger("change");
		  }
	}
	function collaboratorprojects(){
		if ($('#chkboxcollaborator').prop('checked')){
			  $("#chkboxcollaborator").trigger("change");
			  var data = ${collaborator};
		    	console.log(data);
		    	//$('#alljstree').show();
		    	$('#ascollaboratorjstree').jstree(data);
		    	$('#ascollaboratorjstree').show();
		  } else {
			  $('#ascollaboratorjstree').hide();
			  $("#chkboxcollaborator").trigger("change");
		  }
	}
	function wsownerprojects(){
		if ($('#chkboxwsowner').prop('checked')){
			  $("#chkboxwsowner").trigger("change");
			  var data = ${wsowner};
		    	console.log(data);
		    	//$('#alljstree').show();
		    	$('#aswsownerjstree').jstree(data);
		    	$('#aswsownerjstree').show();
		  } else {
			  $('#aswsownerjstree').hide();
			  $("#chkboxwsowner").trigger("change");
		  }
	}
	function wscollaboratorprojects(){
		if ($('#chkboxwscollaborator').prop('checked')){
			  $("#chkboxwscollaborator").trigger("change");
			  var data = ${wscolloborator};
		    	console.log(data);
		    	//$('#alljstree').show();
		    	$('#aswscollaboratorjstree  ').jstree(data);
		    	$('#aswscollaboratorjstree').show();
		  } else {
			  $('#aswscollaboratorjstree').hide();
			  $("#chkboxwscollaborator").trigger("change");
		  }
	}
	
	/* $("#chkboxall").click(function() {
		alert("here");
		var opt = $(this).parent().find('input[type=checkbox]');
        opt.prop('checked', $(this).is(':checked') ? true : false);
	  
	}); */
	
	
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
	<!-- <input type="checkbox"  id="chkboxall" checked > All  -->
	<input type="checkbox"  id="chkboxall" checked > All
	<input type="checkbox"  id="chkboxowner" > Owner 
	<input type="checkbox"  id="chkboxcollaborator" > Collaborator
	<input type="checkbox"  id="chkboxwsowner" > Workspace Owner 
	<input type="checkbox" id="chkboxwscollaborator" > Workspace Collaborator 
	</div>
	
	<div id="alljstree"></div>
	<div id="asownerjstree"></div>
	<div id="ascollaboratorjstree"></div>
	<div id="aswsownerjstree"></div>
	<div id="aswscollaboratorjstree"></div>
	
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
	
	
	
	
	
	
	
	
	
	