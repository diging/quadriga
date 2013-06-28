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
				var divIDs = $("div[id^='bitstream']") // find divs with ID attribute
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
			
				
				
				$.each(IDs, function() {
					var bitstreamid = this.split("bitstream_");
					var ajaxCallback = getBitStreamName(bitstreamid[1]);

					//Do this once the data is available
					ajaxCallback.success(function(data) {
						//Load the new text in the corresponding div tag
						if(data != 'Loading...'){
							//data = '<a href="/quadriga/auth/workbench/workspace/community/collection/'+bitstream[1]+'" style="color:#707070">'+data+'</a>';
							data = data;
						}
						else
						{
							data = '<img src="/quadriga/resources/txt-layout/images/ajax-loader.gif" width="20" height="20" /> '+data;
						}
						$('#bitstream_' + bitstreamid[1]).html(data);						
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
				var divIDs = $("div[id^='bitstream']") // find divs with ID attribute
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
		function getBitStreamName(bitstreamid) {
			return $
					.ajax({
						type : 'GET',
						url : '/quadriga/auth/workbench/workspace/bitstreamstatus?collectionId=${collectionId}&itemId=${itemId}&bitstreamId='
								+ bitstreamid,
						error : function(jqXHR, textStatus, errorThrown) {
							$('#collection_' + collectionid)
									.html(
											"Server not responding...");
						}
					});
		}
	</script>
	
		<h3>
				<a href="/quadriga/auth/workbench/workspace/communities">Home</a> »
				<a href="/quadriga/auth/workbench/workspace/community/${communityId}"><c:out value="${communityName}"></c:out></a> »
				<a href="/quadriga/auth/workbench/workspace/community/collection/${collectionId}"><c:out value="${collectionName}"></c:out></a> »
				<c:out value="${itemName}"></c:out>
			</h3>
		<c:choose>
			<c:when test="${not empty bitList}">
				<c:forEach var="bitstream" items="${bitList}">
				<span style="float: left; margin-left: 50px; font-weight: bold"><div id='bitstream_<c:out value="${bitstream.id}" />'><c:choose><c:when test="${not empty bitstream.name}">${bitstream.name}</c:when><c:otherwise><img src="/quadriga/resources/txt-layout/images/ajax-loader.gif" width="20" height="20" /> Loading...</c:otherwise></c:choose></div></span>
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