<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
<script>

		$(document).ready(function() {
			
			$("input[type=submit]").button().click(function(event) {
				event.preventDefault();
			});
			
			

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
							data = '<input type="checkbox" class="checkbox" name="bitstreamids" value="'+bitstreamid[1]+'">'+ data;
						}
						else
						{
							data = '<img src="${pageContext.servletContext.contextPath}/resources/txt-layout/images/ajax-loader.gif" width="20" height="20" /> '+data;
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
				
				if(IDs.length < divIDs.length)
					{
					//Code to remove the extra loading div tags
					for (i = 0; i < IDs.length; i++) {
						$("#"+IDs[i]).remove();
					}					
					//$('#buttonToggle').append('Toggle All').button().addClass("check");
					}
				else{
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
						url : '${pageContext.servletContext.contextPath}/auth/workbench/workspace/bitstreamstatus?collectionId=${collectionId}&itemId=${itemId}&bitstreamId='
								+ bitstreamid,
						error : function(jqXHR, textStatus, errorThrown) {
							$('#collection_' + collectionid)
									.html(
											"Server not responding...");
						}
					});
		}
		

		function submitClick()
		{
			if($('input:checkbox').is(':checked'))
				{
					$('#bitstream').submit();
				}
			else
				{
					$.alert("Please select atleast one file", "Oops !!!");
					return;
				}
			
		}
		
		$(document).ready(function(){
		    $('.checkall').click(function(){
		    	if($(this).val() == 'check all')
		    		{
		    			$('input:checkbox').prop("checked", true);
		    			$(this).val("uncheck all");
		    		}
		    	else
		    		{
		            	$('input:checkbox').attr('checked',false);
		    			$(this).val("check all");
		    		}
		    })
		})
	</script>
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}"  style="text-decoration: underline;">Workspace</a> »
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/communities" style="text-decoration: underline;">Communities</a> »
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/community/${communityId}"  style="text-decoration: underline;"><c:out value="${communityName}"></c:out></a> »
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/community/collection/${collectionId}"  style="text-decoration: underline;"><c:out value="${collectionName}"></c:out></a> »
				<c:out value="${itemName}"></c:out><br />			
			
		<c:choose>
			<c:when test="${not empty bitList}">
			<form id="bitstream" method="POST" action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspaceId}/addbitstreams?communityid=${communityId}&collectionid=${collectionId}&itemid=${itemId}">
			<span class="byline">Select files to add to workspace.</span>
				<c:forEach var="bitstream" items="${bitList}">
				<div id='bitstream_<c:out value="${bitstream.id}" />'><c:choose><c:when test="${not empty bitstream.name}"><input type="checkbox" class="checkbox" name="bitstreamids" value="${bitstream.id}">${bitstream.name}</c:when><c:otherwise><img src="${pageContext.servletContext.contextPath}/resources/txt-layout/images/ajax-loader.gif" width="20" height="20" /> Loading...</c:otherwise></c:choose></div>
				</c:forEach><br>
				<input type="submit" onclick="submitClick();" value="Add to Workspace" />
				<input type="submit" class="checkall" value="check all" />
				</form>
			</c:when>
			<c:otherwise>
					<span class="byline">No BitStreams found in - 
						${itemName}</span>
					<br>
				</c:otherwise>
		</c:choose>
</article>

<!-- /Content -->