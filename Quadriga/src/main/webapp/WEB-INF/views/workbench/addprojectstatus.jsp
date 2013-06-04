<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->

<article class="is-page-content">

	<c:choose>
      <c:when test="${success=='1'}">
      <span class="byline">Project created successfully.</span>
      <br />
      </c:when>

      <c:otherwise>
      <span class="byline">Error in adding the project.</span>
      <br />
      </c:otherwise>
</c:choose>

</article>

<!-- /Content -->