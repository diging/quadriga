<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<h2>Add your blog</h2>
<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>

<h3>Add Blog Entry</h3>

<script>
	tinymce
			.init({
				selector : '#editable',
				height : 300,
				plugins : 'advlist autolink save link image lists charmap print preview',
				menubar : false,
				toolbar : 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons | save'
			});
</script>

<!-- Action not defined for the form -->
<form method="get"
	action="">
	<textarea
		style="width: 100%; font-weight: bold; font-size: 24px; vertical-align: middle; align: center; text-align: center"> [Title] </textarea>
	<div id="editable"></div>
</form>
