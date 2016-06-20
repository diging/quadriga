var animationDuration = 200;

function defineListeners(cy, path, unixName) {
	cy.on('mouseover', 'node', function(e) {
		var ele = e.cyTarget;
		var statementIds = ele.data('statementIds');
		fadeOut(statementIds);
	})
	
	cy.on('mouseout', 'node', function(e) {
	    fadeIn();
	});
	
	cy.on('click', 'node', function(e) {
		var node = e.cyTarget;
		conceptDescription(path, node);
		getTexts(node, path, unixName);
	});
}

function fadeIn() {
	cy.elements().animate({
      style: { 'opacity': '1' }
    }, {
      duration: animationDuration
    });
	
	// reset leaf node size
	var leafs = cy.filter(function(i, element){
        if(element.isNode() && element.data("group") == '1'){
           return true;
        }
        return false;
     });
    
	leafs.animate({
        css: { 'width':'55', 'height' : '55'}
      }, {
        duration: animationDuration
      });
	
	// reset predicate node size
    var predicates = cy.filter(function(i, element){
        if(element.isNode() && element.data("group") == '0'){
           return true;
        }
        return false;
     });
    
    predicates.animate({
        css: { 'width':'40', 'height' : '40'}
      }, {
        duration: animationDuration
      });
}

function fadeOut(statementIds) {
    var faded = cy.filter(function(i, element){
        for (var i = 0; i<statementIds.length; i++) {
	      if(element.data("statementIds").includes(statementIds[i])){
	         return false;
	      }
        }
        return true;
	 });
    
    faded.animate({
        style: { 'opacity': '0.2' }
      }, {
        duration: animationDuration
      });
    
    // make leaf nodes bigger
    var unfadedLeafs = cy.filter(function(i, element){
        for (var i = 0; i<statementIds.length; i++) {
          if(element.isNode() && element.data("statementIds").includes(statementIds[i]) && element.data("group") == '1'){
             return true;
          }
        }
        return false;
     });
    
    unfadedLeafs.animate({
        css: { 'width':'65', 'height' : '65'}
      }, {
        duration: animationDuration
      });
    
    // make predicates bigger
    var unfadedPredicates = cy.filter(function(i, element){
        for (var i = 0; i<statementIds.length; i++) {
          if(element.isNode() && element.data("statementIds").includes(statementIds[i]) && element.data("group") == '0'){
             return true;
          }
        }
        return false;
     });
    
    unfadedPredicates.animate({
        css: { 'width':'50', 'height' : '50'}
      }, {
        duration: animationDuration
      });
}

function conceptDescription(path, node) {
	lemma = node.data("conceptName");

	var lemmaName = lemma;
	var conceptDesc = "<div id=" + '"conceptdescTextArea"' + ">";

	// This is done to replace all dot (.) with dollar ($)
	// Since our spring controller would ignore any data after dot (.)
	lemma = lemma.replace(".", "$");

	// Ajax call for getting description of the node
	// Note: this ajax call has async = false
	// this allow variables to be assigned inside the ajax and
	// accessed outside
	$.ajax({
		url : path + "/public/concept?id=" + encodeURIComponent(node.data("conceptUri")),
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

function getTexts(node, path, unixName) {
	var conceptDesc = "";
	$.ajax({
		url : path + "/public/concept/texts?conceptId=" + encodeURIComponent(node.data("conceptId")) + "&projectUnix=" + unixName,
		type : "GET",
		success : function(data) {
			if (data == '') {
				data = "No texts available."
			} else {
				parsedData = JSON.parse(data);
				var projects = parsedData['projects'];
				projects.forEach(function(element, index, array) {
					conceptDesc += "<p class='text-muted' style='margin-bottom:2px'>" + element["text"] + "</p>";
					conceptDesc += "... " + hightlight(element["textContent"][0], element["phrases"]) + "..." + "<br>";
				});
			}
			conceptDesc = "<p>" + conceptDesc + "</p>";
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
