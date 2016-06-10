<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>

<script type="text/javascript">
  $(document).ready(function(){
	  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/rest/"+ "${project.projectId}"+ "/dictionaries.json",
		  type : "GET",
          success: function(data){
              $.each(data, function( index, value ) {
            	  $( "#projectDictionaries" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/dictionaries/"+value['id']+"'><i class='fa fa-book'></i> "+value['name']+"</a><br>");
              });
          }
	  });
	  
	  $.ajax({ url: "${pageContext.servletContext.contextPath}/auth/rest/"+ "${project.projectId}"+ "/conceptcollections.json",
		  type : "GET",
          success: function(data){
              $.each(data, function( index, value ) {
            	 $( "#projectConceptCollections" ).prepend( "<a href='${pageContext.servletContext.contextPath}/auth/conceptcollections/"+value['id']+"'><i class='fa fa-list-alt'></i> "+value['name']+"</a><br>");
              });
          }
	  });
  });
</script>
<!--  Issue fixed by QUAD-55 Bharat Srikantan and Ajay Modi -->
<div style="margin-bottom: 20px;">
    <a href="${pageContext.servletContext.contextPath}/auth/workbench"><i class="fa fa-arrow-circle-left" aria-hidden="true"></i> All Projects </a>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Dictionaries</h3>
  </div>
  <div class="panel-body">
	  <div id="projectDictionaries" style="margin-bottom: 10px;"></div>
	  <c:if test="${owner=='1' || isProjectAdmin==true}">	  
	  <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/adddictionary"><i class="fa fa-plus-circle"></i> Add</a> &nbsp; &nbsp;
	  <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deletedictionary"><i class="fa fa-times-circle"></i> Delete</a>
	  </c:if>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h3 class="panel-title">Concept Collections</h3>
  </div>
  <div class="panel-body">
    <div id="projectConceptCollections"></div>
    <c:if test="${owner=='1' || isProjectAdmin==true}">    		
    <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/addconceptcollection"><i class="fa fa-plus-circle"></i> Add</a> &nbsp; &nbsp;
    <a href="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteconceptcollections"><i class="fa fa-times-circle"></i> Delete</a>
    </c:if>
  </div>
</div>

<hr>
To go to the public site, click this link <a href="${pageContext.servletContext.contextPath}/sites/${project.unixName}">http://quadriga.asu.edu/sites/${project.unixName}</a>
                

<hr>
<c:if test="${owner=='1' || isProjectAdmin==true}">
<a href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings"><i class="fa fa-cog"></i> Public Page Settings</a>
</c:if>