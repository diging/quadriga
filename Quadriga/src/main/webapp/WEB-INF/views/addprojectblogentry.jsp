<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>



<article class="is-page-content">
	<form:form commandName="projectBlogEntry" method="POST"
		action="${pageContext.servletContext.contextPath}/sites/${project.unixName}/addprojectblogentry">

		<header>
			<h2>Create new blog entry</h2>
		</header>

		<table style="width: 100%">
			<tr>
				<td style="color: red;"><form:errors path="title"
						class="ui-state-error-text" /> <br> <form:errors
						path="description" class="ui-state-error-text" /> <!-- Create project blog entry button at top right corner -->
				</td>
				<td style="width: 15%"><div style="text-align: right;">
						<input class="btn btn-primary" type="submit" value="Create Entry"
							style="width: 100%; align: center;">
					</div> <br></td>
			</tr>

			<tr>
				<td colspan="2"><form:textarea path="title" id="title"
						placeholder="Enter Title"
						style="width: 100%; border : solid 1px; height: 84px; border-color : #D3D3D3; font-weight: bold; font-size: 24px; vertical-align: bottom; align: center; text-align: center; padding : 20px 0" /><br>
				</td>
			</tr>
			<tr>
				<td colspan="2"><form:textarea path="description"
						id="description" /></td>
			</tr>
			<tr>
				<td colspan="1"><button class="btn btn-primary" type="button"
						data-toggle="collapse" data-target="#networkTable">
						<i class="fa fa-plus-circle" aria-hidden="true"></i> Add a Network<a></a>
					</button></td>
			</tr>
		</table>


		<input type="hidden" name="projectId" value="${project.projectId}">
		<div id="networkTable" class="collapse">
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
										<td>${network.networkName}</td>
										<td><a class="btn btn-primary" data-toggle="collapse"
											href="#networkBox" aria-expanded="false"
											aria-controls="collapseExample" onclick="loadNetwork()">View
												Network</a></td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div id="networkBox" class="collapse" 
							style="min-height: 500px; width: 100%; text-align: left;">
							<div id="addImage">
								<button class="btn btn-primary" type="button">
									<i class="fa fa-plus-circle" aria-hidden="true"></i> Add This
									Network To Editor<a></a>
								</button>
							</div>
						</div>
					</div>
				</c:when>
			</c:choose>
		</div>
	</form:form>

</article>



<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-cose-bilkent/1.0.2/cytoscape-cose-bilkent.js"
	type="text/javascript"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/publicNetwork.js"
	type="text/javascript"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/dist/cytoscape.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function loadNetwork() {
		$.ajax({
			type : "GET",
			contentType : "application/json",
			datatype : 'text',
			url : "${pageContext.servletContext.contextPath}/sites/"
					+ "sampleproj" + "/visualizenetwork/" + 'NETCEnz9o',
			timeout : 100000,
			success : function(data) {
				visualizeNetwork(data);
			},
			error : function(e) {
				console.log(e.Message);
			}
		});

	}

	function visualizeNetwork(jsonString) {

		var container = document.getElementById('networkBox');

		var cy = cytoscape({
			container : container, // container to render in

			layout : {
				name : 'cose',
				idealEdgeLength : 5
			},

			elements : eval(jsonString),

			style : [
					{
						selector : 'node',
						style : {
							'background-color' : 'mapData(group, 0, 1, #E1CE7A, #FDD692)',
							'border-color' : '#B98F88',
							'border-width' : 1,
							'font-family' : 'Open Sans',
							'font-size' : '12px',
							'font-weight' : 'bold',
							'color' : 'black',
							'label' : 'data(conceptName)',
							'width' : 'mapData(group, 0, 1, 40, 55)',
							"height" : "mapData(group, 0, 1, 40, 55)",
							'text-valign' : 'center',
						}
					},

					{
						selector : 'edge',
						style : {
							'width' : 1,
							'line-color' : '#754F44',
							'target-arrow-shape' : 'none'
						}
					} ]
		});

		defineListeners(cy, '${pageContext.servletContext.contextPath}',
				'${unixName}');

		$(document)
				.ready(
						function() {
							$('#addImage')
									.on(
											'click',
											function() {
												var png = cy.png({
													'scale' : 0.75
												});
												tinyMCE
														.execCommand(
																'mceInsertContent',
																false,
																'<img alt="Smiley face" src="' + png + '"/>');
											});
						});
	}
</script>

<script src="//cdn.tinymce.com/4/tinymce.min.js" type="text/javascript"></script>
<!-- Script to generate rich text editor -->
<script type="text/javascript">
	tinymce
			.init({
				selector : '#description',
				height : 300,
				fontsize_formats : "8pt 9pt 10pt 11pt 12pt 14pt 16pt 18pt 20pt 22pt 24pt 48pt 72pt",
				theme : 'modern',
				plugins : 'advlist autolink save link image imagetools lists charmap print preview',
				menubar : false,
				toolbar : 'undo redo | fontsizeselect | fontselect | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons',
				setup : function(ed) {
					ed.on('init', function() {
						this.getDoc().body.style.fontSize = '14px';
					});
				}

			});
</script>
<script src="/quadriga/resources/js/d3.min.js" charset="utf-8"
	type="text/javascript"></script>
<!-- /Content -->