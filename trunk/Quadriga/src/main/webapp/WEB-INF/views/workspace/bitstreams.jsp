<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
		<h3>
				<a href="/quadriga/auth/workbench/workspace/communities">Home</a> »
				<a href="/quadriga/auth/workbench/workspace/community/${communityId}"><c:out value="${communityName}"></c:out></a> »
				<a href="/quadriga/auth/workbench/workspace/community/collection/${collectionId}"><c:out value="${collectionName}"></c:out></a> »
				<c:out value="${itemName}"></c:out>
			</h3>
		<c:choose>
			<c:when test="${not empty bitList}">
				<c:forEach var="bitstream" items="${bitList}">
				<span style="float: left; margin-left: 50px; font-weight: bold"><c:choose><c:when test="${not empty bitstream.name}">${bitstream.name}</c:when><c:otherwise>Loading...</c:otherwise></c:choose></span>
				<br />
				</c:forEach>
			</c:when>
			<c:otherwise>
					<span class="byline">No BitStreams found in - 
						${itemName}</span>
					<br>
				</c:otherwise>
		</c:choose>
</article>

<!-- /Content -->