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
		


$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth": false
	});
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
			
			$(document).ready(function() {
				$("input[type=submit]").button().click(function(event) {
					
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
  

function frmSubmitDelete() {
form.target = this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}';
form.submit();
}


function frmSubmitUpdate() {
	$.alert(hello);
form.target = this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/updateDictionaryItems/${dictionaryid}';
form.submit();
}

  
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
	


	
	<ul>
		<li>
			<input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries'" value='List Dictionaries'>
		</li>
	</ul>
	

    	
			<c:choose>
			      <c:when test="${delsuccess=='1'}">
				     <font color="blue"> <c:out value="${delsuccessmsg}"></c:out></font>
				      
			      </c:when>
			
			      <c:otherwise>
			     	 <font color="red"><c:out value="${delerrormsg}"></c:out></font>
			      
			      </c:otherwise>
		     </c:choose>

	
	
	<H3>Dictionary Items for <c:out value="${dictName}"></c:out></H3>
    <hr>
    <ul>
    	
		<li>
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
    
    <form method="POST"  >

	<input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/addDictionaryItems/${dictionaryid}'" value='Add Items'>
		
    <input type="submit" value="Delete Items" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/deleteDictionaryItems/${dictionaryid}'"/>
    
    <input type="submit" value="Update Items" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/updateDictionaryItems/${dictionaryid}'"/>
    <br/><br/>
    <table style="width: 100%" cellpadding="0" cellspacing="0" border="0"
			class="display dataTable" >
	<!-- <table  class="dataTable" id="pagination1"> -->
		<thead>
		   <tr>
		    	<th>Select</th>
		    	<th>Items </th>
		    	<th> ID</th>
		    	<th> Pos</th>
		    	<th> Action </th>
		    </tr>
		</thead>
		
		<tbody>
			<c:forEach var="dictionaryItem" items="${dictionaryItemList}">	
			<tr>
				<td> <input type="checkbox"  class="chk" name = "selected" value='<c:out value="${dictionaryItem.id}"></c:out>' /></td>
				<td width="25%"  align="center">
				<input name="items" type="hidden" value="<c:out value="${dictionaryItem.items}"></c:out>"/>
				 <c:out value="${dictionaryItem.items}"></c:out>
				</td>
				<td width="25%"  align="justify">
					<c:out value="${dictionaryItem.id}"></c:out> 
				</td>
				<td width="25%"  align="center">
					<c:out value="${dictionaryItem.pos}"></c:out> 
				</td>
				<!--<td align="center">
					<c:out value="${dictionaryItem.vocabulary}"></c:out> 
				</td>
				<td align="center">
					<c:out value="${dictionaryItem.description}"></c:out> 
				</td>-->
				<td align="center">
					
					<!--<input type="button" onclick="deleteItem(this.id);" value="Delete" id="<c:out value="${dictionaryItem.items}"></c:out>"/>-->
					<!--  <input type=button onClick="location.href='${pageContext.servletContext.contextPath}/auth/dictionaries/updateDictionaryItems/${dictionaryid}?item=<c:out value="${dictionaryItem.items}"></c:out>&pos=<c:out value="${dictionaryItem.pos}"></c:out>'" value='Update'>
					-->
					 
				</td>
				
			</tr>
			</c:forEach>	
		</tbody>
			
	</table>
	</form>
	</c:when>

	<c:otherwise> No dictionary items found</c:otherwise>
	</c:choose>
	</div>