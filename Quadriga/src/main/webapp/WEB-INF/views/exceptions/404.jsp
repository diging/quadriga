<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div class="jumbotron">
<div class="row">
    <div class="col-md-6">
      <h3>Page not found</h3>
      <p>This is not the web page you are looking for.</p>
      <p><c:if test="${not empty ex_msg}">${ex_msg}</c:if></p>
    </div>
    
    <div class="col-md-6">
    <div class="page-404">
    404
    </div>
    </div>
    </div>
 </div>
</div>
