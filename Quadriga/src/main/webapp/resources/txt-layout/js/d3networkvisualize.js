
/**
 * @Author : Sowjanya Ambati
 * @Author : Dwaraka Lohith
 * 
 * Data visualization in D3 JQuery.
 * This javascript should work on displaying different layouts.
 * 
 */


function d3init(graph, networkId, path,type) {
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	
	if(graph==null){
		alert("no network");
	}
	// Layout size
	var width = $('#chart').parent().width();
	var height = $('#chart').parent().height();
	var layout;
	var color = d3.scale.category20();
	// Preparing the force directed graph
	if(type=="force"){
		layout = d3.layout.force()
		.charge(-150)
		.linkDistance(200)
		.size([width, height]);
		layout
		.nodes(graph.nodes)
		.links(graph.links)
		.start();
		//displayAllAnnotations();
		defineAnnotationsTable();
		displayAllAnnotationsNew();

		//defineMetadataTable();
		displayItemData();

	} //  tree layout if need we can change the layout
	else if(type=="tree"){
		layout = d3.layout.tree();
		layout
		.nodes(graph.nodes)
		.links(graph.links)
		.start();
	}


	var vis = d3.select("#chart").append("svg:svg")
			.attr("width", "100%")
			.attr("height", "100%")
			.append('svg:g')
			// Zoom in and out
			.call(d3.behavior.zoom().on("zoom", redraw))
			.append('svg:g');
	


	var div1 = d3.select("#allannot_details");

	d3.select("#chart").on("click", function(){return div1.style("visibility", "visible");});

	/*var expand = d3.select("#chart")
				.on("click", function(){

					$("chart").show();
					$("allannot_details").hide();
				});*/


	// Prepare the arrow
	vis.append("defs").selectAll("marker")
	.data(["arrow"])
	.enter()
	.append("marker")
	.attr("id", String)
	.attr("viewBox", "0 -5 10 10")
	.attr("refX", 15)
	.attr("refY", -1.5)
	.attr("markerWidth", 6)
	.attr("markerHeight", 6)
	.attr("orient", "auto")
	.append("path")
	.attr("d", "M0,-5L10,0L0,5");



	// Draw links between the nodes
	var link = vis.selectAll(".link")
	.data(graph.links)
	.enter().append("line")
	.attr("class", "link")
	.text(function(d) { return d.label; })
	.style("stroke-width", function(d) { return Math.sqrt(d.value); })
	.attr("marker-end", "url(#arrow)")
	.on("click", function(d){
		//display_annotations_of_edge(d.source,d.target);
		add_annotationstolink(d);
		//d3.event.preventDefault();
	})
	.on("mouseout", fadeLinks(1));
	//.on("mouseover", fadeLinks(.1)).on("mouseout", fadeLinks(1));;
	//.on("click", highlightStmt(.1)).on("mouseout", fadeLinks(1));;
	
	
	var linktext = link.append("text")
	.text(function(d) {return d.label; })
	.attr("class", "linktext");
	
	linktext.attr("transform", function(d) 
			{
				var dx = (d.target.x - d.source.x),
				dy = (d.target.y - d.source.y);
				var dr = Math.sqrt(dx * dx + dy * dy);
				var sinus = dy/dr;
				var cosinus = dx/dr;
				var l = d.label.length*6;
				var offset = (1 - (l / dr )) / 2;
				var x=(d.source.x + dx*offset);
				var y=(d.source.y + dy*offset);
				return "translate(" + x + "," + y + ") matrix("+cosinus+", "+sinus+", "+-sinus+", "+cosinus+", 0 , 0)";
			});
	
	// Dragging the nodes
	var node_drag = d3.behavior.drag()
	.on("dragstart", dragstart)
	.on("drag", dragmove)
	.on("dragend", dragend);

	// Starts the drag
	// Means starts with force.stop
	function dragstart(d, i) {
		layout.stop(); // stops the force auto positioning before you start dragging
	}

	// Moves the node based on the user interaction
	// and places the node in the selected location
	function dragmove(d, i) {
		d.px += d3.event.dx;
		d.py += d3.event.dy;
		d.x += d3.event.dx;
		d.y += d3.event.dy; 
		tick(); // this is the key to make it work together with updating both px,py,x,y on d !
	}

	// End drag
	function dragend(d, i) {
		d.fixed = true; // of course set the node to fixed so the force doesn't include the node in its auto positioning stuff
		tick();
		layout.resume();
	}

	// Properties for the nodes
	var gnodes = vis.selectAll('g.gnode')
	.data(graph.nodes)
	.enter();

	var node = gnodes.append('circle')
	.attr("class", "node")
	.attr("r",10) 	
	.style("fill", function(d) { return color(d.group); })
	.call(node_drag)
	
	// works on right click
	.on("contextmenu", function(data, index) {

		rightClick(data);
		d3.event.preventDefault();
	})
	
	// Works on left click
	.on("click", function(d){

		display_annotations(d);
		conceptDescription(d);
	})
	
	.call(node_drag)
	.on("mouseover", fade(.1)).on("mouseout", fade(1));;
//	.call(force.drag);

	node.append("svg:circle")
	.attr("r", 10);


//	Appending labels to the nodes
	var text = gnodes.append("text")
	.call(node_drag)
	.text(function(d) { return d.name; });

//	Appending title to the nodes 
//	Mouse hover on the node shows the name of the node.
	node.append("title")
	.text(function(d) { return d.name; });

//	Properties for the position of the node
	function tick() {
		link.attr("x1", function(d) { return d.source.x; })
		.attr("y1", function(d) { return d.source.y; })
		.attr("x2", function(d) { return d.target.x; })
		.attr("y2", function(d) { return d.target.y; })
		.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
		.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });

		node.attr("transform", function(d) { return "translate(" + (d.x) + "," + d.y + ")"; });
		//.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
		//.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });
		text.attr("transform", function(d) { return "translate(" + (d.x) + "," + d.y + ")"; });
//		.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
//		.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });
		
		//linktext.attr("transform", function(d) { return "translate(" + (d.x) + "," + d.y + ")"; });
	};

	function  redraw() {
		console.log("here", d3.event.translate, d3.event.scale);
		vis.attr("transform",
				" scale(" + d3.event.scale + ")");
	};
//	Works on loading the network and placing the nodes randomly for view

	layout.on("tick", tick);

	function neighboring(a, b) {

		//	return linkedByIndex[a.index + "," + b.index];
		return link.some(function(d) {
			return (d.source === a && d.target === b);

		});
	}


	function fade(opacity) {
		return function(d, i) {
			vis.selectAll("circle, line").style("opacity", opacity);
			
			var stId = d.statementid;
			var associated_nodes = vis.selectAll('circle').filter(function(node) {
				for (var i = 0; i < stId.length; i++) {
				    if (($.inArray(stId[i], node.statementid)) > -1)
				    	return true;
				}
				return false;
			});
			associated_nodes.each(function(a) {
				d3.select(this).style("opacity", 1);
				
				var aIndex = a.index;
				
				var associated_links = vis.selectAll("line").filter(function(d) {
					return d.source.index == aIndex;
				});
				associated_links.each(function(d) {
					//unfade links and nodes connected to the current node
					d3.select(this).style("opacity", 1);
				});
			});

		};

	}


	function highlightStmt(opacity) {
		return function(d, i) {
			//fade all elements
			// d3.select(d.source).style("opacity", opacity);
			// d3.select(d.target).style("opacity", opacity);
			//fade all elements

			var snodes = vis.selectAll(".node, line").style("opacity", opacity);
			var source = d.source;
			var nodearray = [];
			var found = [];
			var statementId = [];

			//alert(source);
			statementId = source.statementid;
			/*var snodes = vis.selectAll(".node").each(function(d){

						for(var i in statementId){
							for(var j in d.statementid){
								if(statementId[i] == d.statementid[j]){
									nodearray.push(d);
									//alert(nodearray.length);
								}
							}
						}

						/*found = $.intersection(statementId, d.statementid);
						if(found.size()>0){
							nodearray.push(d);
						}*/

			/*var found = $.inArray(statementId, d.statementid);
						if(found > -1){
							nodearray.push(d);
							alert(nodearray.size());
							}*/

			/*if((d.statementid.indexOf(statementId))>0)
							{array.push(d);
							console.log(d.name);
							}
					});*/


			//var filtered = snodes.filter(function(d) { return d.statementid[0]==statementId;});
			//alert(filtered.size());

			var filtered = snodes.filter(function(d) { return d.statementid[0]==statementId;}).each(function(d) {
				//d3.select(this).style("opacity", 1);
				node.style("stroke-opacity", function(o) {
					return neighboring(d, o) ? 1 : opacity;
				});

				//link.style("stroke-opacity", opacity).style("stroke-opacity", function(o) {
				link.style("stroke-opacity", function(o) {
					return o.source === d || o.target === d ? 1 : opacity;
				});

			});

		};

	}


	/*function highlightStmt(opacity) {
					return function(d, i) {
						fade all elements
						d3.select(d.source).style("opacity", opacity);
						d3.select(d.target).style("opacity", opacity);
						//fade all elements
						vis.selectAll("circle, line").style("opacity", opacity);

						var associated_links = vis.selectAll("line").filter(function(d) {
							return  d.source.index == i;
							// return d.source.index == i || d.target.index == i;
						}).each(function(d) {
							//unfade links and nodes connected to the current node
							d3.select(this).style("opacity", 1);
							//THE FOLLOWING CAUSES: Uncaught TypeError: Cannot call method 'setProperty' of undefined
							//  d3.select(d.source).style("opacity", 1);
							//  d3.select(d.target).style("opacity", 1);
							node.style("opacity", function(o) {
								d3.select(o.source).style("opacity", 1);
							});
						});

					};

				}*/

	function fadeLinks(opacity) {
		return function(d, i) {
			//fade all elements
			//fade all elements
			vis.selectAll("circle, link").style("opacity", opacity);
//			alert(d.source);
//			alert(d.target);
//			alert(i);
			//alert("source:"+d.source.index);
			//alert("target:"+d.target.index);
			//	alert(i);
			var associated_links = vis.selectAll("link").filter(function(d) {
				//   return  d.source.index == i;
				return d.source.index == i || d.target.index == i;
			}).each(function(d) {
				alert("here");
				var stmtid = d.statementid;
				alert(stmtid);

				//unfade links and nodes connected to the current node
				// d3.select(this).style("opacity", 1);
				//THE FOLLOWING CAUSES: Uncaught TypeError: Cannot call method 'setProperty' of undefined
				//  d3.select(d.source).style("opacity", 1);
				//  d3.select(d.target).style("opacity", 1);
//				node.style("opacity", function(o) {
//				d3.select(o.source).style("opacity", 1);
//				});

			});

		};
	};

//	});
	function s4() {
		return Math.floor((1 + Math.random()) * 0x10000)
		.toString(16)
		.substring(1);
	};

	function guid() {
		return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
		s4() + '-' + s4() + s4() + s4();
	}
	function getShape(d){
		if(d.group == 0) {
			return 'rect';
		}else {
			return 'circle';
		}
	}

	function displayAllAnnotations(){
		var output = "<h3>All Annotations of Network</h3>";
		output+="<h5>Node/Relation Name --> Annotation Text</h5>";
		output+="</br>";
		$.ajax({
			url : path+"/auth/editing/getAllAnnotations/"+networkId,
			type : "GET",
			dataType: 'json',
			success : function(data) {
				/*var tr;

					$.each(data.text, function(key,value){

					            tr = $('<tr/>');

					            tr.append("<td>" + json[i].User_Name + "</td>");

					            tr.append("<td>" + json[i].score + "</td>");

					            tr.append("<td>" + json[i].team + "</td>");

					            $('annot_table').append(tr);

					        });*/
				var cnt = 0;
				output += "<ol>";
				$.each(data.text, function(key,value){
					//content += ++cnt +'.<li>'+value.name+'</li>'+value.text+'</li>'; 
					output+="<li>" + ++cnt + ")"+ " " + value.name + " "+ "-->" +" "+ value.text+"</li>"; 
				});
				output += "</ol>";
					$('#allannot_details').html(output);
			},
			error: function() {
				alert("error");
			}
		});
	}


	function displayAllAnnotationsNew(){
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
	
	function defineAnnotationsTable(){
		$('#annotationsTable')
		.dataTable(
				{
					"bJQueryUI" : true,
					"sPaginationType" : "full_numbers",
					"bAutoWidth" : false,
					"aoColumns" : [ {
						"sTitle" : "Name",
						"mDataProp" :"name",
					},{
						"sTitle" : "Text",
						"mDataProp" :"text",
					}, {
						"sTitle" : "Object Type",
						"mDataProp" :"objecttype",
					}],
				});
		//displayAllAnnotationsNew();
	}
	
	function displayItemData(){
		var output = "";
//		$.ajax({
//			url : path+"/auth/editing/getitemmetadata/"+networkId,
//			type : "GET",
//			dataType: 'json',
//			success : function(data) {
//				console.log(data);
//				//var cnt = 0;
//				//output += "<ol>";
//				$.each(data.text, function(key,value){
//					console.log("came in");
//					//output+="<li>" + "<b>File Name</b>" + " --> " + value.filename + "</li>"; 
//					//output+="<li>" + "<b>Author</b>" + " --> " + value.submitter + "</li>"; 
//					//output+="<li>" + "<b>Last Updated</b>" + " --> " + value.modifieddate + "</li>"; 
//					var row = $("<tr><td>" + value.filename + "</td><td>" + value.submitter +"</td><td>"+ value.modifieddate + "</td></tr>");
//					$('#metadataTable').append(row);
//				});
//				//output += "</ol>";
//				//$('#metadataTable').html(row);
//				
//				//drawTable(data.text);
//			},
//			error: function() {
//				alert("error");
//			}
//		});
	}
	
	function rightClick(d){
		var html = "";
		// If the node type is Predicate
		// We can annotate on whole relation or node

		if(d.group==1){

			html = "<div id='popup' title='Annotation' >" +
			"<input type='button' id='annot_node' value='Add Annotation to Node' /> " +			
			"</div>";
		}
		// Annotate on node
		else{
			html = "<div id='popup' title='Annotation'>" +
			"<input type='button' id='annot_node' value='Add Annotation to Node' /> " +
			"</br>" +
			"<input type='button' id='annot_relation' value='Add Annotation to Relation' /> " +
			"</br>" +
			"</div>";
		}				
		$('#inner-details').html(html);	
		// This function annotate for node
		// This works on annot_node tag in the pop.
		$('#annot_node,#annot_relation').click(function() {
			//Type = node
			var type1 ="node";

			// Making it unique, as getting the handle on $(#something).val() 
			// is not working for more than one node.
			var text1ID = "annotText_"+guid();
			var popupId = "popup1_"+guid();

			// Get annotation URL
			var getAnnotationUrl = path+"/auth/editing/getAnnotation/"+networkId;


			// Creating popup html content to facilitate adding annotation
			var html1 = "<div id='"+popupId+"' class='form-group' title='Annotation' style='display: none'>" +
			"<form id='annot_form' action=" + path
			+ "/auth/editing/saveAnnotation/";
			html1 += networkId + " method='POST' >";
			html1 += "<textarea name='annotText' id='"+text1ID+"'class='form-control' cols='15' rows='5'></textarea>";
			html1 += "<input  type='hidden' name='nodeid' id='nodeid' value="
				+ d.id + " />";
			html1 += "<input  type='hidden' name='nodename' id='nodename' value="
				+ d.name + " />";
			html1 += "<input type='button' id='annot_submit' value='submit'>";
			html1 += "</div></form>";

			// Appending to D3 view
			$('#inner-details').html(html1);
			var content = "<h3>Annotations</h3>";

			// ajax Call to get annotation for a node.id
			// Used to add the old annotation in to the popup view
			display_annotations(d);
			event.preventDefault();


			// Saves the relation annotation to DB
			$('#annot_submit').click(function(event) {
				var annottext = $('#'+text1ID+'').val();  
				var objecttype = "node";
				$.ajax({
					url : $('#annot_form').attr("action"),
					type : "POST",
					beforeSend: function(xhr) {
		                xhr.setRequestHeader(header, token);
		            },
					data :"nodename="+d.name+"&nodeid="+d.id+"&annotText="+annottext+"&objecttype="+objecttype,
					success : function() {
						$('#'+popupId+'').dialog('close');
						displayAllAnnotationsNew();
						display_annotations(d);
					},
					error: function() {
						alert("error");
					}
				});
				event.preventDefault();

			});


			$('#annot_details').html(content);
			event.preventDefault();

			// Popup decoration effects
			$( '#'+popupId+'' ).show( "slow" );					
			$('#'+popupId+'').dialog({
				open: function(event, ui) {
					$('.ui-dialog-titlebar-close').css({'text-decoration':'block', 'right':'45px', 'height':'21px', 'width': '20px'}); 
				}
			});
		});


		$('#annot_relation').click(function() {
			var objecttype = "relation";

			
//			var associated_links = vis.selectAll("line").filter(function(d) {
//				return  d.source.index == i;
//				// return d.source.index == i || d.target.index == i;
//			}).each(function(d) {
//				if(d.label == "subject")
//				
//				
//				});
			// Making it unique, as getting the handle on $(#something).val() 
			// is not working for more than one node.
			var text1ID = "annotText_"+guid();
			var popupId = "popup2_"+guid();

			// Creating popup html content to facilitate adding annotation
			var html2 = "<div id='"+popupId+"' title='Annotation' style='display: none'><form id='annot_form' action=" + path
			+ "/auth/editing/saveAnnotation/";
			html2 += networkId + " method='POST' >";
			html2 += "<p id='message'></p>";
			html2 += "<textarea name='annotText' id='"+text1ID+"' cols='15' rows='15'></textarea>";
			html2 += "<input  type='hidden' name='nodename' id='nodename' value="
				+ node.id + " />";
			html2 += "<input type='submit' id='annot_submit1' value='submit'>";
			html2 += "</form></div>";

			// Sending the HTML code to D3 
			$('#inner-details').html(html2);
			var content = "<h3>Annotations</h3>";
			// Ajax call to get annotation for node.id
			// Used to add the old annotation in to the popup view
			$.ajax({
				url : path+"/auth/editing/getAnnotation/"+networkId,
				type : "GET",
				data: "nodeid="+d.id+"&objecttype="+type1,
				dataType: 'json',
				success : function(data) {
					var cnt = 0;
					content += "<ol>";
					$.each(data.text, function(key,value){
						content += ++cnt +'.<li>'+value.name+'</li>';  
					});
					content += "</ol>";
						$('#annot_details').html(content);
				},
				error: function() {
					alert("error");
				}
			});
			

			// Saves the relation annotation to DB
			$('#annot_submit1').click(function(event) {
				var annottext = $('#'+text1ID+'').val();  
				var type ="relation";
				var edgeid = "";
				$.ajax({
					url : $('#annot_form').attr("action"),
					type : "POST",
					beforeSend: function(xhr) {
		                xhr.setRequestHeader(header, token);
		            },
					data :"objecttype="+objecttype+"&nodename="+node.id+"&annotText="+annottext+"&type="+type+"&edgeid="+edgeid,
					success : function() {
						$('#'+popupId+'').dialog('close');
						displayAllAnnotationsNew();
					},
					error: function() {
						alert("error");
					}
				});

				event.preventDefault();

			});

			// Popup decoration effects
			$( '#'+popupId+'' ).show( "slow" );
			$('#'+popupId+'').dialog({
				open: function(event, ui) {
					$('.ui-dialog-titlebar-close').css({'text-decoration':'block', 'right':'45px', 'height':'21px', 'width': '20px'}); 
				}
			});

		});
		$('#popup').dialog();

	}


	function display_annotations(d){
		var objecttype = "node";
		var getAnnotationUrl = path+"/auth/editing/getAnnotation/"+networkId;
		var annotationDesc = "<h5>Annotations</h5>";
		var annotationContent = "<textarea id="+'"annotationtextarea"'+" class='form-control' cols=40 rows=5 readonly>";  /* style='background:#FFFFFF; border: 1px solid #dddddd;'*/
		// ajax Call to get annotation for a node.id
		// Used to add the old annotation in to the popup view
		$.ajax({
			url : getAnnotationUrl,
			type : "GET",
			data: "nodeid="+d.id+"&objecttype="+objecttype,
			dataType: 'json',
			success : function(data) {
				var cnt = 0;

				$.each(data.text, function(key,value){
					annotationContent += ++cnt +'. '+value.name;
					annotationContent += "\n";
				});
				annotationContent += "</textarea>";
				$('#annot_desc').html(annotationDesc);
				$('#annot_details').html(annotationContent);
			},
			error: function() {
				alert("error");
			}

		});




	}

	function conceptDescription(d){
		lemma = d.name;

		var descHeading = "<h5>Description of Node</h5>";

		var lemmaName="<h5> Node name : "+lemma+"</h5>";
		var conceptDesc = "<textarea id="+'"conceptdescTextArea"'+" class='form-control' cols=40 rows=5 readonly>";

		// This is done to replace all dot (.) with dollar ($)
		// Since our spring controller would ignore any data after dot (.)
		lemma = lemma.replace(".","$");

		// Ajax call for getting description of the node
		// Note: this ajax call has async = false
		// this allow variables to be assigned inside the ajax and 
		// accessed outside
		$.ajax({
			url : path+"/sites/network/getconcept/"+lemma,
			//url : path+"/rest/editing/getconcept/PHIL D. PUTWAIN",
			type : "GET",
			success : function(data) {
				conceptDesc+=data + "</textarea>";
				$('#lemma_name').html(lemmaName);
				$('#desc_heading').html(descHeading);
				$('#concept_desc').html(conceptDesc);
			},
			error: function() {
				alert("error");
			}
		});

	}
	function add_annotationstolink(d){
		//Type = node
		var type1 ="edge";
		// Making it unique, as getting the handle on $(#something).val() 
		// is not working for more than one edge.
		var text1ID = "annotText_"+guid();
		var popupId = "popup1_"+guid();

		// Get annotation URL
		var getAnnotationUrl = path+"/auth/editing/getAnnotationOfEdge/"+networkId;


		// Creating popup html content to facilitate adding annotation
		var html = "<div id='"+popupId+"' title='Annotation' >" +//style='display: none'
		"<form id='annot_edge_form' action=" + path
		+ "/auth/editing/saveAnnotationToEdge/";
		html += networkId + " method='GET' >";
		html += "<textarea name='annotText' id='"+text1ID+"' cols='15' rows='15' ></textarea>";
		html+= "<input type='button' id='annot_submit_edge' value='submit'>";
		html += "</div></form>";

		// Appending to D3 view
		$('#inner-details').html(html);
		var content = "";
	//	var content = "<h3>Annotations</h3>";

		// ajax Call to get annotation for a node.id
		// Used to add the old annotation in to the popup view
		//display_annotations(d);
		//event.preventDefault();


		// Saves the relation annotation to DB
		$('#annot_submit_edge').click(function(event) {
			//alert($('annot_edge_form').attr("action"));
		//	var annottext = $('#'+text1ID+'').val();  
			var sourceid = d.source.id;
			var sourcename = d.source.name;
			var targetid = d.target.id;
			var targetname = d.target.name;
			var targettype = d.target.group;
			var objecttype = "edge";
			var annottext = $('#'+text1ID).val();
			var url = path+"/auth/editing/saveAnnotationToEdge/"+networkId;
			var input = "annottext="+annottext+"&sourceid="+sourceid+"&sourcename="+sourcename+"&targetid="+targetid+"&targetname="+targetname+"&targettype="+targettype+"&objecttype="+objecttype;
			$.ajax({
				url : path+"/auth/editing/saveAnnotationToEdge/"+networkId,
				type : "POST",
				beforeSend: function(xhr) {
	                xhr.setRequestHeader(header, token);
	            },
				data : input,
				contentType: 'application/json; charset=utf-8',
				success : function() {
					$('#'+popupId+'').dialog('close');
				//	displayAllAnnotations();
				//	display_annotations(d);
				},
				error: function (xhr, ErrorText, thrownError) {
			        alert(xhr.status);
			        alert(thrownError); 
				}
			});
			event.preventDefault();

		});


		$('#annot_details').html(content);
		event.preventDefault();

		// Popup decoration effects
		$( '#'+popupId+'' ).show( "slow" );					
		$('#'+popupId+'').dialog({
			open: function(event, ui) {
				$('.ui-dialog-titlebar-close').css({'text-decoration':'block', 'right':'45px', 'height':'21px', 'width': '20px'}); 
			}
		});
	}

	function display_annotations_of_edge(source,target){
		var objecttypetype= "edge";
		var getAnnotationUrl = path+"/auth/editing/getAnnotationOfEdge/"+networkId;
		var content = "<h3>Annotations</h3>";
		// ajax Call to get annotation for a node.id
		// Used to add the old annotation in to the popup view
		$.ajax({
			url : getAnnotationUrl,
			type : "GET",
			data: "sourceid="+source.id+"targetid="+target.id+"&type="+objecttypetype,
			dataType: 'json',
			success : function(data) {
				var cnt = 0;
				content += "<ol>";
				$.each(data.text, function(key,value){
					content += ++cnt +'.<li>'+value.name+'</li>';  
				});
				content += "</ol>";
					$('#annot_details').html(content);
			},
			error: function() {
				alert("error");
			}

		});

		$('#annot_details').html(content);


	}

}