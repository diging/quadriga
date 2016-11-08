<%@ page language="java" contentType="text/html;"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
	$(document).ready(
			function() {
				$('#conceptdesctextarea').scrollTop(
						$('#conceptdesctextarea').scrollHeight);
				$('#annotationtextarea').scrollTop(
						$('#annotationtextarea').scrollHeight);

			});
</script>

<style>
textarea {
	border-style: none;
	border-color: Transparent;
	background: #CEE7F5;
	color: #000000;
	overflow: auto;
	left: 0;
}
</style>

<c:if test="${!isNetworkEmpty}">
	<div>
		<p class="text-left">You have searched for:</p>
		<h4>
			${searchNodeLabel}<br /> <small>${description}</small>
		</h4>
	</div>
	<br>

	<div class="panel panel-default" id="concept" role="tablist"
		aria-multiselectable="true">
		<div class="panel-heading" role="tab" id="headingConcept">
			<a role="button" data-toggle="collapse" data-parent="#concept"
				href="#bodyConcept" aria-expanded="true" aria-controls="bodyConcept">
				<small>Concept: <span id="lemma_name">Select a node</span> <i
					class="fa fa-caret-down" aria-hidden="true"></i></small>
			</a>
		</div>
		<div id="bodyConcept" class="panel-body panel-collapse collapse in"
			role="tabpanel" aria-labelledby="headingConcept">
			<div id="loading" style="display:none">
	  			<p><img src="${pageContext.servletContext.contextPath}/resources/public/imgs/ajax-loader.gif" /> <small>Loading...</small></p>
			</div>
			<small>
				<p class="text-left" style="overflow: scroll;">
					<span id="concept_desc">...to display concept information.</span>
				</p>
			</small>
		</div>
	</div>

	<div class="panel panel-default" id="textsPanel" role="tablist"
		aria-multiselectable="true">
		<div class="panel-heading" role="tab" id="headingTexts">
			<a role="button" data-toggle="collapse" data-parent="#textsPanel"
				href="#bodyTexts" aria-expanded="true" aria-controls="bodyTexts">
				<small>Text occurrences <i class="fa fa-caret-down"
					aria-hidden="true"></i></small>
			</a>
		</div>
		<div id="bodyTexts" class="panel-body panel-collapse collapse in"
			style="text-align: left; max-height: 200px; overflow-y: scroll;"
			role="tabpanel" aria-labelledby="headingTexts">
			<center>
			<div id="loading1" style="display:none">
	  			<p><img src="${pageContext.servletContext.contextPath}/resources/public/imgs/ajax-loader.gif" /> <small>Loading...</small></p>
			</div>
			</center>
			<small>
				<div id="texts" class="text-left">Select node to display text.</div>
			</small>
		</div>
	</div>

	<div class="text-left">
		<h5>
			<a href="#" id="exportJson"><i class="fa fa-download"
				aria-hidden="true" title="Download as JSON"></i> Download network as
				JSON</a><br> <small>Right-click and select "Save Link
				As...", then enter a filename and use a '.json' extension. <br>You
				can import the saved JSON file into Cytoscape.
			</small>
		</h5>
	</div>

	<div class="text-left">
		<h5>
			<a href="#" id="exportPng"><i class="fa fa-file-image-o"
				aria-hidden="true" title="Download as PNG"></i> Download network as
				PNG</a><br> <small>Image will open in a new tab or window.</small>
		</h5>
	</div>

</c:if>