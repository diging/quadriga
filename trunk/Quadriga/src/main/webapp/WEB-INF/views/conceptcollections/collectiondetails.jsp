<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/resources/txt-layout/css/style.min.css" />
<script
	src="${pageContext.servletContext.contextPath}/resources/txt-layout/js/jstree.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
				$('.dataTable').dataTable({
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"bAutoWidth": false
				});
				
			});

$(document).ready(function() {
	$("input[type=button]").button().click(function(event) {
		event.preventDefault();
	});
});
$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {
		
	});
});
$(document).ready(function () {
    $('#selectall').click(function () {
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

$(function() {
	
	var data = ${core};
	console.log(data);
	$('#html').jstree(data
	);
});

function addCCtoProjects(id,name){
	
	var e = document.getElementById("dlgConfirm");
    if(e.style.display == 'block')
       e.style.display = 'none';
    else
       e.style.display = 'block';
	
	event.preventDefault();
	$("#dlgConfirm").dialog({
		resizable : false,
		height : 'auto',
		width : 350,
		modal : true,
		buttons : {
			Submit : function() {
				$(this).dialog("close");
				//$("#deletewsform")[0].submit();
				$.ajax({
					url : "${pageContext.servletContext.contextPath}/auth/workbench/"+id+"/addconceptcollection",
					type : "POST",
					data :"selected="+$('#hidden').val(),
					success : function() {
						location.reload();
						//alert("done");
					},
					error: function() {
						alert("error");
					}
				});
				event.preventDefault();
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});

}
function addCCtoWorkspace(id,name){
	
	
	var e = document.getElementById("dlgConfirm1");
    if(e.style.display == 'block')
       e.style.display = 'none';
    else
       e.style.display = 'block';
	
	event.preventDefault();
	$("#dlgConfirm1").dialog({
		resizable : false,
		height : 'auto',
		width : 350,
		modal : true,
		buttons : {
			Submit : function() {
				$(this).dialog("close");
				//$("#deletewsform")[0].submit();
				$.ajax({
					url : "${pageContext.servletContext.contextPath}/auth/workbench/workspace/"+id+"/addconceptcollection",
					type : "POST",
					data :"selected="+$('#hidden').val(),
					success : function() {
						location.reload();
						//alert("done");
					},
					error: function() {
						alert("error");
					}
				});

				event.preventDefault();
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});	
}
</script>
 
<table style="width:100%">
<tr>
<td style="width:90%">
<div>
    <h2>Concept Collection: ${concept.conceptCollectionName}</h2>
    <div>${concept.description}</div>
    <br>
    <div class="user">Owned by: ${concept.owner.name}</div>
    <br />
   
    <div id="html"> </div>
    <input type="button"
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections'"
		value='Done'>
	<input type="button"  onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/updatecollection/${concept.conceptCollectionId}'"
	   value = "Edit">
	   <input type="hidden" id="hidden" value="${concept.conceptCollectionId}" />
	</div>
	<hr>
	<c:choose >
	<c:when test="${not empty concept.conceptCollectionConcepts}">
		<span class="byline">These are all the concepts in this collection.</span>
<form method="post">
	<input type="button"
		onClick="location.href='${pageContext.servletContext.contextPath}/auth/conceptcollections/${concept.conceptCollectionId}/searchitems'"
		value='Add Items'>
	<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/deleteitems'" value='Delete Items'>
	<input type="submit" onClick="this.form.action='${pageContext.servletContext.contextPath}/auth/conceptcollections/updateitems'" value="Update Items">
<br><br>
	<table class="display dataTable" id="conceptSearch">
		<thead>
			<tr>
				<th><input type="checkbox" id="selectall"></input></th>
				<th>Lemma</th>
				<th>ID</th>
				<th>POS</th>
				<th>Description</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="conceptItem" items="${concept.conceptCollectionConcepts}">
				<tr>
					<td><input type="checkbox" class="selected" name="selected" value="${conceptItem.concept.conceptId}"/></td>
					<td align="justify"><font size="2"><c:out
								value="${conceptItem.concept.lemma}"></c:out></font></td>
					<td width="25%" align="justify"><font size="2"><c:out
								value="${conceptItem.concept.conceptId}"></c:out></font></td>
					<td class="center" align="justify"><font size="2"><c:out
								value="${conceptItem.concept.pos}"></c:out></font></td>
					<td width="30%" align="justify"><font size="2"><c:out
								value="${conceptItem.concept.description}"></c:out></font></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	</c:when>
			<c:otherwise>
			You don't have any items. Click on button to add items.
			<br />
			<input type=button
				onClick="location.href='${pageContext.servletContext.contextPath}//auth/conceptcollections/${concept.conceptCollectionId}/searchitems'"
				value='Add Items' />
	</c:otherwise>
	</c:choose>

</td>
<td style="width:10%">
<c:if test="${not empty collaboratingUsers}">
	<section>
		<h3 class="major"><span>Collaborators</span></h3>
		<ul class="collaborators">
			<c:forEach var="collab" items="${collaboratingUsers}">
				<li><c:out value="${collab.userObj.name}"></c:out></li>
			</c:forEach>
		</ul>
	</section>
</c:if>
</td>
</tr>
</table>
<div id="dlgConfirm" title="Confirmation" style="display: none">
			 <p> Do you wanna add concept collection to this project? </p>
		</div>
		<div id="dlgConfirm1" title="Confirmation" style="display: none">
			 <p> Do you wanna add concept collection to this workspace? </p>
		</div>