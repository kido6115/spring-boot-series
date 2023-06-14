var IndexEchart = /** @class */ (function () {
    function IndexEchart(count, echarts, sum) {
        this.sum = 0;
        this.count = count;
        this.echarts = echarts;
        this.sum = sum;
    }
    IndexEchart.prototype.series = function () {
        var self = this;
        var chartDom = document.getElementById('main');
        var myChart = this.echarts.init(chartDom);
        var option;
        var waterMarkText = 'Sungyeh';
        var canvas = document.createElement('canvas');
        var ctx = canvas.getContext('2d');
        canvas.width = canvas.height = 100;
        ctx.textAlign = 'center';
        ctx.textBaseline = 'middle';
        ctx.globalAlpha = 0.08;
        ctx.font = '20px Microsoft Yahei';
        ctx.translate(50, 50);
        ctx.rotate(-Math.PI / 4);
        ctx.fillText(waterMarkText, 0, 0);
        option = {
            backgroundColor: {
                type: 'pattern',
                image: canvas,
                repeat: 'repeat'
            },
            tooltip: {},
            title: [
                {
                    text: '訪客分布',
                    subtext: '總計 ' + self.sum,
                    left: '50%',
                    textAlign: 'center'
                }
            ],
            grid: [
                {
                    top: 50,
                    width: '40%',
                    bottom: '30%',
                    left: 10,
                    containLabel: true
                },
            ],
            xAxis: [
                {
                    type: 'value',
                    max: self.sum,
                    splitLine: {
                        show: false
                    }
                },
            ],
            yAxis: [
                {
                    type: 'category',
                    data: Object.keys(self.count),
                    axisLabel: {
                        interval: 0,
                        rotate: 30
                    },
                    splitLine: {
                        show: false
                    }
                }
            ],
            series: [
                {
                    type: 'bar',
                    stack: 'chart',
                    z: 3,
                    label: {
                        position: 'right',
                        show: true
                    },
                    data: Object.keys(self.count).map(function (key) {
                        return self.count[key];
                    })
                },
                {
                    type: 'bar',
                    stack: 'chart',
                    silent: true,
                    itemStyle: {
                        color: '#eee'
                    },
                    data: Object.keys(self.count).map(function (key) {
                        return self.sum - self.count[key];
                    })
                },
                {
                    type: 'pie',
                    radius: [0, '30%'],
                    center: ['70%', '35%'],
                    data: Object.keys(self.count).map(function (key) {
                        return {
                            name: key,
                            value: self.count[key] / self.sum * 100,
                        };
                    })
                },
            ]
        };
        option && myChart.setOption(option);
        return myChart;
    };
    return IndexEchart;
}());
