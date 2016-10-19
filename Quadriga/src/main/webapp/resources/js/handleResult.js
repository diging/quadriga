function handleResult(status, id) {
	if (status == "SUCCESS") {
		$('#' + id).html('<i class="fa fa-check-circle"></i>');
    } else if (status == "FAILURE") {
    	$('#' + id).html('<i class="fa fa-times"></i>');
    }
}

function performAction(obj, id, contextPath) {
    var data = {};
    data['projectName'] = $("#projectName").val();
    data['projectDescription'] = $('#projectDescription').val();
    data['projectUrl'] = $('#projectUrl').val();
    data['handlePattern'] = $('#handlePattern').val();
    data['resolvedHandlePattern'] = $('#resolvedHandlePattern').val();
    data['handleExample'] = $('#handleExample').val();
    data['resolvedHandleExample'] = $('#resolvedHandleExample').val();
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    
    $
    .ajax({
        type : "POST",
        url :  contextPath + '/auth/resolvers/check',
        data : {
            data : JSON.stringify(data)
        },
        beforeSend : function(xhr) {
        	xhr.setRequestHeader(header, token);
        	$('#' + id).html('<i class="fa fa-spinner fa-spin" aria-hidden="true"></i>');
        },
        success : function(data, status) {
            handleResult(data, id);
        },
        error : function(e) {
            console.log("ERROR: ", e.responseText);
        }
    });
}
