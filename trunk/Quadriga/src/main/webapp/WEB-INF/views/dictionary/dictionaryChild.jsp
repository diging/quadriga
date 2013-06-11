<!--  
	Author Lohith Dwaraka  
	Used to list the items in a dictionary
	and search for items	
-->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script type="text/javascript" charset="utf8">
		


			$(document).ready(function(){
			  $('#pagination').dataTable();
			});
			$(document).ready(function(){
				  $('#pagination1').dataTable();
			});
			$(document).ready(function() {
				$("input[type=button]").button().click(function(event) {
					event.preventDefault();
				});
			});
			$(document).ready(function() {
				$("input[type=a]").button().click(function(event) {
					event.preventDefault();
				});
			});
			
		</script>
		
<script>

$(function() {
    $( "#dialog-confirm" ).dialog({
      resizable: false,
      height:140,
      modal: true,
      buttons: {
        "Delete all items": function() {
          $( this ).dialog( "close" );
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      }
    });
  });
  
function deleteItem(item){
	
	$( "#dialog-message" ).dialog({
	      resizable: false,
	      height:180,
	      modal: true,
	      buttons: {
	        "Delete": function() {
	          $( this ).dialog( "close" );
	          location.href = '${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}?item=' + item;
	        },
	        Cancel: function() {
	          $( this ).dialog( "close" );
	        }
	      }
	    });
	
    /*var checkstr =  confirm('are you sure you want to delete this?');
    if(checkstr == true){
    	location.href = '${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}?item=' + item;
    }else{
    	alert("hello");
    	return false;
    }*/
  }
  
  
</script>

			<div id="dialog-message" title="Confirm ?">   

			</div>		
       
	<style type="text/css">
	
		 	 table, td, th, caption
			{
				border:1px solid black;
			}
			th
			{
				background-color:#E9EEF6;
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
				background-color:#E9EEF6;
				color:black;
				font-weight: bold;
			}
			#myGrid  
	        {  
	         	
  				 
	            height: 5%; 
				width: 40%;
	            background: white;  
	            display: -ms-grid;  
	            -ms-grid-columns: 4;  
	            -ms-grid-rows: 4;  
	        } 
	        h1 {font-size:200%}
			
			table {border-collapse:collapse; table-layout:fixed; width:310px;}
  			 table td { width:400px; word-wrap:break-word;}
	</style>
	


	
	<ul>
		<li>
			<input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries'" value='Back'>
		</li>
	</ul>
	
	

<H1>Word Search</H1>
    				<hr>
    				<br>

	<div id="myGrid">  
 		<ul>
			<li>
				<form name='searchItem' method="POST" action="${pageContext.servletContext.contextPath}/auth/dictionaries/dictionary/wordSearch/${dictionaryid}">
				<!-- <form name='searchItem' method="POST" action="dictionary/wordSearch/${dictionaryid}"> -->	
					<font color="black">Word </font>&nbsp;&nbsp; <input type="text"  name="itemName" id ="itemname"><br>
		
		
					<font color="black">Pos </font> 
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
    	
			<c:choose>
			      <c:when test="${delsuccess=='1'}">
				     <font color="blue"> <c:out value="${delsuccessmsg}"></c:out></font>
				      
			      </c:when>
			
			      <c:otherwise>
			     	 <font color="red"><c:out value="${delerrormsg}"></c:out></font>
			      
			      </c:otherwise>
		     </c:choose>

	
		     <div class="container">
	<c:choose>
	      <c:when test="${status=='1'}">
		      <c:choose>
    				<c:when test="${not empty dictionaryEntry}">
    				<H1>Results</H1>
    				<hr>
    				<br>
    				<form method="POST" action="${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}">
    			
			<table  class="dataTable" id="pagination">
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
     <br>

<br>
	
	<H1>Dictionary Items: <c:out value="${dictName}"></c:out></H1>
    <hr>
    <br>
    <div class="container">
    <c:choose>
			      <c:when test="${updatesuccess=='1'}">
				     <font color="blue"> <c:out value="${updatesuccessmsg}"></c:out></font>
				      
			      </c:when>
			
			      <c:otherwise>
			     	 <font color="red"><c:out value="${updateerrormsg}"></c:out></font>
			      
			      </c:otherwise>
		     </c:choose>
    <c:choose>
    <c:when test="${not empty dictionaryItemList}">
    
	<table  class="dataTable" id="pagination1">
		<thead>
		   <tr>
		    	<th>Items </th>
		    	<th> ID</th>
		    	<th> Pos</th>
		    	<th> Action</th>
		    </tr>
		</thead>
		
		<tbody>
			<c:forEach var="dictionaryItem" items="${dictionaryItemList}">	
			<tr>
				<td align="center">
				<input name="items" type="hidden" value="<c:out value="${dictionaryItem.items}"></c:out>"/>
				 <c:out value="${dictionaryItem.items}"></c:out>
				</td>
				<td align="center">
					<c:out value="${dictionaryItem.id}"></c:out> 
				</td>
				<td align="center">
					<c:out value="${dictionaryItem.pos}"></c:out> 
				</td>
				<!--<td align="center">
					<c:out value="${dictionaryItem.vocabulary}"></c:out> 
				</td>
				<td align="center">
					<c:out value="${dictionaryItem.description}"></c:out> 
				</td>-->
				<td align="center">
					<!--  <input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}?item=<c:out value="${dictionaryItem.items}"></c:out>'" value='Delete' id="deleteItem"/>-->
					
					<input type="button" onclick="deleteItem(this.id);" value="Delete" id="<c:out value="${dictionaryItem.items}"></c:out>"/>
					<input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/updateDictionaryItems/${dictionaryid}?item=<c:out value="${dictionaryItem.items}"></c:out>&pos=<c:out value="${dictionaryItem.pos}"></c:out>'" value='Update'>
					 
				</td>
				
			</tr>
			</c:forEach>	
		</tbody>
			
	</table>
	</c:when>

	<c:otherwise> No dictionary items found</c:otherwise>
	</c:choose>
	</div>