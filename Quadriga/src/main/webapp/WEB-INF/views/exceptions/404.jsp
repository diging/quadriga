<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>
	<h2>Ups... sorry, something went wrong.</h2>
	<span class="byline">Page Not Found</span>
</header>

<section>
	<c:choose>
		<c:when test="${not empty ex_message}">
		<c:out value="${ex_message}"></c:out></c:when>
		<c:otherwise>
	This is not the web page you are looking for.
	</c:otherwise>
	</c:choose>
</section>