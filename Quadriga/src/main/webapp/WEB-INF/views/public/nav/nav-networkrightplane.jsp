<%@ page language="java" contentType="text/html;"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<c:if test="${not empty network}">
	<div class="panel panel-default">
		<div class="panel-heading">Network Details</div>
		<div class="panel-body" style="text-align: left;">
			Network name:<br> <strong>${network.networkName}</strong><br>
			Submitted by:<br> <strong>${network.creator.name}</strong><br>
			Submitted on: <br> <strong>${network.createdDate}</strong><br>
			Annotated text: <br> <strong>${network.textUrl}</strong>
		</div>
	</div>
</c:if>
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
<h5><a href="#" id="exportJson"><i class="fa fa-download" aria-hidden="true"
	title="Download as JSON"></i> Download network as JSON</a><br>
<small>Right-click and select "Save Link As...", then enter a filename and use a '.json' extension. <br>You can import the saved JSON file into Cytoscape.</small></h5>
</div>

<div class="text-left">
<h5><a href="#" id="exportPng"><i class="fa fa-file-image-o" aria-hidden="true"
    title="Download as PNG"></i> Download network as PNG</a><br>
<small>Image will open in a new tab or window.</small></h5>
</div>
