<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
  
  <script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'#editable',height:300, plugins : 'advlist autolink save link image lists charmap print preview', menubar:false,  toolbar: 'undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image | print preview media fullpage | forecolor backcolor emoticons | save' });</script>

<h1>About Project - Edit</h1>
Edit <b>${project.unixName}</b> about details here
<form  method="get" action="${pageContext.servletContext.contextPath}/sites/${project.unixName}/EditAbout">
 	<textarea style="width:100%; font-weight:bold; font-size:24px; vertical-align:middle; align:center; text-align:center"> ${title} </textarea>
 	<div id="editable">`</div>
</form>
  