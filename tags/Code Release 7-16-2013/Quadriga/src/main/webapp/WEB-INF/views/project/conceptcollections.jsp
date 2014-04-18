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
		$("input[type=submit]").button().click(function(event) {

		});
	});
</script>

<input type=button
	onClick="location.href='${pageContext.servletContext.contextPath}/auth/workbench/${projectid}'"
	value='Back to Project'>
<br>
<br>
<c:choose>
	<c:when test="${success=='1'}">
		<font color="blue"><spring:message
				code="project.CC.add.success" /></font>
	</c:when>
	<c:when test="${success=='0'}">
		<font color="red"><spring:message
				code="project.CC.add.fail" /></font>
	</c:when>
	<c:when test="${deletesuccess=='1'}">
		<font color="blue"><spring:message
				code="project.CC.delete.success" /></font>
	</c:when>
	<c:when test="${deletesuccess=='0'}">
		<font color="red"><spring:message
				code="project.CC.delete.fail" /></font>
	</c:when>
</c:choose>

<div class="container">
	<c:choose>
		<c:when test="${not empty conceptCollectionList}">
				
				<ul class="style2 pagination1">
					<c:forEach var="conceptCollection" items="${conceptCollectionList}">
						<li><a
							href="${pageContext.servletContext.contextPath}/auth/conceptcollections/${conceptCollection.id}"><c:out
									value="${conceptCollection.name}"></c:out></a> <br> <c:out
								value="${conceptCollection.description}"></c:out></li>
					</c:forEach>
				</ul>
		</c:when>

		<c:otherwise>

			<br>
			<spring:message code="empty.CC" />
		</c:otherwise>
	</c:choose>
</div>