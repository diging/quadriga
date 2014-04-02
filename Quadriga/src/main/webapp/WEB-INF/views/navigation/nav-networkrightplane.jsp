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
</style>


<H5>DSpace meta data</H5>

Name : Some filename<br/>
created by : Someone<br/>
Created date : 4/2/2014<br/>
<br/>
<div align="left" id="desc_heading"></div>

<div align="left" id="lemma_name"></div>
<div id="concept_desc"></div>
<br/>


<div align="left" id="annot_desc"></div>
<div id="annot_details"></div>
<div id="inner-details"></div>
