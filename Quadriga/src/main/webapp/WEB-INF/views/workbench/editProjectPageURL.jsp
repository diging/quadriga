<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>
	$(document).ready(function() {
		var oldUnixName = $("#unixName").val();
		$("#editProject").prop("disabled", true);

		$("#unixName").keyup(function(event) {
			var keyedInput = $("#unixName").val();
			$("#UnixURL").text('${unixnameurl}' + keyedInput);

			if (keyedInput != oldUnixName) {
				$("#editProject").prop("disabled", false);
			} else {
				$("#editProject").prop("disabled", true);
			}
		});
	});

	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});

		if ($.trim($("#unixName").val())) {
			var keyedInput = $("#unixName").val();
			$("#UnixURL").text('${unixnameurl}' + keyedInput);
		}
	});
</script>


<h2>Edit Project URL of Project: ${project.projectName}</h2>

<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}"><i
			class="fa fa-arrow-circle-left"></i> Back to Project</a>
	</p>
	<hr>
</div>

<form:form id="target" commandName="project" method="POST"
	action="${pageContext.servletContext.contextPath}/auth/workbench/editProjectPageURL/${project.projectId}">


<p>Please enter a custom project URL. This defines the URL of your public project website. Please use only letters, numbers and the following characters: -_.+!*()$ </p>
	<div class="form-group">
		<label class="sr-only" for="exampleInputAmount">Custom Project URL</label>
		<div class="input-group">
			<div class="input-group-addon">${unixnameurl}</div>
			<input type="hidden" name="projectId" value="${project.projectId}" >
			<input type="text" class="form-control" id="unixName" name="unixName"
				placeholder="Custom project URL" value="${project.unixName}">
		</div>
		<div><form:errors path="unixName" class="error"></form:errors></div>
	</div>

	
	<input data-toggle="modal" data-target="#edit-url" class="btn btn-primary" id="editProject" class="command" type="button" value="Update Project URL">
	<a class="btn btn-default"
                href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}">Cancel</a>

</form:form>

<!-- Delete Workspace Modal -->
<div class="modal fade" tabindex="-1" role="dialog" id="edit-url">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Update Custom Project URL</h4>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to update the custom project URL? The old URL will not work anymore and links to the old URL will break.</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
        <button type="button" class="btn btn-primary" id="update-btn">Yes, update!</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<script>
//# sourceURL=filename.js
$(document).ready(function() {
    $('#update-btn').click(function(event) {
    	$('#target').submit();
    });

    
});
</script>

<!-- /Content -->