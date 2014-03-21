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
		  $("#chkboxall").click(function() {
				$("#chkboxwscollaborator").trigger("change");
			  allprojects();
				
			});
		 
		  //allprojects();
		   if($(".users").length == $(".users:checked").length) {
			   if ($(".users:checked").length != 0){
		          
	            $("#chkboxall").prop("checked", true);
	            allprojects();
			   }
			   
	        } 
		  if($(".users").length != $(".users:checked").length)  {
			  $("#chkboxall").prop("checked", false);
			 // allProjects();
	        }   
	     // allProjects();
		  /* ownerprojects();
		  collaboratorprojects();
	      wsownerprojects();
		  wscollaboratorprojects(); */
		  $(".users").click(function(){
			  if($(".users").length == $(".users:checked").length && $(".users:checked").length >0) {
		            $("#chkboxall").prop("checked", true);
		            allprojects();
		        } 
			  if($(".users").length != $(".users:checked").length )  {
				  
			  
		            $("#chkboxall").prop("checked",false);
		        }
		   
		   $("#chkboxowner").click(function() {
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
		    
			
	});
	
	function allprojects(){
		var data = ${allprojects};
    	$('#alljstree').jstree(data);
		if ($('#chkboxall').prop('checked')){
			 $(".users").prop("checked", true);
			if($(".users").length == $(".users:checked").length){
			  
		    	$('#alljstree').show();
			}
			else{
				$('#alljstree').hide();
			}
		  } else {
			  
			  $('#alljstree').hide();
			  $(".users").prop("checked", false);
			  
		  
		  }
	}
	function ownerprojects(){
		var data = ${owner};
    	$('#asownerjstree').jstree(data);
		 if ($('#chkboxowner').prop('checked')){
		    	$('#asownerjstree').show();
		    	
		  } else {
			  
			  $('#asownerjstree').hide();
			  $("#chkboxowner").trigger("change");
		  }
	}
	function collaboratorprojects(){
		var data = ${collaborator};
    	$('#ascollaboratorjstree').jstree(data);
		if ($('#chkboxcollaborator').prop('checked')){
			  
		    	$('#ascollaboratorjstree').show();
		  } else {
			  $('#ascollaboratorjstree').hide();
		  }
	}
	function wsownerprojects(){
		var data = ${wsowner};
    	console.log(data);
    	$('#aswsownerjstree').jstree(data);
		if ($('#chkboxwsowner').prop('checked')){
		    	$('#aswsownerjstree').show();
		  } else {
			  $('#aswsownerjstree').hide();
		  }
	}
	function wscollaboratorprojects(){
		 var data = ${wscollaborator};
	    	$('#aswscollaboratorjstree  ').jstree(data);
	    	
		if ($('#chkboxwscollaborator').prop('checked')){
		    	$('#aswscollaboratorjstree').show();
		  } else {
			  $('#aswscollaboratorjstree').hide();
		  }
	}
	
	function clickproject(id,name){
		window.location.href  = "${pageContext.servletContext.contextPath}/auth/workbench/"+id;
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
	
	
	<!-- <input type="checkbox"  id="chkboxall" checked > All  -->
	<input type="checkbox"  id="chkboxall" checked > All
	<div id="users">
	<input type="checkbox"  id="chkboxowner" class="users" checked> Owner 
	<input type="checkbox"  id="chkboxcollaborator" class="users" checked> Collaborator
	<input type="checkbox"  id="chkboxwsowner" class="users" checked> Workspace Owner 
	<input type="checkbox" id="chkboxwscollaborator" class="users" checked> Workspace Collaborator 
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
	
	
	
	
	
	
	
	
	
	