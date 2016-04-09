<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>


<script>
	$(document).ready(function() {
		$('#example').DataTable();
	});	 
	function handle(e){
	    if(e.keyCode === 13)
	        document.getElementById("sites").submit();
	    
	    return false;
	}
</script>

<!-- Content -->
<form name='searchsites' id="sites" action="${pageContext.servletContext.contextPath}/sites/searchTerm" method='POST'>
	<input type="text" class="form-control"
		placeholder="Search Public Sites Repository" name="searchTerm"
		tabindex="1" onkeypress="handle(event)" value="${searchTerm}" autocapitalize="off">

	</br>
	<c:forEach var="sitesList" items="${projectList}">
		<table id="example" class="table table-striped table-bordered"
			cellspacing="0" width="100%">
			<tbody>
				<tr>
					<td style="font-size:large;font-weight: bolder;">
							<a
								href="${pageContext.servletContext.contextPath}/sites/${sitesList.unixName}">${sitesList.projectName}</a>
						</td>
				</tr>
				<tr>
					<td><span class="comment more"> <c:out
								value="${sitesList.description}"></c:out></span></td>
				</tr>

			</tbody>
		</table>
	</c:forEach>
</form>