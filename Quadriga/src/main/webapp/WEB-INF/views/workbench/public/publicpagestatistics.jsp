<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script>
	$(function() {
		$("input[type=submit]").button().click(function(event) {
		});

		$("input[type=button]").button().click(function(event) {
		});
	});

	$(function() {
		$("input[name='selectAll']").button().click(function() {

			$("form input:checkbox").prop("checked", true);
			event.preventDefault();
			return;
		});

		$("input[name='deselectAll']").button().click(function() {

			$("form input:checkbox").prop("checked", false);
			event.preventDefault();
			return;

		});

	});
</script>
<input type="button" value="Select All" name="selectAll">
<input type="button" value="Deselect All" name="deselectAll">

<form:form method="POST" modelAttribute="publicstatisticspage"
	action="${pageContext.servletContext.contextPath}/auth/workbench/${publicpageprojectid}/submitpublicstatistics">

	<table class="display dataTable" cellpadding="0" cellspacing="0"
		border="0" style="width: 100%">
		<thead>
			<tr>
				<th>Select</th>
				<th>Name</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${statistics}" var="statistic">
				<tr>
				    <c:choose>
					    <c:when test="${statistic.value}">
							<td><form:checkbox path="names" value="${statistic.key}"
									checked="checked" /></td>
						</c:when>
						<c:otherwise>
							<td><form:checkbox path="names" value="${statistic.key}" /></td>
						</c:otherwise>
					</c:choose>
					<td><font size="3"> <c:out value="${statistic.key}"></c:out>
					</font></td>
				</tr>
			</c:forEach>
			<tr>
				<td><input type="submit" value="Update"></td>
			</tr>
	</table>
</form:form>