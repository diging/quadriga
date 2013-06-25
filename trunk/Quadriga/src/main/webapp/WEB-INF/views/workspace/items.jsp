<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
		<h3>
				<a href="/quadriga/auth/workbench/workspace/communities">Home</a> »
				<a href="/quadriga/auth/workbench/workspace/community/${communityId}"><c:out value="${communityName}"></c:out></a> »
				<c:out value="${collectionName}"></c:out>
			</h3>
		<c:choose>
			<c:when test="${not empty itemList}">
				<span class="byline">Select an item to browse its
					files.</span>
				<c:forEach var="item" items="${itemList}">
					<span style="font-weight: bold">${item.id}: ${item.name}</span>
					<br />
				</c:forEach>
			</c:when>

			<c:otherwise>
				<span class="byline">No items found in the
						${collectionName} collection</span>
				<br>
			</c:otherwise>
		</c:choose>

</article>

<!-- /Content -->