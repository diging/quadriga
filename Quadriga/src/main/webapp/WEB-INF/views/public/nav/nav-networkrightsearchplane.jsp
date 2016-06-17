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
			${searchNodeLabel}<br/>
		
		<small>${description}</small>
		</h4>
	</div>
	<br>

	<div class="panel panel-default">
		<div class="panel-heading" id="lemma_name">Select a node</div>
		<div class="panel-body">
			<div id="concept_desc">...to display node information</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading" id="lemma_name">Select a node</div>
		<div class="panel-body">
			<div id="texts">...to display text.</div>
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