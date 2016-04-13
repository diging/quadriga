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
	function handle(e) {
		if (e.keyCode === 13)
			document.getElementById("sites").submit();

		return false;
	}
	function callMethod() {
		document.getElementById("sites").submit();
	}
</script>

<!-- Content -->
<form name='searchsites' id="sites"
	action="${pageContext.servletContext.contextPath}/sites/searchTerm"
	method='POST'>
	<table width="100%">
		<tr>
			<td style="width: 85%"><input type="text" class="form-control"
				placeholder="Search Public Sites Repository" name="searchTerm"
				tabindex="1" onkeypress="handle(event)" value="${searchTerm}"
				autocapitalize="off"></td>
			<td style="width: 2%"></td>
			<td style="width: 25%"><input type="submit" value="Search"
				style="width: 100%; height: 115%; font-weight: bold;" tabindex="2"
				onclick="callMethod()"></td>
		</tr>
	</table>
	</br>
	<c:forEach var="sitesList" items="${projectList}">
		<div
			style="padding-top: 20px; margin-top: 20px; margin-bottom: 10px; border-top: 1px solid #eee;">
			<p>
				<a style="color: #4078c0; text-decoration: none;  font-weight: bold; line-height: 1.2;"
					href="${pageContext.servletContext.contextPath}/sites/${sitesList.unixName}">${sitesList.projectName}</a>
				<br> <span
					style="font-size: 12px !important; color: #767676 !important"><c:out
						value="Lead by ${sitesList.owner.name}"></c:out></span>
			</p>
			<div>
				<span class="comment more"> <c:out
						value="${sitesList.description}"></c:out></span>
			</div>
		</div>
	</c:forEach>
</form>