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
    background: #CEE3F6;
    overflow: auto;        
  }
  div.div_main{
  	width:330px;
	padding:10px;
	margin:0px;
	opacity:0.7;
  }
  table,th,td
	{
	border:1px solid black;
	}
</style>

<div class= div_main align="left">

<h5>DSpace meta data </h5>
<table>
 <tr>
   <th>Name</th>
   <th>Created by</th>
   <th>Created Date</th>
 </tr>
 <tr>
   <td>Some filename</td>
   <td>Someone</td>
   <td>4/2/2014</td>
 </tr>
</table>

</div>

<br/>

<div class="div_main">
<div align="left" id="desc_heading"></div>

<div align="left" id="lemma_name"></div>
<div id="concept_desc"></div>
</div>
<br/>

<div class="div_main">
<div align="left" id="annot_desc"></div>
<div id="annot_details"></div>
</div>

<div id="inner-details"></div>
