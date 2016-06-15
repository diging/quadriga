var labelType, useGradients, nativeTextSupport, animate;

(function() {
	var ua = navigator.userAgent,
	iStuff = ua.match(/iPhone/i) || ua.match(/iPad/i),
	typeOfCanvas = typeof HTMLCanvasElement,
	nativeCanvasSupport = (typeOfCanvas == 'object' || typeOfCanvas == 'function'),
	textSupport = nativeCanvasSupport 
	&& (typeof document.createElement('canvas').getContext('2d').fillText == 'function');
	//I'm setting this based on the fact that ExCanvas provides text support for IE
	//and that as of today iPhone/iPad current text support is lame
	labelType = (!nativeCanvasSupport || (textSupport && !iStuff))? 'Native' : 'HTML';
	nativeTextSupport = labelType == 'Native';
	useGradients = nativeCanvasSupport;
	animate = !(iStuff || !nativeCanvasSupport);
})();

var Log = {
		elem: false,
		write: function(text){
			if (!this.elem) 
				this.elem = document.getElementById('log');
			this.elem.innerHTML = text;
			this.elem.style.left = (500 - this.elem.offsetWidth / 2) + 'px';
		}
};


function init1(json,path){
	// init data
	
	// end
	// init ForceDirected
	
	  //label placement on edges 
	  $jit.ForceDirected.Plot.EdgeTypes.implement({ 
		  'labeled': {
              'render': function(adj, canvas) {
                this.edgeTypes.arrow.render.call(this, adj, canvas);
                var data = adj.data;
                if(data.$labeltext) {
                  var ctx = canvas.getCtx();
                  var posFr = adj.nodeFrom.pos.getc(true);
                  var posTo = adj.nodeTo.pos.getc(true);
                  ctx.fillText(data.$labeltext, (posFr.x + posTo.x)/2, (posFr.y + posTo.y)/2);
                }// if data.labeltext
              }
            }
	  }); 
	  
	var fd = new $jit.ForceDirected({
		//id of the visualization container
		injectInto: 'infovis',
		//Enable zooming and panning
		//by scrolling and DnD
		Navigation: {
			enable: true,
			//Enable panning events only if we're dragging the empty
			//canvas (and not a node).
			panning: 'avoid nodes',
			zooming: 10 //zoom speed. higher is more sensible
		},
		// Change node and edge styles such as
		// color and width.
		// These properties are also set per node
		// with dollar prefixed data-properties in the
		// JSON structure.
		Node: {
		      overridable: true,
		      dim : '10',
		      color : '#8904B1'
		    },
		Edge: {
			overridable: true,
			type : 'arrow',
			color: '#23A4FF',
			lineWidth: 0.4,
			type: 'labeled',
			dim: '15'
		},
		//Native canvas text styling
		Label: {
			type: labelType, //Native or HTML
			size: 10
		},
		//Add Tips
		Tips: {
			enable: true,
			onShow: function(tip, node) {
				//count connections
				var count = 0;
				node.eachAdjacency(function() { count++; });
				//display node info in tooltip
				tip.innerHTML = "<div class=\"tip-title\">" + node.name + "</div>"
				+ "<div class=\"tip-text\"><b>connections:</b> " + count + "</div>";
			}
		},
		// Add node events
		Events: {
			enable: true,
			type: 'Native',
			//Change cursor style when hovering a node
			onMouseEnter: function() {
				fd.canvas.getElement().style.cursor = 'move';
			},
			onMouseLeave: function() {
				fd.canvas.getElement().style.cursor = '';
			},
			//Update node positions when dragged
			onDragMove: function(node, eventInfo, e) {
				var pos = eventInfo.getPos();
				node.pos.setc(pos.x, pos.y);
				fd.plot();
			},
			//Implement the same handler for touchscreens
			onTouchMove: function(node, eventInfo, e) {
				$jit.util.event.stop(e); //stop default touchmove event
				this.onDragMove(node, eventInfo, e);
			},
			//Add also a click handler to nodes
			onClick: function(node) {
				
				var description;
				
				// Author : Lohith Dwaraka
				// If the javascript flow enters here
				// Its a node
				// fetching description of the node
				
				// Get Node name
				lemma = node.name;
				
				// This is done to replace all dot (.) with dollar ($)
				// Since our spring controller would ignore any data after dot (.)
				lemma = lemma.replace(".","$");
				
				getConceptURL = path+"/auth/editing/getconcept/"+lemma;
				
				// Ajax call for getting description of the node
				// Note: this ajax call has async = false
				// this allow variables to be assigned inside the ajax and 
				// accessed outside
				$(document).ready(function() {	
					$.ajax({
						url : getConceptURL,
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
				
				
				if(!node) return;
				// Build the right column relations list.
				// This is done by traversing the clicked node connections.
				var html = "<h4>" + node.name + "</h4>"+ description
				+"<h8> CONNECTION :</h8><ul><li>",
				list = [];
				node.eachAdjacency(function(adj){
					// Adding arrow label to inner-details
					var str2 = adj.data.$labeltext;
					var str1 = adj.nodeTo.name;
					
					var str3 = str2.concat(" : ");
					str3 = str3.concat(str1);
					list.push(str3);
				});
				//append connections information
				$jit.id('inner-details').innerHTML = html + list.join("</li><li>") + "</li></ul>";
			}
		},
		//Number of iterations for the FD algorithm
		iterations: 200,
		//Edge length
		levelDistance: 130,
		// Add text to the labels. This method is only triggered
		// on label creation and only for DOM labels (not native canvas ones).
		onCreateLabel: function(domElement, node){
			domElement.innerHTML = node.name;
			var style = domElement.style;
			style.fontSize = "0.8em";
			style.color = "#ddd";
		},
		// Change node styles when DOM labels are placed
		// or moved.
		onPlaceLabel: function(domElement, node){
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
		iter: 40,
		property: 'end',
		onStep: function(perc){
			Log.write(perc + '% loaded...');
		},
		onComplete: function(){
			Log.write('Loaded completely');
			fd.animate({
				modes: ['linear'],
				transition: $jit.Trans.Elastic.easeOut,
				duration: 2500
			});
		}
	});
	// end
}
