/**
 * @Author : Sowjanya Ambati
 * @Author : Dwaraka Lohith
 * 
 * Data visualization in D3 JQuery.
 * This javascript should work on displaying different layouts.
 * 
 */

/**
 * @Author : Sowjanya Ambati
 * @Author : Dwaraka Lohith
 * 
 * Data visualization in D3 JQuery. This javascript should work on displaying
 * different layouts.
 * 
 */

function d3visualizepublic(graph, networkId, path, type, unixName) {
	if (graph == null) {
		alert("no network");
	}
	// Layout size
	var width = $('#chart').parent().width();
	var height = $('#chart').parent().height();
	var layout;
	var color = d3.scale.category20();
	var svg_id = "svg_id";
	// Preparing the force directed graph
	if (type == "force") {
		layout = d3.layout.force().charge(-150).linkDistance(200).size(
				[ width, height ]);

		layout.nodes(graph.nodes).links(graph.links).start();
	} // tree layout if need we can change the layout
	else if (type == "tree") {
		layout = d3.layout.tree();
		layout.nodes(graph.nodes).links(graph.links).start();
	}

	var vis = d3.select("#chart").append("svg:svg").attr("width", "100%").attr(
			"height", "100%").append('svg:g')
	// Zoom in and out
	.call(d3.behavior.zoom().on("zoom", redraw)).append('svg:g');

	var div1 = d3.select("#allannot_details");

	d3.select("#chart").on("click", function() {
		return div1.style("visibility", "visible");
	});

	/*
	 * var expand = d3.select("#chart") .on("click", function(){
	 * 
	 * $("chart").show(); $("allannot_details").hide(); });
	 */

	// Prepare the arrow
	vis.append("defs").selectAll("marker").data([ "arrow" ]).enter().append(
			"marker").attr("id", String).attr("viewBox", "0 -5 10 10").attr(
			"refX", 15).attr("refY", -1.5).attr("markerWidth", 6).attr(
			"markerHeight", 6).attr("orient", "auto").append("path").attr("d",
			"M0,-5L10,0L0,5");

	// Draw links between the nodes
	var link = vis.selectAll(".link").data(graph.links).enter().append("line")
			.attr("class", "link").text(function(d) {
				return d.label;
			}).style("stroke-width", function(d) {
				return Math.sqrt(d.value);
			}).attr("marker-end", "url(#arrow)").on("click", function(d) {
				// display_annotations_of_edge(d.source,d.target);
				// d3.event.preventDefault();
			}).on("mouseout", fadeLinks(1));
	// .on("mouseover", fadeLinks(.1)).on("mouseout", fadeLinks(1));;
	// .on("click", highlightStmt(.1)).on("mouseout", fadeLinks(1));;

	var linktext = link.append("text").text(function(d) {
		return d.label;
	}).attr("class", "linktext");

	linktext.attr("transform", function(d) {
		var dx = (d.target.x - d.source.x), dy = (d.target.y - d.source.y);
		var dr = Math.sqrt(dx * dx + dy * dy);
		var sinus = dy / dr;
		var cosinus = dx / dr;
		var l = d.label.length * 6;
		var offset = (1 - (l / dr)) / 2;
		var x = (d.source.x + dx * offset);
		var y = (d.source.y + dy * offset);
		return "translate(" + x + "," + y + ") matrix(" + cosinus + ", "
				+ sinus + ", " + -sinus + ", " + cosinus + ", 0 , 0)";
	});

	// Dragging the nodes
	var node_drag = d3.behavior.drag().on("dragstart", dragstart).on("drag",
			dragmove).on("dragend", dragend);

	// Starts the drag
	// Means starts with force.stop
	function dragstart(d, i) {
		layout.stop(); // stops the force auto positioning before you start
						// dragging
	}

	// Moves the node based on the user interaction
	// and places the node in the selected location
	function dragmove(d, i) {
		d.px += d3.event.dx;
		d.py += d3.event.dy;
		d.x += d3.event.dx;
		d.y += d3.event.dy;
		tick(); // this is the key to make it work together with updating both
				// px,py,x,y on d !
	}

	// End drag
	function dragend(d, i) {
		d.fixed = true; // of course set the node to fixed so the force doesn't
						// include the node in its auto positioning stuff
		tick();
		layout.resume();
	}

	// Properties for the nodes
	var gnodes = vis.selectAll('g.gnode').data(graph.nodes).enter();

	var node = gnodes.append('circle').attr("class", "node").attr("r", 10)
			.style("fill", function(d) {
				return color(d.group);
			}).call(node_drag)

			// Works on left click
			.on("click", function(d) {

				conceptDescription(d, path);
				getTexts(d, path, unixName);
			})

			.call(node_drag).on("mouseover", fade(.1)).on("mouseout", fade(1));
	;
	// .call(force.drag);

	node.append("svg:circle").attr("r", 10);

	// Appending labels to the nodes
	var text = gnodes.append("text").call(node_drag).text(function(d) {
		return d.name;
	});

	// Appending title to the nodes
	// Mouse hover on the node shows the name of the node.
	node.append("title").text(function(d) {
		return d.name;
	});

	// Properties for the position of the node
	function tick() {
		link.attr("x1", function(d) {
			return d.source.x;
		}).attr("y1", function(d) {
			return d.source.y;
		}).attr("x2", function(d) {
			return d.target.x;
		}).attr("y2", function(d) {
			return d.target.y;
		}).attr("cx", function(d) {
			return d.x = Math.max(6, Math.min(width - 6, d.x));
		}).attr("cy", function(d) {
			return d.y = Math.max(6, Math.min(height - 6, d.y));
		});

		node.attr("transform", function(d) {
			return "translate(" + (d.x) + "," + d.y + ")";
		});
		// .attr("cx", function(d) { return d.x = Math.max(6, Math.min(width -
		// 6, d.x)); })
		// .attr("cy", function(d) { return d.y = Math.max(6, Math.min(height -
		// 6, d.y)); });
		text.attr("transform", function(d) {
			return "translate(" + (d.x) + "," + d.y + ")";
		});
		// .attr("cx", function(d) { return d.x = Math.max(6, Math.min(width -
		// 6, d.x)); })
		// .attr("cy", function(d) { return d.y = Math.max(6, Math.min(height -
		// 6, d.y)); });

		// linktext.attr("transform", function(d) { return "translate(" + (d.x)
		// + "," + d.y + ")"; });
	}
	;

	function redraw() {
		vis.attr("transform", " scale(" + d3.event.scale + ")");
	}
	;
	// Works on loading the network and placing the nodes randomly for view

	layout.on("tick", tick);

	function neighboring(a, b) {

		// return linkedByIndex[a.index + "," + b.index];
		return link.some(function(d) {
			return (d.source === a && d.target === b);

		});
	}

	function fade(opacity) {
		return function(d, i) {
			vis.selectAll("circle, line").style("opacity", opacity);

			var stId = d.statementid;
			var associated_nodes = vis.selectAll('circle').filter(
					function(node) {
						for (var i = 0; i < stId.length; i++) {
							if (($.inArray(stId[i], node.statementid)) > -1)
								return true;
						}
						return false;
					});
			associated_nodes.each(function(a) {
				d3.select(this).style("opacity", 1);

				var aIndex = a.index;

				var associated_links = vis.selectAll("line").filter(
						function(d) {
							return d.source.index == aIndex;
						});
				associated_links.each(function(d) {
					// unfade links and nodes connected to the current node
					d3.select(this).style("opacity", 1);
				});
			});

		};

	}

	function highlightStmt(opacity) {
		return function(d, i) {
			// fade all elements
			// d3.select(d.source).style("opacity", opacity);
			// d3.select(d.target).style("opacity", opacity);
			// fade all elements

			var snodes = vis.selectAll(".node, line").style("opacity", opacity);
			var source = d.source;
			var nodearray = [];
			var found = [];
			var statementId = [];

			// alert(source);
			statementId = source.statementid;
			/*
			 * var snodes = vis.selectAll(".node").each(function(d){
			 * 
			 * for(var i in statementId){ for(var j in d.statementid){
			 * if(statementId[i] == d.statementid[j]){ nodearray.push(d);
			 * //alert(nodearray.length); } } }
			 * 
			 * /*found = $.intersection(statementId, d.statementid);
			 * if(found.size()>0){ nodearray.push(d); }
			 */

			/*
			 * var found = $.inArray(statementId, d.statementid); if(found >
			 * -1){ nodearray.push(d); alert(nodearray.size()); }
			 */

			/*
			 * if((d.statementid.indexOf(statementId))>0) {array.push(d);
			 * console.log(d.name); } });
			 */

			// var filtered = snodes.filter(function(d) { return
			// d.statementid[0]==statementId;});
			// alert(filtered.size());
			var filtered = snodes.filter(function(d) {
				return d.statementid[0] == statementId;
			}).each(function(d) {
				// d3.select(this).style("opacity", 1);
				node.style("stroke-opacity", function(o) {
					return neighboring(d, o) ? 1 : opacity;
				});

				// link.style("stroke-opacity", opacity).style("stroke-opacity",
				// function(o) {
				link.style("stroke-opacity", function(o) {
					return o.source === d || o.target === d ? 1 : opacity;
				});

			});

		};

	}

	function fadeLinks(opacity) {
		return function(d, i) {
			// fade all elements
			// fade all elements
			vis.selectAll("circle, link").style("opacity", opacity);
			// alert(d.source);
			// alert(d.target);
			// alert(i);
			// alert("source:"+d.source.index);
			// alert("target:"+d.target.index);
			// alert(i);
			var associated_links = vis.selectAll("link").filter(function(d) {
				// return d.source.index == i;
				return d.source.index == i || d.target.index == i;
			}).each(function(d) {
				alert("here");
				var stmtid = d.statementid;
				alert(stmtid);

				// unfade links and nodes connected to the current node
				// d3.select(this).style("opacity", 1);
				// THE FOLLOWING CAUSES: Uncaught TypeError: Cannot call method
				// 'setProperty' of undefined
				// d3.select(d.source).style("opacity", 1);
				// d3.select(d.target).style("opacity", 1);
				// node.style("opacity", function(o) {
				// d3.select(o.source).style("opacity", 1);
				// });

			});

		};
	}
	;

	// });
	function s4() {
		return Math.floor((1 + Math.random()) * 0x10000).toString(16)
				.substring(1);
	}
	;

	function guid() {
		return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4()
				+ s4() + s4();
	}
	function getShape(d) {
		if (d.group == 0) {
			return 'rect';
		} else {
			return 'circle';
		}
	}

	function defineAnnotationsTable() {
		$('#annotationsTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false,
			"aoColumns" : [ {
				"sTitle" : "Name",
				"mDataProp" : "name",
			}, {
				"sTitle" : "Text",
				"mDataProp" : "text",
			}, {
				"sTitle" : "Object Type",
				"mDataProp" : "objecttype",
			} ],
		});
		// displayAllAnnotationsNew();
	}

	function displayItemData() {

	}

	function defineMetadataTable() {
		$('#metadataTable').dataTable({
			"bJQueryUI" : true,
			"sPaginationType" : "full_numbers",
			"bAutoWidth" : false,
			"aoColumns" : [ {
				"sTitle" : "FileName",
				"mDataProp" : "filename",
			}, {
				"sTitle" : "Author",
				"mDataProp" : "submitter",
			}, {
				"sTitle" : "Last Modified Date",
				"mDataProp" : "modifieddate",
			} ],
		});
	}

}