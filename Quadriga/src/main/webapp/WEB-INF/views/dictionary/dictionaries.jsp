
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<!--  
	Author Lohith Dwaraka  
	Used to list the dictionaries
	and add dictionary	
-->
<script type="text/javascript">
$(document).ready(function() {
	$("ul.pagination1").quickPagination({
		pageSize : "5"
	});
});
	$(function() 
			{
				    $( "#tabs" ).tabs();
			});
</script>
<style>
   .tabs
   {
	font-size: 80%;
   }
</style>
<header>
	<h2>Dictionaries</h2>
	<span class="byline">Manage your dictionaries here.</span>
</header>

<c:choose>
	<c:when test="${adddicsuccess=='1'}">
		<font color="blue"><spring:message code="add.dictionary.success" /></font>
	</c:when>
	<c:when test="${deldicitonarysuccess=='1'}">
		<font color="blue"><spring:message code="delete.dictionary.success" /></font>
	</c:when>
	<c:when test="${deldicitonarysuccess=='0'}">
		<font color="red"><spring:message code="delete.dictionary.fail" /></font>
	</c:when>
	<c:when test="${dictionaryaccesserror=='0'}">
		<font color="red"><spring:message code="delete.dictionary.access.fail" /></font>
	</c:when>
</c:choose>
<br />

<div>
	<c:if test="${not empty dictinarylist}">
		You own these Dictionaries:
			<ul>
			<c:forEach var="dictionary" items="${dictinarylist}">
				<li class="dt with-icon"><a
					href="${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionary.dictionaryId}">
						<c:out value="${dictionary.dictionaryName}"></c:out>
				</a> <br> <c:out value="${dictionary.description}"></c:out></li>
			</c:forEach>
		</ul>
	</c:if>
	<c:if test="${empty dictinarylist}"> You don't own any dictionaries.
	</c:if>
</div>

<c:if test="${not empty dictionaryCollabList}">
	You participate in these Dictionaries:
	<ul>
		<c:forEach var="dictionary" items="${dictionaryCollabList}">
			<li class="dt with-icon"><a
				href="${pageContext.servletContext.contextPath}/auth/dictionaries/collab/${dictionary.dictionaryId}">
					<c:out value="${dictionary.dictionaryName}"></c:out>
			</a> <br> <c:out value="${dictionary.description}"></c:out></li>
		</c:forEach>
	</ul>
</c:if>

<c:if test="${empty dictionaryCollabList}">
	<spring:message code="empty.dictionary" />
</c:if>

</div>
</div>