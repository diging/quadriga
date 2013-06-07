<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
	<style type="text/css">
		 	 table, td, th, caption
			{
				border:1px solid black;
			}
			th
			{
				background-color:#D0C6B1;
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
				background-color:#D0C6B1;
				color:black;
				font-weight: bold;
			}
			
			table {border-collapse:collapse; table-layout:fixed; width:310px;}
  			 table td { width:400px; word-wrap:break-word;}
	</style>
		
 	<header>
		<span class="byline">Manage dictionary list for <c:out value="${userId}"></c:out></span>
		<a href="/quadriga/auth/dictionaries/addDictionary">Add Dictionary</a>
		
	</header>
	
	<c:choose>
    	<c:when test="${success=='1'}">
    		<span class="byline"><c:out value="${successMsg}"></c:out></span>
    	</c:when>
		<c:otherwise> 
			<span class="byline"><c:out value="${errormsg}"></c:out></span>
		</c:otherwise>
	</c:choose>
	
	
	
    <c:choose>
    <c:when test="${not empty dictinarylist}">
    	<Center>
		    <table>
			    <caption align="top" >Dictionary List</caption>
			   	<tr>
			    	<th width = "150" height = "20" align="center">Dictionary Name</th>
			    	<th width = "300" height = "20" align="center">Description</th>
			    	<th width = "100" height = "20" align="center">Action</th>
			    </tr>
		    	
					
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
							<a href="#">Edit</a>
							<a href="#">Delete</a>  
						</td>
					</tr>
				</c:forEach>
			</table>
		</Center>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>

	
