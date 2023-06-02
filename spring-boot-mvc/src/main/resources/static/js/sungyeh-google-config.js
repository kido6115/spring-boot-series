(function () {
    function r(e, n, t) {
        function o(i, f) {
            if (!n[i]) {
                if (!e[i]) {
                    var c = "function" == typeof require && require;
                    if (!f && c) return c(i, !0);
                    if (u) return u(i, !0);
                    var a = new Error("Cannot find module '" + i + "'");
                    throw a.code = "MODULE_NOT_FOUND", a
                }
                var p = n[i] = {exports: {}};
                e[i][0].call(p.exports, function (r) {
                    var n = e[i][1][r];
                    return o(n || r)
                }, p, p.exports, r, e, n, t)
            }
            return n[i].exports
        }

        for (var u = "function" == typeof require && require, i = 0; i < t.length; i++) o(t[i]);
        return o
    }

    return r
})()({
    1: [function (require, module, exports) {
        (function (global) {
            (function () {
                global.sungyeh = global.sungyeh || {};
                global.sungyeh.map = global.sungyeh.map || {};
                global.sungyeh.map.config = require('./lib/map-config');

            }).call(this)
        }).call(this, typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
    }, {"./lib/map-config": 2}], 2: [function (require, module, exports) {
        "use strict";
        Object.defineProperty(exports, "__esModule", {value: true});
        /**
         * 常數設定檔
         *
         * @author sungyeh
         * @class Config
         */
        var Config = /** @class */ (function () {
            /**
             *Creates an instance of Config.
             * @memberof Config
             */
            function Config() {
                this.MAX_ZOOM_LEVEL = 23;
                this.TILE_WIDTH = 256;
                this.TILE_HEIGHT = 256;
                this.DEFAULT_LATITUDE = 23.638;
                this.DEFAULT_LONGITUDE = 120.97;
                this.DEFAULT_ZOOM = 8;
                this.mapName = 'map';
                this.serverUrl = '';
                this.serverDatabase = '0911-v025';
                this.DEFAULT_GLMID = '0';
                this.serverDefinitions = {
                    dbType: 'gemap',
                    isAuthenticated: false,
                    layers: [
                        {
                            icon: 'icons/773_l.png',
                            id: 1004,
                            initialState: false,
                            isPng: true,
                            label: 'parking',
                            lookAt: 'none',
                            opacity: 1,
                            requestType: 'VectorMapsRaster',
                            version: 1
                        },
                        {
                            icon: 'icons/773_l.png',
                            id: 1012,
                            initialState: false,
                            isPng: true,
                            label: 'spot_sum',
                            lookAt: 'none',
                            opacity: 1,
                            requestType: 'VectorMapsRaster',
                            version: 15
                        }
                    ],
                    projection: 'mercator',
                    serverUrl: '',
                    useGoogleLayers: true
                };
                this.slayers = [
                    {
                        id: 1,
                        parentId: 0,
                        name: '靜態資料',
                        fusionId: '',
                        kmlWms: '',
                        seq: 0,
                        type: 'folder'
                    },
                    {
                        id: 2,
                        parentId: 1,
                        name: '觀光景點環斑',
                        fusionId: '1012',
                        kmlWms: '',
                        seq: 0,
                        type: 'object',
                        subtype: 'ringspot'
                    },
                    {
                        id: 5,
                        parentId: 1,
                        name: '台中市停車場點位',
                        fusionId: '1004',
                        kmlWms: '',
                        seq: 0,
                        type: '向量'
                    },
                    {
                        id: 3,
                        parentId: 0,
                        name: '動態資料',
                        fusionId: '',
                        kmlWms: '',
                        seq: 0,
                        type: 'folder'
                    },
                    {
                        id: 4,
                        parentId: 3,
                        name: 'WMS縣市分界',
                        fusionId: 'wms_01',
                        kmlWms: 'http://maps.nlsc.gov.tw/S_Maps/wms?VERSION=1.1.1&VERSION=1.1.1&REQUEST=GetMap' +
                            '&SRS=EPSG:4326&WIDTH=1457&HEIGHT=816&LAYERS=CITY&STYLES=default&TRANSPARENT=TRUE&FORMAT=image/png',
                        seq: 1,
                        type: 'wms'
                    },
                    {
                        id: 9,
                        parentId: 3,
                        name: 'WMS通用版電子地圖',
                        fusionId: 'wms_02',
                        kmlWms: 'https://wms.nlsc.gov.tw/wms?VERSION=1.1.1&VERSION=1.1.1&REQUEST=GetMap' +
                            '&SRS=EPSG:4326&WIDTH=512&HEIGHT=512&LAYERS=EMAP6&STYLES=default&TRANSPARENT=TRUE&FORMAT=image/png',
                        seq: 0,
                        type: 'wms'
                    },
                    {
                        id: 6,
                        parentId: 3,
                        name: '觀光景點群聚圖(Cluster)',
                        fusionId: '',
                        kmlWms: '',
                        seq: 2,
                        type: 'object',
                        subtype: 'cluster'
                    },
                    {
                        id: 7,
                        parentId: 3,
                        name: '彰化縣密度圖(Geojson)',
                        fusionId: '',
                        kmlWms: '',
                        seq: 3,
                        type: 'object',
                        subtype: 'density'
                    },
                    {
                        id: 8,
                        parentId: 3,
                        name: '台中市密度圖(Geojson)',
                        fusionId: '',
                        kmlWms: '',
                        seq: 4,
                        type: 'object',
                        subtype: 'density'
                    },
                    {
                        id: 10,
                        parentId: 1,
                        name: '觀光熱像圖',
                        fusionId: '',
                        kmlWms: '',
                        seq: 2,
                        type: 'object',
                        subtype: 'heatmap'
                    },
                    {
                        id: 11,
                        parentId: 3,
                        name: '觀光景點點位(Marker)',
                        fusionId: '',
                        kmlWms: '',
                        seq: 5,
                        type: 'object',
                        subtype: 'marker'
                    },
                    {
                        id: 12,
                        parentId: 3,
                        name: '台中自行車道位置(kml)',
                        fusionId: 'kml01',
                        kmlWms: 'http://datacenter.taichung.gov.tw/swagger/OpenData/94ed4df6-2eeb-4116-b575-ab88aeb354ed',
                        seq: 6,
                        type: 'kml'
                    },
                    {
                        id: 13,
                        parentId: 3,
                        name: '全台自行車道位置(kml)',
                        fusionId: 'kml02',
                        kmlWms: 'http://gis.taiwan.net.tw/XMLReleaseALL_public/Bike_f.kml',
                        seq: 7,
                        type: 'kml'
                    }
                ];
                this.features = [
                    {
                        title: '查詢條件',
                        fa: 'fa fa-plus-square-o',
                        toolType: 'normal'
                    },
                    {
                        title: '定位路線',
                        fa: 'fa fa-map-marker',
                        toolType: 'normal'
                    },
                    {
                        title: '面積計算',
                        fa: 'fa fa-object-group',
                        toolType: 'draw'
                    },
                    {
                        title: '圖層設定',
                        fa: 'fa fa-gears',
                        toolType: 'normal'
                    }
                ];
                this.tileSet = [
                    {
                        type: 'google', group: 'Goolge', tiles: [
                            {
                                name: '', desc: 'Google基本地圖'
                            }
                        ]
                    },
                    {
                        type: 'osm', group: 'OpenStreetMap', tiles: [
                            {
                                name: '', desc: 'OpenStreetMap基本地圖'
                            }
                        ]
                    },
                    {
                        type: 'mapbox', group: 'MapBox', tiles: [
                            {
                                name: 'mapbox/streets-v9', desc: 'Mapbox基本地圖'
                            },
                            {
                                name: 'kido6115/cjm7cmv184hnv2ss54ynshhee', desc: 'Mapbox客製地圖'
                            }
                        ]
                    },
                    {
                        type: 'nlsc', group: '通用版電子地圖', tiles: [
                            {
                                name: 'EMAP', desc: '通用版電子地圖'
                            },
                            {
                                name: 'EMAP8', desc: '通用版電子地圖英文版'
                            }
                        ]
                    }
                ];
                this.mapboxToken = '';
                this.tgosAppId = '';
                this.tgosApiKey = '';
            }

            return Config;
        }());
        exports.Config = Config;

    }, {}]
}, {}, [1]);
