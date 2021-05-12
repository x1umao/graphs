function renderBar(container) {
    // set the dimensions and margins of the graph
    var margin = {top: 0, right: 0, bottom: 30, left: 0},
        width = 800 - margin.left - margin.right,
        height = 500 - margin.top - margin.bottom;

    // set the ranges
    var x = d3.scaleBand()
        .range([0, width])
        .padding(0.4);
    var y = d3.scaleLinear()
        .range([height, 0]);

    var tooltip = d3.select("body").append("div");

    // append the svg object to the divivsion
    var svg = d3.select(`#${container}`).append("svg")
        .attr('preserveAspectRatio', 'xMinYMin meet')
        .attr(
            'viewBox',
            '0 0 ' +
            (width + margin.left + margin.right) +
            ' ' +
            (height + margin.top + margin.bottom)
        )
        .append("g")
        .attr("transform",
            "translate(" + margin.left + "," + margin.top + ")");

    const bgGradient = svg
        .append('linearGradient')
        .attr('id', 'bg-gradient')
        .attr('gradientTransform', 'rotate(90)');
    bgGradient
        .append('stop')
        .attr('stop-color', '#fd1d1d')
        .attr('offset', '0%');
    bgGradient
        .append('stop')
        .attr('stop-color', '#9c0063')
        .attr('offset', '100%');

    let data = [];
    let temp = new Map();
    graph.nodes.forEach(d => {
        if (d.category === 1 && d.year !== '0') {
            if (temp.get(d.year) === undefined) {
                temp.set(d.year, 1);
            } else {
                temp.set(d.year, temp.get(d.year) + 1);
            }
        }
    })
    temp.forEach(function (value, key) {
        data.push({travellers: value, period: key});
    });

    // format the data
    data.forEach(function (d) {
        d.travellers = +d.travellers;
    });

    data.sort((o1, o2) => {
        if (o1['period'] > o2['period']) {
            return 1;
        } else {
            return -1;
        }
    });
    //only show the latest ten years
    data = data.slice(0,10);

    // Scale the range of the data in the domains
    x.domain(data.map(function (d) {
        return d.period;
    }));
    y.domain([0, d3.max(data, function (d) {
        return d.travellers;
    })]);

    var formatComma = d3.format(",");

    // append the rectangles for the bar chart
    svg.selectAll(".bar")
        .data(data)
        .enter().append("rect")
        .attr("fill", "rgba(76, 142, 230, 1)")
        .attr("rx", 25)
        .attr("x", function (d) {
            return x(d.period);
        })
        .attr("width", x.bandwidth())
        .attr("y", function (d) {
            return y(d.travellers);
        })
        .attr("height", function (d) {
            return height - y(d.travellers);
        })
        //add tooltip
        .on("mousemove", function (d) {
            tooltip
                .attr("class", "bar-tooltip")
                .style("left", d3.event.pageX + 5 + "px")
                .style("top", d3.event.pageY - 80 + "px")
                .style("display", "inline-block")
                .html(`Number of articles: ${d.travellers}`);
        })
        .on("mouseout", function (d) {
            tooltip.style("display", "none");
        });

    // add the x Axis
    svg.append("g")
        .attr("class", "bar-chart-axis")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axisBottom(x))
        .call(g => g.select(".domain").remove());

}