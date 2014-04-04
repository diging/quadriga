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
		padding: .2em .8em;
		border: 0px solid #B0BCB0;
	}
	
	caption {
		
		font-weight: 900;
		text-transform: uppercase;
	}
	#textfilestyle
	{
		display: inline-block;
		background: #CEE7F5;
		padding: 0.75em 2em 0.75em 2em;
		border-radius: 8px;
		font-size: 0.85em;
		color: #000000;
	}
	
		#textfilestyle a
		{
			color: inherit;
		}
</style>

<div id="textfilestyle">


	<table style="width: 300px">
		<caption>Text File Details</caption>

		<tbody>
			<tr>
				<td align="left">Name</td>
				<td align="left">123</td>
			</tr>
			<tr>
				<td align="left">Created date</td>

				<td align="left">123</td>
			</tr>
			<tr>
				<td align="left">Name</td>

				<td align="left">123</td>
			</tr>
		</tbody>
	</table>
</div>

<br />

<div class="div_main">
	<div align="left" id="desc_heading"></div>

	<div align="left" id="lemma_name"></div>
	<div id="concept_desc"></div>
</div>


<div class="div_main">
	<div align="left" id="annot_desc"></div>
	<div id="annot_details"></div>
</div>

<div id="inner-details"></div>
