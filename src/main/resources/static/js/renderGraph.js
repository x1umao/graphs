function renderGraph() {

    let nodeMap = {};

    const dom = document.getElementById("graphContainer");
    const myChart = echarts.init(dom);
    const app = {};
    let option;
    const colorTable = ['rgba(76, 142, 230, 1)',
        'rgba(228, 78, 43, 1)',
        'rgba(248, 181, 41, 1)',
        'rgb(128,182,229)'];
    // myChart.showLoading();
    graph.nodes.forEach(function (node) {
        node.symbolSize = 20;
    });
    graph.nodes.forEach(function (node) {
        node.category = parseInt(node.category);
        node.label = {show: false};
        // construct id,name
        nodeMap[node.id] = node.name;
    })
    option = {
        title: {
            show: false
        },
        tooltip: {
            position: 'top',
            confine: true,
            formatter: function (d) {
                if (d.dataType === 'node') {
                    let terms = d.name.split(' ');
                    let result = '';
                    let len = 0;
                    for (let i = 0; i < terms.length; i++) {
                        if (result.indexOf('<br>') === -1) {
                            if (len + terms[i].length + 1 > 37) {
                                result += '<br>';
                                len = 0;
                            }
                        } else {
                            if (len + terms[i].length + 1 > 43) {
                                result += '<br>';
                                len = 0;
                            }
                        }
                        result += terms[i] + ' ';
                        len += terms[i].length + 1;
                    }
                    console.log(d);
                    if (d.data.category === 0 || d.data.category === 3) {
                        return `<p><b>Name:&nbsp&nbsp</b>${result}</p><br>
                                <p><b>Gender:&nbsp&nbsp</b>${d.data.gender}</p><br>
                                <p><b>Status:&nbsp&nbsp</b>${d.data.status}</p>`
                    } else if (d.data.category === 1) {
                        let year = d.data.year !== undefined ? d.data.year : 'N.A';
                        return `<p><b>Title:&nbsp&nbsp</b>${result}</p><br>
                                <p><b>Year:&nbsp&nbsp</b>${year}</p>`;
                    } else if (d.data.category === 2) {
                        return `<p><b>Title:&nbsp&nbsp</b>${d.data.name}</p>`;
                    }
                } else if (d.dataType === 'edge') {
                    let source = nodeMap[d.data.source];
                    let target = nodeMap[d.data.target];
                    let reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
                    if(reg.test(source)){
                        source = source.length > 7 ? source.slice(0, 7) + '...' : source;
                    }else{
                        source = source.length > 17 ? source.slice(0, 17) + '...' : source;
                    }
                    if(reg.test(target)){
                        target = target.length > 7 ? target.slice(0, 7) + '...' : target;
                    }else{
                        target = target.length > 17 ? target.slice(0, 17) + '...' : target;
                    }
                    const order = d.data.order;
                    const tooltip = `<p>${source}<b>&nbsp→&nbsp</b>${target}</p><br>
                                         <p><b>Relationship:&nbsp&nbsp</b></p>${d.data.type}`;
                    if (d.data.type === 'WROTE') {
                        return tooltip + `<br><p><b>Order:&nbsp&nbsp</b>${order}</p>`
                    } else {
                        return tooltip;
                    }
                }
            },
            extraCssText: 'width:350px;',
            textStyle: {
                fontFamily: 'poppins',
                fontSize: 14
            }
        },
        toolbox: {
            show: true,
            itemSize: 45,
            top: '88%',
            right: '3%',
            feature: {
                saveAsImage: {
                    show: true,
                    type: 'png',
                    icon: 'image://images/download.png',
                    name: 'graphs.be'
                }
            }
        },
        legend: [{
            // selectedMode: 'single',
            data: graph.category.map(function (a) {
                return a.name;
            }),
            emphasis: {
                focus: 'none'
            }
        }],
        series: [
            {
                type: 'graph',
                layout: 'force',
                data: graph.nodes,
                links: graph.links,
                categories: graph.category,
                roam: true,
                draggable: true,
                label: {
                    normal: {
                        show: false,
                    }
                },
                force: {
                    edgeLength: 55,
                    repulsion: 150,
                    gravity: 0.03
                },
                edgeSymbol: ['none', 'arrow'],
                scaleLimit: {
                    min: 0.3,
                    max: 3
                },
                emphasis: {
                    focus: 'adjacency',
                    lineStyle: {
                        width: 10
                    }
                }
            }
        ],
        color: colorTable
    };

    myChart.setOption(option);

    if (option && typeof option === 'object') {
        myChart.setOption(option);
    }
    $(window).resize(function(){
        myChart.resize();
    })
}