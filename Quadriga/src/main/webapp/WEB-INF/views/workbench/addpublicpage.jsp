<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
// 	$(document).ready(function() {
// 	});

// 	$(function() {
// 		$("input[type=submit]").button().click(function(event) {
// 		});
// 		$("input[type=submit]").button().click(function(event) {
// 		});
// 	});
	
	jQuery(document).ready(function($) {
		$("#submit-form1").submit(function(event) {

			//enableSubmitButton(false);
			
			// Prevent the form from submitting via the browser.
			//event.preventDefault();
			
			submitAjax();

		});
	});
	
	function submit() {
		var data = {}
		data["id1"] = $("#id1").val();
		data["desc1"] = $("#desc1").val();
		data["order1"] = $("#order").val();
		
		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1",
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 100000,
			success : function(data) {
				console.log("SUCCESS: ", data);
				display(data);
			},
			error : function(e) {
				console.log("ERROR: ", e);
				display(e);
			},
			done : function(e) {
				console.log("DONE");
				enableSubmitButton(true);
			}
		});
	}
	
	function enableSubmitButton(flag) {
		$("#submit1").prop("disabled", flag);
	}
	
	
</script>
<article class="is-page-content">
	<form:form commandName="publicpage" method="POST" id="submit-form1"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1">
		<header>
			<h2>Editing Text Contents to be shown</h2>
			<span class="byline">Please fill in the following information:</span>
		</header>
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title" /></td>
				<td><form:errors path="title" class="ui-state-error-text" id="title1"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text" id="desc1"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference</td>
				<td style="width: 1px"><form:input path="order" size="60" id="order" /></td>
				<td><form:errors path="order" class="ui-state-error-text" id="order1"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" id="submit1" value="SAVE"></td>
			</tr>
		</table>
	</form:form>
	<form:form commandName="publicpage" method="POST" id="submit-form2"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage">
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title" /></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference</td>
				<td style="width: 1px"><form:input path="order" size="60" id="order" /></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE" id="submit2"></td>
			</tr>
		</table>
	</form:form>
	<form:form commandName="publicpage" method="POST" id="submit-form3"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1">
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title" /></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description" /></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference</td>
				<td style="width: 1px"><form:input path="order" size="60" id="order" /></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="submit" value="SAVE" id="submit3"></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->