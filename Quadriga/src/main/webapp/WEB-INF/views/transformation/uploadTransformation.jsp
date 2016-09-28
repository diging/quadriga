<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.form-group.required .control-label:after {
	content: "*";
	color: red;
}

.top5 { margin-top:5px;
		}
</style>

<form name="selectTransformFiles" method="post" class="form-horizontal"
	action="${pageContext.servletContext.contextPath}/auth/transformation/upload"
	enctype="multipart/form-data"
	modelAttribute="UploadTransformationBackingBean">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<div class="form-group required">
		<label for="title" class="col-md-2 control-label"
			style="font-weight: bold;"> Title</label>
		<div class="col-md-8">
			<input type="text" class="form-control" id="title" name="title"
				required="required">
		</div>
	</div>

	<div class="form-group">
		<label for="desciption" class="col-md-2 control-label"
			style="font-weight: bold;">Description </label>
		<div class="col-md-8">
			<textarea class="form-control" rows="2" id="description"
				name="description" placeholder="This field is optional"></textarea>
		</div>
	</div>

	<div id="patternDiv" class="col-md-5"
		style="border: 1px solid #A89E9E; border-radius: 10px;">
		<h4 style="text-align: center">
			<u> Upload Pattern/Transformation File </u>
		</h4>

		<div class="form-group required">
			<label for="transformTitle" class="col-md-4 control-label"
				style="font-weight: bold;"> Title</label>
			<div class="col-md-8">
				<input type="text" class="form-control" id="patternTitle"
					name="patternTitle" required="required">
			</div>
		</div>

		<div class="form-group">
			<label for="transfomrmDesciption" class="col-md-4 control-label"
				style="font-weight: bold;">Description </label>
			<div class="col-md-8">
				<textarea class="form-control" rows="2" id="patternDescription"
					name="patternDescription" placeholder="This field is optional"></textarea>
			</div>
		</div>

		<div class="form-group required">
			<div class="col-md-12">
				<input type="file" class="form-control" id="patternFile" name="file"
					required="required">
			</div>
		</div>

	</div>

	<div id="mappingDiv" class="col-md-offset-1 col-md-5"
		style="border: 1px solid #A89E9E; border-radius: 10px;">

		<h4 style="text-align: center">
			<u> Upload Mapping File </u>
		</h4>

		<div class="form-group required">
			<label for="mappingTitle" class="col-md-4 control-label"
				style="font-weight: bold;"> Title</label>
			<div class="col-md-8">
				<input type="text" class="form-control" id="mappingTitle"
					name="mappingTitle" required="required">
			</div>
		</div>

		<div class="form-group">
			<label for="mappingDesciption" class="col-md-4 control-label"
				style="font-weight: bold;">Description </label>
			<div class="col-md-8">
				<textarea class="form-control" rows="2" id="mappingDescription"
					name="mappingDescription" placeholder="This field is optional"></textarea>
			</div>
		</div>

		<div class="form-group required">
			<div class="col-md-12">
				<input type="file" class="form-control" id="mappingFile" name="file"
					required="required">
			</div>
		</div>

	</div>

<div class="col-md-offset-5">
<button type="submit" class="btn btn-primary top5">Submit Files</button>
</div>
	
</form>