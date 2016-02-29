<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
  <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'#editable',height:300, plugins : 'advlist autolink save link image lists charmap print preview', menubar:false,  toolbar: 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons | save' });</script>
<form  method="get" action="${pageContext.servletContext.contextPath}/sites/${project.unixName}/EditAbout">
 	<textarea style="width:100%; font-weight:bold; font-size:24px; vertical-align:middle; align:center; text-align:center"> Project Title here </textarea>
 	<div id="editable">`</div>
</form>
  