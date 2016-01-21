
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>



	<h1>Browse Networks</h2>
	<p>Browse networks of project <i>${project.projectName}</i>.</p>

<div class="container">
<c:choose>
	<c:when test="${not empty networks}">
	<div class="table-responsive">
			<table class="table table-striped networks">
				<thead>
					<tr>
						<th width="80%">Name</th>
						<th>Action</th>
					</tr>
				</thead>

				<tbody>
					<c:forEach var="network" items="${networks}">
						<tr>
							<td>
								${network.networkName}</td>
							<td><a href='${pageContext.servletContext.contextPath}/sites/${project.unixName}/networks/${network.networkId}' >Visualize</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:when>	
</c:choose>
</div>
