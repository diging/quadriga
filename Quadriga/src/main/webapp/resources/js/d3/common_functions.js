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
		//url : path + "/public/concept/lemma/" + d.name,
		url : path + "/public/concept?id=" + encodeURIComponent(d.conceptId),
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

function getTexts(d, path, unixName) {
	var conceptDesc = "";
	$.ajax({
		//url : path + "/public/concept/lemma/" + d.name,
		url : path + "/public/concept/texts?conceptId=" + encodeURIComponent(d.conceptCpId) + "&projectUnix=" + unixName,
		// url : path+"/rest/editing/getconcept/PHIL D. PUTWAIN",
		type : "GET",
		success : function(data) {
			if (data == '') {
				data = "No texts available."
			} else {
				parsedData = JSON.parse(data);
				var projects = parsedData['projects'];
				projects.forEach(function(element, index, array) {
					conceptDesc += "<h5>" + element["text"] + "</h5>";
					conceptDesc += "... " + hightlight(element["textContent"][0], element["phrases"]) + "..." + "<br>";
				});
			}
			conceptDesc = "<div>" + conceptDesc + "</div>";
			$('#texts').html(conceptDesc);
		},
		error : function() {
			// do nothing
		}
	});
}

function hightlight(text, phrases) {
	var highlightedText = "";
	var lastIdx = 0;
	var idx = 0;
	for (var i = 0; i < phrases.length; i++ ) {
		var element = phrases[i];
		idx = element["position"];
		var length = element["expression"].length;
		highlightedText += text.substring(lastIdx, idx);
		highlightedText += '<span class="highlight">';
		highlightedText += text.substring(idx, idx+length);
		highlightedText += "</span>";
		lastIdx = idx + length;		
	}
	highlightedText += text.substring(lastIdx);
	return highlightedText;
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