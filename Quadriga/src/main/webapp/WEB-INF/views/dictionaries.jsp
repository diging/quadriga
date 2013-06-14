<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script type="text/javascript">
$(document).ready(function() {
	$("ul.pagination1").quickPagination({pageSize:"10"});
	$("ul.pagination2").quickPagination({pageSize:"10"});
	
});
</script>
	<script type="text/javascript" charset="utf8">
			$(document).ready(function() {
				$("input[type=button]").button().click(function(event) {
					event.preventDefault();
				});
			});
		</script>
	

	
		
	<H3>Manage dictionary list for User : <c:out value="${userId}"></c:out></H3>
    				<hr>
    				<br>
    	
    <ul>
		<li>
			<input type=button onClick="location.href='/quadriga/auth/dictionaries/addDictionary'" value='Add Dictionary'>
		</li>
	</ul>		
<!-- 
<div class="example4">	<a href="/quadriga/auth/dictionaries/addDictionary">Add Dictionary</a></div>-->

	
	<c:choose>
    	<c:when test="${adddicsuccess=='1'}">
    		<font color="blue"><c:out value="${adddicsuccessMsg}"></c:out></font>
    	</c:when>
	</c:choose>
	<br/>
	<div  style="float: left; width: 45%; border-radius:5px; border:2px solid #e3daa8; padding: 20px;">
	<h4 align="center"> You own these Dictionaries</h4>
    <c:choose>
    <c:when test="${not empty dictinarylist}">
			<ul class="pagination1">
		    	<c:forEach var="dictionary" items="${dictinarylist}">
		    	
			    	
						<li>
							<a href="dictionaries/${dictionary.id}">
								<c:out value="${dictionary.name}"></c:out>
							</a>

							<br>&nbsp;<c:out value="${dictionary.description}"></c:out>   
						</li>
				</c:forEach>
				</ul>
	</c:when>
	<c:otherwise> No dictionary found</c:otherwise>
	</c:choose>
</div>

<div  style="float: right; width: 45%; border-radius:5px; border:2px solid #e3daa8; padding: 20px;">	

<h4 align="center"> You participate in these Dictionaries</h4>
	<ul class="pagination2">
<li>We have to complete the collaborator part</li>
	</ul> 
	
	</div>