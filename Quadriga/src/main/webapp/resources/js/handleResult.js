function handleResult(status) {
    if (status == "SUCCESS") {
    $(".input-group-addon").html('<i class="fa fa-check-circle"></i>');
    } else if (status == "FAILURE") {
    $(".input-group-addon").html('<i class="fa fa-times"></i>');
    }
}

function performAction(obj) {
    var data = {};
    data['projectName'] = $("#projectName").val();
    data['projectDescription'] = $('#projectDescription').val();
    data['projectUrl'] = $('#projectUrl').val();
    data['handlePattern'] = $('#handlePattern').val();
    data['resolvedHandlePattern'] = $('#resolvedHandlePattern').val();
    data['handleExample'] = $('#handleExample').val();
    data['resolvedHandleExample'] = $('#resolvedHandleExample').val();

    $
    .ajax({
    type : "POST",
    url : "/quadriga/auth/resolvers/check",
    data : {
    data : JSON.stringify(data)
    },
    beforeSend : function() {
    $(".input-group-addon")
    .html('<i class="fa fa-spinner fa-spin" aria-hidden="true"></i>');
    },
    success : function(data, status) {
    handleResult(data);
    },
    error : function(e) {
    console.log("ERROR: ", e.responseText);
    }
    });
}