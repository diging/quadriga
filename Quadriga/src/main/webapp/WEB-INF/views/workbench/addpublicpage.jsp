<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!-- Content -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script>
	$(function() {
		$("#submit1")
				.click(
						function() {
							var data = {};
							var titles = $("#title1").val();
							var desc = $("#description1").val();
							var order = $("#order1").val();
							var mandatory = 0;
							if(titles.length < 1){
								$("#title_err1").html("Title Is Mandatory");
								mandatory = 1;
							}else{
								$("#title_err1").html(''); 
							}
							if(desc.length < 1){
								$("#desc_err1").html("Description Is Mandatory");
								mandatory =1;
							}else{
								$("#desc_err1").html('');
							}
							if(order == 0){
								$("#order_err1").html("Order Is Mandatory");
								mandatory =1 
							}else{
								$("#order_err1").html('');
							}
							if(mandatory){
								return false;
							}
							data["title"] = $("#title1").val();
							data["desc"] = $("#description1").val();
							data["order"] = $("#order1").val();
							$
									.ajax({
										type : "GET",
										url : "${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1",
										data : {
											data1 : JSON.stringify(data)
										},
										success : function(e) {
											$("#title1").val('');
											$("#description1").val('');
											$("#order1").val('');
										},
										error : function(e) {
											console.log("ERROR: ", e);
										}
									});
 						});
	});
	
	$(function() {
		$("#submit2")
				.click(
						function() {
							var data = {};
							var titles = $("#title2").val();
							var desc = $("#description2").val();
							var order = $("#order2").val();
							var mandatory = 0;
							if(titles.length < 1){
								$("#title_err2").html("Title Is Mandatory");
								mandatory = 1;
							}else{
								$("#title_err2").html('');
							}
							if(desc.length < 1){
								$("#desc_err2").html("Description Is Mandatory");
								mandatory =1;
							}else{
								$("#desc_err2").html('');
							}
							if(order == 0){
								$("#order_err2").html("Order Is Mandatory");
								mandatory =1 
							}else{
								$("#order_err2").html('');
							}							
							if(mandatory){
								return false;
							}
							data["title"] = $("#title2").val();
							data["desc"] = $("#description2").val();
							data["order"] = $("#order2").val();
							$
									.ajax({
										type : "GET",
										url : "${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1",
										data : {
											data1 : JSON.stringify(data)
										},
										success : function(e) {
											$("#title2").val('');
											$("#description2").val('');
											$("#order2").val('');
										},
										error : function(e) {
											console.log("ERROR: ", e);
										}
									});
 						});
	});
	
	$(function() {
		$("#submit3")
				.click(
						function() {
							var data = {};
							var titles = $("#title3").val();
							var desc = $("#description3").val();
							var order = $("#order3").val();
							var mandatory = 0;
							if(titles.length < 1){
								$("#title_err3").html("Title Is Mandatory");
								mandatory = 1;
							}else{
								$("#title_err3").html('');
							}
							if(desc.length < 1){
								$("#desc_err3").html("Description Is Mandatory");
								mandatory =1;
							}else{
								$("#desc_err3").html('');
							}
							if(order == 0){
								$("#order_err3").html("Order Is Mandatory");
								mandatory =1 
							}else{
								$("#order_err3").html('');
							}							
							if(mandatory){
								return false;
							}
							data["title"] = $("#title3").val();
							data["desc"] = $("#description3").val();
							data["order"] = $("#order3").val();
							$
									.ajax({
										type : "GET",
										url : "${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1",
										data : {
											data1 : JSON.stringify(data)
										},
										success : function(e) {
											$("#title3").val('');
											$("#description3").val('');
											$("#order3").val('');
										},
										error : function(e) {
											console.log("ERROR: ", e);
										}
									});
 						});
	});
</script>
<article class="is-page-content">
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1">		
		<header>
			<h2>Editing Text Contents to be shown</h2>
			<span class="byline">Please fill in the following information:</span>
		</header>
		<table style="width: 100%">
			<tr>
				<td style="width: 170px">Title *</td>
				<td style="width: 400px"><form:input path="title" size="60"
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
				<td style="width: 1px"><form:input type="number" path="order" size="60" id="order1" /></td>
				<td><div id="order_err1"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" id="submit1" value="SAVE"></td>
			</tr>
		</table>
	</form:form>
	<form:form commandName="publicpage" method="POST"
		action="${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage">
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
				<td style="width: 1px"><form:input type="number" path="order" size="60" id="order2" /></td>
				<td><div id="order_err2"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" value="SAVE" id="submit2"></td>
			</tr>
		</table>
	</form:form>
	<form:form commandName="publicpage" method="POST" 
		action="${pageContext.servletContext.contextPath}/auth/workbench/${ppprojectid}/addpublicpage1">
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
				<td style="width: 1px"><form:input type="number" path="order" size="60" id="order3" /></td>
				<td><div id="order_err3"></div></td>
				<td><form:errors path="order" class="ui-state-error-text"></form:errors></td>
			</tr>
			<tr>
				<td><input type="button" value="SAVE" id="submit3"></td>
			</tr>
		</table>
	</form:form>
</article>

<!-- /Content -->