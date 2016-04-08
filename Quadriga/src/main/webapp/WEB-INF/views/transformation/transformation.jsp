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
	
-->
 <script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize: "10"
		});
		$("ul.pagination2").quickPagination({
			pageSize: "10"
		});
		
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI": true,
			"sPaginationType": "full_numbers",
			"bAutoWidth": false
		});
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
		
		$("#selectAllProjects").click(function(){
			$(".projectList").prop('checked',$(this).prop('checked'));
		});
		$("#selectAllTransformations").click(function(){
			$(".transformationList").prop('checked',$(this).prop('checked'));
		});
		
	});
	
</script>
<script type="text/javascript">
$(document).ready(function () {
    $('#confirmationTransformation').click(function () {
    	if(jQuery('#divProjectList input[type=checkbox]:checked').length && jQuery('#headingTwo input[type=checkbox]:checked').length) {
        	var projects = [];
            $.each($("input[name='project']:checked"), function(){            
                projects.push($(this).val());
            });        
    		var transformations = [];
            $.each($("input[name='transformation']:checked"), function(){            
                transformations.push($(this).val());
            });
        	$('#confirm').dialog({ modal: true,
        						draggable:false,
        						resizable:false,
       							position:'center',
       							width:'auto',
       							height:'auto',
       							title:"Confirm transformation",
        	   							
       							open: function (type, data) {
       						        $(this).parent().appendTo("form");
       						    },
       						    
	       						 buttons: { "Submit": function() { $(this).dialog("close"); },"Cancel": function() { $(this).dialog("close"); } },
       							
				        		open: function(){           
				        			var projects = [];
				        	        $.each($("input[name='project']:checked"), function(){            
				        	            projects.push($(this).val());
				        	        });
				        			
				        			var transformations = [];
				        	        $.each($("input[name='transformation']:checked"), function(){            
				        	            transformations.push($(this).val());
				        	        });
    			
				        			jQuery("#contentholder").html("<h3>Transformations:</h3> "+transformations.join("<br/>")+""+" <br/> <br/><h3>Projects </h3> " + projects.join("<br/>")+"");	
				        		}  		
				        	  }
        					);   
    					}
		    	
    				else{
				 		$('#alert').dialog({ modal: true,
						draggable:false,
						resizable:false,
						position:'center',
						width:'auto',
						height:'auto',
						title:"Transformation cannot be processed. ",
									
						open: function (type, data) {
						    $(this).parent().appendTo("form");
						    },
						 buttons: { "Okay": function() { $(this).dialog("close"); } },
		    				});	
						jQuery("#alertholder").html("Please select at least one transformation and project.");
		    			}
    });    
});
</script>

<style>
 #project div {
  display: inline;
  margin: 0 1em 0 1em;
  width: 30%;
}
</style>


<div>
<header>
	<h2><label><input type="checkbox" id="selectAllProjects"></label> Projects</h2>
	</header>
</div>


            


<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
<c:choose>
	<c:when test="${not empty projects}">
		<ul class="pagination1">
	<c:forEach var="project" items="${projects}" >
	<div class="panel panel-default">
    	<div class="panel-heading" role="tab" id="headingOne">
      		<h4 class="panel-title">   
        		<div class="checkbox1" id="divProjectList"> 
        		<label>
      				<input type="checkbox" value="${project.projectName}"  name="project" class="projectList"> 
        		</label>
        		<a role="button" data-toggle="collapse" data-parent="#accordion" href="#${project}" aria-expanded="true" aria-controls="${project}">
    			${project.projectName}
    			</a> 				
 				</div>       		
      		</h4>
    	</div>
		<div id="${project.projectName}" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
      		<div class="panel-body">   				
   				<ul class="networkToggleList">
					<li>
						<details>
							<ul>							
								<div class="container-fluid">
								<div class="row">
								<c:if test="${not empty networkMap[project.projectName]}">
								<c:forEach var="network" items="${networkMap[project.projectName]}">	
									 <div class="col-md-4">
									 <summary>
										<input type="checkbox" >
										<a href="${pageContext.servletContext.contextPath}/auth/editing/visualize/${network.networkId}">
										<c:out value="${network.networkName}"></c:out>
										</a>
									</summary>
									<li>Workspace: <c:out	value="${network.networkWorkspace.workspace.workspaceName}"></c:out></li>
									<li>Submitted by: <c:out value="${network.creator.userName}"></c:out>
									</li>
									<br>
									</div>
								</c:forEach>
								</c:if>
								</div>
								</div>			
							</ul>
						</details>
					</li>
				</ul>
       		</div>
    	</div>
  </div>

</c:forEach>
</ul>
</c:when>
<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>
</div>

<div class="row">
<div class="col-md-8"><span class="byline"><input type="checkbox" id="selectAllTransformations">  List of available transformations.</span></div>
</div>
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
<c:choose>
	<c:when test="${not empty dummyTransformations}">
		<ul class="pagination1">
			<c:forEach var="transformations" items="${dummyTransformations}">
				<div class="panel panel-default">
    				<div class="panel-heading" role="tab" id="headingTwo">
      				<h4 class="panel-title">   
						<li>
							<input type="checkbox" value="${transformations}" name="transformation" class="transformationList">
							&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${transformations}"></c:out>
						</li>
		    		</h4>
    				</div>
				</div>				
			</c:forEach>
		</ul>
	</c:when>	
</c:choose>
</div>

<input type="button" value='Submit Project and Transformations' id="confirmationTransformation"/>
<div id="confirm" style="display:none;">
	<center><h4>Are you sure you want to transform the following <br>networks with below transformation files?</h4> </center>   
	<p id="contentholder">
 	</p>
</div>

<div id="alert" style="display:none;">
	<center><h4>No transformations or projects selected. </h4> </center>   
 	<p id="alertholder">
 	</p>
</div>
