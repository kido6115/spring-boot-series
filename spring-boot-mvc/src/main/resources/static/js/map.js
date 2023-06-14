var test;
$(function () {

    //config for left map
    var config = new sungyeh.map.config.Config();
    config.mapboxToken = 'pk.eyJ1Ijoia2lkbzYxMTUiLCJhIjoiY2p4azBjNzl2MWp2ODN6bWtiMnBxYnZneiJ9.OI63ByOsO8svoW5A94h8Jw';
    config.tgosAppId = '';
    config.tgosApiKey = '';
    config.features.splice(0, 1);
    //config for right map
    var config2 = new sungyeh.map.config.Config();
    config2.features = [config2.features[3]];

    config2.mapName = 'map2';
    config2.mapboxToken = 'pk.eyJ1Ijoia2lkbzYxMTUiLCJhIjoiY2p4azBjNzl2MWp2ODN6bWtiMnBxYnZneiJ9.OI63ByOsO8svoW5A94h8Jw';
    config2.tgosAppId = '';
    config2.tgosApiKey = ''
    //初始化地圖
    var map = init(config);
    var map2 = init(config2);
    test = map;
    //多視窗模式
    new sungyeh.map.util.MultiWindows(map, map2, 'compare');
    // createPie('pie1');
    //各自地圖底圖選擇器
    new sungyeh.map.util.MapSelector(map, google.maps.ControlPosition.LEFT_BOTTOM);
    new sungyeh.map.util.MapSelector(map2, google.maps.ControlPosition.RIGHT_BOTTOM);
    //left map 功能列表
    var leftFeatureTools = new sungyeh.map.util.FeatureTools(map);
    var drawObject = new sungyeh.map.util.DrawObject();
    var tools = {
        normalTool: [
            // leftFeatureTools.insertTool('query-tool', 'searchSettingElement'),
            leftFeatureTools.insertTool('service-tool', 'serviceElement'),
            layerTool(map, 'layer-tool')
        ],
        drawTool: leftFeatureTools.insertTool('draw-tool', 'drawElement'),
        drawObject: drawObject,
        position: google.maps.ControlPosition.TOP_LEFT,
        orient: 'vertical'
    };
    leftFeatureTools.createTools(tools);
    //right map功能列表
    var rightFeatureTools = new sungyeh.map.util.FeatureTools(map2);
    var tools2 = {
        normalTool: [
            // rightFeatureTools.insertTool('query-tool2', 'searchSettingElement2'),
            // rightFeatureTools.insertTool('service-tool2', 'serviceElement2'),
            layerTool(map2, 'layer-tool2')
        ],
        position: google.maps.ControlPosition.TOP_RIGHT,
        orient: 'vertical'
    };
    rightFeatureTools.createTools(tools2);

    // //其餘trigger
    serviceFeature(map);
    drawFeature(map, drawObject);

});

//地圖初始
function init(config) {
    var myOptions = {
        center: {
            lat: config.DEFAULT_LATITUDE,
            lng: config.DEFAULT_LONGITUDE
        },
        zoom: config.DEFAULT_ZOOM,
        streetViewControl: false,
        navigationControl: true,
        mapTypeControl: false,
        scaleControl: true,
        panControl: true,
        zoomControl: false,
        fullscreenControl: false
    };
    return new sungyeh.fusion.FusionExtendMap(config, '', myOptions);

}

//圖層選擇工具
function layerTool(map, toolId) {
    //假資料
    var key = chcg.features.map(function (data) {
        return data.properties.TOWN_ID;
    });
    var opacity = {};
    key.forEach(function (element) {
        opacity[element] = Math.random();
    });
    var keyTc = tc.features.map(function (data) {
        return data.properties.Town_ID;
    });
    var opacityTc = {};
    keyTc.forEach(function (element) {
        opacityTc[element] = Math.random();
    });
    // 單筆資料必須包含lat,lng屬性
    map.objectData[2] = getPoints();
    map.objectData[6] = getPoints();
    map.objectData[7] = chcg;
    map.objectData[8] = tc;
    map.objectData[10] = getPoints();
    map.objectData[11] = getSamePoints();

    //圖層清單內容
    new sungyeh.map.util.Tree(map.config.mapName, toolId, {
        slayers: map.config.slayers,
        layerClick: function (e, layer) {
            //toggle fusion圖層
            map.toggleMapLayer(layer);
            if (layer.selected) {
                switch (layer.subtype) {
                    //環斑圖，範例為使用fusion圓結合marker
                    case 'ringspot':
                        //google circle option
                        var circleOption = {
                            fillColor: 'red',
                            fillOpacity: 1,
                            strokeWeight: 2,
                            clickable: false,
                            editable: false,
                            zIndex: 1,
                            radius: 10//公尺
                        };
                        map.objectLayer[layer.id] = new sungyeh.map.object.RingSpot(map.objectData[layer.id], map, 15, circleOption);
                        map.objectLayer[layer.id].addMarkerListener('click', function () {
                            console.log(this)
                        });
                        break;
                    //點位，群聚圖一種，將資料蒐集半徑設置最小
                    case 'marker':
                        map.objectLayer[layer.id] = new sungyeh.map.object.Marker(map.objectData[layer.id], map);
                        map.objectLayer[layer.id].addMarkerListener('click', function (event) {
                            var mapMaxZoom = eval('map.mapTypes.' + map.getMapTypeId() + '.maxZoom');
                            if (mapMaxZoom === map.getZoom()) {
                                console.log(this)
                            } else {
                                map.smoothZoomAndPanTo(mapMaxZoom, event.latLng.lat(), event.latLng.lng());
                            }
                        });
                        //zoom in 極限marker仍重複情況
                        map.objectLayer[layer.id].addListener('clusterclick', function (cluster) {
                            var mapMaxZoom = eval('map.mapTypes.' + map.getMapTypeId() + '.maxZoom');
                            if (mapMaxZoom === map.getZoom()) {
                                cluster.getMarkers().forEach(function (marker) {
                                    console.log(marker)
                                });
                            }
                        });
                        break;
                    //群聚圖
                    case 'cluster':
                        map.objectLayer[layer.id] = new sungyeh.map.object.Cluster(map.objectData[layer.id], map);
                        map.objectLayer[layer.id].addMarkerListener('click', function () {
                            console.log(this)
                        });
                        //zoom in 極限marker仍重複情況
                        map.objectLayer[layer.id].addListener('clusterclick', function (cluster) {
                            var mapMaxZoom = eval('map.mapTypes.' + map.getMapTypeId() + '.maxZoom');
                            if (mapMaxZoom === map.getZoom()) {
                                cluster.getMarkers().forEach(function (marker) {
                                    console.log(marker)
                                });
                            }
                        });
                        break;
                    // 熱度圖
                    case 'heatmap':
                        map.objectLayer[layer.id] = new sungyeh.map.object.HeatMap(map.objectData[layer.id], map, 20);
                        break;
                    //密度圖，使用geojson
                    case 'density':
                        switch (layer.id) {
                            case 7:
                                chcg.features.forEach(function (value) {
                                    value.properties.density = opacity[value.properties.TOWN_ID];
                                });
                                map.objectLayer[layer.id] = new sungyeh.map.object.Density(map.objectData[layer.id], map);
                                var defaultStyle = {
                                    strokeWeight: 1,
                                    strokeOpacity: .5,
                                    strokeColor: '#000',
                                    fillColor: 'blue',
                                    fillOpacity: 1,
                                    zIndex: 100
                                };
                                map.objectLayer[layer.id].setStyle(function (feature) {
                                    defaultStyle.fillColor = colorScale(feature.getProperty('density') * 100);
                                    return defaultStyle;
                                });
                                map.objectLayer[layer.id].addListener('click', function (feature) {
                                    console.log(feature);
                                });
                                break;
                            case 8:
                                tc.features.forEach(function (value) {
                                    value.properties.density = opacityTc[value.properties.Town_ID];
                                });
                                map.objectLayer[layer.id] = new sungyeh.map.object.Density(map.objectData[layer.id], map);
                                var defaultStyle = map.objectLayer[layer.id].style;
                                map.objectLayer[layer.id].setStyle(function (feature) {
                                    defaultStyle.fillOpacity = feature.getProperty('density');
                                    return defaultStyle;
                                });
                                map.objectLayer[layer.id].addListener('click', function (feature) {
                                    console.log(feature);
                                });
                                break;
                        }
                        break;
                }
            } else {
                if (map.objectLayer[layer.id]) {
                    map.objectLayer[layer.id].clear();
                }
            }
        }
        // onHighlight: function (e, highlight) {
        //     var data = $(highlight).prev().data();
        //     var isCheck = $(highlight).find('input').is(":checked");
        //     var layer = map.objectLayer[data.id];
        //     map.toggleHighlight(layer, data, isCheck);
        // }
    }, map.config);
    return toolId;
}


//服務工具功能實作
function serviceFeature(map) {
    //google service
    var googleService = new sungyeh.map.service.GoogleMapService();
    $('#directionSearch').click(function () {
        googleService.googleDirection(map, $('#org').val(), $('#dest').val(), document.getElementById('directions_panel'));
    });
    $('#googleGeocodingSearch').click(function () {
        googleService.googleGeocoding({address: $('#google-geocoding').val()}, function (results) {
            new google.maps.Marker({
                position: results[0].geometry.location,
                map: map,
                info: results
            });
            map.setCenter(results[0].geometry.location);
            map.setZoom(13);
        });
    });
    $('#googleMatrixSearch').click(function () {
        var org = [];
        var dest = [];
        $('.matrix-start').each(function () {
            if ($(this).val()) {
                org.push($(this).val());
            }
        });
        $('.matrix-target').each(function () {
            if ($(this).val()) {
                dest.push($(this).val());
            }
        });
        googleService.googleDistanceMatrix(org, dest, function (response) {
            console.log(response);
        })
    });
}

//環域工具功能實作

function drawFeature(map, drawObject) {
    var drawingManager = drawObject;
    google.maps.event.addListener(drawingManager, 'overlaycomplete', function (shape) {
        $('.infos').val('');
        var dataCount = 0;
        switch (shape.type) {
            case 'polygon':
                var data = drawObject.polygonContainsData(getPoints(), shape.overlay);
                dataCount = data.length;
                break;
            case 'rectangle':
                var data = drawObject.rectangleContainsData(getPoints(), shape.overlay);
                dataCount = data.length;
                break;
            case 'circle':
                var data = drawObject.circleContainsData(getPoints(), shape.overlay);
                dataCount = data.length;
                break;
            case 'marker':
                var radius = +$('#markerRadius').val();
                if (radius) {
                    var data = drawObject.markerContainsData(getPoints(), shape.overlay, radius, map);
                    dataCount = data.length;
                }
                break;
        }
        $('#dataCount').val(dataCount);
        console.log(shape);
    });
    google.maps.event.addListener(drawingManager, 'polygoncomplete', function (polygon) {
        $('#area').val(google.maps.geometry.spherical.computeArea(polygon.getPath()));
    });
    google.maps.event.addListener(drawingManager, 'circlecomplete', function (circle) {
        $('#area').val(circle.getRadius() * circle.getRadius() * Math.PI);
        $('#radius').val(circle.getRadius());
    });
    google.maps.event.addListener(drawingManager, 'polylinecomplete', function (polyline) {
        $('#length').val(google.maps.geometry.spherical.computeLength(polyline.getPath()));
    });
    google.maps.event.addListener(drawingManager, 'rectanglecomplete', function (rectangle) {
        $('#bound').val('北:' + rectangle.getBounds().getNorthEast().lat() + '\n南:' + rectangle.getBounds().getSouthWest().lat()
            + '\n東:' + rectangle.getBounds().getNorthEast().lng() + '\n西:' + rectangle.getBounds().getSouthWest().lat());
    });
    google.maps.event.addListener(drawingManager, 'markercomplete', function (marker) {
        $('#latLng').val(marker.position.lat() + ',' + marker.position.lng());
    });
}

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
