<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
//@ sourceURL=filename.js
	
	$(document).ready(function(){
	$("#mainPageTab").attr('class','list-group-item');
	$("#aboutTab").attr('class','list-group-item disabled');
    });

	$(function() {
		$("#submit1").click(function(){performAction(this,1)});
		$("#submit2").click(function(){performAction(this,2)});
		$("#submit3").click(function(){performAction(this,3)});
	});
	
	function setErrorMsg(obj, field, msg) {
		$(obj).closest('div.publicpageform').find('#' + field + '_err').html(msg);
	}

	function performAction(obj,value) {
		var data = {};
		var publicpageid = $(obj).closest('div.publicpageform').find('.publicpageid').val();
		var title = $(obj).closest('div.publicpageform').find('#title' + value).val();
		var desc = $(obj).closest('div.publicpageform').find('#description' + value).val();
		var order = $(obj).closest('div.publicpageform').find('#orderValue' + value).val();
		var linkTo = $(obj).closest('div.publicpageform').find('#linkTo' + value).val();
		var linkText = $(obj).closest('div.publicpageform').find('#linkText' + value).val();
		
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
						$(obj).closest('div.publicpageform').find('#success_message').html('<i class="fa fa-floppy-o" aria-hidden="true"></i>');
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
					<td >Title *</td>
					
					<td ><input path="title" size="60" 
						class="form-control" id="title1" value="${publicpageObject0.title}" />
					<div id="title_err"></div></td>
					
				</tr>
				<tr>
					<td style="vertical-align: top">Description *</td>
					<td><textarea path="description" cols="60" rows="6"
							id="description1" class="form-control">${publicpageObject0.description}</textarea>
					<div id="desc_err"></div></td>
					
				</tr>
				<tr>
				    <td style="vertical-align: top">Link to *</td>
				    <td><select class="linkTo" path="linkTo" id="linkTo1" >
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
                    <td ><input type="text" class="form-control" id="linkText1"
                        path="linkText"  value="${publicpageObject0.linkText}"><div id="linkText_err"></div></td>
                    
                </tr>
				<tr>
					<td >Order Preference *</td>
					<td ><input type="number" id="orderValue1"
						path="order" class="form-control" value="${publicpageObject0.order}">
					<div id="order_err"></div></td>
					
				</tr>
				<tr>
					<td><input type="hidden" class="publicpageid"
						value="${publicpageObject0.publicPageId}"><input
						type="button" id="submit1" class="btn btn-primary" value="Save"></td>
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
					<td>Title *</td>
					<td ><form:input path="title" size="60"
							id="title2" class="form-control" value="${publicpageObject1.title}" />
					<div id="title_err"></div></td>
					
				</tr>
				<tr>
					<td style="vertical-align: top">Description *</td>
					<td><textarea path="description" cols="60" rows="6"
							id="description2" class="form-control" >${publicpageObject1.description}</textarea>
					<div id="desc_err"></div></td>
				</tr>
				<tr>
                    <td style="vertical-align: top">Link to *</td>
                    <td><select class="linkTo" path="linkTo" id="linkTo2" >
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
                    <td ><input id="linkText2" type="text" class="form-control"
                        path="linkText" value="${publicpageObject1.linkText}"/>
                    <div id="linkText_err"></div></td>
                </tr>
				<tr>
					<td >Order Preference *</td>
					<td ><input type="number" id="orderValue2"
						path="order" class="form-control" value="${publicpageObject1.order}" />
					<div id="order_err"></div></td>
					
				</tr>
				<tr>
					<td><input type="hidden" class="publicpageid"
						value="${publicpageObject1.publicPageId}"><input
						type="button" class="btn btn-primary" value="Save" id="submit2"></td>
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
					<td>Title *</td>
					<td ><form:input path="title" size="60"
							id="title3" class="form-control" value="${publicpageObject2.title}" />
					<div id="title_err"></div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top">Description *</td>
					<td><textarea path="description" cols="60" rows="6"
							id="description3" class="form-control">${publicpageObject2.description}</textarea>
					<div id="desc_err"></div></td>
				</tr>
				<tr>
                    <td style="vertical-align: top">Link to *</td>
                    <td>
                    <select class="linkTo" path="linkTo" id="linkTo3" >
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
                    <td ><input class="form-control" type="text" id="linkText3"
                        path="linkText" value="${publicpageObject2.linkText}">
                    <div id="linkText_err"></div></td>
                    
                </tr>
				<tr>
					<td>Order Preference *</td>
					<td><input type="number" id="orderValue3"
						path="order" class="form-control" value="${publicpageObject2.order}">
					<div id="order_err"></div></td>
					
				</tr>
				<tr>
					<td><input type="hidden" class="publicpageid"
						value="${publicpageObject2.publicPageId}"><input
						type="button" class="btn btn-primary" value="Save" id="submit3"></td>
					<td><div id="success_message"></div></td>
				</tr>
			</table>
		</div>
	</form:form>
</article>

<!-- /Content -->