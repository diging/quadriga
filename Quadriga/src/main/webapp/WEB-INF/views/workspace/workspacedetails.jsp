<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:choose>
	<c:when test="${not empty workspacedetails.bitstreams}">
	<c:choose><c:when test="${empty dspaceKeys}">
	<c:choose>
	<c:when test="${empty dspaceLogin}">
	<script>
	$(document).ready(function() {
		
		$('#dspaceSync').click(function dspaceSync() {
				$('#dspaceLogin').attr('action','/quadriga/auth/workbench/workspace/${workspacedetails.id}/syncdspacelogin');
				$( "#login-box" ).dialog( "open" );					
			});
		});
	</script>
	</c:when>
	<c:otherwise>
	<script>
	$(document).ready(function() {
		$('#dspaceSync').click(function dspaceSync() {
			$( "#dialog-message" ).dialog({
		    		resizable: false,
		        	modal: true,
		        	width:'auto',
			      	buttons: {
			        	Ok: function() {
			          				location.href='/quadriga/auth/workbench/workspace/${workspacedetails.id}/updatebitstreams';
			          				$( this ).dialog( "close" );
			        			}
			      			}
			    });
			});
		});
	</script>
	</c:otherwise>
	</c:choose>
	</c:when><c:otherwise><script>
	$(document).ready(function() {
		$('#dspaceSync').click(function dspaceSync() {
			$( "#dialog-message" ).dialog({
		    		resizable: false,
		        	modal: true,
		        	width:'auto',
			      	buttons: {
			        	Ok: function() {
			          				location.href='/quadriga/auth/workbench/workspace/${workspacedetails.id}/updatebitstreams';
			          				$( this ).dialog( "close" );
			        			}
			      			}
			    });
			});
		});
	</script></c:otherwise></c:choose>
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
<a href="${pageContext.servletContext.contextPath}/auth/workbench/workspace/updateworkspacedetails/${workspaceid}">
<input type="button" name="Edit" value="Edit"/>
</a> 

<c:choose><c:when test="${empty dspaceKeys}">
<!-- Dspace Login popup -->
<c:choose>
<c:when test="${not empty dspaceLogin}">
<script>
$(document).ready(function() {

$('a.login-window').click(function() {
    location.href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/communities";        
});

});
</script>
</c:when>
<c:otherwise>
<script>
$(document).ready(function() {

$('a.login-window').click(function() {
	$('#dspaceLogin').attr('action','/quadriga/auth/workbench/workspace/${workspacedetails.id}/adddspacelogin');
    $( "#login-box" ).dialog( "open" );	        
});

});
</script>
</c:otherwise>
</c:choose>
</c:when>
<c:otherwise>
<script>
$(document).ready(function() {

$('a.login-window').click(function() {
    location.href="/quadriga/auth/workbench/workspace/${workspacedetails.id}/communities";        
});

});
</script>
</c:otherwise>
</c:choose>

<a href="#login-box" class="login-window"><input type="submit" value="Add text from Dspace"></a>
<c:choose><c:when test="${empty dspaceKeys}">
<c:choose>
<c:when test="${not empty dspaceLogin}">
<!-- Allow the user to change the dspace login credentials -->
<a href="#change-login" class="change-login">Change Dspace Login</a>
</c:when>
</c:choose>
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
<font size="1">We recommend setting up Dspace Access keys <a href="/quadriga/auth/workbench/keys">here</a>. Its more secure !</font>
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
		$('#dspaceLogin').attr('action','/quadriga/auth/workbench/workspace/${workspacedetails.id}/changedspacelogin');
	    $( "#login-box" ).dialog( "open" );
	});
})
</script>
</c:when></c:choose>

<br><br>
<c:choose>
	<c:when test="${not empty workspacedetails.bitstreams}">
	<div id="dialog-message" title="Synchronization" style="display:none">
    Please check after few minutes. Data will be synced with Dspace.
</div>
	<form id="bitstream" method="POST" action="/quadriga/auth/workbench/workspace/${workspacedetails.id}/deletebitstreams">
	<font size="2"><input type="submit" value="Sync with Dspace" id="dspaceSync" />
	<input type="submit" onclick="submitClick();" value="Delete Dspace Files" /></font> 
		<br><br>
		<table border="1">
				<tr bgcolor="#C3C3C1">
					<th ></th>
					<th>Community</th>
					<th>Collection</th>
					<th>Item</th>
					<th>File</th>
				</tr>
				<c:forEach var="bitstream" items="${workspacedetails.bitstreams}">
					<tr bgcolor="#E0F0FF">
						<td><input type="checkbox" class="checkbox" name="bitstreamids" value="${bitstream.id}"></td>
						<td><font size="1"><c:out value="${bitstream.communityName}"></c:out></font></td>
						<td><font size="1"><c:out value="${bitstream.collectionName}"></c:out></font></td>
						<td><font size="1"><c:out value="${bitstream.itemName}"></c:out></font></td>
						<td><font size="1"><c:out value="${bitstream.name}"></c:out></font></td>
					</tr>
				</c:forEach>
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
					<c:out value="${wscollaborator.userObj.userName}"></c:out>
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
		<table style="width: 100%" cellpadding="0" cellspacing="0" border="0"
			class="display dataTable">
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
							onClick="location.href=''" value='Visualize'></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<spring:message code="empty.networks" />
	</c:otherwise>
</c:choose>

