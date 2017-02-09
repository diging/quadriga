<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="${pageContext.servletContext.contextPath}/resources/js/handleResult.js"></script>

<script>

$(function() {
	$("#testAdd").click(function(){performAction(this,this.id,'${pageContext.servletContext.contextPath}','${_csrf.token}','${_csrf.headerName}');});
});

</script>

<h2>Create new Project Handle Resolver</h2>

<form:form commandName="resolver" method="POST" class="form-horizontal"
        action="${pageContext.servletContext.contextPath}/auth/resolvers/add">
   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>   
   <div class="form-group">
	    <label for="projectName" class="col-sm-3 control-label control-label-left">Project Name *</label>
	    <div class="col-sm-9">
	       <form:input class="form-control" id="projectName"
	                   path="projectName" placeholder="e.g. My Project Website"  />
	       <form:errors path="projectName" class="error"></form:errors>
	    </div>
   </div>
   
   <div class="form-group">
        <label for="projectDescription" class="col-sm-3 control-label control-label-left">Description</label>
        <div class="col-sm-9">
           <form:input class="form-control" id="projectDescription"
                       path="description"  />
           <form:errors path="description" class="error"></form:errors>
        </div>
   </div>
        
        
   <div class="form-group">
        <label for="projectUrl" class="col-sm-3 control-label control-label-left">Project URL</label>
        <div class="col-sm-9">
           <form:input type="url" class="form-control" id="projectUrl" path="projectUrl"  />
           <form:errors path="projectUrl" class="error"></form:errors>
        </div>
   </div>
   
   <div class="form-group">
        <label for="handlePattern" class="col-sm-3 control-label control-label-left">Handle Pattern *</label>
        <div class="col-sm-9">
        <div class="input-group">
           <form:input type="text" class="form-control" id="handlePattern" path="handlePattern" placeholder="e.g. (/[0-9]+/[0-9]+$)" />
         	<span class="input-group-btn">
           <button class="btn btn-primary" type="button" id="testAdd">
						<i class="fa fa-question" aria-hidden="true"></i>
					</button>
           </span>  
        </div>
         <div>
           <form:errors path="handlePattern" class="error"></form:errors>
        </div>
        </div>
   </div>
   
   <div class="form-group">
        <label for="resolvedHandlePattern" class="col-sm-3 control-label control-label-left">Resolved Handle Pattern *</label>
        <div class="col-sm-9">
           <form:input class="form-control" id="resolvedHandlePattern" path="resolvedHandlePattern" placeholder="e.g. http://my.website.org/{0}" />
           <form:errors path="resolvedHandlePattern" class="error"></form:errors>
        </div>
   </div>
   
    <div class="form-group">
        <label for="resolvedHandlePattern" class="col-sm-3 control-label control-label-left">Handle Example *</label>
        <div class="col-sm-9">
           <form:input class="form-control" id="handleExample" path="handleExample" placeholder="e.g. http://my.handle.org/handle/123/456" />
           <form:errors path="handleExample" class="error"></form:errors>
        </div>
   </div>
   
   <div class="form-group">
        <label for="resolvedHandleExample" class="col-sm-3 control-label control-label-left">Resolved Handle Example *</label>
        <div class="col-sm-9">
           <form:input class="form-control" id="resolvedHandleExample" path="resolvedHandleExample" placeholder="e.g. http://my.website.org/123/456" />
           <form:errors path="resolvedHandleExample" class="error"></form:errors>
        </div>
   </div>
      
   <button type="submit" class="btn btn-primary">Create</button>
   <a href="${pageContext.servletContext.contextPath}/auth/resolvers" class="btn btn-default">Cancel</a>
</form:form>
        