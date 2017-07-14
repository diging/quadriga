<%@ page language="java" contentType="text/html;"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="utf8">
	$(document).ready(function() {
		$('#selectall').click(function() {
			$('.selected').prop('checked', isChecked('selectall'));
		});
	});
	function isChecked(checkboxId) {
		var id = '#' + checkboxId;
		return $(id).is(":checked");
	}
	function resetSelectAll() {
		// if all checkbox are selected, check the selectall checkbox
		// and viceversa
		if ($(".selected").length == $(".selected:checked").length) {
			$("#selectall").attr("checked", "checked");
		} else {
			$("#selectall").removeAttr("checked");
		}

		if ($(".selected:checked").length > 0) {
			$('#edit').attr("disabled", false);
		} else {
			$('#edit').attr("disabled", true);
		}
	}

	$(document).ready(function() {
		$("input[type=button]").button().click(function(event) {
			event.preventDefault();
		});
	});
	$(document).ready(function() {
		$("input[type=a]").button().click(function(event) {
			event.preventDefault();
		});
	});

	$(document).ready(function() {
		$("input[type=submit]").button().click(function(event) {

		});
	});
</script>
<script>
	$("input[name='selectall']").button().click(function(event) {
		$.alert("help");
		$("input[name='selected']").prop("checked", true);
		event.preventDefault();
		return;
	});
	$(function() {
		$("#dialog-confirm").dialog({
			resizable : false,
			height : 140,
			modal : true,
			buttons : {
				"Delete all items" : function() {
					$(this).dialog("close");
				},
				Cancel : function() {
					$(this).dialog("close");
				}
			}
		});
	});
</script>
<h2>Remove Concept Collection from Project: ${project.projectName}</h2>
<div class="back-nav">
	<hr>
	<p>
		<a
			href="${pageContext.servletContext.contextPath}/auth/workbench/projects/${project.projectId}"><i
			class="fa fa-arrow-circle-left"></i> Back to Project</a>
	</p>
	<hr>
</div>

<c:choose>
	<c:when test="${not empty collections}">
		<form method="POST">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="panel panel-default" style="margin-top: 20px;">
				<div class="panel-heading">Available Concept Collections</div>
				<div class="panel-body">
					<p>Select concept collection to remove from the project and
						then click "Remove Concept Collections".</p>
					<p>
						<input type="submit" value="Delete Concept Collections"
							class="btn btn-primary"
							onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/workbench/${project.projectId}/deleteconceptcollections'" />

					</p>
				</div>
				<table class="table">
					<!-- <table  class="dataTable" id="pagination1"> -->
					<thead>
						<tr>
							<th align="left"><input type="checkbox" id="selectall">
								All</th>
							<th>Concept Collection Name</th>
							<th>Concept Collection Description</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="collection"
							items="${collections}">
							<tr>
								<td width="10%"><input type="checkbox" class="selected" name="selected"
									value='<c:out value="${collection.conceptCollectionId}"></c:out>' /></td>
								<td width="30%"><input name="items" type="hidden"
									value="<c:out value="${collection.conceptCollectionName}"></c:out>" />
									<c:out
										value="${collection.conceptCollectionName}"></c:out></td>
								<td><c:out
										value="${collection.description}"></c:out></td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
			</div>
		</form>
	</c:when>

	<c:otherwise>

		<spring:message code="empty.CC" />
	</c:otherwise>
</c:choose>
