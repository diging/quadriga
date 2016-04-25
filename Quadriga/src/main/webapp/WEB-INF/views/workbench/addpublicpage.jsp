<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
$(function() {
$("#submit1").click(function(){performAction(this)});
$("#submit2").click(function(){performAction(this)});
$("#submit3").click(function(){performAction(this)});
});
function perform(obj){
    //var a = $(obj).parent().parent().parent();
    var a = $(obj).closest('div.publicpageform').find('.titleValue').val();
    console.log(a);
}
	function performAction(obj) {
		var data = {};
		var title = $(obj).closest('div.publicpageform').find('.titleValue').val();
		var desc = $(obj).closest('div.publicpageform').find('.descValue').val();
		var order = $(obj).closest('div.publicpageform').find('.orderValue').val();
		var mandatory = 0;
		if (title.length < 1) {
			$(obj).closest('div.publicpageform').find('#title_err').html("Please provide a title");
			mandatory = 1;
		} else {
			$(obj).closest('div.publicpageform').find('#title_err').html('');
		}
		if (desc.length < 1) {
			$(obj).closest('div.publicpageform').find('#desc_err').html("Please provide a description");
			mandatory = 1;
		} else {
			$(obj).closest('div.publicpageform').find('#desc_err').html('');
		}
		if (order == "select") {
			$(obj).closest('div.publicpageform').find('#order_err').html("Possible values are 1, 2, or 3.");
			mandatory = 1
		} else {
			$(obj).closest('div.publicpageform').find('#order_err').html('');
		}
		if (mandatory) {
			return false;
		}
		data["title"] = title;
		data["desc"] = desc;
		data["order"] = order;
		$
				.ajax({
					type : "POST",
					url : "${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess",
					data : {
						data : JSON.stringify(data)
					},
					success : function(e) {
						$(obj).closest('div.publicpageform').find('.titleValue').val('');
						$(obj).closest('div.publicpageform').find('.descValue').val('');
						$(obj).closest('div.publicpageform').find('.orderValue').val('');
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
		<div class="publicpageform">
		<table style="width: 100%">
		
			<tr>
				<td style="width: 170px">Title *</td>
				<td style="width: 400px"><input path="title" size="60" class="titleValue"
						id="title1" /></td>
				<td><div id="title_err"></div></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description *</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description1" class="descValue" /></td>
				<td><div id="desc_err"></div></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference *</td>
				<td style="width: 1px">
				<select id="order1" path="order" class="orderValue">
				  <option selected value="select">--Select--</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				</select>
				<td><div id="order_err"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" id="submit1" 
					value="SAVE"></td>
			</tr>
		
		</table>
		</div>
	</form:form>
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess">
		<div class="publicpageform">
		<table style="width: 100%">
		
			<tr>
				<td style="width: 170px">Title *</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title2" class="titleValue"/></td>
				<td><div id="title_err"></div></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description *</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description2" class="descValue"/></td>
				<td><div id="desc_err"></div></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference *</td>
				<td style="width: 1px">
				<select id="order2" path="order" class="orderValue">
				  <option selected value="select">--Select--</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				</select>
				<td><div id="order_err"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" value="SAVE" id="submit2"></td>
			</tr>
		</table>
		</div>
	</form:form>
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess">
		<div class="publicpageform">
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title *</td>
				<td style="width: 400px"><form:input path="title" size="60"
						id="title3" class="titleValue" /></td>
				<td><div id="title_err"></div></td>
				<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="vertical-align: top">Description *</td>
				<td><form:textarea path="description" cols="60" rows="6"
						id="description3" class="descValue"/></td>
				<td><div id="desc_err"></div></td>
				<td><form:errors path="description" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td style="width: 170px">Order Preference *</td>
				<td style="width: 1px">
				<select id="order3" path="order" class="orderValue">
				  <option selected value="select">--Select--</option>
				  <option value="1">1</option>
				  <option value="2">2</option>
				  <option value="3">3</option>
				</select>
				<td><div id="order_err"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" value="SAVE" id="submit3"></td>
			</tr>
		</table>
		</div>
	</form:form>
</article>

<!-- /Content -->