<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<script type="text/javascript" charset="utf8">
		
			$(document).ready(function(){
			  $('#pagination').dataTable();
			  
			});
			$(document).ready(function(){
				  $('#pagination1').dataTable();
			});
		</script>
	<style type="text/css">
			table{
			width: 50%;
			}
		 	 table, td, th, caption
			{
				border:1px solid black;
				
			}
			th
			{
				background-color:#DFEFF0;
				color:black;
				font-weight: bold;
			}
			td
			{
				background-color:white;
				color:black;
				white-space:wrap;
				overflow:wrap;
				text-overflow:ellipsis;
			}
			caption
			{
				background-color:#DFEFF0;
				color:black;
				font-weight: bold;
			}
			h1 {font-size:200%}
			
			.example4 a {font-family:Georgia, "Times New Roman", Times, serif;font-size:large;cursor: auto}
				a:hover { text-decoration: none color: #ff9900; font-weight:bold;}
				
				
			}
			table {border-collapse:collapse; table-layout:fixed; width:310px;}
  			 table td { width:400px; word-wrap:break-word;}
	</style>
	

	
		
	<H1>Manage dictionary list for User : <c:out value="${userId}"></c:out></H1>
    				<hr>
    				<br>
    <div class="example4">				
<a href="/quadriga/auth/dictionaries/addDictionary">Add Dictionary</a></div>
	<c:choose>
    	<c:when test="${success=='1'}">
    		<span class="byline"><c:out value="${successMsg}"></c:out></span>
    	</c:when>
		<c:otherwise> 
			<span class="byline"><c:out value="${errormsg}"></c:out></span>
		</c:otherwise>
	</c:choose>
	
	
	<div class="container">
    <c:choose>
    <c:when test="${not empty dictinarylist}">
    	<Center>
		    <table class="dataTable" id="pagination">
			  <thead>
			   	<tr>
			    	<th width = "75" height = "20" align="center">Dictionary Name</th>
			    	<th width = "30%" height = "20" align="center">Description</th>
			    	<th width = "100" height = "20" align="center">Action</th>
			    </tr>
		    	</thead>
				<tbody>	
		    	<c:forEach var="dictionary" items="${dictinarylist}">
		    	
			    	<tr>
						<td align="center">
							<a href="dictionaries/${dictionary.id}">
								<c:out value="${dictionary.name}"></c:out>
							</a>
						</td>
						<td align="justify">
							&nbsp;<c:out value="${dictionary.description}"></c:out>   
						</td>
						<td align="center">
						<input type=button onClick="location.href='#'" value='Edit'>
						<input type=button onClick="location.href='#'" value='Delete'>

						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</Center>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>
</div>
	
