<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.form-group.required .control-label:after{
   content:"*";
   color:red;
}
</style>

<!-- <script>
function submitClick(id){
	location.href = "${pageContext.servletContext.contextPath}/auth/transformation/selectTransformationFiles";
}
</script>
 -->
<form name="selectTransformFiles" method="post" class="form-horizontal" action="${pageContext.servletContext.contextPath}/auth/transformation/upload" enctype="multipart/form-data" modelAttribute="UploadTransformationBackingBean"> 
<div id="mappingDiv" class="col-md-5" style="border:1px solid#A89E9E; border-radius:10px;"> 

<h4 style="text-align:center"><u> Upload Mapping File </u> </h4>

<div class="form-group required">
<label for="mappingTitle" class="col-md-4 control-label" style="font-weight:bold;"> Title</label>
<div class="col-md-8"><input type="text" class="form-control" id="mappingTitle" name="mappingTitle" required="required"></div>
</div>
	
<div class="form-group">
<label for="mappingDesciption" class="col-md-4 control-label" style="font-weight:bold;">Description </label>
<div class="col-md-8"> <textarea class="form-control" rows="2" id="mappingDescription" name="mappingDescription" placeholder="This field is optional"></textarea></div>
</div>

<div class="form-group required">
<div class="col-md-12"><input type="file" class="form-control" id="mappingFile" name="file" required="required"></div>
</div>

</div>

<div id="transfomrDiv" class="col-md-offset-1 col-md-5" style="border:1px solid#A89E9E; border-radius:10px;">

<h4 style="text-align:center"><u> Upload Transformation File </u> </h4>

<div class="form-group required">
<label for="transformTitle" class="col-md-4 control-label" style="font-weight:bold;"> Title</label>
<div class="col-md-8"><input type="text" class="form-control"  id="transformTitle" name="transformTitle" required="required"></div>
</div>
	
<div class="form-group">
<label for="transfomrmDesciption" class="col-md-4 control-label" style="font-weight:bold;">Description </label>
<div class="col-md-8"> <textarea class="form-control" rows="2"  id="transformDescription" name="transformDescription" placeholder="This field is optional"></textarea></div>
</div>

<div class="form-group required">
<div class="col-md-12"><input type="file" class="form-control" id="tranformFile" name="file"  required="required"></div>
</div>

</div>

<div class="form-group">
<div class="col-md-offset-5 col-md-6"><input type="submit" value="Submit Files"></div>

</div>

</form>
<%-- </c:when>
<c:when test="${success == 1}">
		  <span class="byline">Transformation Files uploaded successfully</span>
		<ul>
					<li><input type="button" id="sucessButton" onClick="submitClick();"
						value='Okay'></li>
				</ul>
		  </c:when>
<c:when test="${success == 2}">
		  <span class="byline">Upload Unsuccessful. Please follow specifications and try again</span>
		<ul>
					<li><input type="button" id="sucessButton" onClick="submitClick();"
						value='Okay'></li>
				</ul>
		  </c:when>		    
</c:choose>
 --%>