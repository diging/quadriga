<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<script>
$(document).ready(function(){
    $('#conceptdesctextarea').scrollTop($('#conceptdesctextarea').scrollHeight);
    $('#annotationtextarea').scrollTop($('#annotationtextarea').scrollHeight);   
    
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
	
	div.div_main {
		width: 330px;
		padding: 10px;
		margin: 0px;
		opacity: 0.7;
	}
	
	table {
		border-collapse: collapse;
	}
	
	caption,th,td {
		font-family: 'Open Sans Condensed', sans-serif;
		padding: 0em 0em;
		border: 0px solid #B0BCB0;
	}
	
	caption {
		
		font-weight: 900;
		text-transform: uppercase;
	}
	#textfilestyle
	{
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
	<h2 class="major"><span>Network details</span></h2>
	<ul class="style3">
		<li>
			<article class="is-post-summary">
				<div align="left" id="item_metadata">
				<table id = metadataTable>
					<tr>
        				<th>Filename</th>
        				<th>Author</th>
        				<th>Last Modified Date</th>
   				 	</tr>
				</table>
				</div>
				
			</article>
		</li>
		<li>
			<article class="is-post-summary">
				<div align="left" id="lemma_name"></div>
				<div id="concept_desc"></div>
			</article>
		</li>
		<li>
			<article class="is-post-summary">
				<div align="left" id="annot_desc"></div>
				<div id="annot_details"></div>
			</article>
		</li>
	</ul>
</section>
