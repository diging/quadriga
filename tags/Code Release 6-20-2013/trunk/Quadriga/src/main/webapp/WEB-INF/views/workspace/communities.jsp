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

				var i = 0
				var IDs = [];
				for (i = 0; i < divIDs.length; i++) {
					if ($('#' + divIDs[i]).text() == 'Loading...') {
						IDs.push(divIDs[i]);
					}
				}

				$.each(IDs, function() {
					var collectionid = this.split("collection_");
					var ajaxCallback = getCollectionName(collectionid[1]);

					//Do this once the data is available
					ajaxCallback.success(function(data) {
						//Load the new text in the corresponding div tag
						$('#collection_' + collectionid[1]).html(data);
						$('#collection_' + collectionid[1]).style('color', 'red');
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
					if ($('#' + divIDs[i]).text() == 'Loading...') {
						IDs.push(divIDs[i]);
					}
				}
				if (IDs.length > 0) {
					setTimeout(makeAjaxCall, 5000);
					setTimeout(checkDiv, 7000);
				}
				else
				{					
					$('#notification').html("(The server is all warmed up to you...)");
				}
			}
			setTimeout(checkDiv, 1000);
		});

		/*
		* Function used to make an ajax call to the controller, inorder to get the collection name
		*/
		function getCollectionName(collectionid) {
			return $
					.ajax({
						type : 'GET',
						url : '/quadriga/auth/workbench/workspace/collectionstatus/'
								+ collectionid,
						error : function(jqXHR, textStatus, errorThrown) {
							$('#collection_' + collectionid)
									.html(
											"Server not responding...");
						}
					});
		}
	</script>

	<div id="result">
		<c:choose>
			<c:when test="${not empty communityList}">
				<h2>Communities in HPS Repository</h2>
				<span class="byline">Select a community to browse its
					collections.</span><span style="font-weight: bold"><div id="notification">(The server will take time to warm up during the first load...)</div></span>
				<c:forEach var="community" items="${communityList}">
					<span style="font-weight: bold"><a
						href='<c:out value="/quadriga/auth/workspace/community/${community.id}" />'>${community.name}</a></span>
					<br />
					<c:forEach var="collectionId" items="${community.collectionIds}">
						<span style="float: left;margin-left: 50px;"><div id='collection_<c:out value="${collectionId}"></c:out>'>Loading...</div></span>
						<!-- 
						<c:forEach var="collection" items="${community.collections}">
							<c:if test="${communityId == collection.id}">
								<div id='collection_<c:out value="${collection.id}"></c:out>'>${collection.name}</div>
							</c:if>
						</c:forEach>
						 -->
						 <br />
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