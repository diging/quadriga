<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>

<!-- Script to generate rich text editor -->
<script>
	tinymce
			.init({
				selector : '#description',
				height : 300,
				fontsize_formats: "8pt 9pt 10pt 11pt 12pt 14pt 16pt 18pt 20pt 22pt 24pt 48pt 72pt",
				theme: 'modern',
				plugins : 'advlist autolink save link image lists charmap print preview',
				menubar : false,
				toolbar : 'undo redo | fontsizeselect | fontselect | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons',
				setup : function(ed)
				{
				    ed.on('init', function() 
				    {
				        this.getDoc().body.style.fontSize = '14px';
				    });
				}
				
			});
</script>

<article class="is-page-content">
	<form:form commandName="projectBlogEntry" method="POST"
		action="${pageContext.servletContext.contextPath}/sites/${project.unixName}/addprojectblogentry">

		<header>
			<h2>Create new blog entry</h2>
		</header>

		<table style="width: 100%">
			<tr>
				<td style="color:red;"><form:errors path="title" class="ui-state-error-text"></form:errors>
					<br> <form:errors path="description"
						class="ui-state-error-text"></form:errors>
					<!-- Create project blog entry button at top right corner -->
					</td>
					<td style="width: 15%"><div style="text-align: right;">
						<input class="btn btn-primary" type="submit" value="Create Entry"
							style="width: 100%; align: center;">
					</div>
					<br>
				</td>
			</tr>
			
			<tr>
				<td colspan=2><form:textarea path="title" id="title"
						placeholder="Enter Title"
						style="width: 100%; border : solid 1px; height: 84px; border-color : #D3D3D3; font-weight: bold; font-size: 24px; vertical-align: bottom; align: center; text-align: center; padding : 20px 0" /><br>
				</td>
			</tr>
			<tr>
				<td colspan=2><form:textarea path="description" id="description" /></td>
			</tr>
		</table>
		<c:choose>
			<c:when test="${not empty networks}">
				<div class="table-responsive">
					<table class="table table-striped networks">
						<thead>
							<tr>
								<th width="80%">Name</th>
								<th>Action</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="network" items="${networks}">
								<tr>
									<td><input type="checkbox" value="dummy""/> </td>
									<td>${network.networkName}</td>
									<td><a
										href='${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks/${network.networkId}'>Visualize</a></td>
								</tr>								
							</c:forEach>
						</tbody>
					</table>
				</div>
			</c:when>
			<c:when test="${empty networks}">
				<div style="margin-top: 30px;">There are currently no networks in this project.</div>
			</c:when>
		</c:choose>
		<input type="hidden" name="projectId" value="${project.projectId}">

	</form:form>
</article>

<!-- /Content -->