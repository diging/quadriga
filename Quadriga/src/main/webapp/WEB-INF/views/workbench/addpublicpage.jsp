<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
$(function() {
$("#submit1").click(function(){perform()});
$("#submit2").click(function(){perform()});
$("#submit3").click(function(){perform()});


});

function perform(){
    console.log(  $(this).parent().parent().find('#title1').val()  );
    console.log("success");
}
	function performAction(idValue) {
		var data = {};
		var test = $(this).siblings('input').val();
		console.log(test);
		var titles = $("#title" + idValue).val();
		var desc = $("#description" + idValue).val();
		var order = $("#order" + idValue).val();
		console.log(order);
		var mandatory = 0;
		if (titles.length < 1) {
			$("#title_err" + idValue).html("Please provide a title");
			mandatory = 1;
		} else {
			$("#title_err" + idValue).html('');
		}
		if (desc.length < 1) {
			$("#desc_err" + idValue).html("Please provide a description");
			mandatory = 1;
		} else {
			$("#desc_err" + idValue).html('');
		}
		if (order == "select") {
			$("#order_err" + idValue).html("Possible values are 1, 2, or 3.");
			mandatory = 1
		} else {
			$("#order_err" + idValue).html('');
		}
		if (mandatory) {
			return false;
		}
		data["title"] = $("#title" + idValue).val();
		data["desc"] = $("#description" + idValue).val();
		data["order"] = $("#order" + idValue).val();
		$
				.ajax({
					type : "POST",
					url : "${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess",
					data : {
						data : JSON.stringify(data)
					},
					success : function(e) {
						$("#title" + idValue).val('');
						$("#description" + idValue).val('');
						$("#order" + idValue).val('');
					},
					error : function(e) {
						console.log("ERROR: ", e);
					}
				});
	}

</script>
<article class="is-page-content">
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess">
		<header>
			<h2>Editing Text Contents to be shown</h2>
			<span class="byline">Please fill in the following information:</span>
		</header>
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title *</td>
				<td style="width: 400px"><form:input path="title" size="60" class="title"
						id="title1" /></td>
				<td><div id="title_err1"></div></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description *</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description1" /></td>
				<td><div id="desc_err1"></div></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference *</td>
				<td style="width: 1px">
				<select id="order1" path="order">
				  <option selected value="select">--Select--</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				</select>
				<td><div id="order_err1"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" id="submit1" 
					value="SAVE"></td>
			</tr>
		</table>
	</form:form>
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess">
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title *</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title2" /></td>
				<td><div id="title_err2"></div></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description *</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description2" /></td>
				<td><div id="desc_err2"></div></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference *</td>
				<td style="width: 1px">
				<select id="order2" path="order">
				  <option selected value="select">--Select--</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				</select>
				<td><div id="order_err2"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" value="SAVE"  onclick="performAction('2')" id="submit2"></td>
			</tr>
		</table>
	</form:form>
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess">
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title *</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title3" /></td>
				<td><div id="title_err3"></div></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description *</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description3" /></td>
				<td><div id="desc_err3"></div></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference *</td>
				<td style="width: 1px">
				<select id="order3" path="order">
				  <option selected value="select">--Select--</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				</select>
				<td><div id="order_err3"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" value="SAVE" onclick="performAction('3')" id="submit3"></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->