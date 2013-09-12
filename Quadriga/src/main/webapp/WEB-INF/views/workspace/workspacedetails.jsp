<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>



<c:choose>
	<c:when test="${not empty workspacedetails.bitstreams}">
	<script>
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
	
	$(document).ready(function() {

		function loadCollectionName() {
			var divIDs = $("div[class^='collection']") // find divs with ID attribute
			.map(function() {
				return this.id;
			}) // convert to set of IDs
			.get();
			
			var i = 0;
			var IDs = [];
			for (i = 0; i < divIDs.length; i++) {
				if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_collection" />') {
					IDs.push(divIDs[i]);
				}
			}	
			
			
			$.each($.unique(IDs), function() {
				var collectionid = this.split("collection_");
				var ajaxCallback = getCollectionName(collectionid[1]);
				
				
				//Do this once the data is available
				ajaxCallback.success(function(data) {
					//Load the new text in the corresponding div tag
					if(data != 'Loading...'){
						data = '<font size="1">'+data+'</font>';
						$('.collection_' + collectionid[1]).html(data);
						
					}
				});//End of ajax callback
			});
		}
		
		loadCollectionName();
		
		/**
		* Function to check if there is any collection name yet to be loaded.
		* If yes, then it will invoke the loadCollectionName() after a wait period of 5 seconds.
		* Author: Ram Kumar Kumaresan
		*/
		function checkCollectionDiv() {
			var divIDs = $("div[id^='collection']") // find divs with ID attribute
			.map(function() {
				return this.id;
			}) // convert to set of IDs
			.get();

			var i = 0;
			var IDs = [];
			for (i = 0; i < divIDs.length; i++) {
				if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_collection" />') {
					IDs.push(divIDs[i]);
				}
			}
			if (IDs.length > 0) {
				setTimeout(loadCollectionName, 5000);
				setTimeout(checkCollectionDiv, 7000);
			}
		}
		setTimeout(checkCollectionDiv, 1000);
		
		
		
		function loadItemName() {
			var divIDs = $("div[class^='item']") // find divs with ID attribute
			.map(function() {
				return this.id;
			}) // convert to set of IDs
			.get();
			
			var i = 0;
			var IDs = [];
			for (i = 0; i < divIDs.length; i++) {
				if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_item" />') {
					IDs.push(divIDs[i]);
				}
			}	
			
			
			$.each($.unique(IDs), function() {
				var collectionid = this.split("_");
				var ajaxCallback = getItemName(collectionid[1],collectionid[2]);
				
				
				//Do this once the data is available
				ajaxCallback.success(function(data) {
					//Load the new text in the corresponding div tag
					if(data != 'Loading...'){
						data = '<font size="1">'+data+'</font>';
						$('.item_' + collectionid[1]+ '_' + collectionid[2]).html(data);
						
					}
				});//End of ajax callback
			});
		}
		
		loadItemName();
		
		/**
		* Function to check if there is any item name yet to be loaded.
		* If yes, then it will invoke the loadItemName() after a wait period of 5 seconds.
		* Author: Ram Kumar Kumaresan
		*/
		function checkItemDiv() {
			var divIDs = $("div[id^='item']") // find divs with ID attribute
			.map(function() {
				return this.id;
			}) // convert to set of IDs
			.get();

			var i = 0;
			var IDs = [];
			for (i = 0; i < divIDs.length; i++) {
				if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_item" />') {
					IDs.push(divIDs[i]);
				}
			}
			if (IDs.length > 0) {
				setTimeout(loadItemName, 5000);
				setTimeout(checkItemDiv, 7000);
			}
		}
		setTimeout(checkItemDiv, 1000);
		
		function loadBitStreamName() {
			var divIDs = $("div[class^='bitstream']") // find divs with ID attribute
			.map(function() {
				return this.id;
			}) // convert to set of IDs
			.get();
			
			var i = 0;
			var IDs = [];
			for (i = 0; i < divIDs.length; i++) {
				if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_bitstream" />') {
					IDs.push(divIDs[i]);
				}
			}	
			
			$.each($.unique(IDs), function() {
				var collectionid = this.split("_");
				var ajaxCallback = getBitStreamName(collectionid[1],collectionid[2],collectionid[3]);
				
				
				//Do this once the data is available
				ajaxCallback.success(function(data) {
					//Load the new text in the corresponding div tag
					if(data != 'Loading...'){
						if(data != 'No Access to File'){
							$('.checkbox_' + collectionid[3]).html('<input type="checkbox" class="checkbox" name="bitstreamids" value="'+collectionid[3]+'" />');
						}
						data = '<font size="1">'+data+'</font>';
						$('.bitstream_' + collectionid[1] + '_' + collectionid[2] + '_' + collectionid[3]).html(data);
						
					}
				});//End of ajax callback
			});
		}
		
		loadBitStreamName();
		
		/**
		* Function to check if there is any item name yet to be loaded.
		* If yes, then it will invoke the loadItemName() after a wait period of 5 seconds.
		* Author: Ram Kumar Kumaresan
		*/
		function checkBitStreamDiv() {
			var divIDs = $("div[id^='bitstream']") // find divs with ID attribute
			.map(function() {
				return this.id;
			}) // convert to set of IDs
			.get();

			var i = 0;
			var IDs = [];
			for (i = 0; i < divIDs.length; i++) {
				if ($('#' + divIDs[i]).text() == '<spring:message code="dspace.access_check_bitstream" />') {
					IDs.push(divIDs[i]);
				}
			}
			if (IDs.length > 0) {
				setTimeout(loadBitStreamName, 5000);
				setTimeout(checkBitStreamDiv, 7000);
			}
		}
		setTimeout(checkBitStreamDiv, 1000);
	});
	
	/*
	* Function used to make an ajax call to the controller, inorder to get the collection name
	*/
	function getCollectionName(collectionid) {
		return $
				.ajax({
					type : 'GET',
					url : '${pageContext.servletContext.contextPath}/auth/workbench/workspace/collectionstatus/'
							+ collectionid,
					error : function(jqXHR, textStatus, errorThrown) {
						$('#collection_' + collectionid)
								.html(
										"Server not responding...");
					}
				});
	}
	
	/*
	* Function used to make an ajax call to the controller, inorder to get the item name
	*/
	function getItemName(collectionid,itemid) {
		return $
				.ajax({
					type : 'GET',
					url : '${pageContext.servletContext.contextPath}/auth/workbench/workspace/itemstatus/'
							+ collectionid+'/'+itemid,
					error : function(jqXHR, textStatus, errorThrown) {
						$('#item_' + collectionid + "_" + itemid)
								.html(
										"Server not responding...");
					}
				});
	}
	
	/*
	* Function used to make an ajax call to the controller, inorder to get the bitstream name
	*/
	function getBitStreamName(collectionid,itemid,bitstreamid) {
		return $
				.ajax({
					type : 'GET',
					url : '${pageContext.servletContext.contextPath}/auth/workbench/workspace/bitstreamaccessstatus?collectionid='+collectionid+'&itemid='+itemid+'&bitstreamid='+bitstreamid,
					error : function(jqXHR, textStatus, errorThrown) {
						$('#bitstream_' + collectionid + "_" + itemid + "_" + bitstreamid)
								.html(
										"Server not responding...");
					}
				});
	}
	</script>
	</c:when>
</c:choose>
<script>
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			return;
		});
		
		$("input[type=submit]").button().click(function(event) {
			event.preventDefault();
		});
		
		$('#dspacePublicAccess').click(function publicAccess(){
			if ($(this).is(':checked'))
		    {
				$("#username").val('');
				$("#password").val('');
				$("#username").attr("disabled", "disabled");				
				$("#username").attr("placeholder","Input Disabled");
				$("#password").attr("disabled", "disabled");
				$("#password").attr("placeholder","Input Disabled");
		    }
		    else
		    {
		    	$("#username").removeAttr("disabled");
		    	$("#username").attr("placeholder","Username");
		    	$("#password").removeAttr("disabled");
		    	$("#password").attr("placeholder","Password");
		    }
		});		
	});
	
	$(document).ready(function() {
		activeTable = $('.dataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false
		});
	});
</script>


<table style="width:100%">
  <tr>
    <!-- Display workspace details -->
    <td style="width:90%">
    <h2>Workspace: ${workspacedetails.name}</h2>
<div>${workspacedetails.description}</div>
<hr>
<div class="user">Owned by: ${workspacedetails.owner.name}</div>
<hr>

	<c:choose>
		<c:when test="${AssignEditorSuccess=='1'}">
			<font color="blue"> <spring:message
					code="workspace.assign.owner.editor.success" /></font>

		</c:when>
		<c:when test="${AssignEditorSuccess=='0'}">
			<font color="red"> <spring:message
					code="workspace.assign.owner.editor.failure" /></font>
		</c:when>
		<c:when test="${AssignEditorSuccess=='2'}">
			<font color="red"> <spring:message
					code="workspace.assign.owner.editor.assigned" /></font>
		</c:when>
	</c:choose>
	<c:choose>
		<c:when test="${DeleteEditorSuccess=='1'}">
			<font color="blue"> <spring:message
					code="workspace.delete.owner.editor.success" /></font>

		</c:when>
		<c:when test="${DeleteEditorSuccess=='0'}">
			<font color="red"> <spring:message
					code="workspace.delete.owner.editor.failure" /></font>
		</c:when>
		<c:when test="${DeleteEditorSuccess=='2'}">
			<font color="red"> <spring:message
					code="workspace.delete.owner.editor.assigned" /></font>
		</c:when>
	</c:choose>
	<br/>
	
<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/updateworkspacedetails/${workspaceid}">
<input type="button" name="Edit" value="Edit"/>
</a> 
<c:choose>
	<c:when test="${owner=='1'}">
		<c:choose>
			<c:when test="${editoraccess=='0' }">
				<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/assignEditorRoleToOwner/${workspaceid}">
						<input type="button" name="Get Editor Role" value="Get Editor Role" />
				</a> 
			</c:when>
			<c:otherwise>
				<c:choose>
								<c:when test="${projectinherit == '0' }">
									<a
										href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/deleteEditorRoleToOwner/${workspaceid}">
										<input type="button" name="Delete Editor Role"
										value="Delete Editor Role" />
									</a>
								</c:when>
							</c:choose>
			</c:otherwise>
		</c:choose>

	</c:when>
</c:choose>



<c:choose><c:when test="${empty dspaceKeys}">
<!-- Dspace Login popup -->
<script>
$(document).ready(function() {

$('a.login-window').click(function() {
    location.href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.id}/communities";        
});

});
</script>
</c:when>
<c:otherwise>
<script>
$(document).ready(function() {

$('a.login-window').click(function() {
    location.href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.id}/communities";        
});

});
</script>
</c:otherwise>
</c:choose>

<a href="#login-box" class="login-window"><input type="submit" value="Add text from Dspace"></a>
<c:choose><c:when test="${empty dspaceKeys}">
<!-- Allow the user to change the dspace login credentials -->
<a href="#change-login" class="change-login">Change Dspace Login<c:choose><c:when test="${not empty wrongDspaceLogin}">*</c:when></c:choose></a>
<div id="login-box" class="login-popup" title="Dspace Authentication">
<form id="dspaceLogin" method="post" class="signin">
<fieldset class="textbox">
    <label class="username"><span>Dspace UserName:</span>
        <input id="username" name="username" value="" type="text" autocomplete="on" placeholder="Username" />
    </label>
    <label class="password"><span>Dspace Password: </span>
        <input id="password" name="password" value="" type="password" placeholder="Password" />
    </label>
    </fieldset>
    <label><input type="checkbox" name="dspacePublicAccess" id="dspacePublicAccess" value="public" /><font size="2">Use Public Access</font></label>
</form>
<font size="1">We recommend setting up Dspace Access keys <a href="${pageContext.servletContext.contextPath}/auth/workbench/keys">here</a>. Its more secure !</font>
</div>
<script>
$(document).ready(function(){
	$("#login-box").dialog({
	    autoOpen: false,
	    modal: false,
	    resizable: false,
	    buttons: {
	        Login: function () {
	        	var bValid = true;
	        	var $username = $('#username');
	        	var $password = $('#password');
	        	
	        	if(!$('#dspacePublicAccess').is(':checked'))
        		{
        			if ($.trim($username.val()) === '') {
	            		$username.effect( "shake", { times:1 }, 300);
	            		$username.focus();
	            		bValid = false;
	            	}
	            	if($.trim($password.val()) === '') {
		            	$password.effect( "shake", { times:1 }, 300);
	            		if(bValid)
	            		$password.focus();
	            		bValid = false;
	            	}
        		}
	            
	            if(bValid)
	            	{
	            		$('#dspaceLogin').submit();
	            	}
	        }
	    }
	});
	
	
	$('a.change-login').click(function() {
		$('#dspaceLogin').attr('action','${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.id}/changedspacelogin');
	    $( "#login-box" ).dialog( "open" );
	});
})
</script>
</c:when></c:choose>

<br><br>
<c:choose>
	<c:when test="${not empty workspacedetails.bitstreams}">
	<c:choose><c:when test="${not empty wrongDspaceLogin}">*Invalid dspace login credentails. Please provide the correct details to view all files.</c:when></c:choose>
	<form id="bitstream" method="POST" action="${pageContext.servletContext.contextPath}/auth/workbench/workspace/${workspacedetails.id}/deletebitstreams">
	<font size="2"><input type="submit" onclick="submitClick();" value="Delete Dspace Files" />
	<c:choose><c:when test="${empty dspaceKeys}"></c:when></c:choose></font> 
		<br>
		<table class="display dataTable" width="100%">
			<thead>
				<tr>
					<th ></th>
					<th>Community</th>
					<th>Collection</th>
					<th>Item</th>
					<th>File</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bitstream" items="${workspacedetails.bitstreams}">
					<tr bgcolor="#E0F0FF">
						<td>
    						<div id='checkbox_<c:out value="${bitstream.id}"/>' class='checkbox_<c:out value="${bitstream.id}"/>'>
        						<c:choose>
            					<c:when test="${not((bitstream.name == 'No Access to File') or (bitstream.name == 'Wrong Dspace Authentication') or (bitstream.name == 'Dspace is Down...')) }">
                					<c:choose>
	                    				<c:when test="${not(bitstream.name == 'Checking BitStream Access...')}">
                        					<input type="checkbox" class="checkbox" name="bitstreamids" value="${bitstream.id}" />
                    					</c:when>
                					</c:choose>
            					</c:when>
        						</c:choose>
    						</div>
						</td>
						<td><div class='community_<c:out value="${bitstream.communityid}"/>' id='community_<c:out value="${bitstream.communityid}"/>'><font size="1"><c:out value="${bitstream.communityName}"></c:out></font></div></td>
						<td><div class='collection_<c:out value="${bitstream.collectionid}"/>' id='collection_<c:out value="${bitstream.collectionid}"/>'><font size="1"><c:out value="${bitstream.collectionName}"></c:out></font></div></td>
						<td><div class='item_<c:out value="${bitstream.collectionid}"/>_<c:out value="${bitstream.itemid}"/>' id='item_<c:out value="${bitstream.collectionid}"/>_<c:out value="${bitstream.itemid}"/>'><font size="1"><c:out value="${bitstream.itemName}"></c:out></font></div></td>
						<td><div class='bitstream_<c:out value="${bitstream.collectionid}"/>_<c:out value="${bitstream.itemid}"/>_<c:out value="${bitstream.id}"/>' id='bitstream_<c:out value="${bitstream.collectionid}"/>_<c:out value="${bitstream.itemid}"/>_<c:out value="${bitstream.id}"/>'><font size="1"><c:out value="${bitstream.name}"></c:out></font></div></td>
					</tr>
				</c:forEach>
				</tbody>
				<tfoot>
				<tr>
					<th ></th>
					<th>Community</th>
					<th>Collection</th>
					<th>Item</th>
					<th>File</th>
				</tr>
			</tfoot>
		</table>
		</form>
	</c:when>
	<c:otherwise>
					Workspace does not contain any files from dspace !
				</c:otherwise>
</c:choose>

    <!-- Display collaborators -->
    <td style="width:10%"> 
    <c:if test="${not empty workspacedetails.collaborators}">
   <h3 class="major"><span>Collaborators</span></h3>
    <ul class="collaborators">
			<c:forEach var="wscollaborator" items="${workspacedetails.collaborators}">
				<li>
					<c:out value="${wscollaborator.userObj.name}"></c:out>
				</li>
			</c:forEach>
		</ul>
    </c:if>
    </td>
  </tr>
</table>





<c:choose>
	<c:when test="${not empty networkList}">
		<span class="byline">Networks belonging to this workspace</span>
		<hr>
		<table style="width: 100%" class="display dataTable">
			<thead>
				<tr>
					<th>Name</th>
					<th>Network Owner</th>
					<th>Status</th>
					<th>Action</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="network" items="${networkList}">
					<tr>
						<td width="25%" align="center"><input name="items"
							type="hidden" value="<c:out value="${network.name}"></c:out>" />
							<c:out value="${network.name}"></c:out></td>
						<td width="25%" align="center"><c:out
								value="${network.creator.userName}"></c:out></td>
						<td width="25%" align="center"><c:out
								value="${network.status}"></c:out></td>
						<td width="25%" align="center"><input type=button
								onClick="location.href='${pageContext.servletContext.contextPath}/auth/networks/visualize/${network.id}'" value='Visualize'></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>

