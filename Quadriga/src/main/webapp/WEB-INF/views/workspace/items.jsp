<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}"  style="text-decoration: underline;">Workspace</a> »
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/communities" style="text-decoration: underline;">Communities</a> »
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/community/${communityId}"  style="text-decoration: underline;"><c:out value="${communityName}"></c:out></a> »
				<c:out value="${collectionName}"></c:out>
		<c:choose>
			<c:when test="${not empty itemList}">
				<span class="byline">Select an item to browse its
					files.</span>
				<c:forEach var="item" items="${itemList}">
					<span style="font-weight: bold"><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/community/collection/item?collectionId=${collectionId}&itemId=${item.id}" style="color:#707070">${item.name}</a></span>
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