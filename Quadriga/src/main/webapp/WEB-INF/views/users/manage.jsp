<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">

	<script>
	<!-- Script for UI validation of user requests -->
		String.prototype.replaceAll = function(str1, str2, ignore) {
			return this.replace(new RegExp(str1.replace(
					/([\,\!\\\^\$\{\}\[\]\(\)\.\*\+\?\|\<\>\-\&])/g,
					function(c) {
						return "\\" + c;
					}), "g" + (ignore ? "i" : "")), str2);
		};


		
		function jqEnableAll(name, flag) {
			if (flag == 1) {
				//Allow is selected. Enable user roles check boxes
				$("input." + name).removeAttr("disabled");

				//TODO: Check a default role when allow is selected

			} else {
				//Deny is selected. Uncheck all checkboxes and disable them
				$("input." + name).attr("checked", false);
				$("input." + name).attr("disabled", true);
			}
		}

		function submitClick(id) {

			//Check if Allow or Deny is selected
			var selectedAccess = $(
					"input[type='radio'][name='" + id + "']:checked").map(
					function() {
						return this.value;
					}).get();
			if (selectedAccess.length == 0) {				
				$.alert("Please Approve/Deny the request","Oops !!!");
				return;
			}

			//If Allow is selected, atleast one role should be selected
			var checkedVals = $('.' + id + ':checkbox:checked').map(function() {
				return this.value;
			}).get();
			if (checkedVals.length == 0 && selectedAccess == 'approve') {
				$.alert("Please select atleast one role for the user","Oops !!!");
				return;
			}

			//Create a path for the user to be passed to the Controller
			var path = id + "," + selectedAccess + "," + checkedVals.join(",");
			path = path.replaceAll(",", "-");
			location.href = '/quadriga/auth/users/access/' + path;
		}

		$(function() {
			$("input[type=submit]").button().click(function(event) {
				event.preventDefault();
			});
		});

		$(function() {
			$("input[type=submit]").button().click(function(event) {
				event.preventDefault();
			});
		});
	</script>


	<c:if test="${not empty userRequestsList}">
		<h3>User Requests to access Quadriga</h3>
		<c:forEach var="user" items="${userRequestsList}">
    User Name: <b><c:out value="${user.userName}"></c:out></b>, Name     : <c:out
				value="${user.name}"></c:out>, Email	 : <c:out value="${user.email}"></c:out>
			<br>
			<input type="radio"
				onclick="jqEnableAll('<c:out value="${user.userName}"></c:out>',1);"
				name="<c:out value="${user.userName}"></c:out>" value="approve">Approve&nbsp;&nbsp;<input
				type="checkbox" class="<c:out value="${user.userName}"></c:out>"
				name="admin" value="role3" disabled="disabled">Admin&nbsp;<input
				type="checkbox" class="<c:out value="${user.userName}"></c:out>"
				name="standard" value="role4" disabled="disabled">Standard User&nbsp;<input
				type="checkbox" class="<c:out value="${user.userName}"></c:out>"
				name="restricted" value="role6" disabled="disabled">Restricted User&nbsp;<input
				type="checkbox" class="<c:out value="${user.userName}"></c:out>"
				name="collaborator" value="role5" disabled="disabled">Collaborator&nbsp;
	<br>
			<input type="radio" name="<c:out value="${user.userName}"></c:out>"
				value="deny"
				onclick="jqEnableAll('<c:out value="${user.userName}"></c:out>',0);">Deny
	<br>
			<font size="1"> <input type="submit"
				id="<c:out value="${user.userName}"></c:out>"
				onclick="submitClick(this.id);" value="Submit"></font>
			<br>
			<br>
		</c:forEach>
	</c:if>


	<c:if test="${not empty activeUserList}">
		<h3>Current Active Users</h3>
		<table>
			<c:forEach var="user" items="${activeUserList}">
				<tr>
					<td width="52%"><font size="3"><c:out
								value="${user.name}"></c:out></font></td>
					<td><font size="1"> <input type="submit"
							onclick="location.href='/quadriga/auth/users/deactivate/${user.userName}'"
							value="Deactivate"></font></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>



	<c:if test="${not empty inactiveUserList}">
		<h3>Deactivated User Accounts</h3>
		<table>
			<c:forEach var="user" items="${inactiveUserList}">
				<tr>
					<td width="54%"><font size="3"><c:out
								value="${user.name}"></c:out></font></td>
					<td><font size="1"> <input type="submit"
							onclick="location.href='/quadriga/auth/users/activate/${user.userName}'"
							value="Activate"></font></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>


</article>

<!-- /Content -->