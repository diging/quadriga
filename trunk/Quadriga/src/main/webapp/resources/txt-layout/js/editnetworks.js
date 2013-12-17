var labelType, useGradients, nativeTextSupport, animate, pathName;

(function() {
	var ua = navigator.userAgent, iStuff = ua.match(/iPhone/i)
	|| ua.match(/iPad/i), typeOfCanvas = typeof HTMLCanvasElement, nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'), textSupport = nativeCanvasSupport
	&& (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
	// I'm setting this based on the fact that ExCanvas provides text support
	// for IE
	// and that as of today iPhone/iPad current text support is lame
	labelType = (!nativeCanvasSupport || (textSupport && !iStuff)) ? 'Native'
			: 'HTML';
	nativeTextSupport = labelType == 'Native';
	useGradients = nativeCanvasSupport;
	animate = !(iStuff || !nativeCanvasSupport);



})();

var Log = {
		elem : false,
		write : function(text) {
			if (!this.elem)
				this.elem = document.getElementById('log');
			this.elem.innerHTML = text;
			this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
		}
};

function init(json, networkId, path) {
	console.log("init");
	pathName = path;

	$jit.ForceDirected.Plot.EdgeTypes.implement({
		'labeled' : {
			'render' : function(adj, canvas) {
				this.edgeTypes.arrow.render.call(this, adj, canvas);
				var data = adj.data;
				if (data.$labeltext) {
					var ctx = canvas.getCtx();
					var posFr = adj.nodeFrom.pos.getc(true);
					var posTo = adj.nodeTo.pos.getc(true);
					ctx.fillText(data.$labeltext, (posFr.x + posTo.x) / 2,
							(posFr.y + posTo.y) / 2);
				}
				var from = adj.nodeFrom.pos.getc(true),
				to = adj.nodeTo.pos.getc(true),
				dim = adj.getData('dim'),
				direction = adj.data.$direction,
				inv = (direction && direction.length>1 && direction[0] != adj.nodeFrom.id);
				this.edgeHelper.arrow.render(from, to, dim, inv, canvas);
			},
			'contains': function(adj, pos) {
				var from = adj.nodeFrom.pos.getc(true),
				to = adj.nodeTo.pos.getc(true);
				return this.edgeHelper.arrow.contains(from, to, pos, this.edge.epsilon);
			}

		},

		'contains': function(adj, pos) { 
			var from = adj.nodeFrom.pos.getc(true), 
			to = adj.nodeTo.pos.getc(true); 
			return this.edgeHelper.arrow.contains(from, to, pos, 
					this.edge.epsilon); 
		} 

	});

	var fd = new $jit.ForceDirected(
			{
				// id of the visualization container
				injectInto : 'infovis',
				// Enable zooming and panning
				// by scrolling and DnD
				Navigation : {
					enable : true,
					// Enable panning events only if we're dragging the empty
					// canvas (and not a node).
					panning : 'avoid nodes',
					zooming : 10
					// zoom speed. higher is more sensible
				},
				// Change node and edge styles such as
				// color and width.
				// These properties are also set per node
				// with dollar prefixed data-properties in the
				// JSON structure.
				Node : {
					overridable : true,
					dim : '10',
					color : '#8904B1'
				},
				Edge : {
					overridable : true,
					color : '#23A4FF',
					lineWidth : 1,
					type : 'labeled',
					dim : '15'
				},
				// Native canvas text styling
				Label : {
					type : labelType, // Native or HTML
					size : 10

				},
				// Add Tips
				Tips : {
					enable : true,
					onShow : function(tip, node) {
						// count connections
						var count = 0;
						node.eachAdjacency(function() {
							count++;
						});
						// display node info in tooltip
						tip.innerHTML = "<div class=\"tip-title\">"
							+ node.name
							+ "</div>"
							+ "<div class=\"tip-text\"><b>connections:</b> "
							+ count + "</div>";
					}
				},
				// Add node events
				Events : {
					enable : true,
					enableForEdges : true,
					type : 'Native',
					// Change cursor style when hovering a node
					onMouseEnter : function() {
						fd.canvas.getElement().style.cursor = 'move';
					},
					onMouseLeave : function() {
						fd.canvas.getElement().style.cursor = '';
					},
					// Update node positions when dragged
					onDragMove : function(node, eventInfo, e) {
						var pos = eventInfo.getPos();
						node.pos.setc(pos.x, pos.y);
						fd.plot();
					},
					// Implement the same handler for touchscreens
					onTouchMove : function(node, eventInfo, e) {
						$jit.util.event.stop(e); // stop default touchmove
						// event
						this.onDragMove(node, eventInfo, e);
					},
					// Add also a left click handler to nodes or edges
					onClick : function(edge, eventInfo,e) {

						// Global variable for description 
						// to access inside ajax calls
						var description; 
						if (!edge)
							return;

						if(edge.nodeFrom){
							// This is an edge
							// we may have to check for Relation
							// Work still incomplete

							console.log("target is a edge");
							alert(edge.nodeFrom.name);
							alert(edge.nodeTo.name);
							var node1 = edge.nodeTo;
							var node2 = edge.nodeFrom;
							node1.setData('dim', 10, 'end');
							node2.setData('dim', 10, 'end');
							/*edge.selected = true;
					          edge.setData('dim', 17, 'end');*/
							fd.fx.animate({
								modes: ['node-property:dim',
								        'edge-property:lineWidth:color'],
								        duration: 500
							});
							/*edge.setDataset('end', {
					              lineWidth: 3,
					              color: '#36acfb'
					            });*/


						}else{
							// Author : Lohith
							// If the javascript flow enters here
							// Its a node
							// fetching description of the node

							// Get Node name
							lemma = edge.name;

							// Ajax call for getting description of the node
							// Note: this ajax call has async = false
							// this allow variables to be assigned inside the ajax and 
							// accessed outside
							$(document).ready(function() {	
								$.ajax({
									url : path+"/rest/editing/getconcept/"+lemma,
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
						}
						// Build the right column relations list.
						// This is done by traversing the clicked node
						// connections.


						var html = "<h4>" + edge.name
						+ "</h4>"+ description
						+"<h8> CONNECTIONS:</h8> <ul><li>", list = [];
						
						// This function gets all the adjacent nodes
						edge.eachAdjacency(function(adj) {
							// Adding arrow label to inner-details
							var str2 = adj.data.$labeltext;
							var str1 = adj.nodeTo.name;

							var str3 = str2.concat(" : ");
							str3 = str3.concat(str1);
							list.push(str3);
						});


						// append connections information
						$jit.id('inner-details').innerHTML = html
						+ list.join("</li><li>") + "</li></ul>";
					},
					//  Works on right click on any node
					onRightClick : function(node) {
						if (!node)
							return;

						var html = "";
						// If the node type is Predicate
						// We can annotate on whole relation or node
						if(node.data.nodetype=="Predicate"){
							html = "<div id='popup' title='Annotation' >" +
										"<input type='button' id='annot_node' value='Add Annotation to Node' /> " +
										"</br>" +
										"<input type='button' id='annot_relation' value='Add Annotation to Relation' /> " +
										"</br>" +
									"</div>";
						} else{
							// Annotate on edge
							if(node.nodeFrom){
								html = "<div id='popup' title='Annotation'>" +
											"<input type='button' id='annot_relation' value='Add Annotation to Relation' /> " +
										"</div>";
							}
							// Annotate on node
							else{
								html = "<div id='popup' title='Annotation'>" +
											"<input type='button' id='annot_node' value='Add Annotation to Node' /> " +
										"</div>";
							}
						}



						// append connections information
						$jit.id('inner-details').innerHTML = path + html;
						//$jit.id('inner-details').innerHTML = html1;

						// This function annotate for node
						// This works on annot_node tag in the pop.
						$('#annot_node').click(function() {
							$('#annotText').val('');
							var type1 ="node";
							var html1 = "<div id='popup1' title='Annotation' style='display: none'>" +
										"<form id='annot_form' action=" + path
										+ "/auth/editing/saveAnnotation/";
							html1 += networkId + " method='POST' >";
							html1 += "<textarea name='annotText' id='annotText' cols='15' rows='15'></textarea>";
							html1 += "<input  type='hidden' name='nodename' id='nodename' value="
								+ node.id + " />";
							html1 += "<input type='button' id='annot_submit' value='submit'>";
							html1 += "</div></form>";

							$jit.id('inner-details').innerHTML = html1;
							$.ajax({
								url : path+"/auth/editing/getAnnotation/"+networkId,
								type : "GET",
								//data : $('#nodename').serialize(),
								data: "nodeid="+node.id+"&type="+type1,
								success : function(data) {
									//alert("done");
									//alert("data:"+data);
									//alert("before:" +$('#text').val());
									//$('#text').append(data);
									//$('#text').text(data);
									//alert("after:"+$('#text').val());
									//$jit.id('inner-details').innerHTML = path + html1;

								},
								error: function() {
									alert("error");
								}
							});
							event.preventDefault();


							$('#annot_submit').click(function(event) {
								var annottext = $('#annotText').val();  
								var nodename = $('#nodename').val(); 
								var url = path+"/auth/editing/saveAnnotation/"+networkId;
								alert("text:"+annottext);
								alert("nodename:"+nodename);
								alert("url:"+url);
								$.ajax({
									url : $('#annot_form').attr("action"),
									type : "POST",
									//data : $('#nodename').serialize(),
									//data: $('#annot_form').serialize(),
									data :"nodename="+node.id+"&annotText="+annottext+"&type=node",
									success : function() {
										alert("done");
										$('#popup1').dialog('close');
									},
									error: function() {
										alert("error");
									}
								});
								//
								event.preventDefault();
							});

							$( '#popup1' ).show( "slow" );

							/*$('.ui-dialog-titlebar-close').css({'text-decoration':'block', 'right':'45px', 'height':'21px', 'width': '20px'});
							  $('#popup1').children('.ui-dialog-titlebar-close').show();*/
							$('#popup1').dialog({
								open: function(event, ui) {
									$('.ui-dialog-titlebar-close').css({'text-decoration':'block', 'right':'45px', 'height':'21px', 'width': '20px'}); 
								}
							});
							/*$('.ui-dialog-titlebar-close').css({'text-decoration':'block', 'right':'45px', 'height':'21px', 'width': '20px'});
							  $('#popup1').children('.ui-dialog-titlebar-close').show();*/
							//  $(".ui-dialog-titlebar").hide();
						});
						$('#annot_relation').click(function() {
							//  alert( "Handler for .click() called." );
							var type1 = "relation";
							var html2 = "<div id='popup2' title='Annotation' style='display: none'><form id='annot_form' action=" + path
							+ "/auth/editing/saveAnnotation/";
							html2 += networkId + " method='POST' >";
							html2 += "<p id='message'></p>";
							html2 += "<textarea name='annotText' id='text' cols='15' rows='15'></textarea>";
							html2 += "<input  type='hidden' name='nodename' id='nodename' value="
								+ node.id + " />";
							html2 += "<input type='submit' id='annot_submit1' value='submit'>";
							html2 += "</form></div>";
							$jit.id('inner-details').innerHTML = path + html2;
							$.ajax({
								url : path+"/auth/editing/getAnnotation/"+networkId,
								type : "GET",
								//data : $('#nodename').serialize(),
								data: "nodeid="+node.id+"&type="+type1,
								success : function(data) {
									//alert("done");
									//alert("data:"+data);
									//alert("before:" +$('#text').val());
									$('#text').append(data);
									//$('#text').text(data);
									//alert("after:"+$('#text').val());
									//$jit.id('inner-details').innerHTML = path + html1;

								},
								error: function() {
									alert("error");
								}
							});
							//  event.preventDefault();


							$('#annot_submit1').click(function(event) {
								var annottext = $('#text').val();  
								var nodename = $('#nodename').val(); 
								var url = path+"/auth/editing/saveAnnotation/"+networkId;
								alert("text:"+annottext);
								alert("nodename:"+nodename);
								alert("url:"+url);
								$.ajax({
									url : $('#annot_form').attr("action"),
									type : "POST",
									data :"nodename="+node.id+"&annotText="+annottext+"&type=relation",
									success : function() {

										//alert("done");
										$('#popup2').dialog('close');
									},
									error: function() {
										alert("error");
									}
								});

								event.preventDefault();
							});

							//  $jit.id('inner-details').innerHTML = path + html2;
							$( '#popup2' ).show( "slow" );
							/*var dialogOpts = {
								      modal: true,
								      autoOpen: false,
								      height: 320,
								      width: 480,
								      draggable: true,
								      resizeable: true,
								      title:'Annotation'
								   };*/
							$('#popup2').dialog();
						});
						/*$('#annot_form').submit(function(event) {
							var text = $('#annotText').val();  
						    var nodename = $('#nodename').val(); 

							$.ajax({
								url : $('#annot_form').attr("action"),
								type : "POST",
								data: $('#annot_form').serialize(),

								success : function() {
									alert("done");
								},
								error: function() {
									alert("error");
								}
							});

							event.preventDefault();
						});*/

						$('#popup').dialog();


					},
				},
				// Number of iterations for the FD algorithm
				iterations : 200,
				// Edge length
				levelDistance : 130,
				// Add text to the labels. This method is only triggered
				// on label creation and only for DOM labels (not native canvas
				// ones).
				onCreateLabel : function(domElement, node) {
					domElement.innerHTML = node.name;
					var style = domElement.style;
					style.fontSize = "0.8em";
					style.color = "#ddd";
				},
				// Change node styles when DOM labels are placed
				// or moved.
				onPlaceLabel : function(domElement, node) {
					var style = domElement.style;
					var left = parseInt(style.left);
					var top = parseInt(style.top);
					var w = domElement.offsetWidth;
					style.left = (left - w / 2) + 'px';
					style.top = (top + 10) + 'px';
					style.display = '';
				}
			});
	// load JSON data.
	fd.loadJSON(json);
	// compute positions incrementally and animate.
	fd.computeIncremental({
		iter : 40,
		property : 'end',
		onStep : function(perc) {
			Log.write(perc + '% loaded...');
		},
		onComplete : function() {
			Log.write('Loaded completely');
			fd.animate({
				modes : [ 'linear' ],
				transition : $jit.Trans.Elastic.easeOut,
				duration : 2500
			});
		}
	});
	// end
}
