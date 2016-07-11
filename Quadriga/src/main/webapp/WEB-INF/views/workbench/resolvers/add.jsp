<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<h2>Add Project Handle Resolver to Project</h2>

<form method="POST"
        action="${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/resolvers/add">
   
   <div class="form-group">
      <label for="resolverId" class="col-sm-3 control-label control-label-left">Select Project Handle Resolver:</label>
      <div class="col-sm-9">
         <select class="form-control" name="resolverId"  >
            <c:forEach items="${resolvers}" var="resolver">
                <option value="${resolver.id}" label="${resolver.projectName}" />
            </c:forEach>
         </select>
      </div>
   </div>
   
   <div class="text-right" >
     <button type="submit" style="margin-top: 20px;" class="btn btn-primary">Add Resolver</button>
   </div>
   
</form>
