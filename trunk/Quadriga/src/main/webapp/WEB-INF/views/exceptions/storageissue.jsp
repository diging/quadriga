<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<header>
	<h2>Ups... sorry, something went wrong.</h2>
	<span class="byline">Database trouble</span>
</header>

<section>
	<c:choose>
		<c:when test="${not empty ex_message}">
		<c:out value="${ex_message}"></c:out></c:when>
		<c:otherwise>
	Sorry, looks like our database is having hiccups. Try again later.
	</c:otherwise>
	</c:choose>
</section>