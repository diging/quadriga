<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf8">
	
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
<h2>Workspace: ${workspacedetails.name}</h2>
<hr />
<input type=button
	onClick="location.href='${pageContext.servletContext.contextPath}/auth/workbench/workspace/workspacedetails/${workspaceid}'"
	value='Back to Workspace'>
<br>
<br>
<c:choose>
	<c:when test="${success=='1'}">
		<font color="blue"><spring:message
				code="workspace.dictionary.add.success" /></font>
	</c:when>
	<c:when test="${success=='0'}">
		<font color="red"><spring:message
				code="workspace.dictionary.add.fail" /></font>
	</c:when>
	<c:when test="${deletesuccess=='1'}">
		<font color="blue"><spring:message
				code="workspace.dictionary.delete.success" /></font>
	</c:when>
	<c:when test="${deletesuccess=='0'}">
		<font color="red"><spring:message
				code="workspace.dictionary.delete.fail" /></font>
	</c:when>
</c:choose>

<div class="container">
	<c:choose>
		<c:when test="${not empty dicitonaryList}">
				
				<ul class="style2 pagination1">
					<c:forEach var="dictionary" items="${dicitonaryList}">
						<li><a
							href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionary.id}"><c:out
									value="${dictionary.name}"></c:out></a> <br> <c:out
								value="${dictionary.description}"></c:out></li>
					</c:forEach>
				</ul>
		</c:when>

		<c:otherwise>

			<br>
			<spring:message code="empty.dictionary" />
		</c:otherwise>
	</c:choose>
</div>