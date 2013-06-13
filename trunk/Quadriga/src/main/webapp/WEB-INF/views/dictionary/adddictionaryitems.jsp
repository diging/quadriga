<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" charset="utf8">
		


			$(document).ready(function() {
				activeTable = $('.dataTable').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"bAutoWidth": false
				});
			});
			$(document).ready(function() {
				$("input[type=submit]").button().click(function(event) {
					
				});
			});
			$(document).ready(function() {
				$("input[type=a]").button().click(function(event) {
					event.preventDefault();
				});
			});
			
		</script>

<style type="text/css">
#myGrid  
	        {  
	         	
  				 
	            height: 5%; 
				width: 40%;
	            background: white;  
	            display: -ms-grid;  
	            -ms-grid-columns: 4;  
	            -ms-grid-rows: 4;  
	        } 
	 
</style>
<H1>Word Search</H1>
    				<hr>
    				<br>
<div id="myGrid">  
 		<ul>
			<li>
				<form name='searchItem' method="POST" action="${pageContext.servletContext.contextPath}/auth/dictionaries/dictionary/wordSearch/${dictionaryid}">
				<!-- <form name='searchItem' method="POST" action="dictionary/wordSearch/${dictionaryid}"> -->	
					<font color="black">Word: </font>&nbsp;&nbsp; <input type="text"  name="itemName" id ="itemname" placeholder=" Enter a word"><br>
		
		
					<font color="black">POS: </font> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="posdropdown">
						<option value="noun">Nouns</option>
						<option value="verb">Verb</option>
						<option value="adverb">Adverb</option>
						<option value="adjective">Adjective</option>
						<option value="other">Other</option>
					</select>
					<br>

					<input type="submit" value="Search">
		 
				</form>
				<c:choose>
				      <c:when test="${errorstatus=='1'}">
					      <font color="red"> Word not found, please provide the correct input</font>
				      </c:when>
			     </c:choose>
			     <c:choose>
			      <c:when test="${success=='1'}">
				     <font color="blue"> <c:out value="${successmsg}"></c:out></font>
				      
			      </c:when>
			
			      <c:otherwise>
			     	 <font color="red"><c:out value="${errormsg}"></c:out></font>
			      
			      </c:otherwise>
		     </c:choose>
			</li>
		</ul>
    </div>
    
    
     <div class="container">
	<c:choose>
	      <c:when test="${status=='1'}">
		      <c:choose>
    				<c:when test="${not empty dictionaryEntry}">
    				<H3>Results</H3>
    				<hr>
    				<br>
    				<form method="POST" action="${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}">
    			<table cellpadding="0" cellspacing="0" border="0"
			class="display dataTable" width="70%">
			<!-- <table  class="dataTable" id="pagination"> -->
					<thead>
						   <tr>
						    	<th width = "75" height = "20" align="center">Term </th>
						    	<th width = "75" height = "20" align="center"> ID</th>
						    	<th width = "75" height = "20" align="center"> POS	</th>
						    	<th width = "90" height = "20" align="center"> Vocabulary</th>
						    	<th width = "500" height = "20" align="center"> Description</th>
						    	<th width = "75" height = "20" align="center"> Action</th>
						    	
						    </tr>
					</thead>	
					<tbody>   
								<tr>
								
									<td align="center">
										<input name="items" type="hidden" value="<c:out value="${dictionaryEntry.lemma}"></c:out>"/>
										<c:out value="${dictionaryEntry.lemma}"></c:out>
									</td>
									<td align="center">
										<input name="id" type="hidden" value="<c:out value="${dictionaryEntry.id}"></c:out>"/>
										<c:out value="${dictionaryEntry.id}"></c:out>
									</td>
									<td align="center">
										<input name="pos" type="hidden" value="<c:out value="${dictionaryEntry.pos}"></c:out>"/>
										<c:out value="${dictionaryEntry.pos}"></c:out>
									</td>
									<td align="center">
										<input name="vocabulary" type="hidden" value="<c:out value="${dictionaryEntry.vocabulary}"></c:out>"/>
										<c:out value="${dictionaryEntry.vocabulary}"></c:out>
									</td>
									<td align="left">
										<input name="description" type="hidden" value="<c:out value="${dictionaryEntry.description}"></c:out>"/>
										<c:out value="${dictionaryEntry.description}"></c:out>
									</td>
									<td align="center">
										<input type="submit" value="Add">  
									</td>
								
								</tr>	

								</tbody>
							</table>
						</form> 
				</c:when>
	
				
			</c:choose>
		      
		</c:when>
	
		<c:otherwise>
	
	
		</c:otherwise>
     </c:choose>
	</div>