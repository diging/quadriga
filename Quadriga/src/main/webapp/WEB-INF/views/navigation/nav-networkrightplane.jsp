<%@ page language="java" contentType="text/html;"%>


<style>
textarea {
	border-style: none;
	border-color: Transparent;
	background: #CEE7F5;
	color: #000000;
	overflow: auto;
	left: 0;
}

div.div_main {
	width: 330px;
	padding: 10px;
	margin: 0px;
	opacity: 0.7;
}

table, th, td {
	border: 1px solid black;
}

}
caption, th, td {
	font-family: 'Open Sans Condensed', sans-serif;
	padding: 0em 0em;
	border: 0px solid #B0BCB0;
}

caption {
	font-weight: 900;
	text-transform: uppercase;
}

#textfilestyle {
	display: inline-block;
	background: #DDEFF8;
	padding: 0.75em 0.75em 0.75em 0.75em;
	border-radius: 8px;
	font-size: 0.85em;
	color: #000000;
	margin-left: -4.3em;
	left: 0;
}
</style>


<section>
	<div class="panel panel-default">
		<div class="panel-heading">Network Details</div>
		<div class="panel-body">
			Network name:<br> <strong>${network.networkName}</strong><br>
			Submitted by:<br> <strong>${network.creator.name}</strong><br>
			Submitted on: <br> <strong>${network.createdDate}</strong><br>
			Annotated text: <br> <strong>${network.textUrl}</strong>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading" id="lemma_name">Select a node</div>
		<div class="panel-body">
			<div id="concept_desc">...to display node information</div>
		</div>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading" id="lemma_name">Annotations</div>
		<div class="panel-body">
			<div id="annot_details">Select a node.</div>
		</div>
	</div>

</section>
