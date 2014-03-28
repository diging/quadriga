
/**
 * @Author : Sowjanya Ambati
 * @Author : Dwaraka Lohith
 * 
 * Data visualization in D3 JQuery.
 * This javascript should work on displaying different layouts.
 * 
 */


function d3visualize(graph, networkId, path,type) {
	
	// Layout size
	var width = 1000,
	height = 900;
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
		displayAllAnnotations();
	} //  tree layout if need we can change the layout
	else if(type=="tree"){
		layout = d3.layout.tree();
		layout
		.nodes(graph.nodes)
		.links(graph.links)
		.start();
	}
	

	var svg = d3.select("#chart").append("svg")
	.attr("width", width)
	.attr("height", height);

	var div1 = d3.select("#allannot_details");
	
	d3.select("#chart").on("click", function(){return div1.style("visibility", "visible");});


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

	// Draw links between the nodes
	var link = svg.selectAll(".link")
	.data(graph.links)
	.enter().append("line")
	.attr("class", "link")
	.style("stroke-width", function(d) { return Math.sqrt(d.value); })
	.attr("marker-end", "url(#arrow)")
	

	// Properties for the nodes

	var gnodes = svg.selectAll('g.gnode')
	.data(graph.nodes)
	.enter();

	var node = gnodes.append('circle')
	.attr("class", "node")
	.attr("r",10) 	
			.style("fill", function(d) { return color(d.group); })
				.call(node_drag)
				.on("click", function(d){
					display_annotations(d);
					conceptDescription(d);
				});

				node.append("svg:circle")
			      .attr("r", 10)
			      
				
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
					//.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
					//.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });
					text.attr("transform", function(d) { return "translate(" + (d.x) + "," + d.y + ")"; })
//					.attr("cx", function(d) { return d.x = Math.max(6, Math.min(width - 6, d.x)); })
//					.attr("cy", function(d) { return d.y = Math.max(6, Math.min(height - 6, d.y)); });
				};

//				Works on loading the network and placing the nodes randomly for view
				
				layout.on("tick", tick);

				

				
				
				
				
				function s4() {
					return Math.floor((1 + Math.random()) * 0x10000)
					.toString(16)
					.substring(1);
				};

				
				function displayAllAnnotations(){
					var output = "<h3>All Annotations of Network</h3>";
					output+="<h5>Node/Relation Name --> Annotation Text</h5>";
					output+="</br>";
					$.ajax({
					url : path+"/auth/editing/getAllAnnotations/"+networkId,
					type : "GET",
					dataType: 'json',
					success : function(data) {
					
					var cnt = 0;
					output += "<ol>";
					$.each(data.text, function(key,value){
					    output+="<li>" + ++cnt + ")"+ " " + value.name + " "+ "-->" +" "+ value.text+"</li>"; 
					                });
					output += "</ol>"
					$('#allannot_details').html(output);
					},
					error: function() {
					alert("error");
					}
					});
				}

				
				function display_annotations(d){
					var type1= "node";
					var getAnnotationUrl = path+"/auth/editing/getAnnotation/"+networkId;
					var content = "<h3>Annotations</h3>";
					// ajax Call to get annotation for a node.id
					// Used to add the old annotation in to the popup view
					$.ajax({
						url : getAnnotationUrl,
						type : "GET",
						data: "nodeid="+d.id+"&type="+type1,
						dataType: 'json',
						success : function(data) {
							var cnt = 0;
							content += "<ol>";
						$.each(data.text, function(key,value){
			                    content += ++cnt +'.<li>'+value.name+'</li>';  
		                });
							content += "</ol>"
							$('#annot_details').html(content);
						},
						error: function() {
							alert("error");
						}
						
					});
					
					$('#annot_details').html(content);
					
					
				}
				
				function conceptDescription(d){
					lemma = d.name;
					
					// This is done to replace all dot (.) with dollar ($)
					// Since our spring controller would ignore any data after dot (.)
					lemma = lemma.replace(".","$");
					
					var output = "<h3>Description of Node</h3>";
					output+="<h5>"+lemma+"</h5>";
					
					// Ajax call for getting description of the node
					// Note: this ajax call has async = false
					// this allow variables to be assigned inside the ajax and 
					// accessed outside
						$.ajax({
							url : path+"/auth/editing/getconcept/"+lemma,
							//url : path+"/rest/editing/getconcept/PHIL D. PUTWAIN",
							type : "GET",
							success : function(data) {
								output+="<h8>" + data + "</h8>";
								$('#concept_desc').html(output);
							},
							error: function() {
								alert("error");
							}
						});
						
				}
				function add_annotationstolink(d){
					alert("link");
				}
}