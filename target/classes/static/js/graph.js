var curNodesData = [];
var curLinksData = [];
var items_json, experts_json, link_json;

var width = 1000;
var height = 700;

var radius = 10;

var canvas = d3.select("#canvas").append("canvas")
        .attr('width', width + 'px')
        .attr('height', height + 'px')
        .node(),
    context = canvas.getContext("2d");

var div = d3.select("body").append("div")
    .attr("class", "tooltip")
    .style("opacity", 0);

var simulation = d3.forceSimulation()
    .force("collide", d3.forceCollide(50))
    .force("link", d3.forceLink().distance(100))
    .force("charge", d3.forceManyBody().strength(-50))
    .force("center", d3.forceCenter(width / 2, height / 2));

var transform = d3.zoomIdentity;

function graph(items, exps, link, patentStyle, expertStyle, patentRadius, expertRadius) {
    console.log("graph");
    curNodesData = curNodesData.concat(items);
    curNodesData = curNodesData.concat(exps);
    curLinksData = curLinksData.concat(link);

    simulation.nodes(curNodesData)
        .on("tick", ticked);

    simulation.force("link")
        .links(curLinksData);

    d3.select(canvas)
        .call(d3.drag()
            .subject(dragsubject)
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended))
        .call(d3.zoom()
            .scaleExtent([1 / 10, 10])
            .on("zoom", zoomed));

    function zoomed() {
        transform = d3.event.transform;
        ticked();
    }

    function ticked() {
        context.save();

        context.clearRect(0, 0, width, height);

        context.translate(transform.x, transform.y);
        context.scale(transform.k, transform.k);

        // draw links
        context.strokeStyle = "#ccc";
        context.beginPath();
        curLinksData.forEach(function(d) {
            context.moveTo(d.source.x, d.source.y);
            context.lineTo(d.target.x, d.target.y);
        });
        context.stroke();

        //draw nodes
        context.beginPath();

        context.fillStyle = patentStyle;
        for(var i = 0; i< 1000; i++) {
            var d = curNodesData[i];
            context.moveTo(d.x, d.y);
            context.arc(d.x, d.y, patentRadius, 0, 2 * Math.PI);
        }
        context.fill();

        context.beginPath();
        context.fillStyle = expertStyle;
        for(var i = 1000; i< curNodesData.length; i++) {
            var d = curNodesData[i];
            context.moveTo(d.x, d.y);
            context.arc(d.x, d.y, expertRadius, 0, 2 * Math.PI);
        }
        context.fill();

        context.restore();
    }

    function dragsubject() {
        var i,
            x = transform.invertX(d3.event.x),
            y = transform.invertY(d3.event.y),
            dx,
            dy;
        for (i = curNodesData.length - 1; i >= 0; --i) {
            node = curNodesData[i];
            dx = x - node.x;
            dy = y - node.y;

            if (dx * dx + dy * dy < radius * radius) {

                node.x =  transform.applyX(node.x);
                node.y = transform.applyY(node.y);

                showProps(node);

                return node;
            }
        }
    }

    function dragstarted() {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d3.event.subject.fx = transform.invertX(d3.event.x);
        d3.event.subject.fy = transform.invertY(d3.event.y);
    }

    function dragged() {
        d3.event.subject.fx = transform.invertX(d3.event.x);
        d3.event.subject.fy = transform.invertY(d3.event.y);
    }

    function dragended() {
        if (!d3.event.active) simulation.alphaTarget(0);
        d3.event.subject.fx = null;
        d3.event.subject.fy = null;
    }
};

function load() {
    d3.json("http://localhost:8082/kgraph/dataSource/type/part", function (error, json) {
        if (error) {
            return console.warn(error);
        }
        dataRootPartition = json;
    });
    
    $.ajax({
        url: "http://localhost:8082/kgraph/findAllItems",
        dataType: "json",
        async: false,
        success: function (data) {
            items_json = data;
        }
    })

    $.ajax({
        url: "http://localhost:8082/kgraph/findAllExperts",
        dataType: "json",
        async: false,
        success: function (data) {
            experts_json = data;
        }
    })

    $.ajax({
        url: "http://localhost:8082/kgraph/findAllRelations",
        dataType: "json",
        async: false,
        success: function (data) {
            link_json = data;
        }
    })

    console.log(items_json, experts_json, link_json);

    graph(items_json, experts_json,link_json, "orange", "#fff", 20, 10);
}

load();