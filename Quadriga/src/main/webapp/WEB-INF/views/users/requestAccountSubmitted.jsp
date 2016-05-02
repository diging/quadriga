<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<header>
<c:choose>
      <c:when test="${requestStatus=='1'}"><pre><h2>${username}, Your account request has been submitted...</h2></pre>
	<span class="byline">You will be notified when your request was processed.</span>
      </c:when>

      <c:otherwise><pre><h2>${username}, You already have an open request...</h2></pre>
	<span class="byline">You will be notified when your request was processed.</span>
      <br />
      </c:otherwise>
</c:choose>

	
</header>
