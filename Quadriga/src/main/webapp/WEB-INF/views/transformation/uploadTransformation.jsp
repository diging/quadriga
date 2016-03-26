<style>
.form-group.required .control-label:after{
   content:"*";
   color:red;
}
</style>

<script>
$(function() {
	$("input[type=submit]").button().click(function(event){
	});	
});
</script>

<c:choose>
<c:when test="${success == '0'}">
<form name="selectTransformFiles" method="post" class="form-horizontal" action="${pageContext.servletContext.contextPath}/auth/uploadTransformation">
<div id="mappingDiv" class="col-md-5" style="border:1px solid#A89E9E; border-radius:10px;"> <!-- style="border:1px solid#A89E9E; border-radius:10px;" -->

<div class="form-group required">
<label for="mappingTitle" class="col-md-4 control-label" style="font-weight:bold;"> Title</label>
<div class="col-md-8"><input type="text" class="form-control" id="mappingTitle" required="required"></div>
</div>
	
<div class="form-group">
<label for="mappingDesciption" class="col-md-4 control-label" style="font-weight:bold;">Description </label>
<div class="col-md-8"> <textarea class="form-control" rows="2" id="mappingDescription" placeholder="This field is optional"></textarea></div>
</div>

<div class="form-group required">
<div class="col-md-12"><input type="file" class="form-control" style="padding-top:10px;" required="required"></div>
</div>


</div>


<div id="transfomrDiv" class="col-md-offset-1 col-md-5" style="border:1px solid#A89E9E; border-radius:10px;">

<div class="form-group required">
<label for="transformTitle" class="col-md-4 control-label" style="font-weight:bold;"> Title</label>
<div class="col-md-8"><input type="text" class="form-control" id="transfomrTitle" required="required"></div>
</div>
	
<div class="form-group">
<label for="transfomrDesciption" class="col-md-4 control-label" style="font-weight:bold;">Description </label>
<div class="col-md-8"> <textarea class="form-control" rows="2" id="transfomrDescription" placeholder="This fild is optional"></textarea></div>
</div>

<div class="form-group required">
<div class="col-md-12"><input type="file" class="form-control" style="padding:10px;" required="required"></div>
</div>


</div>

<div class="form-group">
<div class="col-md-offset-5 col-md-6"><input type="submit" value="Submit Files"></div>

</div>

</form>
</c:when>
<%-- <c:when test="${success == '1'}">
		  <span class="byline">Transformation Files uploaded successfully</span>
		<ul>
					<li><input type="button" onClick="submitClick(this.id);"
						value='Okay'></li>
				</ul>
		  </c:when> --%>
</c:choose>