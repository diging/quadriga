<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
//@ sourceURL=filename.js
	$(function() {
		$("#submit1").click(function(){performAction(this)});
		$("#submit2").click(function(){performAction(this)});
		$("#submit3").click(function(){performAction(this)});
	});
    
	function setErrorMsg(obj, field, msg) {
		$(obj).closest('div.publicpageform').find('#' + field + '_err').html(msg);
	}

	function performAction(obj) {
		var data = {};
		var publicpageid = $(obj).closest('div.publicpageform').find('.publicpageid').val();
		var title = $(obj).closest('div.publicpageform').find('.titleValue').val();
		var desc = $(obj).closest('div.publicpageform').find('.descValue').val();
		var order = $(obj).closest('div.publicpageform').find('.orderValue').val();
		var linkTo = $(obj).closest('div.publicpageform').find('.linkTo').val();
		var linkText = $(obj).closest('div.publicpageform').find('.linkText').val();
		
		var mandatory = 0;
		if (title.length < 1) {
			setErrorMsg(obj, 'title', 'Please provide a title');
			mandatory = 1;
		} else {
			setErrorMsg(obj, 'title', '');
		}
		if (desc.length < 1) {
			setErrorMsg(obj, 'desc', 'Please provide a description');
			mandatory = 1;
		} else {
			setErrorMsg(obj, 'desc', '');
		}
		if (order <=0 || order >3) {
			setErrorMsg(obj, 'order', 'Possible values are 1, 2, or 3.');
			mandatory = 1
		} else {
			setErrorMsg(obj, 'order', '');
		}
		
		if (linkTo.length < 1) {
			setErrorMsg(obj, 'linkTo', 'Please specify where to link to.');
			mandatory = 1;
		} else {
			setErrorMsg(obj, 'linkTo', '');
        }
		
		if (linkText.length < 1) {
			setErrorMsg(obj, 'linkText', 'Please specify a link text.');
            mandatory = 1;
        } else {
        	setErrorMsg(obj, 'linkText', '');
        }
		
		if (mandatory) {
			return false;
		}
		
		/* if all checks pass submit */
		data["title"] = title;
		data["desc"] = desc;
		data["order"] = order;
		data["publicpageid"] = publicpageid;
		data["linkTo"] = linkTo;
		data["linkText"] = linkText;
		$
				.ajax({
					type : "POST",
					url : "${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/addpublicpagesuccess",
					data : {
						data : JSON.stringify(data)
					},
					success : function(e) {
						$(obj).closest('div.publicpageform').find('#success_message').html('Successfully Updated');
					},
					error : function(e) {
						response = JSON.parse(e.responseText)
						console.log("ERROR: ", e.responseText);
						fieldArray = response["fields"];
						fieldArray.forEach(function(entry) {
							setErrorMsg(obj, entry, 'Please specify a value for this field.');
						});
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
					<td style="width: 400px"><input path="title" size="60"
						class="titleValue" id="title1" value="${publicpageObject0.title}" /></td>
					<td><div id="title_err"></div></td>
					<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td style="vertical-align: top">Description *</td>
					<td><textarea path="description" cols="60" rows="6"
							id="description1" class="descValue">${publicpageObject0.description}</textarea></td>
					<td><div id="desc_err"></div></td>
					<td><form:errors path="description"
							class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
				    <td style="vertical-align: top">Link to *</td>
				    <td><select class="linkTo" path="linkTo" >
						<c:forEach var="item" items="${linkTypes}">
						<option value="${item.key}"  ${item.key == publicpageObject0.linkTo ? 'selected' : ''}>${item.value}</option>
						</c:forEach> 
					</select>
					</td>
				    <td><div id="linkTo_err"></div></td>
				    <td></td>
				</tr>
				<tr>
                    <td style="vertical-align: top">Link Text *</td>
                    <td style="width: 1px"><input class="linkText" type="text"
                        path="linkText"  value="${publicpageObject0.linkText}"></td>
                    <td><div id="linkText_err"></div></td>
                    <td></td>
                </tr>
				<tr>
					<td style="width: 170px">Order Preference *</td>
					<td style="width: 1px"><input type="number" id="order1"
						path="order" class="orderValue" value="${publicpageObject0.order}"></td>
					<td><div id="order_err"></div></td>
					<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td><input type="hidden" class="publicpageid"
						value="${publicpageObject0.publicPageId}"><input
						type="button" id="submit1" value="SAVE"></td>
					<td><div id="success_message"></div></td>
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
							id="title2" class="titleValue" value="${publicpageObject1.title}" /></td>
					<td><div id="title_err"></div></td>
					<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td style="vertical-align: top">Description *</td>
					<td><textarea path="description" cols="60" rows="6"
							id="description2" class="descValue">${publicpageObject1.description}</textarea></td>
					<td><div id="desc_err"></div></td>
					<td><form:errors path="description"
							class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
                    <td style="vertical-align: top">Link to *</td>
                    <td><select class="linkTo" path="linkTo" >
						<c:forEach var="item" items="${linkTypes}">
						<option value="${item.key}"  ${item.key == publicpageObject1.linkTo ? 'selected' : ''}>${item.value}</option>
						</c:forEach> 
					</select> 
				    </td>
                    <td><div id="linkTo_err"></div></td>
                    <td></td>
                </tr>
                <tr>
                    <td style="vertical-align: top">Link Text *</td>
                    <td style="width: 1px"><input class="linkText" type="text"
                        path="linkText" value="${publicpageObject1.linkText}"></td>
                    <td><div id="linkText_err"></div></td>
                    <td></td>
                </tr>
				<tr>
					<td style="width: 170px">Order Preference *</td>
					<td style="width: 1px"><input type="number" id="order2"
						path="order" class="orderValue" value="${publicpageObject1.order}">
					<td><div id="order_err"></div></td>
					<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td><input type="hidden" class="publicpageid"
						value="${publicpageObject1.publicPageId}"><input
						type="button" value="SAVE" id="submit2"></td>
					<td><div id="success_message"></div></td>
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
							id="title3" class="titleValue" value="${publicpageObject2.title}" /></td>
					<td><div id="title_err"></div></td>
					<td><form:errors path="title" class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td style="vertical-align: top">Description *</td>
					<td><textarea path="description" cols="60" rows="6"
							id="description3" class="descValue">${publicpageObject2.description}</textarea></td>
					<td><div id="desc_err"></div></td>
					<td><form:errors path="description"
							class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
                    <td style="vertical-align: top">Link to *</td>
                    <td>
                    <select class="linkTo" path="linkTo" >
						<c:forEach var="item" items="${linkTypes}">
						<option value="${item.key}"  ${item.key == publicpageObject2.linkTo ? 'selected' : ''}>${item.value}</option>
						</c:forEach> 
					</select> 
				    </td>
                    <td><div id="linkTo_err"></div></td>
                    <td></td>
                </tr>
                <tr>
                    <td style="vertical-align: top">Link Text *</td>
                    <td style="width: 1px"><input class="linkText" type="text"
                        path="linkText" value="${publicpageObject2.linkText}"></td>
                    <td><div id="linkText_err"></div></td>
                    <td></td>
                </tr>
				<tr>
					<td style="width: 170px">Order Preference *</td>
					<td style="width: 1px"><input type="number" id="order3"
						path="order" class="orderValue" value="${publicpageObject2.order}">
					<td><div id="order_err"></div></td>
					<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
				</tr>
				<tr>
					<td><input type="hidden" class="publicpageid"
						value="${publicpageObject2.publicPageId}"><input
						type="button" value="SAVE" id="submit3"></td>
					<td><div id="success_message"></div></td>
				</tr>
			</table>
		</div>
	</form:form>
</article>

<!-- /Content -->