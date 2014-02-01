//Author : Sowjanya Ambati


function d3init(graph, networkId, path) {
	console.log("init");

	// Layout size
	var width = 1000,
	height = 900;

	var color = d3.scale.category20();

	// Preparing the force directed graph
	var force = d3.layout.force()
	.charge(-150)
	.linkDistance(200)
	.size([width, height]);


	var svg = d3.select("#chart").append("svg")
	.attr("width", width)
	.attr("height", height);


	alert(graph);
	force
	.nodes(graph.nodes)
	.links(graph.links)
	.start();


	// Prepare the arrow
	svg.append("defs").selectAll("marker")
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
	var link = svg.selectAll(".link")
	.data(graph.links)
	.enter().append("line")
	.attr("class", "link")
	.style("stroke-width", function(d) { return Math.sqrt(d.value); })
	.attr("marker-end", "url(#arrow)")
	.on("mouseover", fade(.1)).on("mouseout", fade(1));;
	// Dragging the nodes
	var node_drag = d3.behavior.drag()
	.on("dragstart", dragstart)
	.on("drag", dragmove)
	.on("dragend", dragend);

	// Starts the drag
	// Means starts with force.stop
	function dragstart(d, i) {
		//force.stop() // stops the force auto positioning before you start dragging
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
		//d.fixed = true; // of course set the node to fixed so the force doesn't include the node in its auto positioning stuff
		//tick();
		//force.resume();
	}

	// Properties for the nodes







	//.classed('gnode', true);

//	var node = gnodes.append('path')
//	attr("d", d3.svg.symbol().type(function(d) { return d3.svg.symbolTypes[d.group]; }))
//	.attr("class", "node")
//	.attr("width", 10)
//	.attr("height", 10)
//	.attr("r", 5)
//	.style("fill", function(d) { return color(d.group); },"size",function(d) { return size(d.group); })
	var gnodes = svg.selectAll('g.gnode')
	.data(graph.nodes)
	.enter();

	var node = gnodes.append('path')
	.attr("class", "node")
	.attr("d", d3.svg.symbol()
			.type(function(d) {
				var type = d.group;
				if (type == 0){
					type = 2;
				}
				else{
					type = 0;
				}
				return d3.svg.symbolTypes[type]; }))
				.attr("r", 20)
				.style("fill", function(d) { return color(d.group); })
				.call(node_drag)
				// works on right click
				.on("contextmenu", function(data, index) {
					
					rightClick(data);
					
				})
				// Works on left click
				.on("click", function(d){

					var type1 ="node";
					// Making it unique, as getting the handle on $(#something).val() 
					// is not working for more than one node.
					var text1ID = "annotText_"+guid();
					var popupId = "popup1_"+guid();

					// Get annotation URL
					var getAnnotationUrl = path+"/auth/editing/getAnnotation/"+networkId;


					// Creating popup html content to facilitate adding annotation
					var html1 = "<div id='"+popupId+"' title='Annotation' style='display: none'>" +
					"<form id='annot_form' action=" + path
					+ "/auth/editing/saveAnnotation/";
					html1 += networkId + " method='POST' >";
					html1 += "<textarea name='annotText' id='"+text1ID+"' cols='15' rows='15'></textarea>";
					html1 += "<input  type='hidden' name='nodename' id='nodename' value="
						+ d.id + " />";
					html1 += "<input type='button' id='annot_submit' value='submit'>";
					html1 += "</div></form>";

					$('#inner-details').html(html1);	
					$.ajax({
						url : getAnnotationUrl,
						type : "GET",
						data: "nodeid="+d.id+"&type="+type1,
						success : function(data) {
							$('#'+text1ID+'').val(data); 
						},
						error: function() {
							alert("error");
						}
					});
					event.preventDefault();

					// Saves the relation annotation to DB
					$('#annot_submit').click(function(event) {
						var annottext = $('#'+text1ID+'').val();  
						$.ajax({
							url : $('#annot_form').attr("action"),
							type : "POST",
							data :"nodename="+d.id+"&annotText="+annottext+"&type=node",
							success : function() {
								alert("done");
								$('#'+popupId+'').dialog('close');
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

					var lemma = d.name;
					//alert(lemma);
					// This is done to replace all dot (.) with dollar ($)
					// Since our spring controller would ignore any data after dot (.)
					lemma = lemma.replace(".","$");

					// Ajax call for getting description of the node
					// Note: this ajax call has async = false
					// this allow variables to be assigned inside the ajax and 
					// accessed outside
					$(document).ready(function() {	
						$.ajax({
							url : path+"/rest/editing/getconcept/"+lemma,
							//url : path+"/rest/editing/getconcept/PHIL D. PUTWAIN",
							type : "GET",
							async: false,
							success : function(data) {
								desc = data;
							},
							error: function() {
								alert("error");
							}
						});
						description = "<h8>DESCRIPTION : </h8> "+desc
						+" <p>";
					});
					var html = "<h4>" + d.name
					+ "</h4>"+ description
					$('#inner-details').html(html);


				})
				.call(node_drag)
				.on("mouseover", fade(.1)).on("mouseout", fade(1));;
//				.call(force.drag);

//				Appending labels to the nodes
				var text = gnodes.append("text")
				.call(node_drag)
				.text(function(d) { return d.name; });

//				Appending title to the nodes 
//				Mouse hover on the node shows the name of the node.
				node.append("title")
				.text(function(d) { return d.name; });

//				Properties for the position of the node
				function tick() {
					link.attr("x1", function(d) { return d.source.x; })
					.attr("y1", function(d) { return d.source.y; })
					.attr("x2", function(d) { return d.target.x; })
					.attr("y2", function(d) { return d.target.y; })
					.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
					.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });

					node.attr("transform", function(d) { return "translate(" + (d.x) + "," + d.y + ")"; })
					.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
					.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });
					text.attr("transform", function(d) { return "translate(" + (d.x) + "," + d.y + ")"; })
					.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
					.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });
				};

//				Works on loading the network and placing the nodes randomly for view
				force.on("tick", tick);

				function neighboring(a, b) {

					//	return linkedByIndex[a.index + "," + b.index];
					return link.some(function(d) {
						return (d.source === a && d.target === b)

					});
				}


				function fade(opacity) {
					return function(d, i) {
						//fade all elements
//						d3.select(d.source).style("opacity", opacity);
//						d3.select(d.target).style("opacity", opacity);
						//fade all elements
						svg.selectAll("circle, line").style("opacity", opacity);

						var associated_links = svg.selectAll("line").filter(function(d) {
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

				}
				function fadeLinks(opacity) {
					return function(d, i) {
						//fade all elements
						//fade all elements
						svg.selectAll("circle, line").style("opacity", opacity);
						alert(d.source);
						alert(d.target);
						alert(i);
						var associated_links = svg.selectAll("line").filter(function(d) {
							//   return  d.source.index == i;
							return d.source.index == i || d.target.index == i;
						}).each(function(d) {
							//unfade links and nodes connected to the current node
							// d3.select(this).style("opacity", 1);
							//THE FOLLOWING CAUSES: Uncaught TypeError: Cannot call method 'setProperty' of undefined
							//  d3.select(d.source).style("opacity", 1);
							//  d3.select(d.target).style("opacity", 1);
							node.style("opacity", function(o) {
								d3.select(o.source).style("opacity", 1);
							});
						});

					};
				};

//				});
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
					alert(d.group);
					if(d.group == 0) {
						return 'rect';
					}else {
						return 'circle';
					}
				}
				
				function rightClick(d){
					alert("right click");
					var html = "";
					// If the node type is Predicate
					// We can annotate on whole relation or node
					if(d.group==1){
						
						html = "<div id='popup' title='Annotation' >" +
						"<input type='button' id='annot_node' value='Add Annotation to Node' /> " +
						"</br>" +
						"<input type='button' id='annot_relation' value='Add Annotation to Relation' /> " +
						"</br>" +
						"</div>";
					}
					// Annotate on node
					else{
						html = "<div id='popup' title='Annotation'>" +
						"<input type='button' id='annot_node' value='Add Annotation to Node' /> " +
						"</div>";
					}				
					$('#inner-details').html(html);	
					// This function annotate for node
					// This works on annot_node tag in the pop.
					$('#annot_node').click(function() {

						//Type = node
						var type1 ="node";

						// Making it unique, as getting the handle on $(#something).val() 
						// is not working for more than one node.
						var text1ID = "annotText_"+guid();
						var popupId = "popup1_"+guid();

						// Get annotation URL
						var getAnnotationUrl = path+"/auth/editing/getAnnotation/"+networkId;


						// Creating popup html content to facilitate adding annotation
						var html1 = "<div id='"+popupId+"' title='Annotation' style='display: none'>" +
						"<form id='annot_form' action=" + path
						+ "/auth/editing/saveAnnotation/";
						html1 += networkId + " method='POST' >";
						html1 += "<textarea name='annotText' id='"+text1ID+"' cols='15' rows='15'></textarea>";
						html1 += "<input  type='hidden' name='nodename' id='nodename' value="
							+ node.id + " />";
						html1 += "<input type='button' id='annot_submit' value='submit'>";
						html1 += "</div></form>";

						// Appending to D3 view
						$('#inner-details').html(html1);
						
						// ajax Call to get annotation for a node.id
						// Used to add the old annotation in to the popup view
						$.ajax({
							url : getAnnotationUrl,
							type : "GET",
							data: "nodeid="+node.id+"&type="+type1,
							success : function(data) {
								$('#'+text1ID+'').val(data); 
							},
							error: function() {
								alert("error");
							}
						});
						event.preventDefault();

						// Saves the relation annotation to DB
						$('#annot_submit').click(function(event) {
							var annottext = $('#'+text1ID+'').val();  
							$.ajax({
								url : $('#annot_form').attr("action"),
								type : "POST",
								data :"nodename="+node.id+"&annotText="+annottext+"&type=node",
								success : function() {
									alert("done");
									$('#'+popupId+'').dialog('close');
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


					$('#annot_relation').click(function() {
						var type1 = "relation";

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
						$('#inner-details').html(html2);;

						// Ajax call to get annotation for node.id
						// Used to add the old annotation in to the popup view
						$.ajax({
							url : path+"/auth/editing/getAnnotation/"+networkId,
							type : "GET",
							data: "nodeid="+node.id+"&type="+type1,
							success : function(data) {
								$('#'+text1ID+'').append(data);
							},
							error: function() {
								alert("error");
							}
						});

						// Saves the relation annotation to DB
						$('#annot_submit1').click(function(event) {
							var annottext = $('#'+text1ID+'').val();  
							$.ajax({
								url : $('#annot_form').attr("action"),
								type : "POST",
								data :"nodename="+node.id+"&annotText="+annottext+"&type=relation",
								success : function() {
									alert("done");
									$('#'+popupId+'').dialog('close');
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


}