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
						class="ui-state-error-text"></form:errors> <br> <form:errors
						path="description" class="ui-state-error-text"></form:errors> <!-- Create project blog entry button at top right corner -->
				</td>
				<td style="width: 15%"><div style="text-align: right;">
						<input class="btn btn-primary" type="submit" value="Create Entry"
							style="width: 100%; align: center;">
					</div> <br></td>
			</tr>

			<tr>
				<td colspan=2><form:textarea path="title" id="title"
						placeholder="Enter Title"
						style="width: 100%; border : solid 1px; height: 84px; border-color : #D3D3D3; font-weight: bold; font-size: 24px; vertical-align: bottom; align: center; text-align: center; padding : 20px 0" /><br>
				</td>
			</tr>
			<tr>
				<td colspan=2><form:textarea path="description"
						id="description" /></td>
			</tr>
			<tr>
				<td colspan=1><button class="btn btn-primary" type="button"
						data-toggle="modal" data-target="#txtModal"">
						<i class="fa fa-plus-circle" aria-hidden="true"></i> Add a Network</a>
					</button></td>
			</tr>
		</table>


		<input type="hidden" name="projectId" value="${project.projectId}">
		<div>
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
										<td><input type="checkbox" value="${network.networkId}" /></td>
										<td>${network.networkName}</td>
										<td><a class="btn btn-primary" data-toggle="collapse"
											href="#collapseExample" aria-expanded="false"
											aria-controls="collapseExample" onclick="loadNetwork()">View
												Network</a></td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="collapse" id="collapseExample">
							<div class="card card-block"></div>
						</div>
				</c:when>
			</c:choose>
		</div>
	</form:form>
</article>
<div class="modal text-modal" id="txtModal" role="dialog"
	aria-labelledby="txtModal" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content ">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel"></h4>
			</div>
			<div class="modal-body" style="height: 500px; overflow-y: scroll;">
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>

<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-cose-bilkent/1.0.2/cytoscape-cose-bilkent.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/publicNetwork.js"></script>
<script
	src="${pageContext.servletContext.contextPath}/resources/js/cytoscape/dist/cytoscape.js"></script>
<script type="text/javascript">

function loadNetwork(){
	var data = {};
	$.ajax({
		type : "GET",
		contentType : "text/plain",
		url : "${pageContext.servletContext.contextPath}/sites/"+"sampleproj"+"/visualizenetwork/"+'NETCEnz9o',
		data : JSON.stringify(data),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			JSON.stringify(data);
			console.log("SUCCESS: ", data);
			visualizeNetwork(data);
		},
		error : function(e) {
			JSON.stringify(data);
			console.log("ERROR: ", e);
			visualizeNetwork(e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}

function visualizeNetwork(jsonString){
var container = document.getElementById('collapseExample');

var cy = cytoscape({
    container: container, // container to render in

    elements: jsonString,
    layout: {
        name: 'cose',
        idealEdgeLength: 5
      },
    style: [ 
             {
               selector: 'node',
               style: {
                 'background-color': 'mapData(group, 0, 1, #E1CE7A, #FDD692)',
                 'border-color' : '#B98F88',
                 'border-width' : 1,
                 'font-family': 'Open Sans',
                 'font-size': '12px',
                 'font-weight' : 'bold',
                 'color': 'mapData(group, 0, 1, #666, #333)',
                 'label': 'data(conceptName)',
                 'width':'mapData(group, 0, 1, 40, 55)',
                 "height":"mapData(group, 0, 1, 40, 55)",
                 'text-valign' : 'center',
               }
             },

             {
               selector: 'edge',
               style: {
                 'width': 1,
                 'line-color': '#754F44',
                 'target-arrow-shape': 'none'
               }
             }
           ]
});

defineListeners(cy, '${pageContext.servletContext.contextPath}', '${unixName}');

$( document ).ready(function() {
	$('#exportJson').on('click', function() {
		var json = cy.json();
		window.open('data:application/json,' +
        encodeURIComponent(JSON.stringify(json), '_blank'));
	});
});

$( document ).ready(function() {
    $('#exportPng').on('click', function() {
        var png = cy.png({'scale' : 5});
        window.open(png, '_blank');
    });
});
}

</script>

<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
<!-- Script to generate rich text editor -->
<script>
	tinymce
			.init({
				selector : '#description',
				height : 300,
				fontsize_formats : "8pt 9pt 10pt 11pt 12pt 14pt 16pt 18pt 20pt 22pt 24pt 48pt 72pt",
				theme : 'modern',
				plugins : 'advlist autolink save link image lists charmap print preview',
				menubar : false,
				toolbar : 'undo redo | fontsizeselect | fontselect | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons',
				setup : function(ed) {
					ed.on('init', function() {
						this.getDoc().body.style.fontSize = '14px';
					});
				}

			});
</script>

<!-- /Content -->