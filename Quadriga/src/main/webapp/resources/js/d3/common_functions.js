function display_annotations(d, path, networkId) {
	var getAnnotationUrl = path + "/auth/editing/getAnnotation/" + networkId;
	var annotationContent = "";
	// ajax Call to get annotation for a node.id
	// Used to add the old annotation in to the popup view
	$('#annot_details').html('');
	$.ajax({
		url : getAnnotationUrl,
		type : "GET",
		data : "nodeid=" + d.id + "&objecttype=node",
		dataType : 'json',
		success : function(data) {
			var cnt = 0;
			$.each(data.text, function(key, value) {
				annotationContent += '<li>' + value.name;
				annotationContent += "</li>";
			});
			if (annotationContent == "") {
				annotationContent = "No annotations found."
			}
			annotationContent = "<div id=" + '"annotationtextarea"' + "><ol>"
					+ annotationContent + "</ol></div>";
			$('#annot_details').append(annotationContent);
		},
		error : function(event) {
			console.log(event);
		}

	});

}

function conceptDescription(d,path) {
	lemma = d.name;

	var lemmaName = "Node: " + lemma;
	var conceptDesc = "<div id=" + '"conceptdescTextArea"' + ">";

	// This is done to replace all dot (.) with dollar ($)
	// Since our spring controller would ignore any data after dot (.)
	lemma = lemma.replace(".", "$");

	// Ajax call for getting description of the node
	// Note: this ajax call has async = false
	// this allow variables to be assigned inside the ajax and
	// accessed outside
	$.ajax({
		url : path + "/public/concept/" + encodeURIComponent(d.conceptId),
		// url : path+"/rest/editing/getconcept/PHIL D. PUTWAIN",
		type : "GET",
		success : function(data) {
			if (data == '') {
				data = "No information available."
			}
			conceptDesc += data + "</div>";
			$('#lemma_name').html(lemmaName);

			$('#concept_desc').html(conceptDesc);
		},
		error : function() {
			alert("error");
		}
	});

}

function displayAllAnnotationsNew(networkId, path){
	$.ajax({
		url : path+"/auth/editing/getAllAnnotations/"+networkId,
		type : "GET",
		dataType: 'json',
		success : function(data) {
			if (data.length > 0) {
				$('#annotationsTable')
						.dataTable()
						.fnClearTable();
				$('#annotationsTable')
						.dataTable().fnAddData(data);
			} else {
				$('#annotationsTable')
						.dataTable()
						.fnClearTable();
			}
		},
		error: function() {
			alert("error");
		}
	});
}