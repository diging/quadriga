
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!--  
	Author Lohith Dwaraka  
	Used to list the networks
-->

<script type="text/javascript">
	$(document).ready(function() {
		$("ul.pagination1").quickPagination({
			pageSize : "10"
		});
		$("ul.pagination2").quickPagination({
			pageSize : "10"
		});

	});
</script>
<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
</script>


<header>
	<h2>Networks</h2>
	<span class="byline">Manage your Networks here.</span>
</header>



<br />
<div
	style="float: left; width: 45%; border-radius: 5px; border: 2px solid #e3daa8; padding: 20px;">
	<h4 align="center">You own these Networks</h4>
	<hr>
	<c:choose>
		<c:when test="${not empty networkList}">
			<ul class="pagination1">
				<c:forEach var="network" items="${networkList}">
					<li><details>
							<summary>
								<a
									href="${pageContext.servletContext.contextPath}/auth/networks">
									<c:out value="${network.name}"></c:out>
								</a>
							</summary>
							<c:out value="${network.status}"></c:out>
						</details> <c:choose>
							<c:when test="${network.status=='PENDING'}">
								<input type=button onClick="location.href=''" value='Approve'>
								<input type=button onClick="location.href=''" value='Reject'>
							</c:when>
						</c:choose></li>


				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<spring:message code="empty.networks" />
		</c:otherwise>
	</c:choose>
</div>

<div
	style="float: right; width: 45%; border-radius: 5px; border: 2px solid #e3daa8; padding: 20px;">

	<h4 align="center">You participate in these Networks</h4>
	<hr>
	<c:choose>
		<c:when test="${not empty dictionaryCollabList}">
			<ul class="pagination1">
				<c:forEach var="dictionary" items="${dictionaryCollabList}">
					<li><details>
							<summary>
								<a
									href="${pageContext.servletContext.contextPath}/auth/dictionaries/collab/${dictionary.id}">
									<c:out value="${dictionary.name}"></c:out>
								</a>
							</summary>
							<c:out value="${dictionary.description}"></c:out>
						</details></li>
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<spring:message code="empty.networks" />
		</c:otherwise>
	</c:choose>

</div>