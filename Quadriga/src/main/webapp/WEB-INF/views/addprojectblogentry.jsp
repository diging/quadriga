<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>

<!-- Content -->
<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});
		$("input[type=button]").button().click(function(event) {
		});
	});

	function submitClick(id) {
		location.href = "${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}";
	}
</script>

<!-- Script to generate rich text editor -->
<script>
	tinymce
			.init({
				selector : '#description',
				height : 300,
				plugins : 'advlist autolink save link image lists charmap print preview',
				menubar : false,
				toolbar : 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons'
			});
</script>

<article class="is-page-content">
	<form:form commandName="projectBlogEntry" method="POST"
		action="${pageContext.servletContext.contextPath}/sites/${project.projectId}/addprojectblogentry">

		<header>
			<h2>Create new blog entry</h2>
		</header>

		<!-- Create project blog entry button at top right corner -->
		<div style="text-align: right;">
			<input class="btn btn-primary" type="submit" value="Create Entry"
				style="width: 15%; align: center;">
		</div>
		<br>
		<table style="width: 100%">
			<tr>
				<td><form:textarea path="title" id="title"
						placeholder="Enter Title Here"
						style="width: 100%; font-weight: bold; font-size: 24px; vertical-align: middle; align: center; text-align: center" /></td>
			</tr>
			<tr>
				<td><form:textarea path="description" id="description" /></td>
			</tr>

		</table>
	
		<br>

		<!-- Create project blog entry button at top right corner -->
		<div style="text-align: right;">
			<input class="btn btn-primary" type="submit" value="Create Entry"
				style="width: 15%; align: center;">
		</div>
		
	</form:form>
</article>

<!-- /Content -->