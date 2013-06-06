<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
	<c:choose>
		<c:when test="${not empty communityList}">
			<pre>
				<h2>Communities in HPS Repository</h2>
			</pre>
			<span class="byline">Select a community to browse its
				collections.</span>
				<c:forEach var="community" items="${communityList}">
				<span style="font-weight:bold"><a href='<c:out value="/quadriga/auth/workspace/community/${community.title}" />'>${community.title}</a></span><br />
				<c:forEach var="collection" items="${community.collections}">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:out value="${collection.title}"></c:out><br />
				</c:forEach>
				<br />
				</c:forEach>
		</c:when>

		<c:otherwise>
			<pre>
				<h2>No Communities in HPS Repository</h2>
			</pre>
			<br>
		</c:otherwise>
	</c:choose>


</article>

<!-- /Content -->