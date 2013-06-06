<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->

<article class="is-page-content">

	<c:choose>
      <c:when test="${success=='1'}">
      <span class="byline">Dictionary created successfully.</span>
      <br />
      </c:when>

      <c:otherwise>
      <span class="byline"><c:out value="${errormsg}"></c:out></span>
      <br />
      </c:otherwise>
</c:choose>

</article>

<!-- /Content -->