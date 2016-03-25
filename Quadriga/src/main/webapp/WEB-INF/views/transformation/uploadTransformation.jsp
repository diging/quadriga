<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<form class="form-inline">
<div class="col-md-6">
Upload Pattern File
<label for="patternTitle" class="col-md-2"> Title</label>
<input type="text" class="col-md-4">
<label for="patternDesciption" class="col-md-2"> Optional Description </label> 
<input type="text" class="col-md-4">
<input type="file" >
</div>
<div class="col-md-6">Upload Mapping File
<label for "mapTitle"> Title:</label>
<label> Optional Description </label>
<input type="file" >
</div>
<button type="submit" class="btn btn-default"> Submit </button>
</form>
</body>
</html>
