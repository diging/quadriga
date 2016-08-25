<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">

<script>
		$(document).ready(function() {

			/**
			* Function to load any collection name which was not loaded in the previous load.
			* Author: Ram Kumar Kumaresan
			*/
			function makeAjaxCall() {
				var divIDs = $("div[id^='collection']") // find divs with ID attribute
				.map(function() {
					return this.id;
				}) // convert to set of IDs
				.get();
				
				var i = 0;
				var IDs = [];
				for (i = 0; i < divIDs.length; i++) {
					if ($('#' + divIDs[i]).text() == ' Loading...') {
						IDs.push(divIDs[i]);
					}
				}			
				
				
				
				$.each(IDs, function() {
					var collectionid = this.split("collection_");
					var ajaxCallback = getCollectionName(collectionid[1]);

					//Do this once the data is available
					ajaxCallback.success(function(data) {
						//Load the new text in the corresponding div tag
						if(data != 'Loading...'){
							data = '<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/community/collection/'+collectionid[1]+'" style="color:#707070">'+data+'</a>';
						}
						else
						{
							data = '<img src="${pageContext.servletContext.contextPath}/resources/txt-layout/images/ajax-loader.gif" width="20" height="20" /> '+data;
						}
						$('#collection_' + collectionid[1]).html(data);
					});//End of ajax callback
				});//End of for-each for divs

			}//End of function

			makeAjaxCall();

			/**
			* Function to check if there is any collection name yet to be loaded.
			* If yes, then it will invoke the makeAjaxCall() after a wait period of 5 seconds.
			* Author: Ram Kumar Kumaresan
			*/
			function checkDiv() {
				var divIDs = $("div[id^='collection']") // find divs with ID attribute
				.map(function() {
					return this.id;
				}) // convert to set of IDs
				.get();

				var i = 0
				var IDs = [];
				for (i = 0; i < divIDs.length; i++) {
					if ($('#' + divIDs[i]).text() == ' Loading...') {
						IDs.push(divIDs[i]);
					}
				}
				if (IDs.length > 0) {
					setTimeout(makeAjaxCall, 5000);
					setTimeout(checkDiv, 7000);
				}
			}
			setTimeout(checkDiv, 1000);
		});

		/*
		* Function used to make an ajax call to the controller, inorder to get the collection name
		*/
		function getCollectionName(collectionid) {
			return $.ajax({
						type : 'GET',
						url : '${pageContext.servletContext.contextPath}/auth/workbench/workspace/collectionstatus/'+collectionid,
						error : function(jqXHR, textStatus, errorThrown) {
							$('#collection_' + collectionid)
									.html(
											"Server not responding...");
						}
					});
		}
	</script>
	<c:choose>
		<c:when test="${not empty communityName }">
		<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}"  style="text-decoration: underline;">Workspace</a> »
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/communities"  style="text-decoration: underline;">Communities</a> »
				<c:out value="${communityName}"></c:out>
			<c:choose>
				<c:when test="${not empty collectionList}">
					<span class="byline">Select a collection to browse its items</span>
					<c:forEach var="collection" items="${collectionList}">
						<div id='collection_<c:out value="${collection.id}" />'><c:choose><c:when test="${not empty collection.name}"><a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/community/collection/${collection.id}" style="color:#707070">${collection.name}</a></c:when><c:otherwise><img src="${pageContext.servletContext.contextPath}/resources/txt-layout/images/ajax-loader.gif" width="20" height="20" /> Loading...</c:otherwise></c:choose></div>
					</c:forEach>
				</c:when>

				<c:otherwise>
					<span class="byline">No Collections found in the
						${communityName} community</span>
					<br>
				</c:otherwise>
			</c:choose>
		</c:when>

	</c:choose>

</article>

<!-- /Content -->