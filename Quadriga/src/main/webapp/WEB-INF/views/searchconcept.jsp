<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!DOCTYPE html>
<html>
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
<form action="${pageContext.servletContext.contextPath}/auth/searchitems">
		<input type="text"  name="name" id ="name" placeholder="enter a word">
		<select name="pos">
						<option value="noun">Nouns</option>
						<option value="verb">Verb</option>
						<option value="adverb">Adverb</option>
						<option value="adjective">Adjective</option>
						<option value="other">Other</option>
		</select> 
		<input type="submit" value="Search Concepts" >
</form>
<display:table class="display" name="result" keepStatus="true" requestURI="/auth/conceptcollections" uid="1" pagesize = "10"  cellspacing="2" cellpadding="2">
	<display:column property="lemma" sortable="false" title="Lemma"/>
	<display:column property="id" sortable="false" title="Id" 	/>
	<display:column property="pos" sortable="false" title="Pos" 	/>
	<display:column property="description" sortable="false" title="Description" style="width: 7%;"	/>
	<display:column property="type" sortable="false" title="Type" />
	<display:column property="conceptList" sortable="false" title="ConceptList" 	/>
</display:table>


</html>
