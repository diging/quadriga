<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
	
$(function() {
	$("#testEdit").click(function(){
		performAction(this)
		});
});

function handleResult(status){
	
	if (status == "SUCCESS") {
	$(".input-group-addon").html('<i class="fa fa-check-circle"></i>');
	} else if (status == "FAILURE") {
	$(".input-group-addon").html('<i class="fa fa-times"></i>');	
	}
	
}

function performAction(obj) {
	
	var data = {};
	data['projectName'] = $("#projectName").val();
	data['projectDescription'] = $('#projectDescription').val();
	data['projectUrl'] = $('#projectUrl').val();
	data['handlePattern'] = $('#handlePattern').val();
	data['resolvedHandlePattern'] = $('#resolvedHandlePattern').val();
	data['handleExample'] = $('#handleExample').val();
	data['resolvedHandleExample'] = $('#resolvedHandleExample').val();
	
	$
	.ajax({
		type : "POST",
		url : "${pageContext.servletContext.contextPath}/auth/resolvers/testEdit",
		data : {
			data : JSON.stringify(data)
		},
		beforeSend: function() {
			$(".input-group-addon").html('<i class="fa fa-spinner fa-spin" aria-hidden="true"></i>');
        },
        success : function(data,status) {
			handleResult(data);
		},
		error : function(e) {
			console.log("ERROR: ", e.responseText);
		}
	});
}
	
</script>

<h2>Edit new Project Handle Resolver</h2>

<form:form commandName="resolver" method="POST" class="form-horizontal"
        action="${pageContext.servletContext.contextPath}/auth/resolvers/update">
        
   <div class="form-group">
	    <label for="projectName" class="col-sm-3 control-label control-label-left">Project Name *</label>
	    <div class="col-sm-9">
	       <form:input class="form-control" id="projectName"
	                   path="projectName"  />
	       <form:errors path="projectName" class="error"></form:errors>
	    </div>
   </div>
   
   <div class="form-group">
        <label for="projectDescription" class="col-sm-3 control-label control-label-left">Description</label>
        <div class="col-sm-9">
           <form:input class="form-control" id="projectDescription"
                       path="description" />
           <form:errors path="description" class="error"></form:errors>
        </div>
   </div>
        
        
   <div class="form-group">
        <label for="projectUrl" class="col-sm-3 control-label control-label-left">Project URL</label>
        <div class="col-sm-9">
           <form:input type="url" class="form-control" id="projectUrl" path="projectUrl"/>
           <form:errors path="projectUrl" class="error"></form:errors>
        </div>
   </div>
   
   <div class="form-group">
        <label for="handlePattern" class="col-sm-3 control-label control-label-left">Handle Pattern *</label>
        <div class="col-sm-9">
        <div class="input-group">
           <form:input type="text" class="form-control" id="handlePattern" path="handlePattern" placeholder="e.g. (/[0-9]+/[0-9]+$)" />
           <span class="input-group-addon">
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
           <form:input class="form-control" id="resolvedHandlePattern" path="resolvedHandlePattern"/>
           <form:errors path="resolvedHandlePattern" class="error"></form:errors>
        </div>
   </div>
   
    <div class="form-group">
        <label for="resolvedHandlePattern" class="col-sm-3 control-label control-label-left">Handle Example *</label>
        <div class="col-sm-9">
           <form:input class="form-control" id="handleExample" path="handleExample"/>
           <form:errors path="handleExample" class="error"></form:errors>
        </div>
   </div>
   
   <div class="form-group">
        <label for="resolvedHandleExample" class="col-sm-3 control-label control-label-left">Resolved Handle Example *</label>
        <div class="col-sm-9">
           <form:input class="form-control" id="resolvedHandleExample" path="resolvedHandleExample"/>
           <form:errors path="resolvedHandleExample" class="error"></form:errors>
        </div>
   </div>
   
   <form:input type="hidden" path="id" value="${resolver.id}"/>
   <form:input type="hidden" path="username" value="${resolver.username}"/>
   
   <button type="button" id="testEdit" class="btn btn-primary">
   		<i class="fa fa-check-square" aria-hidden="true"></i>
   </button>
   <button type="submit" class="btn btn-primary">Create</button>
   <a href="${pageContext.servletContext.contextPath}/auth/resolvers" class="btn btn-default">Cancel</a>
</form:form>
        