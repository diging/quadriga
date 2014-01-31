//Author : Sowjanya Ambati


function d3init(graph, networkId, path) {
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

	//d3.json(path+"/resources/txt-layout/data1.json", function(error,graph) {
		//d3.json(graph, function(error, graph) {
			alert(graph);
		force
		.nodes(graph.nodes)
		.links(graph.links)
		.start();

		d3.select("body").append("p").html("Look at me, I'm a paragraph.")
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
		
		
		
		
		
		var gnodes = svg.selectAll('g.gnode')
		.data(graph.nodes)
		.enter();
	
		//.classed('gnode', true);
	
//	var node = gnodes.append('path')
//	attr("d", d3.svg.symbol().type(function(d) { return d3.svg.symbolTypes[d.group]; }))
//		.attr("class", "node")
//		.attr("width", 10)
//      .attr("height", 10)
//		.attr("r", 5)
//		.style("fill", function(d) { return color(d.group); },"size",function(d) { return size(d.group); })
		
		
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
	    .style("fill", function(d) { return color(d.group); })
	    .call(node_drag)
		.on("click", function(d)
{

			
			
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

		function neighboring(a, b) {
			
		//	return linkedByIndex[a.index + "," + b.index];
			  return link.some(function(d) {
			    return (d.source === a && d.target === b)
			        
			  });
			}
		
		
		function fade(opacity) {
	        return function(d, i) {
	            //fade all elements
//	        	d3.select(d.source).style("opacity", opacity);
//	        	d3.select(d.target).style("opacity", opacity);
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
		
	//});
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
	

}