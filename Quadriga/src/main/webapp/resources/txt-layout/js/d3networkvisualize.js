
function d3init(json1, networkId, path) {
	console.log("init");

	var width = 960,
	    height = 700;

	var color = d3.scale.category20();

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

	  var link = svg.selectAll(".link")
	      .data(graph.links)
	    .enter().append("line")
	      .attr("class", "link")
	      .style("stroke-width", function(d) { return Math.sqrt(d.value); });
	  
	  // Prepare the arrow
	  svg.append("svg:defs").selectAll("marker")
	  		.data(["succeeds", "from", "received", "sent", "redeemed", "same_owner", "owns", "transfers", "identifies"])
	  		.enter().append("svg:marker")
	  		.attr("id", String)
	  		.attr("viewBox", "0 -5 10 10")
	  		.attr("refX", 25)
	  		.attr("refY", -1.5)
	  		.attr("markerWidth", 6)
	  		.attr("markerHeight", 6)
	  		.attr("orient", "auto")
	  		.append("svg:path")
	  		.attr("d", "M0,-5L10,0L0,5");
	  
	  
	  var path = svg.append("svg:g").selectAll("path")
	    .data(force.links())
	  .enter().append("path")
	    .attr("class", "link")
	    .attr("marker-end", function(d) { return "url(#" + d.value + ")"; });
//	  var path = svg.append("g").selectAll("path")
//	    .data(force.links())
//	  .enter().append("path")
//	    .attr("class", function(d) {//console.log(d.value); 
//	    return "link " + d.value; })
//	    .attr("marker-end", function(d) { return "url(#" + d.value + ")"; });
	  
//	  // Draw the arrow
//	  link.attr("class", function(d)
//	  {
//		//  console.log(d.value)
//	  	return "link " + d.value;
//	  });
//	   
//	  // The end market
//	  link.attr("marker-end", function(d)
//	  {
//	  	return "url(#" + d.value + ")";
//	  });
	  var gnodes = svg.selectAll('g.gnode')
	     .data(graph.nodes)
	     .enter()
	     .append('g')
	     .classed('gnode', true);
	    
	  var node = gnodes.append("circle")
	      .attr("class", "node")
	      .attr("r", 5)
	      .style("fill", function(d) { return color(d.group); })
	     .call(force.drag);

//	  var labels = gnodes.append("text")
//	  .attr("class", "word")
//	      .text(function(d) { console.log(d.name);return d.name; });
	  
	  var text = svg.append("g").selectAll("text")
	    .data(force.nodes())
	  .enter().append("text")
	  .attr("class", "text")
	  // .attr("x", 8)
	   //.attr("y", ".31em")
	    .text(function(d) { //console.log(d.name);
	    return d.name; });
	 
	  
	  node.append("title")
     .text(function(d) { return d.name; });
	  
	  
	  
	//  console.log(labels);
//	  var node = svg.selectAll(".node")
//	      .data(graph.nodes)
//	    .enter().append("circle")
//	      .attr("class", "node")
//	      .attr("r", 5)
//	      .style("fill", function(d) { return color(d.group); })
//	      .call(force.drag);
//	  var labels = node.append("text")
//	  .text(function(d) { return d.name; });
//	  node.append("text")
//      .attr("x", 12)
//      .attr("dy", ".35em")
//      .text(function(d) { return  d.name; });
//	 // .text("node56");
//
//	  node.append("title")
//	      .text(function(d) { return d.name; });

	  force.on("tick", function() {
	    link.attr("x1", function(d) { return d.source.x; })
	        .attr("y1", function(d) { return d.source.y; })
	        .attr("x2", function(d) { return d.target.x; })
	        .attr("y2", function(d) { return d.target.y; });
	    text.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
	  //  node.attr("cx", function(d) { return d.x; })
	   //     .attr("cy", function(d) { return d.y; });
	    
	    
	    node.attr("transform", function(d) { return "translate(" + d.x + "," + d.y + ")"; });
	  });
	  
	// Populate the nodes
//		for (var node in graph.nodes.node)
//		{		
//			// Only add new stuff.  This provides correct behavior on data updates
//			if (!(graph.nodes.node[node]['@'].id in nodes))
//			{
//				nodes[graph.nodes.node[node]['@'].id] = {"name": graph.nodes.node[node]['@'].id, "data": "d3"};
//			}			
//		}
//		// Populate the links
//		for (var edge in graph.links.link)
//		{	
//			links[graph.links.linsk[edge]['@'].id] = {"source": nodes[graph.links.links[edge]['@'].source], "target": nodes[graph.links.link[edge]['@'].target], "data":graph.links.link[edge]};				
//		}
	  
	});
	

    
}