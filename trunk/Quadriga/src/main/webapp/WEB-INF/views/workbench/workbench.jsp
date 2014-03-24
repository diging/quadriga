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
		if($(".users").length == $(".users:checked").length) {
			   if ($(".users:checked").length != 0){
		          
	           $("#chkboxall").prop("checked", true);
	            allprojects();
			   }
		  }
	});
	function checkUsersSelect() {
			  if($(".users").length == $(".users:checked").length ) {
		            $("#chkboxall").prop("checked", true);
		            allprojects();
		            return true;
		        } 
			  
		       return false;
		}
		function checkUsersDeSelect() {  
			if($(".users").length != $(".users:checked").length)  {
				  
				  
	            $("#chkboxall").prop("checked",false);
	            $('#alljstree').hide();
	            
	         // allprojects();
	            return false;
	        }
			return false;
		}
	$(function() 
	{
		  $( "#tabs" ).tabs();
		  $("#chkboxall").click(function() {
				$("#chkboxwscollaborator").trigger("change");
			  allprojects();
				
			});
		  
		
		  $("#chkboxowner").click(function() {
			  
		    	$("#chkboxowner").trigger("change");
		    	ownerprojects();
		    	//checkOtherUsers();
		    	//checkUsersCLick();
		    	collaboratorprojects();
			      wsownerprojects();
				  wscollaboratorprojects();
			});
		   
		   
		   $("#chkboxcollaborator").click(function() {
			      
				$("#chkboxcollaborator").trigger("change");
				collaboratorprojects();
				//checkOtherUsers();
				//checkUsersCLick();
				ownerprojects();
			      wsownerprojects();
				  wscollaboratorprojects(); 
		    	
			});
		   $("#chkboxwsowner").click(function() {
			      
				$("#chkboxwsowner").trigger("change");
				wsownerprojects();
				//checkOtherUsers();
				//checkUsersCLick();
				ownerprojects();
				  collaboratorprojects();
				  wscollaboratorprojects(); 
				 
			});
			
			
			$("#chkboxwscollaborator").click(function() {
				
				$("#chkboxwscollaborator").trigger("change");
				wscollaboratorprojects();
				//checkOtherUsers();
				//checkUsersCLick();
				ownerprojects();
				  collaboratorprojects();
			      wsownerprojects(); 
				
				
			});
		    
			
	});
	
	function allprojects(){
		var data = ${allprojects};
    	$('#alljstree').jstree(data);
		if ($('#chkboxall').prop('checked')){
			 $(".users").prop("checked", true);
		    	$('#alljstree').show();
		}
		if (!$('#chkboxall').prop('checked')){
			  
			  $('#alljstree').hide();
			  $(".users").prop("checked", false);
			  wsownerprojects();
				
				ownerprojects();
				  collaboratorprojects();
				  wscollaboratorprojects(); 
		  
		  }
	}
		
	
	function ownerprojects(){
		var data = ${owner};
		
    	
		 if ($('#chkboxowner').prop('checked')){
			 if(!checkUsersSelect()) {
				  $('#asownerjstree').jstree(data);
				  $('#asownerjstree').show();
			 }
			 
		  } else {
			  if(!checkUsersDeSelect()){
			  $('#asownerjstree').hide();
			  }
		  }
	}
	function collaboratorprojects(){
		var data = ${collaborator};
    	
		if ($('#chkboxcollaborator').prop('checked')){
			if(!checkUsersSelect()){
			$('#ascollaboratorjstree').jstree(data);
		    	$('#ascollaboratorjstree').show();
		   }
		  } else {
			  if(! checkUsersDeSelect())
			  $('#ascollaboratorjstree').hide();
			  
		  }
	}
	function wsownerprojects(){
		var data = ${wsowner};
    	
		if ($('#chkboxwsowner').prop('checked')){
			if(!checkUsersSelect()){
			$('#aswsownerjstree').jstree(data);
		    	$('#aswsownerjstree').show();
			
		  } 
		}else {
			  if(!checkUsersDeSelect())
			  $('#aswsownerjstree').hide();
		  }
		
	}
	
	function wscollaboratorprojects(){
		 var data = ${wscollaborator};
	    	
		
		if ($('#chkboxwscollaborator').prop('checked')){
			 if(!checkUsersSelect()){
			$('#aswscollaboratorjstree  ').jstree(data);
		    	$('#aswscollaboratorjstree').show();
			 }
				 
		  } else {
			  if(!checkUsersDeSelect())
			  $('#aswscollaboratorjstree').hide();
			  
		  }
	}
	
	function clickproject(id,name){
		window.location.href  = "${pageContext.servletContext.contextPath}/auth/workbench/"+id;
	}
	function clickWorkspace(id,name){
		window.location.href  = "${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/"+id;
	}
	
	
	
</script>
<style>
.tabs {
	font-size: 80%;
}
</style>
<header>
	<h2>Quadriga Workbench</h2>
	<span class="byline">Manage projects and workspaces</span>
</header>


<!-- <input type="checkbox"  id="chkboxall" checked > All  -->
<input type="checkbox" id="chkboxall" checked>
All
<div id="users">
	<input type="checkbox" id="chkboxowner" class="users" checked>
	Owner <input type="checkbox" id="chkboxcollaborator" class="users"
		checked> Collaborator <input type="checkbox"
		id="chkboxwsowner" class="users" checked> Workspace Owner <input
		type="checkbox" id="chkboxwscollaborator" class="users" checked>
	Workspace Collaborator
</div>

<div id="alljstree"></div>

<div id="asownerjstree"></div>

<div id="ascollaboratorjstree"></div>

<div id="aswsownerjstree"></div>

<div id="aswscollaboratorjstree"></div>

<div id="tabs" class="tabs">
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
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out
								value="${project.name}"></c:out></a> <br>
					<c:out value="${project.description}"></c:out></li>
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
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out
								value="${project.name}"></c:out></a> <br>
					<c:out value="${project.description}"></c:out></li>
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
					<li><a
						href="${pageContext.servletContext.contextPath}/auth/workbench/${project.internalid}"><c:out
								value="${project.name}"></c:out></a> <br>
					<c:out value="${project.description}"></c:out></li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${empty projectlistaswscollaborator}">
		    You are not collaborator to any workspace associated with the projects.
		</c:if>
	</div>
</div>









