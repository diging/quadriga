<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
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