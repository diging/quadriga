<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<script>

$(document).ready(function(){
	  $("#projId").keyup(function(event)
	  {
		var keyedInput = $("#projId").val();
		var constant = "";
		if($.trim($("#projId").val()))
			{
			  constant = $("#unixnameurl").val();
			}
	    $("#UnixURL").text(constant+keyedInput);
	  });
	});
	
	$(function() {
		$("input[type=submit]").button().click(function(event) {
			if (!$.trim($("#name").val())) {
				$.alert("Please enter a project name", "Oops !!!");
				$("#name").val("");
				event.preventDefault();
				return;
			}

			if (!$.trim($("#description").val())) {
				$.alert("Please enter a project description", "Oops !!!");
				$("#description").val("");
				event.preventDefault();
				return;
			}

			if (!$.trim($("#projId").val())) {
				$.alert("Please enter Unix name", "Oops !!!");
				$("#projId").val("");
				event.preventDefault();
				return;
			}
			
			if ($.trim($("#projId").val())) {
				var regex = /[^a-zA-Z0-9-_.+!*'()$]/;
				if(regex.test($('#projId').val()))
				{ 
					$.alert("Unix name allows only -_.+!*()$ special characters", "Oops !!!");
					event.preventDefault();
					return;
				}
			}
		});
	});
</script>

<header>
	<h2>Create new Project</h2>
	<span class="byline">Please fill in the following information:</span>
</header>

<article class="is-page-content">

	<form:form modelAttribute="project" method="POST"
		action="/auth/workbench/addproject">
		<table style="width: 100%">
			<c:choose>
				<c:when test="${success=='0'}">
					<span class="byline" style="color: #f00;"><c:out
							value="${errormsg}"></c:out></span>
					<br />
				</c:when>
			</c:choose>
			<tr>
				<td style="width: 170px">Name:</td>
				<td><form:input path="name" size="80" id="name" /></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description:</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
			</tr>
			<tr>
				<td>Project Public Access:</td>
				<td><form:select path="projectAccess">
						<form:options />
					</form:select>
			</tr>
			<tr>
				<td>Unix name:</td>
				<td><form:input path="id" size="80" id="projId" /></td>
			</tr>
			<tr>
				<td></td>
				<td><div id="UnixURL"></div></td>
			</tr>
			<tr>
		    <td>  <input class="command" type="submit" value="Create Project"> </td>
		    <td><input type="hidden" id="unixnameurl" value=<c:out value="${unixnameurl}"></c:out>/></td>
		    </tr>
		</table>

	</form:form>

</article>

<!-- /Content -->