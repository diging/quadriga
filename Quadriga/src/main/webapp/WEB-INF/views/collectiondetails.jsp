<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script>
$(function() { // when DOM is ready
    $("#additems").click(function(){ // when #showhidecomment is clicked
        $("#addsection").load("searchitems"); // load the sample.jsp page in the #chkcomments element
    }); 
});
</script>
<link rel="stylesheet"
		href="/quadriga/resources/txt-layout/css/demo_table_jui.css" />
		<style>
		table.display thead tr {
	font-weight: 700;
	color: rgb(107, 119, 112);
	text-align: center;
	background-color: #e3daa8;
	
	}
	table.its thead tr a:LINK {
		text-decoration:none;
	}
	table.its thead tr a:HOVER {
		text-decoration:underline;
		
	}
	
	</style>
<div style="height: 50%; vertical-align: top; " >
<header>
		<h3>Collection Name: <span   contenteditable="true">  ${concept.name}</span> </h3>
		
</header>
	 <h3>Description: </h3><wbr>
	<span contenteditable="true" >
	<c:out value="${concept.description }"></c:out>
	</span>

	<display:table  class="display" name="${concept.items}" keepStatus="true" uid="1" pagesize = "10" cellspacing="2" cellpadding="2">
	<display:column property="name" sortable="false" title="name"/>
	<display:column property="description" title="description"/>
	<display:column property="pos" title="pos"/>
	</display:table>
	 <button id="additems">Add Items to List</button>
	 <div id="addsection">
	 </div>
</div>

	
</html>