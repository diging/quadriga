/**
 * @Author : Bharath Srikantan
 * 
 * Data visualization in D3 JQuery.
 * This javascript should work on displaying concept collections on statistic page
 * 
 */

function d3ProjectStatistics(data) {

	var margin = {top: 40, right: 20, bottom: 30, left: 300},
	width = 1160 - margin.left - margin.right,
	height = 500 - margin.top - margin.bottom;

	var formatPercent = d3.format("");

	var x = d3.scale.ordinal()
	.rangeRoundBands([0, width], .1);

	var y = d3.scale.linear()
	.range([height, 0]);

	var xAxis = d3.svg.axis()
	.scale(x)
	.orient("bottom");

	var yAxis = d3.svg.axis()
	.scale(y)
	.orient("left")
	.tickFormat(formatPercent);

	var tip = d3.tip()
	.attr('class', 'd3-tip')
	.offset([-10, 0])
	.html(function(d) {
		return "Description : " + d.description + " </br>" +
			   "Concept ID : " + d.conceptId + "</br>" +
			   "Count : "+ d.count;
	})

	var svg = d3.select('#stats').append("svg")
	.attr("width", width + margin.left + margin.right)
	.attr("height", height + margin.top + margin.bottom)
	.append("g")
	.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	svg.call(tip);	

	data = JSON.parse(data);
	x.domain(data.map(function(d) { return d.label; }));
	y.domain([0, d3.max(data, function(d) { return d.count; })]);

	svg.append("g")
	.attr("class", "x axis")
	.attr("transform", "translate(0," + height + ")")
	.call(xAxis);

	svg.append("g")
	.attr("class", "y axis")
	.call(yAxis)
	.append("text")
	.attr("y", -10)
	.attr("dy", ".71em")
	.style("text-anchor", "end")
	.text("Count");

	svg.selectAll(".bar")
	.data(data)
	.enter()
	.append("rect")
	.attr("class", "bar")
	.attr("x", function(d) { return x(d.label); })
	.attr("width", x.rangeBand())
	.attr("y", function(d) { return y(d.count); })
	.attr("height", function(d) { return height - y(d.count); })
	.on('mouseover', tip.show)
	.on('mouseout', tip.hide);
}