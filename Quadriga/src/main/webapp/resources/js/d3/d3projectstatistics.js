/**
 * @Author : Bharath Srikantan
 * 
 * Data visualization in D3 JQuery. This javascript should work on displaying
 * concept collections on statistic page
 * 
 */

function d3ProjectStatistics(data) {

	var origWidth = $('#stats').width();
	var origHeight = $('#stats').height() > 400 ? $('#stats').height() : 400;

	var margin = {
		top : 40,
		right : 20,
		bottom : 100,
		left : 30
	}, width = origWidth - margin.left - margin.right, height = origHeight
			- margin.top - margin.bottom;

	var formatPercent = d3.format("");

	var x = d3.scale.ordinal().rangeRoundBands([ 0, width ], .1);

	var y = d3.scale.linear().range([ height, 0 ]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom");

	var yAxis = d3.svg.axis().scale(y).orient("left").tickFormat(formatPercent);

	var tip = d3.tip().attr('class', 'd3-tip').offset([ -10, 0 ]).html(
			function(d) {
				return "Description : " + d.description + " </br>"
						+ "Concept ID : " + d.conceptId + "</br>" + "Count : "
						+ d.count;
			})

	var svg = d3.select('#stats').append("svg").attr("width",
			width + margin.left + margin.right).attr("height",
			height + margin.top + margin.bottom).append("g").attr("transform",
			"translate(" + margin.left + "," + margin.top + ")");

	svg.call(tip);

	x.domain(data.map(function(d) {
		return d.label;
	}));
	y.domain([ 0, d3.max(data, function(d) {
		return d.count;
	}) ]);

	svg.append("g").attr("class", "x axis").attr("transform",
			"translate(0," + height + ")").call(xAxis).selectAll("text").attr(
			"y", 0).attr("x", 9).attr("dy", "0.2em").attr("transform",
			"rotate(70)").style("text-anchor", "start");

	svg.append("g").attr("class", "y axis").call(yAxis).append("text").attr(
			"y", -10).attr("dy", ".71em").style("text-anchor", "end").text(
			"Count");

	svg.selectAll(".bar").data(data).enter().append("rect")
			.attr("class", "bar").attr("x", function(d) {
				return x(d.label);
			}).attr("width", x.rangeBand()).attr("y", function(d) {
				return y(d.count);
			}).attr("height", function(d) {
				return height - y(d.count);
			}).on('mouseover', tip.show).on('mouseout', tip.hide);

}

function d3ProjectActivity(data, divSection) {
	data = JSON.parse(data);

	var origWidth = $('#' + divSection).width();
	var origHeight = $('#' + divSection).height() > 400 ? $('#' + divSection)
			.height() : 400;

	var margin = {
		top : 40,
		right : 40,
		bottom : 100,
		left : 20
	}, width = origWidth - margin.left - margin.right, height = origHeight
			- margin.top - margin.bottom;

	var parseDate = d3.time.format("%d-%b-%y").parse, bisectDate = d3
			.bisector(function(d) {
				return d.date;
			}).left, formatValue = d3.format("d"), formatCurrency = function(d) {
		return +formatValue(d);
	};

	data.forEach(function(d) {
		d.date = parseDate(d.date);
		d.count = +d.count;
	});

	data.sort(function(a, b) {
		return a.date - b.date;
	});

	var dateArray = [];
	data.forEach(function(d) {
		dateArray.push(d.date);
	});

	var todayDate = new Date();
	var minDate = new Date();
	minDate.setDate(todayDate.getDate()- 30);
	console.log("fixed date" + minDate);
	
	var x = d3.time.scale().domain([minDate,todayDate]).range(
			[ 20, width - 40 ]);

	var y = d3.scale.linear().range([ height, 0 ]);

	var xAxis = d3.svg.axis().scale(x).orient("bottom")
			.tickFormat(d3.time.format("%d %B %Y"));

	var yAxis = d3.svg.axis().scale(y).orient("left")
			.tickFormat(d3.format("d"))

	var line = d3.svg.line().x(function(d) {
		return x(d.date);
	}).y(function(d) {
		return y(d.count);
	});

	var svg = d3.select("#" + divSection).append("svg").attr("width",
			width + margin.left + margin.right).attr("height",
			height + margin.top + margin.bottom).append("g").attr("transform",
			"translate(" + margin.left + "," + margin.top + ")");

	// x.domain([data[0].date, data[data.length - 1].date]);
	y.domain([ 0, d3.max(data, function(d) {
		return d.count;
	}) ]);

	svg.selectAll("bar").data(data).enter().append("rect").style("fill",
			"steelblue").attr("x", function(d) {
		console.log(d.date + x(d.date));
		return x(d.date);
	}).attr("width", 10).attr("y", function(d) {
		return y(d.count);
	}).attr("height", function(d) {
		return height - y(d.count);
	});

	svg.append("g").attr("class", "x axis").attr("transform",
			"translate(5," + height + ")").call(xAxis).selectAll("text").attr(
			"y", 6).attr("x", 6).attr("dy", "0.2em").attr("transform",
			"rotate(70)").style("text-anchor", "start");
	;

	svg.append("g").attr("class", "y axis").call(yAxis).append("text").attr(
			"transform", "rotate(-90)").attr("y", 6).attr("dy", ".71em").style(
			"text-anchor", "end").text("Count");

	var focus = svg.append("g").attr("class", "focus").style("display", "none");

	focus.append("circle").attr("r", 5.5);

	focus.append("text").attr("x", 9).attr("dy", ".35em");

	svg.append("rect").attr("class", "overlay").attr("width", width).attr(
			"height", height).on("mouseover", function() {
		focus.style("display", null);
	}).on("mouseout", function() {
		focus.style("display", "none");
	}).on("mousemove", mousemove);

	function mousemove() {
		var x0 = x.invert(d3.mouse(this)[0]), i = bisectDate(data, x0, 1), d0 = data[i - 1], d1 = i == 1 ? data[i - 1]
				: data[i], d = x0 - d0.date > d1.date - x0 ? d1 : d0;
		focus.attr("transform", "translate(" + (x(d.date) + 5) + "," + y(d.count)
				+ ")");
		focus.select("text").text(formatCurrency(d.count));
	}
}