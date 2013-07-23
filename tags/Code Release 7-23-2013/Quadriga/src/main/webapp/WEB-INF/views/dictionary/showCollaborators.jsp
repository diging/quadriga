<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>


<script>
$(document).ready(function() {
	activeTable = $('.dataTable').dataTable({
		"bJQueryUI" : true,
		"sPaginationType" : "full_numbers",
		"bAutoWidth" : false
	});
});

function validate()
{
	ValidateSelectBox('userName','text');
}

function ValidateSelectBox(selectbox,returntype)
{
	var retMessage = "";
	
	if(getSelectedValue(selectbox) == 'NONE'){
		
		retMessage = 'you must select a value';
		
		if(returntype == 'text')
			{
				return retMessage;
			}
		
	}
}


$(document).ready(function(){
	$(".selectAll").click(function(){
	if($(this).val() == "check all")
		{
			$('input:checkbox').prop("checked",true);
			$(this).val("uncheck all");
		}
	else
		{
			$('input:checkbox').prop("checked",false);
			$(this).val("check all");	
		}
	});
});	


$(document).ready(function() {
	$("input[type=submit]").button().click(function(event) {

	});
});


function enableDisable()
{
	var chkboxArray = document.getElementById("chkboxDivision").getElementsByTagName("input");
	
	var elt = document.getElementById("selectbox");
	
	var selectedUser = elt.options[elt.selectedIndex].value;

	if(selectedUser != "NONE")
	{
		for(var i=0;i<chkboxArray.length;i++)
		{
			chkboxArray[i].disabled = false;
		}
	}
	
	else
	{
		for(var i=0;i<chkboxArray.length;i++)
		{
			chkboxArray[i].disabled = true;
		}
	}
}

function validation()
{	
	var elt = document.getElementById("selectbox");
	
	var selectedUser = elt.options[elt.selectedIndex].value;
	
	var chkboxArray = document.getElementById("chkboxDivision").getElementsByTagName("input");

	var flag = 0;
	for(var i=0;i<chkboxArray.length;i++)
		{
			if(selectedUser == "NONE")
			{    
			 alert("please select collaborator !!!");
			 break;
			}
			else if(chkboxArray[i].checked == true)
			{
				flag = 1;
			}
	    }
	
	if(flag == 0)
		{
			alert("please select role !!!");
		}
}

</script>


<form method="POST" name="myForm">
	<select  id="selectbox" name="userName" onchange="enableDisable()" >
		    <option value="NONE" label="--- Select ---"/>
		    <c:forEach var="users" items="${nonCollaboratingUsers}">
		   		<option value="${users.userName}" label="${users.userName}" onclick="enableCheckboxes('<c:out value = "${users.userName}"></c:out>', 1);" />
		   	</c:forEach>
	</select>
	
	<br><br>
	
	<div id="chkboxDivision">
	<c:forEach var="roles" items="${possibleCollaboratorRoles}">
	<input type="checkbox" class="'<c:out value = "${users.userName}"></c:out>'" name="roleselected" id="roleselected"
		   value='<c:out value="${roles.roleDBid}"></c:out>'  disabled="disabled" />
	<label><c:out value="${roles.displayName}"></c:out></label> 
	</c:forEach>
    </div><br><input type="submit" id="submit_btn"  value="Add Collaborator" onclick="validation();this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/addCollaborators'">
      
	<br><br><br>

    <input type="submit" value="Delete Collaborator" onclick="this.form.action='${pageContext.servletContext.contextPath}/auth/dictionaries/${dictionaryid}/deleteCollaborators'">

	<br><br>
	
<table style="width:100%" cellpadding="0" cellspacing="0"
					border="0" class="display dataTable">	
	<thead>
		<tr>
			<th align="left"><input type="checkbox" class="selectAll" value="check all" name="selected">Select All</th>
			<th align="left">collaborator</th>
			<th align="left">roles</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach var="collab" items="${collaboratingUsers}">
		<tr>
		  <td><input type="checkbox" name="selected" value='<c:out value="${collab.userObj.userName}"></c:out>'/></td>
		  <td><c:out value="${collab.userObj.userName}"></c:out> </td>
		  <td>
			  <c:forEach var="roles" items="${collab.collaboratorRoles}">
			  	<c:out value="${roles.displayName}"></c:out>||
			  </c:forEach>
		  </td>
		</tr>
	</c:forEach>
	</tbody>
</table>

</form>




