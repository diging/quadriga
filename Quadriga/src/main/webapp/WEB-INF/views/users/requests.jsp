<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- Content -->

<article class="is-page-content">
	<script>
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

			var selectedAccess = $(
					"input[type='radio'][name='" + id + "']:checked").map(
					function() {
						return this.value;
					}).get();
			if (selectedAccess.length == 0) {
				alert('Please Approve/Deny the request');
				return;
			}
			var checkedVals = $('.' + id + ':checkbox:checked').map(function() {
				return this.value;
			}).get();
			if (checkedVals.length == 0 && selectedAccess == 'approve') {
				alert('Please select atleast one role for the user');
				return;
			}

			var path = id+","+selectedAccess + "," + checkedVals.join(",");
			path = path.replaceAll(",", "-");
			alert(path);
			location.href = '/quadriga/auth/users/access/' + path;
		}

		$(function() {
			$("input[type=submit]").button().click(function(event) {
				event.preventDefault();
			});
		});
	</script>
	<p>Users requesting access...</p>

	<c:if test="${not empty userRequestsList}">
		<form id="requestForm" method="post"
			action="/quadriga/auth/users/access">
			<c:forEach var="user" items="${userRequestsList}">
    User Name: <c:out value="${user.userName}"></c:out>, Name     : <c:out
					value="${user.name}"></c:out>, Email	 : <c:out
					value="${user.email}"></c:out>
				<br>
				<input type="radio"
					onclick="jqEnableAll('<c:out value="${user.userName}"></c:out>',1);"
					name="<c:out value="${user.userName}"></c:out>" value="approve">Approve&nbsp;&nbsp;<input
					type="checkbox" class="<c:out value="${user.userName}"></c:out>"
					name="admin" value="admin" disabled="disabled">Admin&nbsp;<input
					type="checkbox" class="<c:out value="${user.userName}"></c:out>"
					name="std" value="std" disabled="disabled">Standard User&nbsp;<input
					type="checkbox" class="<c:out value="${user.userName}"></c:out>"
					name="rest" value="rest" disabled="disabled">Restricted User&nbsp;<input
					type="checkbox" class="<c:out value="${user.userName}"></c:out>"
					name="collab" value="collab" disabled="disabled">Collaborator&nbsp;
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
		</form>
	</c:if>


</article>
