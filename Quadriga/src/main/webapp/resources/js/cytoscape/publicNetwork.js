var animationDuration = 200;



function defineListeners(cy, path, unixName) {
	cy.on('mouseover', 'node', function(e) {
		var ele = e.cyTarget;
		var statementIds = ele.data('statementIds');
		fadeOut(statementIds, 65, 50);
	})
	
	cy.on('mouseout', 'node', function(e) {
	    fadeIn(55, 40);
	});
	
	cy.on('click', 'node', function(e) {
		var node = e.cyTarget;
		conceptDescription(path, node);
		getTexts(node, path, unixName);
		getEntireText(node,path);
	});
}

function defineFadeListenersSearch(cy, path) {
	cy.on('mouseover', 'node', function(e) {
		var ele = e.cyTarget;
		var statementIds = ele.data('statementIds')
		fadeOut(statementIds, 45, 30);
	})
	
	cy.on('mouseout', 'node', function(e) {
	    fadeIn(35, 10);
	});
}

function defineDoubleClickSearch(cy, path) {
	cy.on('click', 'node', function(e) {
		var node = e.cyTarget;
		var name = node.data('conceptName');
		var uri = node.data('conceptUri');
		$('#nodeName').html(name);
		$('#searchForNode').css({'display':'inline'});
		$('#searchNodeLink').attr('href', path + "/search/texts?conceptId=" + uri);
	});
}

function fadeIn(sizeLeafs, sizePreds) {
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
        css: { 'width': sizeLeafs, 'height' : sizeLeafs}
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
        css: { 'width':sizePreds, 'height' : sizePreds}
      }, {
        duration: animationDuration
      });
}

function fadeOut(statementIds, sizeLeafs, sizePreds) {
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
        css: { 'width':sizeLeafs, 'height' : sizeLeafs}
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
        css: { 'width':sizePreds, 'height' : sizePreds}
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
		beforeSend: function() { 
			$('#lemma_name').html("");
			$('#concept_desc').html("");
			$('#loading').show();
			},
		success : function(data) {
			$('#loading').hide();
			if (data == '') {
				data = "No information available."
			}
			conceptDesc += data + "</div>";
			$('#lemma_name').html(lemmaName);

			$('#concept_desc').html(conceptDesc);
		},
		error : function() {
			$('#loading').hide();
			alert("error");
		}
	});

}

function getTexts(node, path, unixName) {
	var conceptDesc = "";
	var sample = "";
	var temp = "";
	$.ajax({
		url : path + "/public/concept/texts?conceptId=" + encodeURIComponent(node.data("conceptId")) + "&projectUnix=" + unixName,
		type : "GET",
		beforeSend: function() { 
			$('#texts').html("");
			$('#loading1').show(); 
			},
		success : function(data) {
			$('#loading1').hide();
			if (data == '') {
				data = "No texts available."
			} else {
				parsedData = JSON.parse(data);
				var projects = parsedData['projects'];
				projects.forEach(function(element, index, array) {
					conceptDesc += "<p class='text-muted' style='margin-bottom:2px'>" + element["text"] + "</p>";
					conceptDesc += "<a href='#' data-toggle='modal' data-target='#txtModal' data-txtid="+ element["textId"] + " data-txttitle=" + element["textTitle"] + " data-txtauthor=" + element["textAuthor"] + " data-txtdate=" + element["textCreationDate"] + ">" + element["textAuthor"] + ", " + element["textTitle"] + ":" + "(" + element["textCreationDate"] + ")" + "</a>";
					//conceptDesc += "<p class='text-muted' style='margin-bottom:2px'>" + element["textTitle"] + "</p>";
					//conceptDesc += "<p class='text-muted' style='margin-bottom:2px'>" + element["textCreationDate"] + "</p>";
					conceptDesc += "..." + hightlight(element["textContent"][0], element["phrases"]) + "..." + "<br>";
				});
			}
			conceptDesc = "<p>" + conceptDesc + "</p>";
			$('#texts').html(conceptDesc);
		},
		error : function() {
			$('#loading1').hide();
			// do nothing
		}
	});
}

function getEntireText(node,path){
	$(document).ready(function() {
	$('#txtModal').on('show.bs.modal',function(event){
    	var link = $(event.relatedTarget);
    	var txtid = link.data('txtid');
        var title = link.data('txttitle');
        var author = link.data('txtauthor');
        var date = link.data('txtdate');
        var header = "No author and title information provided."
            if (title != '' || author != '' || date != '') {
                header = '';
                if (author != null) {
                    header += author + ", ";
                }
                if (title != null) {
                    header += "<em>" + title + "</em> ";
                }
                if (date != null) {
                    header += "(" + date + ")";
                }
            }            
            //header += "<br><small>" + txtname + "</small>";
        $.ajax({ 
        	type: "GET",
            url: path + "/public/text/view?conceptUri=" + encodeURIComponent(node.data("conceptUri")) + "&txtid=" + txtid,
          success: function(data){ 
        	  $('.modal-title')
              .html(header);
        	  $('.modal-body')
              .html(data);
          }
        });

        return true;
    });
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

