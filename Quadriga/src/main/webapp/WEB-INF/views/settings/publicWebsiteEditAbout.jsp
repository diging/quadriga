<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
<script>


	tinymce
			.init({
				selector : '.editable',
				height : 300,
				plugins : 'advlist autolink save link lists charmap print',
				menubar : false,
				save_enablewhendirty: false,
				toolbar : 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons | save'
			});

	$("#dlgConfirm")
			.dialog(
					{
						resizable : false,
						height : 'auto',
						width : 350,
						modal : true,
						buttons : {
							Submit : function() {
								$(this).dialog("close");
								//$("#deletewsform")[0].submit();
								$
										.ajax({
											url : "${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings/saveabout",
											type : "POST",
											data : "selected="
													+ $('#hidden').val(),
											success : function() {
												location.reload();
												//alert("done");
											},
											error : function() {
												alert("error");
											}
										});
								event.preventDefault();
							},
							Cancel : function() {
								$(this).dialog("close");
							}
						}
					});
</script>

</script>

<h2>Project : ${project.unixName}</h2>
<span class="byline">Edit about text for ${project.unixName}</span>
<form method="post"
	action="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}/settings/saveabout"
	modelAttribute="AboutTextBackingBean">

	<textarea path="title" name="title" id="title" 
		style="width: 100%; font-weight: bold; font-size: 24px; vertical-align: middle; align: center; text-align: center"> ${aboutText.title}</textarea>
	<div style="" path="description" name="description" id="description" class="editable"
		 value=${aboutText.description} </textarea>

</form>

