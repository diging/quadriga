//Author : Sowjanya Ambati


function d3init(json1, networkId, path) {
	console.log("init");

	// Layout size
	var width = 960,
	height = 700;

	var color = d3.scale.category20();

	// Preparing the force directed graph
	var force = d3.layout.force()
	.charge(-120)
	.linkDistance(100)
	.size([width, height]);


	var svg = d3.select("#chart").append("svg")
	.attr("width", width)
	.attr("height", height);

	d3.json(path+"/resources/txt-layout/data.json", function(error, graph) {
		force
		.nodes(graph.nodes)
		.links(graph.links)
		.start();


		// Prepare the arrow
		svg.append("defs").selectAll("marker")
		.data(["arrow"])
		.enter().append("marker")
		.attr("id", "arrow")
		.attr("viewBox", "0 -5 10 10")
		.attr("refX", 10)
		.attr("refY", -1.5)
		.append("path")
		.attr("d", "M0,-5L10,0L0,5");

		// Draw links between the nodes
		var link = svg.selectAll(".link")
		.data(graph.links)
		.enter().append("line")
		.attr("class", "link")
		.style("stroke-width", function(d) { return Math.sqrt(d.value); })
		.attr("marker-end", "url(#arrow)");

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
		var gnodes = svg.selectAll('g.gnode')
		.data(graph.nodes)
		.enter()
		.append('g')
		.classed('gnode', true);

		//Constructing circle shape for the nodes
		var node = gnodes.append("circle")
		.attr("class", "node")
		.attr("r", 5)
		.style("fill", function(d) { return color(d.group); })
		.call(node_drag);
		//.call(force.drag);

		// Appending labels to the nodes
		var text = gnodes.append("text")
		.call(node_drag)
		.text(function(d) { return d.name; });

		// Appending title to the nodes 
		// Mouse hover on the node shows the name of the node.
		node.append("title")
		.text(function(d) { return d.name; });

		//Properties for the position of the node
		function tick() {
			link.attr("x1", function(d) { return d.source.x; })
			.attr("y1", function(d) { return d.source.y; })
			.attr("x2", function(d) { return d.target.x; })
			.attr("y2", function(d) { return d.target.y; });

			node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
			text.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
		};

		// Works on loading the network and placing the nodes randomly for view
		force.on("tick", tick);


	});



}