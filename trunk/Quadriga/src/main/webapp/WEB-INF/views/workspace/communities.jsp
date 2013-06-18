<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
	<script>
		$(document).ready(function() {
			$("input[type=submit]").button();
		});

		setTimeout('window.location.href=window.location.href;', 5000);

		function getCommunitiesAndCollections() {

			$
					.ajax({
						type : 'GET',
						url : '/quadriga/auth/workbench/workspace/communities-collections',
						success : function(data) {
							$('#result').html(data);
						},
						error : function(jqXHR, textStatus, errorThrown) {
							$('#result').html(
									"Sorry, Unable to connect to Dspace");
						}

					});

		}
	</script>
	<input type="submit" value="Refresh"
		onclick="location.href = '/quadriga/auth/workbench/workspace/communities'" /><br>

	<div id="result">
		<c:choose>
			<c:when test="${not empty communityList}">
				<pre>
				<h2>Communities in HPS Repository</h2>			</pre>
				<span class="byline">Select a community to browse its
					collections.</span>
				<c:forEach var="community" items="${communityList}">
					<span style="font-weight: bold"><a
						href='<c:out value="/quadriga/auth/workspace/community/${community.id}" />'>${community.name}</a></span>
					<br />
					<c:forEach var="communityId" items="${community.collectionIds}">
						<c:forEach var="collection" items="${community.collections}">
							<c:if test="${communityId == collection.id}">
								     <div id="">${collection.name}</div>
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:forEach>
			</c:when>

			<c:otherwise>
				<pre>
				<h2>No Communities in HPS Repository</h2>
			</pre>
				<br>
			</c:otherwise>
		</c:choose>

	</div>

</article>

<!-- /Content -->