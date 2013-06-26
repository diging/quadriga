<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
	<c:choose>
		<c:when test="${not empty collectionList}">
			<span class="byline">Collections in the <u>${communityTitle}</u> community</span>
				<c:forEach var="collection" items="${collectionList}">
				<span style="font-weight:bold">${collection.title}</span><br />
				${collection.description}
				<br /><br />
				</c:forEach>
		</c:when>

		<c:otherwise>
			<span class="byline">No Collections found in the ${communityTitle} community</span>
			<br>
		</c:otherwise>
	</c:choose>


</article>

<!-- /Content -->