<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>
	<h2>Ups... sorry, something went wrong.</h2>
	<span class="byline">Access Error</span>
</header>

<section>
	<c:choose>
		<c:when test="${not empty ex_message}">
		<c:out value="${ex_message}"></c:out></c:when>
		<c:otherwise>
	Sorry, You don't have permission to access this resource.
	</c:otherwise>
	</c:choose>
</section>