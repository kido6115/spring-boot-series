<#ftl output_format="HTML" />
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <#include "component/meta.ftl" />
    <script
            src="https://maps.googleapis.com/maps/api/js?key=${key}&libraries=visualization,drawing,geometry"></script>
    <script src="https://cdn.jsdelivr.net/npm/echarts@5.4.2/dist/echarts.min.js"></script>
    <script src="/js/index-echart.js"></script>

    <script>
        var count;
        var county = ['連江縣', '金門縣', '宜蘭縣', '新竹縣', '苗栗縣', '彰化縣', '南投縣',
            '雲林縣', '嘉義縣', '屏東縣', '台東縣', '花蓮縣', '澎湖縣', '基隆市', '新竹市',
            '嘉義市', '台北市', '高雄市', '新北市', '台中市', '台南市', '桃園市'];
        var myChart;
        var sum = 0;

        $(function () {
            $.getJSON('/group-location', function (data) {
                count = data;
                county.forEach(function (element) {
                    if (count[element]) {

                    } else {
                        count[element] = 0;
                    }
                    sum += count[element];
                });
                var map = new google.maps.Map(document.getElementById("map"), {
                    zoom: 8,
                    center: {lat: 23.638, lng: 120.97},
                    zoomControl: false,
                    streetViewControl: false

                });
                map.data.loadGeoJson(
                    "/js/city.json"
                );

                map.data.setStyle(function (feature) {
                    var name = county.includes(feature.getProperty('NAME_2014_ALIAS')) ?
                        feature.getProperty('NAME_2014_ALIAS') : feature.getProperty('NAME_2014');
                    myChart.dispatchAction({
                        type: 'showTip',
                        seriesIndex: 0,
                        name: name
                    });

                    return /** @type {!google.maps.Data.StyleOptions} */({
                        fillColor: colorScale(count[name] / sum * 100),
                        strokeWeight: 1,
                        strokeOpacity: .5,
                        strokeColor: '#000',
                        fillOpacity: .5
                    });
                });
                map.data.addListener('mouseover', function (event) {
                    map.data.revertStyle();
                    map.data.overrideStyle(event.feature, {strokeWeight: 8, strokeOpacity: 1});
                });

                map.data.addListener('mouseout', function (event) {
                    map.data.revertStyle();
                });
                map.controls[google.maps.ControlPosition.RIGHT_BOTTOM].push($('#legend')[0]);

                var indexEchart = new IndexEchart(count, echarts, sum);
                myChart = indexEchart.series();
            });
        });

        function colorScale(percent) {
            var r, g, b = 0;
            if (percent < 50) {
                g = 255;
                r = Math.round(5.1 * percent);
            } else {
                r = 255;
                g = Math.round(510 - 5.10 * percent);
            }
            var h = r * 0x10000 + g * 0x100 + b * 0x1;
            return '#' + ('000000' + h.toString(16)).slice(-6);
        }

    </script>
    <style>
        #map {
            height: calc(100vh - 65px);
        }

        #legend {
            background: linear-gradient(0deg, rgba(0, 255, 0, 1) 0%, rgba(255, 0, 0, 1) 100%);
            width: 50px;
            height: 300px;
        }

        #minimum {
            top: 250px;
        }

    </style>
</head>
<body>
<#include "component/nav.ftl" />

<div class="container-fluid">
    <div class="row">
        <#include "component/sidebar.ftl" />

        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div class="row">
                <div id="main" class="col-md-6">
                </div>
                <div class="col-md-6" id="map">
                </div>
            </div>

            <div class="col-md-12 legend-block">
                <div class="col-md-6" id="legend"></div>
                <div id="maximum" class="col-md-6"></div>
                <div id="minimum" class="col-md-6"></div>
            </div>
        </main>
    </div>
</div>
</body>

</html>
