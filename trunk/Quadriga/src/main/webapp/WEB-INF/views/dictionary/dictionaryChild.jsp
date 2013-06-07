<!--  
	Author Lohith Dwaraka  
	Used to list the items in a dictionary
	and search for items	
-->

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
				width: 35%;
	            background: white;  
	            display: -ms-grid;  
	            -ms-grid-columns: 4;  
	            -ms-grid-rows: 4;  
	        } 
			
			table {border-collapse:collapse; table-layout:fixed; width:310px;}
  			 table td { width:400px; word-wrap:break-word;}
	</style>
	<!-- <script>
		function generatenew(){
		    document.addItem.itemName.style.visibility="visible";
		    document.addItem.submit1.style.visibility="visible";
		}
	</script> -->
	
 	<header>
		<span class="byline">Manage Dictionary : <c:out value="${dictName}"></c:out></span>
		<!-- 	<a href="/quadriga/auth/dictionaries/addDictionaryItems">Add Dictionary Items</a> -->
		
	</header>
	
	<c:choose>
	      <c:when test="${success=='1'}">
		     <font color="blue"> <c:out value="${successmsg}"></c:out></font>
		      
	      </c:when>
	
	      <c:otherwise>
	     	 <font color="red"><c:out value="${errormsg}"></c:out></font>
	      
	      </c:otherwise>
     </c:choose>
     
	<!-- The following code is not used, 
		 kept for future reference along with generatenew()
		 function declared above.
	
			<form name='addItem' method="post" action="">
				<div id="div"></div>
				<input type="button"  value="Add new Item" onclick="generatenew();">  
				<input type="text" style="visibility:hidden"  name="itemName">
				<input type="submit"  style="visibility:hidden" value="Add" name="submit1">  
			</form>
	  -->
	
	<ul>
		<li>
			<input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries'" value='List Dictionaries'>
		</li>
	</ul>
	
	<div id="myGrid">  
 		<ul>
			<li>
				<form name='searchItem' method="POST" action="${pageContext.servletContext.contextPath}/auth/dictionaries/dictionary/wordSearch">
					
					<font color="black">Word </font>&nbsp;&nbsp; <input type="text"  name="itemName"><br>

		
					<font color="black">Pos </font> 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="posdropdown">
						<option value="nouns">Nouns</option>
						<option value="verb">Verb</option>
						<option value="adverb">Adverb</option>
						<option value="adjective">Adjective</option>
						<option value="other">Other</option>
					</select>
					<br>

					<input type="submit" value="Search">
		 
				</form>
			</li>
		</ul>
    </div>
    

    
	
	
   <!-- <c:choose>
    <c:when test="${not empty dictionaryItemList}">
    <b>Dictionary Items of <c:out value="${dictName}"></c:out>:</b>
    <c:forEach var="dictionaryItem" items="${dictionaryItemList}">
	<li>
	<c:out value="${dictionaryItem.items}"></c:out>   
	</li>
	</c:forEach>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>-->


    <c:choose>
    <c:when test="${not empty dictionaryItemList}">
    
	    <table>
	    <caption align="top" >Dictionary Items of <c:out value="${dictName}"></c:out></caption>
		   <tr>
		    	<th width = "300" height = "20" align="center">Items </th>
		    	<th width = "300" height = "20" align="center"> Select Pos</th>
		    </tr>
		    <c:forEach var="dictionaryItem" items="${dictionaryItemList}">
				<tr>
					<td align="center">
					 <label for="item"><c:out value="${dictionaryItem.items}"></c:out> </label>
					</td>
					<td align="center">
					<form name='wordpower' method="POST" action="/auth/dictionaries/dictionary/wordSearch">
						<select name="posdropdown">
							<option value="nouns">Nouns</option>
							<option value="verb">Verb</option>
							<option value="adverb">Adverb</option>
							<option value="adjective">Adjective</option>
							<option value="other">Other</option>
						</select>
						<input type="submit" name="Search">
					</form>
					</td>
				</tr>	
			</c:forEach>
		</table>
	</c:when>
	
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>
	