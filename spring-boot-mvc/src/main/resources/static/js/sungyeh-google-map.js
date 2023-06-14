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
        "use strict";
        var __extends = (this && this.__extends) || (function () {
            var extendStatics = function (d, b) {
                extendStatics = Object.setPrototypeOf ||
                    ({__proto__: []} instanceof Array && function (d, b) {
                        d.__proto__ = b;
                    }) ||
                    function (d, b) {
                        for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
                    };
                return extendStatics(d, b);
            };
            return function (d, b) {
                extendStatics(d, b);

                function __() {
                    this.constructor = d;
                }

                d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
            };
        })();
        Object.defineProperty(exports, "__esModule", {value: true});
        /**
         * Class for a flat (Platte-Carree) projection. In Maps api v3, we
         * only have to convert coordinates for zoom level 0 and it infers the rest.
         */
        var GeeFlatProjection = /** @class */ (function () {
            /**
             *Creates an instance of GeeFlatProjection.
             * @memberof GeeFlatProjection
             */
            function GeeFlatProjection() {
                this.projection = 'flat';
                // Only need zoom level 0, i.e. for a single tile
                var pixels = 256.0;
                this.pixelsPerDegree = pixels / 360.0;
                this.pixelOrigin = new google.maps.Point(pixels / 2.0, pixels / 2.0);
            }

            /**
             * Converts lat/lng coordinates to pixel coordinates at zoom level 0.
             * Note that it is important that both are pairs of floating point
             * numbers (i.e. fractional pixel values are required).
             * @param latLng Latitude and longitude to be converted.
             * @return pixel coordinates.
             */
            GeeFlatProjection.prototype.fromLatLngToPoint = function (latLng) {
                var x = this.pixelOrigin.x + latLng.lng() * this.pixelsPerDegree;
                var y = this.pixelOrigin.y - latLng.lat() * this.pixelsPerDegree;
                return new google.maps.Point(x, y);
            };
            /**
             * Converts pixel coordinates to lat/lng coordinates at zoom level 0.
             * Note that it is important that both are pairs of floating point
             * numbers (i.e. fractional pixel values are required).
             * @param  point Pixel coordinates to be converted.
             * @return  lattitude and longitude.
             */
            GeeFlatProjection.prototype.fromPointToLatLng = function (point) {
                var y = point.y;
                var x = point.x;
                var lng = (x - this.pixelOrigin.x) / this.pixelsPerDegree;
                var lat = (this.pixelOrigin.y - y) / this.pixelsPerDegree;
                return new google.maps.LatLng(lat, lng);
            };
            return GeeFlatProjection;
        }());
        exports.GeeFlatProjection = GeeFlatProjection;
        /**
         * Class for storing vector layer with its index. The index gives
         * position in array of all vector layers in the map. Allows the layer to be
         * toggled on and off.
         */
        var GeeFusionLayer = /** @class */ (function () {
            /**
             * @param index Index into array of vector layers.
             * @param overlay Map overlay for the vector layer.
             */
            function GeeFusionLayer(index, overlay) {
                this.index = index;
                this.overlay = overlay;
            }

            return GeeFusionLayer;
        }());
        exports.GeeFusionLayer = GeeFusionLayer;
        /**
         *
         *
         * @export
         * @class GeeImageMapType
         * @extends {google.maps.ImageMapType}
         */
        var GeeImageMapType = /** @class */ (function (_super) {
            __extends(GeeImageMapType, _super);

            /**
             *Creates an instance of GeeImageMapType.
             * @param {google.maps.ImageMapTypeOptions} opts
             * @memberof GeeImageMapType
             */
            function GeeImageMapType(opts) {
                return _super.call(this, opts) || this;
            }

            return GeeImageMapType;
        }(google.maps.ImageMapType));
        exports.GeeImageMapType = GeeImageMapType;
        /**
         * google.maps.Map擴充
         *
         * @sungyeh
         * @class FusionExtendMap
         * @extends {google.maps.Map}
         */
        var FusionExtendMap = /** @class */ (function (_super) {
            __extends(FusionExtendMap, _super);

            /**
             *Creates an instance of FusionExtendMap.
             * @param {Config} config 常數設定
             * @param {string} imgName
             * @param {google.maps.MapOptions} [mapOpts] 地圖物件選項
             * @memberof FusionExtendMap
             */
            function FusionExtendMap(config, imgName, mapOpts) {
                var _this = this;
                var div = document.getElementById(config.mapName);
                // The map div must exist!
                var mapDiv = document.getElementById(config.mapName);
                if (!mapDiv) {
                    throw new Error("Missing: " + config.mapName + " html node");
                }
                // Create a map based on the imagery layer's channel and version.
                // let projection = config.serverDefs.projection;
                // If we are using Google for the base map, get map from the api.
                // if (config.serverDefs.useGoogleLayers) {
                //     if (projection == 'flat') {
                //         console.log('Expected Mercator projection.');
                //     }
                //     super(div, mapOpts);
                // If not, create the map with the imagery layer in the requested projection.
                // } else {
                //     if (projection == 'flat') {
                //         this.geeCreateFlatMap(config);
                //     } else {
                //         // projection is 'mercator'.
                //         this.geeCreateMercatorMap(config);
                //     }
                // }
                _this = _super.call(this, div, mapOpts) || this;
                _this.config = config;
                _this.objectLayer = {};
                _this.objectData = {};
                _this.imgName = imgName;
                _this.layerMap = {};
                _this.layerVisible = {};
                _this.layerName = {};
                _this.options = {};
                _this.layerIds = [];
                _this.kmlLayer = {};
                _this.wmsLayer = [];
                _this.initializeLayers(config.serverDefinitions);
                return _this;
            }

            /**
             * 初始化Fusion圖層相關屬性
             *
             * @private
             * @param {*} serverDefs Struct of map layers, base url, etc.
             * @returns
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.initializeLayers = function (serverDefs) {
                var layerDefs = serverDefs.layers;
                var serverUrl = serverDefs.serverUrl;
                var REQUEST_TYPE = 'VectorMapsRaster';
                if (layerDefs === undefined || layerDefs.length === 0) {
                    alert('Error: No Layers are defined for this URL.');
                    return;
                }
                // Create tile layers.
                // The base map was created with an empty tile function so that we can
                // also add the 'base' layer here instead of treating it as an exception.
                // By treating it uniformly, its visibility can be controlled.
                var numLayers = 0;
                for (var i = 0; i < layerDefs.length; ++i) {
                    var name_1 = layerDefs[i].label;
                    var channel = layerDefs[i].id;
                    // Use layer glm_id if it is defined. Otherwise, set it to 0.
                    var glmId = layerDefs[i].glm_id;
                    if (glmId === undefined) {
                        glmId = 0;
                    }
                    var requestType = layerDefs[i].requestType;
                    var version = layerDefs[i].version;
                    var enabled = layerDefs[i].initialState;
                    var isPng = layerDefs[i].isPng;
                    if (layerDefs[i].requestType === REQUEST_TYPE) {
                        this.addLayer(name_1, numLayers, isPng, this.geeMapVectorFunc(serverUrl, glmId, channel, version), glmId, channel, enabled);
                    } else if (this.geeIsImageryLayer(layerDefs[i])) {
                        this.addLayer(name_1, numLayers, isPng, this.geeMapImageryFunc(requestType, serverUrl, glmId, channel, version), glmId, channel, enabled);
                    }
                    this.layerIds[numLayers++] =
                        this.config.serverDatabase + "_" + glmId + "-" + channel;
                }
            };
            /**
             * 新增Fusion圖層
             *
             * @param {string} name Name of the layer.
             * @param {number} index Index of vector layer in array of overlays.
             * @param {boolean} isPng Whether vector tiles are png files.
             * @param {string} urlFunction Function to get vector tiles urls.
             * @param {number} glmId Id for this glm (set of channels).
             * @param {number} channel Channel for this layer.
             * @param {boolean} enabled Whether vector layer is visible initially.
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.addLayer = function (name, index, isPng, urlFunction, glmId, channel, enabled) {
                var option = {
                    getTileUrl: urlFunction,
                    tileSize: new google.maps.Size(this.config.TILE_WIDTH, this.config.TILE_HEIGHT),
                    maxZoom: this.config.MAX_ZOOM_LEVEL
                };
                if (name === 'Imagery')
                    option.name = this.imgName;
                var overlay = new GeeImageMapType(option);
                if (enabled) {
                    this.overlayMapTypes.setAt(index, overlay);
                }
                var id = this.config.serverDatabase + "_" + glmId + "-" + channel;
                this.layerMap[id] = new GeeFusionLayer(index, overlay);
                this.layerVisible[id] = enabled;
                this.layerName[id] = name;
                this.options[id] = option;
                if (name === 'Imagery')
                    this.mapTypes.set(this.config.serverDatabase, overlay);
            };
            /**
             * 調整fusion圖層透明度
             *
             * @param {string} id Fusion圖層id
             * @param {number} opacity Opacity ranging from 0 (clear) to 1 (opaque).
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.setOpacity = function (id, opacity) {
                var layer = this.layerMap[id];
                if (layer) {
                    layer.overlay.setOpacity(opacity);
                }
            };
            /**
             * 依id.type顯示Fusion圖層
             *
             * @param {string} id Fusion圖層id
             * @param {string} [type] Gee底圖(not sure)
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.showFusionLayer = function (id, type) {
                if (this.layerMap[id] !== undefined) {
                    this.layerMap[id].overlay.id = id;
                    this.layerVisible[id] = true;
                    var mt = this.layerMap[id].overlay;
                    if (type === 'Imagery') {
                        var max = this.overlayMapTypes.getLength();
                        var checked = false;
                        for (var i = 0; i < max; i++) {
                            var mapType = this.overlayMapTypes.getAt(i);
                            if (mapType && mapType.type === 'layer') {
                                this.overlayMapTypes.insertAt(i, mt);
                                checked = true;
                                break;
                            }
                        }
                        if (checked === false)
                            this.overlayMapTypes.push(mt);
                    } else {
                        this.overlayMapTypes.push(mt);
                    }
                } else {
                    throw new Error("Unknown layer: " + id);
                }
            };
            /**
             * 依id隱藏Fusion圖層
             *
             * @param {string} id Fusion圖層id
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.hideFusionLayer = function (id) {
                if (this.layerMap[id] !== undefined) {
                    var max = this.overlayMapTypes.getLength();
                    for (var i = max - 1; i >= 0; i--) {
                        var mapType = this.overlayMapTypes.getAt(i);
                        if (mapType && mapType.id === id)
                            this.overlayMapTypes.removeAt(i);
                    }
                    this.layerVisible[id] = false;
                } else {
                    throw new Error("Unknown layer: " + id);
                }
            };
            /**
             * 依id查詢此圖層是否顯示
             *
             * @param {string} id Fusion圖層id
             * @returns {boolean}
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.isFusionLayerVisible = function (id) {
                return this.layerVisible[id];
            };
            /**
             * 依id取得此圖層名稱
             *
             * @param {string} id Fusion圖層id
             * @returns {string}
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.getFusionLayerNameById = function (id) {
                return this.layerName[id];
            };
            /**
             * 依照index取得圖層id
             *
             * @param {number} index 索引
             * @returns {string}
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.getFusionLayerIdByIndex = function (index) {
                return this.layerIds[index];
            };
            /**
             * 取得總圖層數
             *
             * @returns {number}
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.getFusionLayerCount = function () {
                return this.layerIds.length;
            };
            /**
             * 新增WMS圖層
             *
             * @param {string} id WMS圖層id
             * @param {string} wmsUrl WMS url
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.addWMSLayer = function (id, wmsUrl) {
                var self = this;
                if (wmsUrl === '') {
                    throw Error('WMS url is empty!');
                }
                var wmsLayer = new google.maps.ImageMapType({
                    getTileUrl: function (coord, zoom) {
                        var proj = self.getProjection();
                        var zfactor = Math.pow(2, zoom);
                        // get Long Lat coordinates
                        var top = proj.fromPointToLatLng(new google.maps.Point(coord.x * 256 / zfactor, coord.y * 256 / zfactor));
                        var bot = proj.fromPointToLatLng(new google.maps.Point((coord.x + 1) * 256 / zfactor, (coord.y + 1) * 256 / zfactor));
                        //  corrections for the slight shift of the SLP (mapserver)
                        var deltaX = 0.0013;
                        var deltaY = 0.00058;
                        //  create the Bounding box string
                        var bbox = top.lng() + deltaX + "," + (bot.lat() + deltaY) + "," + (bot.lng() + deltaX) + "," + (top.lat() + deltaY);
                        //  base WMS URL
                        var urlarr = wmsUrl.split('?');
                        var url = urlarr[0] + '?';
                        var paramarr = urlarr[1].split('&');
                        paramarr.forEach(function (p) {
                            var kvo = p.split('=');
                            switch (kvo[0].toLowerCase()) {
                                case 'width':
                                    break;
                                case 'height':
                                    break;
                                case 'srs':
                                    break;
                                case 'bgcolor':
                                    break;
                                case 'transparent':
                                    break;
                                default:
                                    url += kvo[0] + "=" + kvo[1] + "&";
                                    break;
                            }
                        });
                        url += '&BGCOLOR=0xFFFFFF';
                        url += '&TRANSPARENT=TRUE';
                        url += '&SRS=EPSG:4326';
                        url += "&BBOX=" + bbox;
                        url += '&WIDTH=256';
                        url += '&HEIGHT=256';
                        return url;
                    },
                    tileSize: new google.maps.Size(256, 256)
                });
                wmsLayer.id = id;
                if (this.wmsLayer.indexOf(id) < 0) {
                    this.wmsLayer.push(id);
                }
                this.overlayMapTypes.push(wmsLayer);
                this.layerVisible[id] = true;
            };
            /**
             * 依照WMS圖層ID取得圖層
             *
             * @param {string} id WMS圖層ID
             * @returns {*}
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.getWMSLayerById = function (id) {
                var self = this;
                var layer = null;
                this.wmsLayer.forEach(function (ele) {
                    if (ele === id) {
                        self.overlayMapTypes.forEach(function (element) {
                            if (element) {
                                var mapType = element;
                                if (mapType.id === id) {
                                    layer = mapType;
                                }
                            }
                        });
                    }
                });
                return layer;
            };
            /**
             * 移除WMS圖層
             *
             * @param {string} id WMS圖層id
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.removeWMSLayerById = function (id) {
                var self = this;
                this.wmsLayer.forEach(function (ele) {
                    if (ele === id) {
                        self.overlayMapTypes
                            .forEach(function (element, index) {
                                if (element) {
                                    var mapType = element;
                                    if (mapType.id === id) {
                                        self.overlayMapTypes.removeAt(index);
                                    }
                                }
                            });
                    }
                });
                this.layerVisible[id] = false;
            };
            /**
             * 新增KML圖層
             *
             * @param {string} id KML圖層id
             * @param {string} kmlUrl KML url
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.addKMLLayer = function (id, kmlUrl) {
                var kmlOptions = {
                    preserveViewport: true,
                    url: kmlUrl,
                    map: this
                };
                var kmlLayer = new google.maps.KmlLayer(kmlOptions);
                this.kmlLayer[id] = kmlLayer;
                this.layerVisible[id] = true;
            };
            /**
             * 移除KML圖層
             *
             * @param {string} id KML圖層id
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.removeKMLLayerById = function (id) {
                var kmlLayer = this.kmlLayer[id];
                kmlLayer.setMap(null);
                this.layerVisible[id] = false;
            };
            /**
             * Toggle顯示Config.slayer設定圖層
             *
             * @param {*} param
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.toggleMapLayer = function (param) {
                if (param.fusionId !== undefined && '' !== param.fusionId) {
                    var lid = this.config.serverDatabase + "_" + this.config.DEFAULT_GLMID + "-" + param.fusionId;
                    switch (param.type) {
                        case 'object':
                            if (param.selected) {
                                if (this.layerMap[lid]) {
                                    this.layerMap[lid].overlay.type = 'layer';
                                }
                                this.showFusionLayer(lid);
                            } else {
                                this.hideFusionLayer(lid);
                            }
                            break;
                        case 'wms':
                            param.selected ? this.addWMSLayer(lid, param.kmlWms) : this.removeWMSLayerById(lid);
                            break;
                        case 'kml':
                            param.selected ? this.addKMLLayer(lid, param.kmlWms) : this.removeKMLLayerById(lid);
                            break;
                        case '向量':
                            if (param.selected) {
                                if (this.layerMap[lid]) {
                                    this.layerMap[lid].overlay.type = 'layer';
                                }
                                this.showFusionLayer(lid);
                            } else {
                                this.hideFusionLayer(lid);
                            }
                            break;
                    }
                }
            };
            /**
             * Fusion圖層以外hightlight功能
             *
             * @param {(HeatMap | RingSpot | Cluster | Density | Marker)} objectLayer
             * @param {*} param
             * @param {boolean} checked 關燈與否
             * @memberof FusionExtendMap
             * @deprecated
             */
            FusionExtendMap.prototype.toggleHighlight = function (objectLayer, param, checked) {
                var lid = this.config.serverDatabase + "_" + this.config.DEFAULT_GLMID + "-" + param.fusionId;
                switch (param.type) {
                    case 'object':
                        objectLayer.isHighlight(!checked);
                        if (param.fusionId) {
                            if (checked) {
                                this.setOpacity(lid, 1);
                                this.setOpacity(lid, this.layerMap[lid].overlay.getOpacity() / 2);
                            } else {
                                this.setOpacity(lid, this.layerMap[lid].overlay.getOpacity() * 2);
                            }
                        }
                        break;
                    case 'wms':
                        var wms = this.getWMSLayerById(lid);
                        if (wms !== null) {
                            checked ? wms.setOpacity(0.5) : wms.setOpacity(1);
                        }
                        break;
                    case '向量':
                        if (checked) {
                            this.setOpacity(lid, 1);
                            this.setOpacity(lid, this.layerMap[lid].overlay.getOpacity() / 2);
                        } else {
                            this.setOpacity(lid, this.layerMap[lid].overlay.getOpacity() * 2);
                        }
                        break;
                }
            };
            /**
             * 漸進縮放至指定地點
             *
             * @param {number} targetZoom 目標視野等級
             * @param {number} lat
             * @param {number} lng
             * @memberof FusionExtendMap
             */
            FusionExtendMap.prototype.smoothZoomAndPanTo = function (targetZoom, lat, lng) {
                var currentZoom = this.getZoom();
                var cLat = this.getCenter().lat();
                var cLng = this.getCenter().lng();
                this.panTo({lat: (lat + cLat) / 2, lng: (lng + cLng) / 2});
                var self = this;
                if (currentZoom === targetZoom) {
                    this.panTo({lat: lat, lng: lng});
                } else {
                    google.maps.event.addListenerOnce(self, 'zoom_changed', function () {
                        self.smoothZoomAndPanTo(targetZoom, lat, lng);
                    });
                    setTimeout(function () {
                        self.setZoom(currentZoom + (targetZoom > currentZoom ? 1 : -1));
                    }, 150);
                }
            };
            /**
             * Determines whether layer is an imagery layer based on requestType property
             * of layer definition.
             * @private
             * @param layer definition.
             * @return whether layer is an imagery layer.
             */
            FusionExtendMap.prototype.geeIsImageryLayer = function (layer) {
                return layer.requestType.search('Imagery') !== -1;
            };
            /**
             * Creates a map with a flat (Plate-Carree) projection.
             * @private
             * @param {Config} config 常數設定
             */
            FusionExtendMap.prototype.geeCreateFlatMap = function (config) {
                var option = {
                    getTileUrl: this.geeMapEmptyTileFunc(),
                    tileSize: new google.maps.Size(config.TILE_WIDTH, config.TILE_HEIGHT),
                    minZoom: 0,
                    isPng: true,
                    maxZoom: config.MAX_ZOOM_LEVEL,
                    name: 'flat'
                };
                var flatMapType = new google.maps.ImageMapType(option);
                flatMapType.projection = new GeeFlatProjection();
                this.mapTypes.set('flatMap', flatMapType);
                this.setMapTypeId('flatMap');
                this.overlayMapTypes.insertAt(0, flatMapType);
            };
            /**
             * Creates a map with a Mercator projection. This is the default projection
             * for the Maps API so we don't have to define the projection.
             * @param config 地圖常數設定
             *    Parameters for setting up the map. If this  parameter is not passed
             *    in, default parameters are used.
             * @private
             */
            FusionExtendMap.prototype.geeCreateMercatorMap = function (config) {
                var option = {
                    // The underlying map is created with empty tiles, then added as an overlay
                    // layer same as all the other layers. This allows the visibility toggle to
                    // work on the base layer.
                    getTileUrl: this.geeMapEmptyTileFunc(),
                    tileSize: new google.maps.Size(config.TILE_WIDTH, config.TILE_HEIGHT),
                    minZoom: 0,
                    isPng: true,
                    maxZoom: config.MAX_ZOOM_LEVEL,
                    name: 'mercator'
                };
                var mercatorMapType = new google.maps.ImageMapType(option);
                this.mapTypes.set('mercatorMap', mercatorMapType);
                this.setMapTypeId('mercatorMap');
                this.overlayMapTypes.insertAt(0, mercatorMapType);
            };
            /**
             * Returns a function that can be used to grab vector tiles for the map.
             * @param server The address of the server providing vector tiles.
             * @param glmId Id of the glm.
             * @param channel The channel that vectors are served on.
             * @param version The version of vectors being served.
             * @return function for grabbing vector tiles.
             * @private
             */
            FusionExtendMap.prototype.geeMapVectorFunc = function (server, glmId, channel, version) {
                return this.geeMapTileFunc('VectorMapsRaster', server, glmId, channel, version);
            };
            /**
             * Returns a function that can be used to grab imagery tiles for the map.
             * @param requestType The type of request that imagery is served on.
             * @param server The address of the server providing imagery tiles.
             * @param glmId Id of the glm.
             * @param channel The channel that imagery is served on.
             * @param version The version of imagery being served.
             * @return  function for grabbing imagery tiles.
             * @private
             */
            FusionExtendMap.prototype.geeMapImageryFunc = function (requestType, server, glmId, channel, version) {
                return this.geeMapTileFunc(requestType, server, glmId, channel, version);
            };
            /**
             * Returns a function that will NOT grab any real tiles for the map, but
             * instead fetches a placeholder background tile.
             * @return  function for grabbing imagery tiles.
             * @private
             */
            FusionExtendMap.prototype.geeMapEmptyTileFunc = function () {
                return function (coord, zoom) {
                    return '/shared_assets/images/empty4.png';
                };
            };
            /**
             * Returns a function that can be used to grab tiles for the map.
             * @param request The type of tile being requested.
             * @param server The address of the server providing tiles.
             * @param glmId Id of the glm.
             * @param channel The channel that tiles are served on.
             * @param version The version of tiles being served.
             * @return function for grabbing tiles.
             * @private
             */
            FusionExtendMap.prototype.geeMapTileFunc = function (request, server, glmId, channel, version) {
                return function (coord, zoom) {
                    var numTiles = 1 << zoom;
                    // Don't wrap tiles vertically.
                    if (coord.y < 0 || coord.y >= numTiles) {
                        return '';
                    }
                    // Wrap tiles horizontally.
                    var x = (coord.x % numTiles + numTiles) % numTiles;
                    var glmPath = '';
                    if (glmId) {
                        glmPath = "/" + glmId;
                    }
                    // For simplicity, we use a tileset consisting of 1 tile at zoom level 0
                    // and 4 tiles at zoom level 1.
                    var url = "" + server + glmPath + "/query?request=" + request;
                    url +=
                        "&channel=" + channel + "&version=" + version + "&x=" + x + "&y=" + coord.y + "&z=" + zoom;
                    return url;
                };
            };
            return FusionExtendMap;
        }(google.maps.Map));
        exports.FusionExtendMap = FusionExtendMap;

    }, {}], 2: [function (require, module, exports) {
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
                        id: 4,
                        parentId: 1,
                        name: 'WMS縣市分界',
                        fusionId: 'wms_01',
                        kmlWms: 'http://maps.nlsc.gov.tw/S_Maps/wms?VERSION=1.1.1&VERSION=1.1.1&REQUEST=GetMap' +
                            '&SRS=EPSG:4326&WIDTH=1457&HEIGHT=816&LAYERS=CITY&STYLES=default&TRANSPARENT=TRUE&FORMAT=image/png',
                        seq: 1,
                        type: 'wms'
                    },
                    {
                        id: 9,
                        parentId: 1,
                        name: 'WMS通用版電子地圖',
                        fusionId: 'wms_02',
                        kmlWms: 'https://wms.nlsc.gov.tw/wms?VERSION=1.1.1&VERSION=1.1.1&REQUEST=GetMap' +
                            '&SRS=EPSG:4326&WIDTH=512&HEIGHT=512&LAYERS=EMAP6&STYLES=default&TRANSPARENT=TRUE&FORMAT=image/png',
                        seq: 0,
                        type: 'wms'
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
                        parentId: 3,
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
                        id: 2,
                        parentId: 3,
                        name: '觀光景點環斑',
                        fusionId: '1012',
                        kmlWms: '',
                        seq: 0,
                        type: 'object',
                        subtype: 'ringspot'
                    }
                ];
                this.features = [
                    {
                        title: '查詢條件',
                        fa: 'fa-regular fa-square-plus',
                        toolType: 'normal'
                    },
                    {
                        title: '定位路線',
                        fa: 'fa-solid fa-location-dot',
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

    }, {}], 3: [function (require, module, exports) {
        "use strict";
        var __extends = (this && this.__extends) || (function () {
            var extendStatics = function (d, b) {
                extendStatics = Object.setPrototypeOf ||
                    ({__proto__: []} instanceof Array && function (d, b) {
                        d.__proto__ = b;
                    }) ||
                    function (d, b) {
                        for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
                    };
                return extendStatics(d, b);
            };
            return function (d, b) {
                extendStatics(d, b);

                function __() {
                    this.constructor = d;
                }

                d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
            };
        })();
        Object.defineProperty(exports, "__esModule", {value: true});
        /**
         * 熱度圖
         *
         * @author sungyeh
         * @class HeatMap
         * @extends {google.maps.visualization.HeatmapLayer}
         * @implements {MapObjectInterface}
         */
        var HeatMap = /** @class */ (function (_super) {
            __extends(HeatMap, _super);

            /**
             *Creates an instance of HeatMap.
             * @param {AbstractData[]} data 傳入資料
             * @param {FusionExtendMap} map 地圖物件
             * @param {number} [radius] 資料蒐集半徑(pixel)
             * @memberof HeatMap
             */
            function HeatMap(data, map, radius) {
                var _this = this;
                if (radius === undefined) {
                    radius = 20;
                }
                _this = _super.call(this, {
                    data: data.map(function (element) {
                        return new google.maps.LatLng(element.lat, element.lng);
                    }),
                    map: map,
                    radius: radius
                }) || this;
                return _this;
            }

            /**
             *
             *
             * @memberof HeatMap
             */
            HeatMap.prototype.clear = function () {
                this.setMap(null);
            };
            /**
             *
             *
             * @param {number} opacity
             * @memberof HeatMap
             */
            HeatMap.prototype.setOpacity = function (opacity) {
                this.set('opacity', opacity);
            };
            /**
             *
             *
             * @param {boolean} isHighligh
             * @memberof HeatMap
             */
            HeatMap.prototype.isHighlight = function (isHighligh) {
                isHighligh ? this.setOpacity(0.6) : this.setOpacity(0.2);
            };
            return HeatMap;
        }(google.maps.visualization.HeatmapLayer));
        exports.HeatMap = HeatMap;
        /**
         * 環斑圖
         *
         * @author sungyeh
         * @class RingSpot
         * @implements {SimpleMapObjectInterface}
         */
        var RingSpot = /** @class */ (function () {
            /**
             *Creates an instance of RingSpot.
             * @param {AbstractData[]} data 傳入資料
             * @param {FusionExtendMap} map 地圖物件
             * @param {number} visibleLevel 可視視野等級
             * @param {google.maps.CircleOptions} [circleOption] 圓形物件參數
             * @memberof RingSpot
             */
            function RingSpot(data, map, visibleLevel, circleOption) {
                var self = this;
                this.data = data;
                this.map = map;
                this.markers = this.createMarkers(data, map, visibleLevel);
                if (circleOption) {
                    this.circles = this.createCircle(data, map, circleOption);
                }
                google.maps.event.addListener(map, 'zoom_changed', function () {
                    if (map.getZoom() > visibleLevel) {
                        self.markers.forEach(function (marker) {
                            marker.setVisible(true);
                        });
                    } else {
                        self.markers.forEach(function (marker) {
                            marker.setVisible(false);
                        });
                    }
                });
            }

            /**
             * 依傳入資料建立markers
             *
             * @private
             * @param {AbstractData[]} data 傳入資料
             * @param {FusionExtendMap} map 地圖建
             * @param {number} visibleLevel 可視視野等級
             * @returns {google.maps.Marker[]} 建立之markers
             * @memberof RingSpot
             */
            RingSpot.prototype.createMarkers = function (data, map, visibleLevel) {
                var markers = data.map(function (element) {
                    var option = {
                        position: new google.maps.LatLng(element.lat, element.lng),
                        map: map,
                        visible: map.getZoom() > visibleLevel,
                        info: element,
                        icon: ''
                    };
                    if (element.icon) {
                        option.icon = element.icon;
                    }
                    return new google.maps.Marker(option);
                });
                return markers;
            };
            /**
             * 依傳入資料建立圓形
             *
             * @private
             * @param {AbstractData[]} data 傳入資料
             * @param {FusionExtendMap} map 地圖物件
             * @param {google.maps.CircleOptions} circleOption 圓形物件參數
             * @returns {google.maps.Circle[]}
             * @memberof RingSpot
             */
            RingSpot.prototype.createCircle = function (data, map, circleOption) {
                var circles = data.map(function (element) {
                    circleOption.center =
                        new google.maps.LatLng(element.lat, element.lng);
                    circleOption.map = map;
                    return new google.maps.Circle(circleOption);
                });
                return circles;
            };
            /**
             *
             *
             * @memberof RingSpot
             */
            RingSpot.prototype.clear = function () {
                this.markers.forEach(function (marker) {
                    marker.setMap(null);
                });
                if (this.circles) {
                    this.circles.forEach(function (circle) {
                        circle.setMap(null);
                    });
                }
            };
            /**
             *
             *
             * @param {number} opacity
             * @memberof RingSpot
             */
            RingSpot.prototype.setOpacity = function (opacity) {
                this.markers.forEach(function (marker) {
                    marker.setOpacity(opacity);
                });
                if (this.circles) {
                    this.circles.forEach(function (circle) {
                        circle.setOptions({
                            fillOpacity: opacity
                        });
                    });
                }
            };
            /**
             *
             *
             * @param {boolean} isHighligh
             * @memberof RingSpot
             */
            RingSpot.prototype.isHighlight = function (isHighligh) {
                isHighligh ? this.setOpacity(1) : this.setOpacity(0.2);
            };
            /**
             * 標記物件綁定事件
             *
             * @param {google.maps.MarkerMouseEventNames} trigger 事件類型
             * @param {(this: google.maps.Marker, event: MouseEvent) => void} event 事件
             * @memberof RingSpot
             */
            RingSpot.prototype.addMarkerListener = function (trigger, event) {
                this.markers.forEach(function (marker) {
                    marker.addListener(trigger, event);
                });
            };
            /**
             * 取得marker以及circle
             *
             * @returns {object}
             * @memberof RingSpot
             */
            RingSpot.prototype.getObject = function () {
                return {
                    markers: this.markers,
                    circles: this.circles
                };
            };
            return RingSpot;
        }());
        exports.RingSpot = RingSpot;
        /**
         * 群聚圖
         *
         * @author sungyeh
         * @class Cluster
         * @extends {MarkerClusterer}
         * @implements {MapObjectInterface}
         */
        var Cluster = /** @class */ (function (_super) {
            __extends(Cluster, _super);

            /**
             *Creates an instance of Cluster.
             * @param {AbstractData[]} data 傳入資料
             * @param {FusionExtendMap} map 地圖物件
             * @param {MarkerClustererOptions} [options] 群聚圖參數
             * @memberof Cluster
             */
            function Cluster(data, map, options) {
                var _this = this;
                var markers = data.map(function (element) {
                    var option = {
                        position: {lat: element.lat, lng: element.lng},
                        info: element,
                        icon: '',
                        map: map
                    };
                    if (element.icon) {
                        option.icon = element.icon;
                    }
                    return new google.maps.Marker(option);
                });
                var defaultImage = 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m';
                if (options) {
                    if (options.imagePath === undefined) {
                        options.imagePath = defaultImage;
                    }
                } else {
                    options = {};
                    options.imagePath = defaultImage;
                }
                _this = _super.call(this, map, markers, options) || this;
                _this.setClusterClass(_this.createUuid());
                return _this;
            }

            /**
             * 標記物件綁定事件
             *
             * @param {google.maps.MarkerMouseEventNames} trigger 事件類型
             * @param {(this: google.maps.Marker, event: MouseEvent) => void} event 事件
             * @memberof Cluster
             */
            Cluster.prototype.addMarkerListener = function (trigger, event) {
                this.getMarkers().forEach(function (marker) {
                    marker.addListener(trigger, event);
                });
            };
            /**
             *
             *
             * @memberof Cluster
             */
            Cluster.prototype.clear = function () {
                this.clearMarkers();
            };
            /**
             *
             *
             * @param {number} opacity
             * @memberof Cluster
             */
            Cluster.prototype.setOpacity = function (opacity) {
                this.getMarkers().forEach(function (marker) {
                    marker.setOpacity(opacity);
                });
                var clusters = document.getElementsByClassName(this.getClusterClass());
                Array.prototype.forEach.call(clusters, function (cluster) {
                    var img = cluster.getElementsByTagName('img')[0];
                    if (img) {
                        img.style.opacity = opacity;
                    }
                });
                var self = this;
                google.maps.event.addListener(self, 'clusteringend', function () {
                    var clusters = document.getElementsByClassName(self.getClusterClass());
                    Array.prototype.forEach.call(clusters, function (cluster) {
                        var img = cluster.getElementsByTagName('img')[0];
                        if (img) {
                            img.style.opacity = '' + opacity;
                        }
                    });
                });
            };
            /**
             *
             *
             * @param {boolean} isHighligh
             * @memberof Cluster
             */
            Cluster.prototype.isHighlight = function (isHighligh) {
                isHighligh ? this.setOpacity(1) : this.setOpacity(0.2);
            };
            /**
             * 產生uuid
             *
             * @private
             * @returns {string}
             * @memberof Cluster
             */
            Cluster.prototype.createUuid = function () {
                var dt = new Date().getTime();
                var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'
                    .replace(/[xy]/g, function (c) {
                        var r = (dt + Math.random() * 16) % 16 | 0;
                        dt = Math.floor(dt / 16);
                        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
                    });
                return uuid;
            };
            return Cluster;
        }(MarkerClusterer));
        exports.Cluster = Cluster;
        /**
         * 密度圖
         *
         * @author sungyeh
         * @class Density
         * @extends {google.maps.Data}
         * @implements {MapObjectInterface}
         */
        var Density = /** @class */ (function (_super) {
            __extends(Density, _super);

            /**
             *Creates an instance of Density.
             * @param {object} geojson 傳入geojson
             * @param {FusionExtendMap} map 地圖物件
             * @memberof Density
             */
            function Density(geojson, map) {
                var _this = _super.call(this) || this;
                _this.addGeoJson(geojson);
                var defaultStyle = {
                    strokeWeight: 1,
                    strokeOpacity: .5,
                    strokeColor: '#000',
                    fillColor: 'red',
                    fillOpacity: 1
                };
                // this.setStyle(defaultStyle);
                var self = _this;
                _this.addListener('mouseover', function (event) {
                    self.revertStyle();
                    self.overrideStyle(event.feature, {fillColor: 'black', fillOpacity: .7});
                });
                _this.addListener('mouseout', function () {
                    self.revertStyle();
                });
                _this.setMap(map);
                // this.setStyle(function (feature: any): DensityStyleOptions {
                //     if (feature.getProperty('density')) {
                //     defaultStyle.fillOpacity = feature.j.density;
                //     }
                //     return defaultStyle;
                // });
                _this.geojson = geojson;
                _this.map = map;
                _this.style = defaultStyle;
                return _this;
            }

            /**
             *
             * 利用geojson自訂desity屬性改變區塊透明度
             * @param {DensityStyleOptions} style geojson style參數
             * @memberof Density
             */
            // public setStyle(style: DensityStyleOptions): void {
            //     this.style = style;
            //     super.setStyle(function (feature): DensityStyleOptions {
            //         style.fillOpacity = feature.getProperty('density');
            //         return style;
            //     });
            // }
            /**
             *
             *
             * @param {boolean} isHighligh
             * @memberof Density
             */
            Density.prototype.isHighlight = function (isHighligh) {
                this.style.fillOpacity = isHighligh ?
                    this.style.fillOpacity : this.style.fillOpacity / 2;
                this.setStyle(this.style);
            };
            /**
             *
             *
             * @memberof Density
             */
            Density.prototype.clear = function () {
                this.setMap(null);
            };
            /**
             *
             *
             * @param {number} opacity
             * @memberof Density
             */
            Density.prototype.setOpacity = function (opacity) {
                throw new Error('Method not implemented.');
            };
            return Density;
        }(google.maps.Data));
        exports.Density = Density;
        /**
         * 標記圖
         *
         * @author sungyeh
         * @class Marker
         * @extends {Cluster}
         */
        var Marker = /** @class */ (function (_super) {
            __extends(Marker, _super);

            /**
             *Creates an instance of Marker.
             * @param {AbstractData[]} data 傳入資料
             * @param {FusionExtendMap} map 地圖物件
             * @param {MarkerClustererOptions} [options] 群聚圖參數
             * @memberof Marker
             */
            function Marker(data, map, options) {
                var _this = this;
                if (options) {
                    options.gridSize = 1;
                } else {
                    options = {};
                    options.gridSize = 1;
                }
                _this = _super.call(this, data, map, options) || this;
                _this.setMap(null);
                var self = _this;
                map.addListener('zoom_changed', function () {
                    if (map.getZoom() > 15) {
                        self.setMap(map);
                    } else {
                        if (self.getMap() !== null) {
                            self.setMap(null);
                            self.getMarkers().forEach(function (marker) {
                                marker.setMap(map);
                            });
                        }
                    }
                });
                return _this;
            }

            return Marker;
        }(Cluster));
        exports.Marker = Marker;

    }, {}], 4: [function (require, module, exports) {
        "use strict";
        Object.defineProperty(exports, "__esModule", {value: true});
        /**
         * Google地圖服務
         *
         * @author sungyeh
         * @class GoogleMapService
         */
        var GoogleMapService = /** @class */ (function () {
            /**
             *Creates an instance of GoogleMapService.
             * @memberof GoogleMapService
             */
            function GoogleMapService() {
            }

            /**
             * google 路線規劃服務
             *
             * @param {FusionExtendMap} map 地圖物件
             * @param {(string | google.maps.LatLng)} start 起點
             * @param {(string | google.maps.LatLng)} end 迄點
             * @param {Element} panel 詳細路程面板
             * @memberof GoogleMapService
             */
            GoogleMapService.prototype.googleDirection = function (map, start, end, panel) {
                if (this.oldDirection) {
                    this.oldDirection.setMap(null);
                }
                var directionsService = new google.maps.DirectionsService();
                var directionsDisplay = new google.maps.DirectionsRenderer({
                    'map': map,
                    'preserveViewport': true,
                    'draggable': true
                });
                this.oldDirection = directionsDisplay;
                var request = {
                    origin: start,
                    destination: end,
                    //   旅行工具 WALKING | DRIVING
                    travelMode: google.maps.TravelMode.DRIVING
                };
                if (panel) {
                    directionsDisplay.setPanel(panel);
                }
                directionsService.route(request, function (response, status) {
                    if (status === google.maps.DirectionsStatus.OK) {
                        map.setCenter(new google.maps.LatLng(response.routes[0].legs[0].start_location.lat(), response.routes[0].legs[0].start_location.lng()));
                        directionsDisplay.setDirections(response);
                        map.setZoom(16);
                    }
                });
            };
            /**
             * 清除路線
             *
             * @memberof GoogleMapService
             */
            GoogleMapService.prototype.directionClear = function () {
                if (this.oldDirection) {
                    this.oldDirection.setMap(null);
                }
            };
            /**
             * google 定位服務
             *
             * @param {google.maps.GeocoderRequest} request 地址、地標、行政區...etc
             * @param {(result: google.maps.GeocoderResult[]) => void} callBack 回呼函式
             * @memberof GoogleMapService
             */
            GoogleMapService.prototype.googleGeocoding = function (request, callBack) {
                var geocoder = new google.maps.Geocoder();
                geocoder.geocode(request, function (results, status) {
                    if (status === google.maps.GeocoderStatus.OK) {
                        callBack(results);
                    }
                });
            };
            /**
             * gooogle 多維度距離矩陣，取得不同組合之距離與時間
             *
             * @param {string[]} origins 起點陣列
             * @param {string[]} destinations 迄點陣列
             * @param {(response: google.maps.DistanceMatrixResponse) => void} callBack 回呼函式
             * @memberof GoogleMapService
             */
            GoogleMapService.prototype.googleDistanceMatrix = function (origins, destinations, callBack) {
                var service = new google.maps.DistanceMatrixService();
                var request = {
                    origins: origins,
                    destinations: destinations,
                    travelMode: google.maps.TravelMode.DRIVING,
                    avoidHighways: true,
                    avoidTolls: true
                };
                service.getDistanceMatrix(request, function (response, status) {
                    if (status === google.maps.DistanceMatrixStatus.OK) {
                        callBack(response);
                    }
                });
            };
            return GoogleMapService;
        }());
        exports.GoogleMapService = GoogleMapService;
        /**
         * Tgos地圖服務功能
         *
         * @author sungyeh
         * @class TgosMapService
         */
        var TgosMapService = /** @class */ (function () {
            /**
             *Creates an instance of TgosMapService.
             * @memberof TgosMapService
             */
            function TgosMapService() {
                this.tgosService = new TGOS.TGLocateService();
            }

            /**
             * tgos地址定位
             *
             * @param {string} address 詳細地址
             * @param {(result: object) => void} callBack 回呼函式
             * @memberof TgosMapService
             */
            TgosMapService.prototype.tgosAddressGeocode = function (address, callBack) {
                this.tgosService.locateWGS84({
                    address: address
                }, function (result, status) {
                    if (status === TGOS.TGLocatorStatus.OK) {
                        callBack(result);
                    }
                });
            };
            /**
             * tgos地標定位
             *
             * @param {string} poiName 地標名稱
             * @param {(result: object) => void} callBack 回呼函式
             * @memberof TgosMapService
             */
            TgosMapService.prototype.tgosLandmarGeocode = function (poiName, callBack) {
                this.tgosService.locateWGS84({
                    poi: poiName
                }, function (results, status) {
                    if (status === TGOS.TGLocatorStatus.OK) {
                        callBack(results);
                    }
                });
            };
            /**
             * tgos行政區定位
             *
             * @param {string} districtName 行政區名稱
             * @param {(result: object) => void} callBack 回呼函式
             * @memberof TgosMapService
             */
            TgosMapService.prototype.tgosDistrictGeocode = function (districtName, callBack) {
                this.tgosService.locateWGS84({
                    district: districtName
                }, function (results, status) {
                    if (status === TGOS.TGLocatorStatus.OK) {
                        callBack(results);
                    }
                });
            };
            /**
             * tgos座標轉換最近地址
             *
             * @param {AbstractData} latLng 經緯度座標
             * @param {(result: object) => void} callack 回呼函式
             * @memberof TgosMapService
             */
            TgosMapService.prototype.tgosLatLngGeocode = function (latLng, callack) {
                var tgPoint = new TGOS.TGPoint(latLng.lng, latLng.lat);
                //  利用滑鼠點擊位置查詢最鄰近地址
                new TGOS.TGAddress().nearestAddress(tgPoint, TGOS.TGCoordSys.EPSG3857, function (result, status) {
                    if (status === TGOS.TGLocatorStatus.OK) {
                        callack(result);
                    }
                });
            };
            return TgosMapService;
        }());
        exports.TgosMapService = TgosMapService;

    }, {}], 5: [function (require, module, exports) {
        "use strict";
        var __extends = (this && this.__extends) || (function () {
            var extendStatics = function (d, b) {
                extendStatics = Object.setPrototypeOf ||
                    ({__proto__: []} instanceof Array && function (d, b) {
                        d.__proto__ = b;
                    }) ||
                    function (d, b) {
                        for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p];
                    };
                return extendStatics(d, b);
            };
            return function (d, b) {
                extendStatics(d, b);

                function __() {
                    this.constructor = d;
                }

                d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
            };
        })();
        var __assign = (this && this.__assign) || function () {
            __assign = Object.assign || function (t) {
                for (var s, i = 1, n = arguments.length; i < n; i++) {
                    s = arguments[i];
                    for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                        t[p] = s[p];
                }
                return t;
            };
            return __assign.apply(this, arguments);
        };
        Object.defineProperty(exports, "__esModule", {value: true});
        /**
         * 底圖物件
         *
         * @author sungyeh
         * @class Tile
         */
        var Tile = /** @class */ (function () {
            /**
             *Creates an instance of Tile.
             * @param {string} param1 mapbox|tgos|nlsc
             * @param {string} param2 mapbox地圖Id|tgos地圖名稱|nlsc地圖名稱
             * @param {Config} [config] 常數設定
             * @memberof Tile
             */
            function Tile(param1, param2, config) {
                this.obj = {};
                this.config = config;
                if (param1 && param2) {
                    switch (param1) {
                        case 'tgos':
                            this.obj['tgos'] = this.getTgos(param2);
                            break;
                        case 'mapbox':
                            this.obj['mapbox'] = this.getMapBox(param2);
                            break;
                        case 'nlsc':
                            this.obj['nlsc'] = this.getNlsc(param2);
                            break;
                    }
                    if (param1 === 'osm') {
                        this.obj['osm'] = this.getOsm();
                    }
                } else {
                    this.obj['tgos'] = this.getTgos('TGOSMAP_W.aspx');
                    this.obj['mapbox'] = this.getMapBox('mapbox/streets-v9');
                    this.obj['nlsc'] = this.getNlsc('EMAP');
                    this.obj['osm'] = this.getOsm();
                }
            }

            /**
             * 依類型取得底圖
             *
             * @param {string} name 地圖類型
             * @returns {google.maps.ImageMapTypeOptions|undefined}
             * @memberof Tile
             */
            Tile.prototype.getTile = function (name) {
                return this.obj[name];
            };
            /**
             * 取得Tgos底圖
             *
             * @private
             * @param {string} mapName tgos地圖名稱
             * @returns {google.maps.ImageMapTypeOptions|undefined}
             * @memberof Tile
             */
            Tile.prototype.getTgos = function (mapName) {
                var topLevel = {};
                topLevel['TGOSMAP_W.aspx'] = 20;
                topLevel['MOTCMAP_W.aspx'] = 19;
                topLevel['NLSCMAP_W.aspx'] = 20;
                topLevel['F2IMAGE_W.aspx'] = 18;
                topLevel['ROADMAP_W.aspx'] = 18;
                var tileServer = 'http://api.tgos.nat.gov.tw/TileAgent/';
                var option = undefined;
                if (this.config
                    && this.config.tgosAppId.length > 0
                    && this.config.tgosApiKey.length > 0) {
                    var key_1 = this.config.tgosApiKey;
                    var appId_1 = this.config.tgosAppId;
                    option = {
                        getTileUrl: function (coord, zoom) {
                            return "" + tileServer + mapName + "/GetCacheImage?APPID=" +
                                (appId_1 + "&APIKEY=" + key_1) +
                                ("&S=" + (topLevel[mapName] - zoom - 1)) +
                                ("&X=" + coord.x + "&Y=" + (-1 - coord.y) + "&L=0");
                        },
                        tileSize: new google.maps.Size(256, 256),
                        maxZoom: 19,
                        minZoom: 8,
                        name: 'tgos'
                    };
                } else {
                    console.log('缺少tgos appid及appkey');
                }
                return option;
            };
            /**
             * 取得通用版電子地圖底圖
             *
             * @private
             * @param {string} mapName nlsc地圖名稱
             * @returns {google.maps.ImageMapTypeOptions}
             * @memberof Tile
             */
            Tile.prototype.getNlsc = function (mapName) {
                return {
                    getTileUrl: function (coord, zoom) {
                        return "https://wmts.nlsc.gov.tw/wmts/" + mapName +
                            ("/default/EPSG:3857/" + zoom + "/" + coord.y + "/" + coord.x);
                    },
                    tileSize: new google.maps.Size(256, 256),
                    maxZoom: 20,
                    minZoom: 0,
                    name: 'nlsc'
                };
            };
            /**
             * 取得mapbox底圖
             *
             * @private
             * @param {string} mapId mapbox地圖Id
             * @returns {google.maps.ImageMapTypeOptions|undefined}
             * @memberof Tile
             */
            Tile.prototype.getMapBox = function (mapId) {
                var option = undefined;
                if (this.config && this.config.mapboxToken.length > 0) {
                    var token_1 = this.config.mapboxToken;
                    option = {
                        getTileUrl: function (coord, zoom) {
                            return "https://api.mapbox.com/styles/v1/" + mapId +
                                ("/tiles/256/" + zoom + "/" + coord.x + "/" + coord.y + "?") +
                                ("access_token=" + token_1);
                        },
                        tileSize: new google.maps.Size(256, 256),
                        maxZoom: 18,
                        minZoom: 0,
                        name: 'mapbox'
                    };
                } else {
                    console.log('缺少mapbox token');
                }
                return option;
            };
            /**
             * 取得OpenStreetMap底圖
             *
             * @private
             * @returns {google.maps.ImageMapTypeOptions}
             * @memberof Tile
             */
            Tile.prototype.getOsm = function () {
                var option = undefined;
                option = {
                    getTileUrl: function (coord, zoom) {
                        return "http://tile.openstreetmap.org/" + zoom +
                            ("/" + coord.x + "/" + coord.y + ".png");
                    },
                    tileSize: new google.maps.Size(256, 256),
                    maxZoom: 22,
                    minZoom: 0,
                    name: 'osm'
                };
                return option;
            };
            return Tile;
        }());
        exports.Tile = Tile;
        /**
         * 地圖選擇器
         *
         * @author sungyeh
         * @class MapSelector
         */
        var MapSelector = /** @class */ (function () {
            /**
             *Creates an instance of MapSelector.
             * @param {FusionExtendMap} map 地圖元件
             * @param {google.maps.ControlPosition} position 位置
             * @memberof MapSelector
             */
            function MapSelector(map, position) {
                var self = this;
                var select = document.createElement('select');
                select.id = map.config.mapName + "-mapSelector";
                select.className = 'mapSelector';
                map.config.tileSet.forEach(function (data) {
                    var group = document.createElement('optgroup');
                    group.label = data.group;
                    data.tiles.forEach(function (map) {
                        var option = document.createElement('option');
                        option.setAttribute('type', data.type);
                        option.value = map.name;
                        option.innerText = map.desc;
                        group.appendChild(option);
                    });
                    select.appendChild(group);
                });
                this.select = select;
                map.controls[position].push(select);
                select.onchange = function () {
                    var selectedOotion = self.select.getElementsByTagName('option');
                    var index = self.select.selectedIndex;
                    var type = selectedOotion[index].getAttribute('type');
                    var name = selectedOotion[index].value;
                    if (type !== 'google' && type !== null) {
                        var tile = new Tile(type, name, map.config);
                        var mapOption = tile.getTile(type);
                        map.setMapTypeId(type);
                        if (mapOption) {
                            map.mapTypes.set(type, new google.maps.ImageMapType(mapOption));
                        }
                    } else {
                        map.setMapTypeId('roadmap');
                    }
                };
            }

            /**
             * 事件監聽
             *
             * @param {string} trigger 事件類型
             * @param {(evt: Event) => void} event 事件
             * @memberof MapSelector
             */
            MapSelector.prototype.addListener = function (trigger, event) {
                this.select.addEventListener(trigger, event);
            };
            /**
             * 隱藏選擇器
             *
             * @memberof MapSelector
             */
            MapSelector.prototype.hide = function () {
                this.select.style.display = 'none';
            };
            /**
             * 顯示選擇器
             *
             * @memberof MapSelector
             */
            MapSelector.prototype.show = function () {
                this.select.style.display = 'block';
            };
            return MapSelector;
        }());
        exports.MapSelector = MapSelector;
        /**
         * 繪圖物件
         *
         * @sungyeh
         * @class DrawObject
         */
        var DrawObject = /** @class */ (function (_super) {
            __extends(DrawObject, _super);

            /**
             *Creates an instance of DrawObject.
             * @param {DrawObjectOptions} optionDrawingManagerOption屬性
             * @memberof DrawObject
             */
            function DrawObject(option) {
                var _this = this;
                if (option === undefined) {
                    option = {
                        drawingMode: null,
                        drawingControl: true,
                        drawingControlOptions: {
                            position: google.maps.ControlPosition.BOTTOM_CENTER,
                            drawingModes: [
                                google.maps.drawing.OverlayType.MARKER,
                                google.maps.drawing.OverlayType.RECTANGLE,
                                google.maps.drawing.OverlayType.CIRCLE,
                                google.maps.drawing.OverlayType.POLYGON,
                                google.maps.drawing.OverlayType.POLYLINE
                            ]
                        },
                        circleOptions: {
                            fillColor: 'red',
                            fillOpacity: 0.5,
                            strokeWeight: 2,
                            clickable: false,
                            editable: false,
                            zIndex: 1
                        },
                        autoClear: true
                    };
                }
                _this = _super.call(this, option) || this;
                _this.draw = [];
                var self = _this;
                if (option.autoClear) {
                    // 將上一個圖形清除
                    google.maps.event.addListener(_this, 'overlaycomplete', function (shape) {
                        if (self.draw.length > 0) {
                            self.clear();
                        }
                        self.draw.push(shape.overlay);
                    });
                }
                return _this;
            }

            /**
             * 清除地圖上drawing
             *
             * @memberof DrawObject
             */
            DrawObject.prototype.clear = function () {
                this.draw.forEach(function (data) {
                    data.set('map', null);
                });
            };
            /**
             * 檢核資料是否包含在polygon中
             *
             * @param {AbstractData[]} data 單筆資料須包含lat,lng屬性
             * @param {google.maps.Polygon} polygon 框選之polygon
             * @returns {AbstractData[]}
             * @memberof DrawObject
             */
            DrawObject.prototype.polygonContainsData = function (data, polygon) {
                return data.filter(function (element) {
                    return google.maps.geometry.poly.containsLocation(new google.maps.LatLng(element.lat, element.lng), polygon);
                });
            };
            /**
             * 檢核資料是否包含在rectangle中
             *
             * @param {AbstractData[]} data 單筆資料須包含lat,lng屬性
             * @param {google.maps.Rectangle} rectangle 框選之rectangle
             * @returns {AbstractData[]}
             * @memberof DrawObject
             */
            DrawObject.prototype.rectangleContainsData = function (data, rectangle) {
                var bound = rectangle.getBounds();
                var lngMin = bound.getSouthWest().lng();
                var latMin = bound.getSouthWest().lat();
                var lngMax = bound.getNorthEast().lng();
                var latMax = bound.getNorthEast().lat();
                var path = [
                    {lat: latMin, lng: lngMin},
                    {lat: latMax, lng: lngMin},
                    {lat: latMax, lng: lngMax},
                    {lat: latMin, lng: lngMax}
                ];
                var simulatePolygon = new google.maps.Polygon({
                    paths: path
                });
                return this.polygonContainsData(data, simulatePolygon);
            };
            /**
             * 檢核資料是否包含在circle中
             *
             * @param {AbstractData[]} data 單筆資料須包含lat,lng屬性
             * @param {google.maps.Circle} circle 框選之circle
             * @returns {AbstractData[]}
             * @memberof DrawObject
             */
            DrawObject.prototype.circleContainsData = function (data, circle) {
                return data.filter(function (element) {
                    return google.maps.geometry.spherical.computeDistanceBetween(new google.maps.LatLng(element.lat, element.lng), circle.getCenter())
                        < circle.getRadius();
                });
            };
            /**
             * 檢核資料是否包含在marker半徑中
             *
             * @param {AbstractData[]} data 單筆資料須包含lat,lng屬性
             * @param {google.maps.Marker} marker 點選之marker
             * @param {number} radius 資料半徑(meter)
             * @param {FusionExtendMap} map 地圖物件
             * @returns {AbstractData[]}
             * @memberof DrawObject
             */
            DrawObject.prototype.markerContainsData = function (data, marker, radius, map) {
                var position = marker.getPosition();
                if (position === null) {
                    position = undefined;
                }
                var circle = new google.maps.Circle({
                    fillColor: 'red',
                    fillOpacity: 0.5,
                    strokeWeight: 2,
                    zIndex: 1,
                    radius: radius,
                    center: position,
                    map: map
                });
                this.draw.push(circle);
                return this.circleContainsData(data, circle);
            };
            return DrawObject;
        }(google.maps.drawing.DrawingManager));
        exports.DrawObject = DrawObject;
        /**
         * 主要功能列表
         *
         * @author sungyeh
         * @class FeatureTools
         */
        var FeatureTools = /** @class */ (function () {
            /**
             *Creates an instance of FeatureTools.
             * @param {FusionExtendMap} map 地圖物件
             * @memberof FeatureTools
             */
            function FeatureTools(map) {
                this.toolIds = [];
                this.featureIcons = [];
                this.featuresMain;
                this.drawObject;
                this.map = map;
            }

            /**
             * 建立主要功能列表
             *
             * @param {FeatureToolsOptions} tools FeatureTools屬性
             * @param listener 建立完成後事件
             * @memberof FeatureTools
             */
            FeatureTools.prototype.createTools = function (tools, printable, listener) {
                var self = this;
                if (tools.drawObject) {
                    this.drawObject = tools.drawObject;
                }
                var orient = 'block';
                if (tools.orient) {
                    if (tools.orient === 'vertical') {
                        orient = 'block';
                    }
                    if (tools.orient === 'horizontal') {
                        orient = 'inline-block';
                    }
                }
                var mapId = this.map.config.mapName;
                var featuresMain = document.createElement('div');
                featuresMain.id = mapId + "-features";
                featuresMain.className = 'map-feature js-fold';
                featuresMain.style.height =
                    (this.map.config.features.length * 83) + 140 + "px";
                //  列印圖資按鈕
                var featurePrint = document.createElement('button');
                featurePrint.style.display = orient;
                featurePrint.setAttribute('class', 'btn-print btn btn-secondary rounded-0  fa-solid fa-print');
                featurePrint.innerHTML = ' 列印';
                featurePrint.id = 'print-map';
                //  摺疊選單按鈕
                var featureFold = document.createElement('button');
                featureFold.style.display = orient;
                featureFold.setAttribute('class', 'sidebar-fold btn btn-secondary rounded-0  fa-solid fa-caret-up');
                var normalCount = 0;
                this.map.config.features.forEach(function (feat) {
                    var featDiv = document.createElement('div');
                    featDiv.title = feat.title;
                    featDiv.style.display = orient;
                    featDiv.className = 'sidebar-btn';
                    var featureIcon;
                    featureIcon = document.createElement('i');
                    featureIcon.className = feat.fa;
                    featureIcon.style.fontSize = '30';
                    if (feat.img) {
                        featureIcon = document.createElement('img');
                        featureIcon.setAttribute('src', feat.img);
                        featureIcon.style.width = '35px';
                    }
                    featureIcon.setAttribute('tool-type', feat.toolType);
                    var toolId;
                    switch (feat.toolType) {
                        case 'draw':
                            if (tools.drawTool) {
                                toolId = tools.drawTool;
                                self.toolIds.push(toolId);
                                featureIcon.id = toolId + "-icon";
                            }
                            break;
                        case 'normal':
                            if (tools.normalTool) {
                                toolId = tools.normalTool[normalCount];
                                self.toolIds.push(toolId);
                                normalCount++;
                                featureIcon.id = toolId + "-icon";
                            }
                            break;
                    }
                    featDiv.onclick = function () {
                        //  展開工具列
                        var tool = document.getElementById(toolId);
                        if (tool) {
                            if (tool.classList.contains('d-none')) {
                                tool.classList.toggle('d-none');
                                // 關閉其他工具列
                                Array.prototype.forEach.call(document.getElementsByClassName(mapId + "-tools"), function (element) {
                                    if (element.id !== tool.id) {
                                        element.classList.add('d-none');
                                    }
                                });
                                // 展開/關閉drawObject
                                if (self.drawObject) {
                                    featureIcon.getAttribute('tool-type') === 'draw' ?
                                        self.drawObject.setMap(self.map) :
                                        self.drawObject.setMap(null);
                                    self.drawObject.clear();
                                }
                                var featDivAll = document.getElementById(mapId + "-features");
                                if (featDivAll) {
                                    Array.prototype.forEach.call(featDivAll.getElementsByClassName('sidebar-btn'), function (element) {
                                        element.classList.remove('click');
                                    });
                                }
                                tool.style.display = 'block';
                                // 藍底
                                featDiv.classList.add('click');
                                // 展開工具列
                                featuresMain.classList.add('open');
                            } else {
                                // 關閉drawObject
                                if (featureIcon.getAttribute('tool-type') === 'draw'
                                    && self.drawObject) {
                                    self.drawObject.setMap(null);
                                    self.drawObject.clear();
                                }
                                tool.classList.add('d-none');
                                // 藍底
                                featDiv.classList.remove('click');
                                // 展開工具列
                                featuresMain.classList.remove('open');
                            }
                        }
                        ;
                    };
                    var featureTitle = document.createElement('span');
                    featureTitle.innerText = feat.title;
                    featureTitle.style.display = orient;
                    featDiv.appendChild(featureIcon);
                    featDiv.appendChild(featureTitle);
                    featuresMain.appendChild(featDiv);
                    self.featureIcons.push(featureIcon);
                    //  摺疊選單
                    featureFold.onclick = function () {
                        featuresMain.classList.toggle('slideup');
                        featuresMain.classList.toggle('click');
                        var featFoldList = document.getElementsByClassName(mapId + "-tools");
                        var icon = featuresMain.querySelectorAll('.sidebar-btn');
                        if (featuresMain.classList.contains('slideup')) {
                            Array.prototype.forEach.call(featFoldList, function (element) {
                                element.classList.add('d-none');
                            });
                            Array.prototype.forEach.call(icon, function (element) {
                                element.classList.remove('click');
                            });
                        } else {
                            if (featuresMain.classList.contains('open')) {
                                featFoldList[0].classList.remove('d-none');
                                icon[0].classList.add('click');
                            }
                        }
                    };
                });
                if (printable) {
                    featuresMain.appendChild(featurePrint);
                    if (listener) {
                        featurePrint.addEventListener('click', listener);
                    }
                }
                featuresMain.appendChild(featureFold);
                this.map.controls[tools.position].push(featuresMain);
                this.featuresMain = featuresMain;
            };
            /**
             * 注入自製tool
             *
             * @param {string} toolId 自製tool id
             * @param {string} elementId 目標樣板名稱
             * @returns {string}
             * @memberof FeatureTools
             */
            FeatureTools.prototype.insertTool = function (toolId, elementId) {
                var tool = document.createElement('div');
                var mapId = this.map.config.mapName;
                tool.id = toolId;
                tool.className = mapId + "-tools d-none";
                // tool.style.display = 'none';
                tool.style.userSelect = 'none';
                var element = document.getElementById(elementId);
                if (element) {
                    tool.appendChild(element);
                }
                var map = document.getElementById(mapId);
                if (map) {
                    map.appendChild(tool);
                }
                return toolId;
            };
            /**
             * icon事件監聽
             *
             * @param {string} trigger 事件類型
             * @param {(evt: Event) => void} event 事件內容
             * @memberof FeatureTools
             */
            FeatureTools.prototype.addIconListener = function (trigger, event) {
                this.featureIcons.forEach(function (element) {
                    element.addEventListener(trigger, event);
                });
            };
            /**
             * feature tool事件監聽
             *
             * @param {string} trigger 事件類型
             * @param {(evt: Event) => void} event 事件內容
             * @memberof FeatureTools
             */
            FeatureTools.prototype.addFeatureToolListener = function (trigger, event) {
                if (this.featuresMain) {
                    this.featuresMain.addEventListener(trigger, event);
                }
            };
            /**
             * 隱藏主要功能列
             *
             * @memberof FeatureTools
             */
            FeatureTools.prototype.hide = function () {
                if (this.featuresMain) {
                    this.featuresMain.style.display = 'none';
                }
                this.toolIds.forEach(function (element) {
                    var object = document.getElementById(element);
                    if (object) {
                        object.style.display = 'none';
                    }
                });
                if (this.drawObject) {
                    this.drawObject.setMap(null);
                }
            };
            /**
             * 顯示主要功能列
             *
             * @memberof FeatureTools
             */
            FeatureTools.prototype.show = function () {
                if (this.featuresMain) {
                    this.featuresMain.style.display = 'block';
                }
            };
            return FeatureTools;
        }());
        exports.FeatureTools = FeatureTools;
        /**
         * 雙視窗工具
         *
         * @export
         * @class MultiWindows
         */
        var MultiWindows = /** @class */ (function () {
            /**
             *Creates an instance of MultiWindows.
             * @param {FusionExtendMap} firstMap 左側地圖物件
             * @param {FusionExtendMap} secondMap 右側地圖物件
             * @param {string} container 雙視窗容器Id
             * @memberof MultiWindows
             */
            function MultiWindows(firstMap, secondMap, container) {
                this.firstMap = firstMap;
                this.secondMap = secondMap;
                this.container = container;
                var containerDiv = document.getElementById(container);
                if (containerDiv) {
                    this.init(firstMap, secondMap, containerDiv);
                    this.single();
                } else {
                    throw new Error(container + " is not exists");
                }
            }

            /**
             * 初始化多視窗地圖
             *
             * @private
             * @param {FusionExtendMap} firstMap 左側地圖
             * @param {FusionExtendMap} secondMap 右側地圖
             * @param {HTMLElement} containerDiv container html element
             * @memberof MultiWindows
             */
            MultiWindows.prototype.init = function (firstMap, secondMap, containerDiv) {
                var firstMapDiv = document.getElementById(firstMap.getDiv().id);
                var secondMapDiv = document.getElementById(secondMap.getDiv().id);
                containerDiv.style.position = 'fixed';
                if (firstMapDiv && secondMapDiv) {
                    //  調整地圖配合container
                    firstMapDiv.style.width = containerDiv.offsetWidth + "px";
                    firstMapDiv.style.height = '100%';
                    secondMapDiv.style.width = containerDiv.offsetWidth + "px";
                    secondMapDiv.style.height = '100%';
                }
                //  左方地圖CSS
                var left = document.createElement('div');
                left.id = 'left-map';
                left.style.position = 'absolute';
                left.style.overflow = 'hidden';
                left.style.width = '50%';
                left.style.height = '100%';
                left.style.zIndex = '2';
                // 右方地圖CSS
                var right = document.createElement('div');
                right.id = 'right-map';
                right.style.width = '100%';
                right.style.height = '100%';
                right.style.zIndex = '1';
                left.appendChild(firstMap.getDiv());
                right.appendChild(secondMap.getDiv());
                //  中央分隔線
                var bar = this.barGenerator(left, right);
                //  resize event for swipe mode
                var resize = function () {
                    if (firstMapDiv && secondMapDiv) {
                        firstMapDiv.style.width = containerDiv.offsetWidth + "px";
                        secondMapDiv.style.width = containerDiv.offsetWidth + "px";
                    }
                };
                //  模式選擇器
                this.switchGenerator(resize, containerDiv, left, right, bar);
                this.left = left;
                this.bar = bar;
                this.right = right;
                var firstCenterListener;
                var secondCenterListener;
                //  兩側地圖座標同步事件
                firstCenterListener = firstMap.addListener('center_changed', function () {
                    secondCenterListener.remove();
                    secondMap.setCenter(firstMap.getCenter());
                    secondCenterListener = secondMap.addListener('center_changed', function () {
                        firstMap.setCenter(secondMap.getCenter());
                    });
                });
                secondCenterListener = secondMap.addListener('center_changed', function () {
                    firstCenterListener.remove();
                    firstMap.setCenter(secondMap.getCenter());
                    firstCenterListener = firstMap.addListener('center_changed', function () {
                        secondMap.setCenter(firstMap.getCenter());
                    });
                });
                var firstZoomListener;
                var secondZoomListener;
                //  兩側地圖縮放同步事件
                firstZoomListener = firstMap.addListener('zoom_changed', function () {
                    secondZoomListener.remove();
                    secondCenterListener.remove();
                    secondMap.setZoom(firstMap.getZoom());
                    secondMap.setCenter(firstMap.getCenter());
                    secondZoomListener = secondMap.addListener('zoom_changed', function () {
                        firstMap.setZoom(secondMap.getZoom());
                        firstMap.setCenter(secondMap.getCenter());
                    });
                    secondCenterListener = secondMap.addListener('center_changed', function () {
                        firstCenterListener.remove();
                        firstMap.setCenter(secondMap.getCenter());
                        firstCenterListener =
                            firstMap.addListener('center_changed', function () {
                                secondMap.setCenter(firstMap.getCenter());
                            });
                    });
                });
                secondZoomListener = secondMap.addListener('zoom_changed', function () {
                    firstZoomListener.remove();
                    firstCenterListener.remove();
                    firstMap.setZoom(secondMap.getZoom());
                    firstMap.setCenter(secondMap.getCenter());
                    firstZoomListener = firstMap.addListener('zoom_changed', function () {
                        secondMap.setZoom(firstMap.getZoom());
                        secondMap.setCenter(firstMap.getCenter());
                    });
                    firstCenterListener = firstMap.addListener('center_changed', function () {
                        secondCenterListener.remove();
                        secondMap.setCenter(firstMap.getCenter());
                        secondCenterListener =
                            secondMap.addListener('center_changed', function () {
                                firstMap.setCenter(secondMap.getCenter());
                            });
                    });
                });
            };
            /**
             * 產生中央分隔線
             *
             * @private
             * @param {HTMLDivElement} left 左方div
             * @param {HTMLDivElement} right 右方div
             * @returns {HTMLDivElement} 產生bar元素
             * @memberof MultiWindows
             */
            MultiWindows.prototype.barGenerator = function (left, right) {
                var bar = document.createElement('div');
                bar.id = 'mutilwindow-control-bar';
                bar.style.left = left.style.width;
                bar.onmousedown = function () {
                    var move = function (event) {
                        left.style.width = event.clientX - left.offsetLeft + "px";
                        bar.style.left = event.clientX - left.offsetLeft + "px";
                    };
                    document.onmousemove = function (event) {
                        move(event);
                        if (event.clientX - left.offsetLeft <= 0) {
                            left.style.width = '0px';
                            bar.style.left = '0px';
                        } else if (event.clientX - left.offsetLeft
                            >= right.offsetWidth) {
                            left.style.width = right.offsetWidth + "px";
                            bar.style.left = right.offsetWidth + "px";
                        }
                    };
                    document.onmouseup = function () {
                        document.onmousemove = null;
                    };
                };
                return bar;
            };
            /**
             * 產生模式選擇器
             *
             * @private
             * @param {() => void} resize 重新計算bar function
             * @param {HTMLElement} containerDiv 雙視窗容器
             * @param {HTMLDivElement} left 左方div
             * @param {HTMLDivElement} right 右方div
             * @param {HTMLDivElement} bar bar元素
             * @memberof MultiWindows
             */
            MultiWindows.prototype.switchGenerator = function (resize, containerDiv, left, right, bar) {
                var single = document.createElement('input');
                var singleLabel = document.createElement('label');
                singleLabel.innerText = '單一地圖';
                singleLabel.htmlFor = 'singleMode';
                single.type = 'radio';
                var self = this;
                single.onclick = function () {
                    window.removeEventListener('resize', resize);
                    self.single();
                };
                single.id = 'singleMode';
                single.name = 'map-mode';
                var swipe = document.createElement('input');
                var swipeLabel = document.createElement('label');
                swipeLabel.innerText = '滑曳模式';
                swipeLabel.htmlFor = 'swipeMode';
                swipe.type = 'radio';
                single.checked = true;
                swipe.onclick = function () {
                    self.swipe();
                    window.addEventListener('resize', resize);
                };
                swipe.id = 'swipeMode';
                swipe.name = 'map-mode';
                var compare = document.createElement('input');
                var compareLabel = document.createElement('label');
                compareLabel.innerText = '雙視窗模式';
                compareLabel.htmlFor = 'compareMode';
                compare.type = 'radio';
                compare.onclick = function () {
                    window.removeEventListener('resize', resize);
                    self.compare();
                };
                compare.id = 'compareMode';
                compare.name = 'map-mode';
                var selector = document.createElement('div');
                selector.id = 'mode-selector';
                selector.appendChild(single);
                selector.appendChild(singleLabel);
                selector.appendChild(swipe);
                selector.appendChild(swipeLabel);
                selector.appendChild(compare);
                selector.appendChild(compareLabel);
                selector.onclick = function () {
                    selector.classList.toggle('hide');
                };
                containerDiv.appendChild(selector);
                containerDiv.appendChild(left);
                containerDiv.appendChild(bar);
                containerDiv.appendChild(right);
            };
            /**
             * 雙視窗模式
             *
             * @private
             * @memberof MultiWindows
             */
            MultiWindows.prototype.compare = function () {
                var firstMapDiv = document.getElementById(this.firstMap.getDiv().id);
                var secondMapDiv = document.getElementById(this.secondMap.getDiv().id);
                var containerDiv = document.getElementById(this.container);
                if (containerDiv && firstMapDiv && secondMapDiv) {
                    firstMapDiv.style.width = '100%';
                    secondMapDiv.style.width = '100%';
                }
                if (this.left) {
                    this.left.style.position = '';
                    this.left.style.display = 'inline-block';
                    this.left.style.width = '50%';
                }
                if (this.right) {
                    this.right.style.display = 'inline-block';
                    this.right.style.width = '50%';
                }
                if (this.bar && this.left) {
                    this.bar.style.display = 'inline-block';
                    this.bar.style.left = this.left.style.width;
                    this.bar.onmousedown = null;
                }
            };
            /**
             * 滑曳模式
             *
             * @private
             * @memberof MultiWindows
             */
            MultiWindows.prototype.swipe = function () {
                var firstMapDiv = document.getElementById(this.firstMap.getDiv().id);
                var secondMapDiv = document.getElementById(this.secondMap.getDiv().id);
                var containerDiv = document.getElementById(this.container);
                if (containerDiv && firstMapDiv && secondMapDiv) {
                    firstMapDiv.style.width = containerDiv.offsetWidth + "px";
                    secondMapDiv.style.width = containerDiv.offsetWidth + "px";
                }
                if (this.left) {
                    this.left.style.position = 'absolute';
                    this.left.style.display = '';
                    if (containerDiv) {
                        this.left.style.width = containerDiv.offsetWidth / 2 + "px";
                    }
                }
                if (this.right) {
                    this.right.style.display = '';
                    this.right.style.width = '100%';
                }
                var self = this;
                if (self.bar) {
                    if (containerDiv) {
                        self.bar.style.left = containerDiv.offsetWidth / 2 + "px";
                    }
                    self.bar.style.display = '';
                    self.bar.onmousedown = function () {
                        var move = function (event) {
                            if (self.left && self.bar) {
                                self.left.style.width =
                                    event.clientX - self.left.offsetLeft + "px";
                                self.bar.style.left =
                                    event.clientX - self.left.offsetLeft + "px";
                            }
                        };
                        document.onmousemove = function (event) {
                            move(event);
                            if (self.left && self.bar && self.right) {
                                if (event.clientX - self.left.offsetLeft <= 0) {
                                    self.left.style.width = '0px';
                                    self.bar.style.left = '0px';
                                } else if (event.clientX - self.left.offsetLeft
                                    >= self.right.offsetWidth) {
                                    self.left.style.width =
                                        self.right.offsetWidth + "px";
                                    self.bar.style.left = self.right.offsetWidth + "px";
                                }
                            }
                        };
                        document.onmouseup = function () {
                            document.onmousemove = null;
                        };
                    };
                }
            };
            /**
             * 左方主視窗模式
             *
             * @private
             * @memberof MultiWindows
             */
            MultiWindows.prototype.single = function () {
                var containerDiv = document.getElementById(this.container);
                var firstMapDiv = document.getElementById(this.firstMap.getDiv().id);
                if (containerDiv && firstMapDiv) {
                    if (this.left && this.right && this.bar && firstMapDiv) {
                        this.left.style.width = '100%';
                        firstMapDiv.style.width = '100%';
                        this.right.style.display = 'none';
                        this.bar.style.display = 'none';
                    }
                }
            };
            return MultiWindows;
        }());
        exports.MultiWindows = MultiWindows;
        /**
         * 圖層清單巢狀樣板
         * @author sungyeh
         * @class Tree
         */
        var Tree = /** @class */ (function () {
            /**
             *Creates an instance of Tree.
             * @param {string} mapId 地圖id
             * @param {string} toolId tool id
             * @param {TreeOptionsInterface} options Tree 參數
             * @memberof Tree
             */
            function Tree(mapId, toolId, options, config) {
                var div = document.createElement('div');
                div.id = toolId;
                div.className = mapId + "-tools";
                this.target = div;
                this.config = config;
                var mapDiv = document.getElementById(mapId);
                if (mapDiv) {
                    mapDiv.appendChild(div);
                }
                var defaults = {
                    slayers: [],
                    activeItem: [],
                    highlight: [],
                    iconType: {
                        'folder': {
                            'close': '../demo/image/folder-close.jpg',
                            'open': '../demo/image/folder-open.jpg'
                        },
                        '向量': {
                            'close': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAABHUlEQVRYhWNgGGDAiC5Q3DNThJWBKYgcw5gYGAx/MP7v7ytOu0W2Ayp6ZhtLi4seMNNT5yHVAYdOXWb49fvX77ffv+kQ6wgWbIIiAvw/zPQ0SHbAqYs3GYI9bFmXbdp7taR7oXFPafwlQnqYSLWEEFCUkWCI8nNmEeBiPlvSvVCP7g4g1RE0cQApjqCZA4h1BNUd8PTlGxTMxsrCYGOiy8LHwXS2uneqLLp6rLmAXKCtKs9w6uJNrHJ8PNx/n7/5psfAwPCYZg5wtDDAKbdiy4HPL96+fYEuTtM0QAwYdcCoA0YdMOqAUQeMOmDUAaMOGHAHYG2SvfnwkePUpRtUtejNh48cRDngN8O/h09fvi5ev/s1VR0AM5vqhlIKAAgnYwPURwamAAAAAElFTkSuQmCC',
                            'open': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAADPElEQVRYhe2X20sUURzHv2ecmZ29sO666nhJrdxKSddrElQP4Us9FFFUEERUoH+AG0QX6CmI7Cb2UImxFRHUU/TgQ4JEBFlraKUZmmW07tbqqq2zszO7Mz2sSS5ruTJrL35hHs7vXL6fc/j9mHOA/ywSH2hqvpHJgNq7lMUooEok6pXLTQ0flwxwsvlWTT6f1VXn2GBKFuBZ91tIsiSPh4SyxULQiYKZlnSxzlGSNEB37yD27djG3H/c+d550VXTfOJI37/mUMma/EtrVuXg0O562mJIczsvuhzLDpAsREoAkoFIGcBiITQH+Obzz/tYhsbW2nLazFHu05euF8SPT1gFS9XGdUXo7h1M2Gc2GaNjfsEB4GvKALZvrlyw78GTrp/e8XFvfDylObAYrQCsACwbgE6vozMtZmN8XNMy/FNSVMaQbxQBYRJsGosILeuzsrILUw4QDAt45O7AgGcY2VYLOB0LRVXgn55m/dOTV4+2n8LtY+fvpQRgbPIHWjpdKC7IQ/2mapA/7zuFIGFZtr0a7G9tuHN2T4D7cPDhgYdRzXIgGBbQ0ulCdel6FPE5881npWMYbC2rSC/Oy9+ZIZW0Ahom4SN3B+wF+bAYF75IsWksTIwJlXa7Qc/q9h9uc5ZpAiBFZQx4hlHAZ//VvJavQg1fCYaiUW3fYDNw3BlNAIZ8o8i2WhIe+2/zGr4KBsaAj4EhRJQoeKsVUMkWTQACwhQ4HTvXzuCsyNTb5sxr+SoYGQPe+fsxNhP7IRJCQBHCalIFbBoDRVViC4OgJGM99LQeAxODWG0uhGHW3Dvji5+qaHICuZYsTAcFAIAKFT3fexGOSthoK13QXJQkqCoCmgCssuZgJiRClKTY4hERr309CEVCC+0cI16PGFWVu5qV4a6Kerz79GmuLUZEvPC8TGguhEW8H/08QUL0tYQ54J+c4rr7PiQFQBEjSJhF3/AwyovXgoDM5UW8+VO3e1wMSwfvNF4QNHucrl1duM6cbsx9O/Fmk0gFiyqK7TreagUhMQtRkjAy5gn3fxmZkGX5QPvxC8+BBI9TLXS4zVlm4LgzUMkWihAWgKKqCChK9B5E5urNxnPC77G/AJ0gPVRroZO2AAAAAElFTkSuQmCC'
                        },
                        'kml': {
                            'close': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAABHUlEQVRYhWNgGGDAiC5Q3DNThJWBKYgcw5gYGAx/MP7v7ytOu0W2Ayp6ZhtLi4seMNNT5yHVAYdOXWb49fvX77ffv+kQ6wgWbIIiAvw/zPQ0SHbAqYs3GYI9bFmXbdp7taR7oXFPafwlQnqYSLWEEFCUkWCI8nNmEeBiPlvSvVCP7g4g1RE0cQApjqCZA4h1BNUd8PTlGxTMxsrCYGOiy8LHwXS2uneqLLp6rLmAXKCtKs9w6uJNrHJ8PNx/n7/5psfAwPCYZg5wtDDAKbdiy4HPL96+fYEuTtM0QAwYdcCoA0YdMOqAUQeMOmDUAaMOGHAHYG2SvfnwkePUpRtUtejNh48cRDngN8O/h09fvi5ev/s1VR0AM5vqhlIKAAgnYwPURwamAAAAAElFTkSuQmCC',
                            'open': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAADPElEQVRYhe2X20sUURzHv2ecmZ29sO666nhJrdxKSddrElQP4Us9FFFUEERUoH+AG0QX6CmI7Cb2UImxFRHUU/TgQ4JEBFlraKUZmmW07tbqqq2zszO7Mz2sSS5ruTJrL35hHs7vXL6fc/j9mHOA/ywSH2hqvpHJgNq7lMUooEok6pXLTQ0flwxwsvlWTT6f1VXn2GBKFuBZ91tIsiSPh4SyxULQiYKZlnSxzlGSNEB37yD27djG3H/c+d550VXTfOJI37/mUMma/EtrVuXg0O562mJIczsvuhzLDpAsREoAkoFIGcBiITQH+Obzz/tYhsbW2nLazFHu05euF8SPT1gFS9XGdUXo7h1M2Gc2GaNjfsEB4GvKALZvrlyw78GTrp/e8XFvfDylObAYrQCsACwbgE6vozMtZmN8XNMy/FNSVMaQbxQBYRJsGosILeuzsrILUw4QDAt45O7AgGcY2VYLOB0LRVXgn55m/dOTV4+2n8LtY+fvpQRgbPIHWjpdKC7IQ/2mapA/7zuFIGFZtr0a7G9tuHN2T4D7cPDhgYdRzXIgGBbQ0ulCdel6FPE5881npWMYbC2rSC/Oy9+ZIZW0Ahom4SN3B+wF+bAYF75IsWksTIwJlXa7Qc/q9h9uc5ZpAiBFZQx4hlHAZ//VvJavQg1fCYaiUW3fYDNw3BlNAIZ8o8i2WhIe+2/zGr4KBsaAj4EhRJQoeKsVUMkWTQACwhQ4HTvXzuCsyNTb5sxr+SoYGQPe+fsxNhP7IRJCQBHCalIFbBoDRVViC4OgJGM99LQeAxODWG0uhGHW3Dvji5+qaHICuZYsTAcFAIAKFT3fexGOSthoK13QXJQkqCoCmgCssuZgJiRClKTY4hERr309CEVCC+0cI16PGFWVu5qV4a6Kerz79GmuLUZEvPC8TGguhEW8H/08QUL0tYQ54J+c4rr7PiQFQBEjSJhF3/AwyovXgoDM5UW8+VO3e1wMSwfvNF4QNHucrl1duM6cbsx9O/Fmk0gFiyqK7TreagUhMQtRkjAy5gn3fxmZkGX5QPvxC8+BBI9TLXS4zVlm4LgzUMkWihAWgKKqCChK9B5E5urNxnPC77G/AJ0gPVRroZO2AAAAAElFTkSuQmCC'
                        },
                        'wms': {
                            'close': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAABHUlEQVRYhWNgGGDAiC5Q3DNThJWBKYgcw5gYGAx/MP7v7ytOu0W2Ayp6ZhtLi4seMNNT5yHVAYdOXWb49fvX77ffv+kQ6wgWbIIiAvw/zPQ0SHbAqYs3GYI9bFmXbdp7taR7oXFPafwlQnqYSLWEEFCUkWCI8nNmEeBiPlvSvVCP7g4g1RE0cQApjqCZA4h1BNUd8PTlGxTMxsrCYGOiy8LHwXS2uneqLLp6rLmAXKCtKs9w6uJNrHJ8PNx/n7/5psfAwPCYZg5wtDDAKbdiy4HPL96+fYEuTtM0QAwYdcCoA0YdMOqAUQeMOmDUAaMOGHAHYG2SvfnwkePUpRtUtejNh48cRDngN8O/h09fvi5ev/s1VR0AM5vqhlIKAAgnYwPURwamAAAAAElFTkSuQmCC',
                            'open': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAADPElEQVRYhe2X20sUURzHv2ecmZ29sO666nhJrdxKSddrElQP4Us9FFFUEERUoH+AG0QX6CmI7Cb2UImxFRHUU/TgQ4JEBFlraKUZmmW07tbqqq2zszO7Mz2sSS5ruTJrL35hHs7vXL6fc/j9mHOA/ywSH2hqvpHJgNq7lMUooEok6pXLTQ0flwxwsvlWTT6f1VXn2GBKFuBZ91tIsiSPh4SyxULQiYKZlnSxzlGSNEB37yD27djG3H/c+d550VXTfOJI37/mUMma/EtrVuXg0O562mJIczsvuhzLDpAsREoAkoFIGcBiITQH+Obzz/tYhsbW2nLazFHu05euF8SPT1gFS9XGdUXo7h1M2Gc2GaNjfsEB4GvKALZvrlyw78GTrp/e8XFvfDylObAYrQCsACwbgE6vozMtZmN8XNMy/FNSVMaQbxQBYRJsGosILeuzsrILUw4QDAt45O7AgGcY2VYLOB0LRVXgn55m/dOTV4+2n8LtY+fvpQRgbPIHWjpdKC7IQ/2mapA/7zuFIGFZtr0a7G9tuHN2T4D7cPDhgYdRzXIgGBbQ0ulCdel6FPE5881npWMYbC2rSC/Oy9+ZIZW0Ahom4SN3B+wF+bAYF75IsWksTIwJlXa7Qc/q9h9uc5ZpAiBFZQx4hlHAZ//VvJavQg1fCYaiUW3fYDNw3BlNAIZ8o8i2WhIe+2/zGr4KBsaAj4EhRJQoeKsVUMkWTQACwhQ4HTvXzuCsyNTb5sxr+SoYGQPe+fsxNhP7IRJCQBHCalIFbBoDRVViC4OgJGM99LQeAxODWG0uhGHW3Dvji5+qaHICuZYsTAcFAIAKFT3fexGOSthoK13QXJQkqCoCmgCssuZgJiRClKTY4hERr309CEVCC+0cI16PGFWVu5qV4a6Kerz79GmuLUZEvPC8TGguhEW8H/08QUL0tYQ54J+c4rr7PiQFQBEjSJhF3/AwyovXgoDM5UW8+VO3e1wMSwfvNF4QNHucrl1duM6cbsx9O/Fmk0gFiyqK7TreagUhMQtRkjAy5gn3fxmZkGX5QPvxC8+BBI9TLXS4zVlm4LgzUMkWihAWgKKqCChK9B5E5urNxnPC77G/AJ0gPVRroZO2AAAAAElFTkSuQmCC'
                        },
                        'object': {
                            'close': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAABHUlEQVRYhWNgGGDAiC5Q3DNThJWBKYgcw5gYGAx/MP7v7ytOu0W2Ayp6ZhtLi4seMNNT5yHVAYdOXWb49fvX77ffv+kQ6wgWbIIiAvw/zPQ0SHbAqYs3GYI9bFmXbdp7taR7oXFPafwlQnqYSLWEEFCUkWCI8nNmEeBiPlvSvVCP7g4g1RE0cQApjqCZA4h1BNUd8PTlGxTMxsrCYGOiy8LHwXS2uneqLLp6rLmAXKCtKs9w6uJNrHJ8PNx/n7/5psfAwPCYZg5wtDDAKbdiy4HPL96+fYEuTtM0QAwYdcCoA0YdMOqAUQeMOmDUAaMOGHAHYG2SvfnwkePUpRtUtejNh48cRDngN8O/h09fvi5ev/s1VR0AM5vqhlIKAAgnYwPURwamAAAAAElFTkSuQmCC',
                            'open': 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAADPElEQVRYhe2X20sUURzHv2ecmZ29sO666nhJrdxKSddrElQP4Us9FFFUEERUoH+AG0QX6CmI7Cb2UImxFRHUU/TgQ4JEBFlraKUZmmW07tbqqq2zszO7Mz2sSS5ruTJrL35hHs7vXL6fc/j9mHOA/ywSH2hqvpHJgNq7lMUooEok6pXLTQ0flwxwsvlWTT6f1VXn2GBKFuBZ91tIsiSPh4SyxULQiYKZlnSxzlGSNEB37yD27djG3H/c+d550VXTfOJI37/mUMma/EtrVuXg0O562mJIczsvuhzLDpAsREoAkoFIGcBiITQH+Obzz/tYhsbW2nLazFHu05euF8SPT1gFS9XGdUXo7h1M2Gc2GaNjfsEB4GvKALZvrlyw78GTrp/e8XFvfDylObAYrQCsACwbgE6vozMtZmN8XNMy/FNSVMaQbxQBYRJsGosILeuzsrILUw4QDAt45O7AgGcY2VYLOB0LRVXgn55m/dOTV4+2n8LtY+fvpQRgbPIHWjpdKC7IQ/2mapA/7zuFIGFZtr0a7G9tuHN2T4D7cPDhgYdRzXIgGBbQ0ulCdel6FPE5881npWMYbC2rSC/Oy9+ZIZW0Ahom4SN3B+wF+bAYF75IsWksTIwJlXa7Qc/q9h9uc5ZpAiBFZQx4hlHAZ//VvJavQg1fCYaiUW3fYDNw3BlNAIZ8o8i2WhIe+2/zGr4KBsaAj4EhRJQoeKsVUMkWTQACwhQ4HTvXzuCsyNTb5sxr+SoYGQPe+fsxNhP7IRJCQBHCalIFbBoDRVViC4OgJGM99LQeAxODWG0uhGHW3Dvji5+qaHICuZYsTAcFAIAKFT3fexGOSthoK13QXJQkqCoCmgCssuZgJiRClKTY4hERr309CEVCC+0cI16PGFWVu5qV4a6Kerz79GmuLUZEvPC8TGguhEW8H/08QUL0tYQ54J+c4rr7PiQFQBEjSJhF3/AwyovXgoDM5UW8+VO3e1wMSwfvNF4QNHucrl1duM6cbsx9O/Fmk0gFiyqK7TreagUhMQtRkjAy5gn3fxmZkGX5QPvxC8+BBI9TLXS4zVlm4LgzUMkWihAWgKKqCChK9B5E5urNxnPC77G/AJ0gPVRroZO2AAAAAElFTkSuQmCC'
                        }
                    },
                    toolIcon: {
                        list: {
                            src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAAAfElEQVRYhe3UsQ2DMBCF4f/wNMyQZSIKSiYi7MMqqWnvLkUAIRLRQcP7mrNlS89uHojcnS2Lxzj1kE+g7C9lJOFJeODzjL/z6Ow7ATfL4d3VLUC1ibkiHKBkWrNsqn3gyeE/1gdYMgB+Qbgb9jr6uNyLekA9IKIeUA+IyAfwD9fqRZ6k2wAAAABJRU5ErkJggg==',
                            desc: '圖層清單'
                        },
                        search: {
                            src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAAEC0lEQVRYheWVb0zVVRjHP8/5gcjFP4CQQ9vCkYz8B3fOCjfRtZXWVmJbNFcxoM3qBXO1+a4Vr2wre9d6kwqOahoZNVc6X9Sma6tN7ZLDcUnx+j8REL1eAe/9nacX9w+CCb+LaW19X52d5znn+ZznPOc88H+XeHVsamoyoVCoxHGcImttnqpedl23p7W1tfe+AtTW1pYaY94G1otI0TizisgxYE80Gv20tbU18o8BNDY2ZkUikY9U9U0gEwBfPvjykWk+dDgM1y9BbDhOonoJ2NzS0tJ2zwD19fWFwLfASoyDFFciJauR2fPGOtoY9AZxT+yHgdMACmxtbm5+1yuAM35i06ZNmdbafcAqyZmDqdqMWbASmT7zb/ANzHgIs2AlZM2E3i5Btcrv9w8FAoGfvQCY8RPRaHQbsFpy5mCe2oLkzvd0ElNShXnidRJJ/aCurm5V2gC1tbWlwFsYB6l8I36qNCTzy5HH1gEYEfkYD0U+BiBR7ZlSXOn55HdsWPYMkp0LsKKhoWGNZ4CmpiYDrAcwj0667u5ypiHFlQCoarVngFAoVCIiReLLh1njn3t6kqLFyeGkdZCRIjHmYVVFcwpSxrICQ3VpBp1XLPv+iAHwwsJMFhUK7cEYwX5LTqZQVx7/Jlo6okSiivpSe0x6j6kMWGtnAEhGVsq4MM+Q5QiLCkZLZVGhkOUIpfnxubk5UJAtFGQLc3MSGZiWnXSf7TkDIhL/00fCKePhcy4AwQGbmmsPxijNNxw6G7eFrimHzrqoxscAOnQt6X7JM4DjOCHXdVXDlwUbA5PB4LCmUp9UsN8S7B8Fsgo/nRnrw+D55Oj0ZACp3G7fvv2yiBwjOgS9wcnWTSi92AGAiOz3DJDQHgC383tQnVr0SB967iiA67ruN2kBhMPhT4DzXD2D7TmcfnC1uEe+iDcp2LFr165TaQG0tbUNqeo7gGrH1+iFjrSC26NfwpVugH5Vfd/Lsju6YSAQOOH3+zNRrdLzx0AtMqcYzB2uo7pxBfeXHRC/exfwiUhPIBA4kjZAAuLHioqKQRGepu+k4cyv6K2b8W/WyQDjoEOD0NuNBg9if9sNkT6AASCbeGafLV28dLjz+O8TtuUJu1WipW4TkccnOUhMRJqtte8Bz4nIZ9eHbpkLV2+Q65u++0D7no1TAkj6NDQ0rEk0llXAPCAP+BM4BRyw1u69veBe2vjqlnMD4Q81EWCWL+vzg+1fvTZVgClp7YaanddujtQnH3PeXTIxQWXdm051dX63eFn5IyNR1w8wHI0tWbqsouxkV+feBwLgFeK+AiQhliwrXzASdSsARmLuEv/y5T90dx6/eL9jj9HaDTU7n1xXreterNn6QAPfrudffmXFvxb8P6u/AMWwla7K3S4xAAAAAElFTkSuQmCC',
                            desc: '圖層搜尋'
                        },
                        clear: {
                            src: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAYAAABzenr0AAAABmJLR0QA/wD/AP+gvaeTAAAGi0lEQVRYhb1Xa2wU1xk9d3Zmn7Mvr5/4/QgY45hg1o+4EBwlhQAKbZXEoKhKGkVqpUYKLU0Ap62y/ROnSZRWKFJCEkh+EFHZVIU6tQHjOA3GAT8KiakxNvbix67fa3t3x7OzuzO3P5CNH4uB1OL8vHO+c858372jOwQPGA4HZS7pm9L5NNfNqrIymTxI8+cqK1WSO6PRpNdleH3ClItncpkHGUAaSP9jakJM7qb87FgTb4iKm5DTH1iArW81Zas59pW1GYl8KCzDK4iSIauvZy5A6V8aLNvfuZAJSld8LA4HZbRadaU9NzOaIQQ9AyMzMqXvVZWVyQwAlB1qPZCktXSvXmVpevZQW9vTjlb9SgZoM7aWp66KTrOYDFAUBc7BEZ+ehA4DALuz4rxVzTGv/WlPXjRDCL66OmSpbnHtBVCxEuZPVrRkqFnVb9dmJBoBoG9oIgTg86p9JSIAMBK0VhuvURhyq/PxZp1ayzIpK2HucFBGryH/sD+caWMYBqBAt9M96Rfld2c5zLmDdqfLI7q+ujoU7BiYxvHGvvGpQPjDlQjQZmx7PSXBlmY18QAA99ikIlNaU/9G0cQshwUhVHK0bqpuce3VsKo0nyB9cPpgSfv9vmkT25IIjcZz9vX1AnCr9RzLvJaTmWSa5V3rHZwQJPHN+bX/947f8X5zGUeYv/J6HSOKAUaWaZ0/EN5n0Klqi9eveSTKbCAAMD7pRUt7T82pffad8+vZSKIbD7dyWUF8ToEcv6i8VLO/8EpE84qLzxg49UdbCnOsrEoFCsA14tnd3tX3VGKslZ01B4COHtd4KBg8uFgjYoBoT3jXupyYnz6WE6v/8PT1owDyF3NKHQ1ajVb1MSWw+AQRVhMPAiApLkq1KsYaBdA5rtc/A0EM9NREGG3EAAyU3m63VwIFKCUdkTgxVr7yJ0XJfHqckXx6rpfKRIUN2WlEp9WAYQjmT7fT6faFQuHjkXTuuAe2vX1pPSFKZkAM/PNrx+PheW/OWsymWoteteXN3Xnc7Hp7/xSOfXOT2qxm5GQmE45VzWkJMwFc6bzp8fnFKyFJfPZf5Zsn7xogEp58u9Vs0dLa4jUxeWGFGqaFIAoesmFDehQAgFKg/vthnPluWElLiiWZyfGEkNsW7rFJ+XKH89pGn329w0EUAFBFtloESslO844XTWqmes/mjKwn8uI1uSkWRJu0aLw2Bo9fQkacEYQAGfE8tqyLJb0uD/n2ar+i1alh1OsIABgNOsbrF9gOqav1xrkjzrt2YPuhGg0XtO3RqNnfr0k0xT/3aIrRpOcWcMKygqqmfrg9IvJSzXg0Oxa89tbWmhZCOPq1k45Oh+gja1MZi9GAG/1Dwavd/b8+faDkyJIADgdlmtXNWQyLYoOefZ5S5OdnROmfyIs3RBs1yzZpSgiivW8Szd0e/HJrFoy620H7xwUcqXdShuUQkELihE/cUnegqHVBgB9XXNzKqVBpM2q4zTkx7OoEkzo5xgCGRG6SXwyBYxlouIVT7BiYxqnmAcSYtUi06rAtf9WcRnXLIK1vHxk9ta8gfpY/dx+oKy8+GyZkQyAkO8WAEl7O/Lrbi6EpcYk5AOQkm1H+TC6etidCphRH63sgSGFIYQXfdo6PBUOyfT5/iUOpo4G1Wfl3bLz6hV9tW22L4m+3XpDCOHlpEFkJPIoeio4YbjH+0+vBmctueMVQSJDkN6p/V/jesgFmsf3PF4t5Hfu3nxUnJxRk2tQXOsfwZasLuwoSsTkn7p7MZ9E56KWfnuu+/Pe9djsIofOfLX8KHBdNRit3DATbvDNB9e5NqSjNjV+uZAl8YghvnfjvyMTMTH7dgU3uxc+XvZTWOoq9lXs37goq8su8jvMkRvPKnbiDEzPwiaEFa7JC8UHtdY9XDP8ikvldA8zi5G8Kj81IyrpP6rpavvh3r1+Wb3eRUqChfRgjU+KCowcAxxtveicF6f2a/YWn76R9b19CAN11n/jLig4f/V7q5S51TTy8Nsms94khfHSmG2YDt2Q056+NSo0do3VVrxa8spzuD7qQbK9o2qDXqU+GqZK8Md1Gfl6avkDoO+ekfOwbZ4dARgtqX90hLaf1g35MastLLjNKMJsl5MS4LzAhzJv9jWG/8sV5p3NSkB+/mzmwMleyp4yc6rPnH0uL0Ws48vHZrgEhoBTX7C8cvpf6FfkL2vruhViTRnOCykiRhOCPvvxDiWsldO8bpY4G/n5r/gf/La2xE6roxwAAAABJRU5ErkJggg==',
                            desc: '清除圖層'
                        }
                    },
                    layerClick: function () {
                    },
                    onHighlight: function () {
                    }
                };
                this.opt = __assign({}, defaults, options);
                this.mapId = mapId;
                this.style();
                this.event();
                this.subscribeEvents();
            }

            /**
             * 建立UI
             */
            Tree.prototype.style = function () {
                var tree = this;
                //  上方工具列
                var toolbar = document.createElement('div');
                toolbar.className = 'tree-toolbar';
                Object.keys(this.opt.toolIcon).forEach(function (icontype) {
                    var img = document.createElement('img');
                    img.className = 'tree-toolbar-icon';
                    img.src = tree.opt.toolIcon[icontype].src;
                    img.title = tree.opt.toolIcon[icontype].desc;
                    img.setAttribute('data-type', icontype);
                    toolbar.appendChild(img);
                });
                this.target.appendChild(toolbar);
                //  搜尋工具容器
                var searchContainer = document.createElement('div');
                searchContainer.className = 'tree-search-container';
                var searchInput = document.createElement('input');
                searchInput.className = 'form-control';
                searchInput.id = this.mapId + "-tree-search-input";
                searchInput.placeholder = '請輸入圖層關鍵字';
                var searchResultList = document.createElement('div');
                searchResultList.className = 'tree-search-result-list';
                searchContainer.appendChild(searchInput);
                searchContainer.appendChild(searchResultList);
                this.target.appendChild(searchContainer);
                //  圖層清單容器
                var itemList = document.createElement('div');
                itemList.className = 'tree-item-list';
                itemList.style.display = 'none';
                //  產生圖層清單並塞到容器中
                this.opt.slayers
                    .filter(function (slayers) {
                        return slayers.parentId === 0;
                    })
                    .sort(function (a, b) {
                        return a.seq - b.seq;
                    })
                    .forEach(function (slayer) {
                        var div = document.createElement('div');
                        div.className = 'tree-item border card-header';
                        div.setAttribute('data-id', '' + slayer.id);
                        div.setAttribute('data-state', 'close');
                        div.setAttribute('data-path', '' + slayer.id);
                        var span = document.createElement('span');
                        span.className = 'tree-content-text';
                        span.textContent = slayer.name;
                        div.appendChild(span);
                        var icon = document.createElement('i');
                        icon.className = 'tree-content-icon fa-solid fa-chevron-down';
                        div.appendChild(icon);
                        itemList.appendChild(div);
                    });
                this.target.appendChild(itemList);
            };
            /**
             * 開關目錄及圖層
             *
             * @private
             * @param {HTMLElement} target Tree.target
             * @memberof Tree
             */
            Tree.prototype.menu = function (target) {
                var tree = this;
                var dataId = target.getAttribute('data-id');
                var lid = dataId ? +dataId : 0;
                var state = target.getAttribute('data-state');
                var path = target.getAttribute('data-path');
                var slayer = tree.getSlayerDetail(lid);
                var type = slayer.type;
                var pattern = new RegExp("^" + path + "_");
                var targetParent = target.parentNode;
                // 目錄開關
                if (state === 'close') {
                    target.setAttribute('data-state', 'open');
                    Array.prototype.forEach.call(target.getElementsByClassName('tree-content-icon'), function (child) {
                        child.setAttribute('src', tree.opt.iconType[type]['open']);
                    });
                    if (type === 'folder') {
                        tree.opt.slayers
                            .filter(function (slayers) {
                                return slayers.parentId === lid;
                            })
                            .sort(function (a, b) {
                                return (+a.parentId < +b.parentId) ? 1 : -1;
                            })
                            .forEach(function (slayer) {
                                var div = document.createElement('div');
                                div.className = 'tree-item btn btn-outline-secondary';
                                div.setAttribute('data-id', '' + slayer.id);
                                div.setAttribute('data-sort', '' + slayer.seq);
                                div.setAttribute('data-state', 'close');
                                div.setAttribute('data-path', path + "_" + slayer.id);
                                if (tree.opt.activeItem.indexOf(+slayer.id) >= 0) {
                                    div.setAttribute('data-state', 'open');
                                }
                                var span = document.createElement('span');
                                span.className = 'tree-content-text';
                                span.textContent = slayer.name;
                                div.appendChild(span);
                                if (targetParent) {
                                    targetParent.insertBefore(div, target.nextSibling);
                                }
                                // 淡化功能
                                // const highlight = $('<div/>',
                                //     {'class': 'highlight'});
                                // highlight.css({
                                //     'display': 'inline',
                                //     'float': 'right'
                                // });
                                // div.after(highlight);
                                // const checkbox = $('<input/>',
                                //     {'type': 'checkbox'});
                                // if (o.opt.highlight.indexOf(+div.data('id'))
                                //     > -1) {
                                //     checkbox.prop('checked', true);
                                // } else {
                                //     checkbox.prop('checked', false);
                                //     if (div.data('state') !== 'open') {
                                //         checkbox.prop('disabled', true);
                                //     }
                                // }
                                // highlight.append(checkbox);
                                // checkbox.after($('<br>'));
                            });
                    } else {
                        tree.opt.activeItem.push(lid);
                        var merge = __assign({}, tree.getSlayerDetail(lid), {selected: true});
                        this.layerClickObject = merge;
                        var event_1 = this.layerClickEvent;
                        if (event_1) {
                            this.target.dispatchEvent(event_1);
                        }
                        // et.next().find('input').prop('disabled', false);
                    }
                } else {
                    target.setAttribute('data-state', 'close');
                    Array.prototype.forEach.call(target.getElementsByClassName('tree-content-icon'), function (child) {
                        child.setAttribute('src', tree.opt.iconType[type]['close']);
                    });
                    if (type === 'folder' && targetParent) {
                        var removeElement_1 = [];
                        Array.prototype.forEach.call(targetParent.childNodes, function (sibling) {
                            var path = sibling.getAttribute('data-path');
                            if (path) {
                                if (pattern.test(path)) {
                                    removeElement_1.push(sibling);
                                }
                            }
                        });
                        removeElement_1.forEach(function (sibling) {
                            targetParent.removeChild(sibling);
                        });
                    } else {
                        var merge = __assign({}, tree.getSlayerDetail(lid), {selected: false});
                        this.layerClickObject = merge;
                        var event_2 = this.layerClickEvent;
                        if (event_2) {
                            this.target.dispatchEvent(event_2);
                        }
                        tree.opt.activeItem
                            .splice(tree.opt.activeItem.indexOf(+lid), 1);
                        // et.next().find('input').prop('disabled', true);
                        // et.next().find('input').prop('checked', false);
                    }
                }
            };
            /**
             * 切換選單/搜尋模式
             *
             * @private
             * @param {HTMLElement} target Tree.target
             * @param {string} type 搜尋/清單
             * @memberof Tree
             */
            Tree.prototype.toggleSearchList = function (type) {
                if (type === 'search' || type === 'list') {
                    Array.prototype.forEach.call(this.target.getElementsByClassName('tree-item-list'), function (element) {
                        element.style.display = type === 'search' ? 'none' : '';
                    });
                    Array.prototype.forEach.call(this.target.getElementsByClassName('tree-search-container'), function (element) {
                        element.style.display = type === 'search' ? '' : 'none';
                    });
                } else if (type === 'clear') {
                    this.removeActiveData();
                }
            };
            /**
             * 註冊click事件
             *
             * @private
             * @memberof Tree
             */
            Tree.prototype.event = function () {
                var tree = this;
                //  點擊搜圖層清單/搜尋工具/清除圖層
                this.target.addEventListener('click', function (e) {
                    var click = e.target;
                    tree.toggleSearchList(click.getAttribute('data-type'));
                });
                //  點擊圖層選項
                this.target.addEventListener('click', function (e) {
                    var click = e.target;
                    var parent = click.parentNode;
                    var checkClass = function (click, parent) {
                        var target = undefined;
                        var clickClasses = click.className.split(' ');
                        var parentClasses = parent.className.split(' ');
                        target = clickClasses.indexOf('tree-item') > -1 ? click :
                            parentClasses.indexOf('tree-item') > -1 ?
                                parent : undefined;
                        return target;
                    };
                    var target = checkClass(click, parent);
                    if (target) {
                        tree.menu(target);
                    }
                });
                // 顯示搜尋結果
                var searchInput = document.getElementById(tree.mapId + "-tree-search-input");
                if (searchInput) {
                    searchInput.addEventListener('keyup', function (e) {
                        var code = e.keyCode;
                        var KEY_CODE_ENTER = 13;
                        var KEY_CODE_SPACE = 32;
                        var KEY_CODE_COMMA = 188;
                        var KEY_CODE_SEMICOLON = 186;
                        var KEY_CODE = [KEY_CODE_ENTER, KEY_CODE_SPACE, KEY_CODE_COMMA, KEY_CODE_SEMICOLON];
                        if (KEY_CODE.indexOf(code) > -1) {
                            var input_1 = searchInput.value;
                            var resultDiv_1 = document.createElement('div');
                            tree.opt.slayers
                                .filter(function (slayer) {
                                    if (input_1) {
                                        return slayer.type !== 'folder'
                                            && slayer.name.indexOf(input_1.trim()) >= 0;
                                    } else {
                                        return false;
                                    }
                                })
                                .forEach(function (slayer) {
                                    var state = 'close';
                                    if (tree.opt.activeItem
                                        .indexOf(slayer.id * 1) >= 0) {
                                        state = 'open';
                                    }
                                    var div = document.createElement('div');
                                    div.className = 'tree-item';
                                    div.setAttribute('data-id', '' + slayer.id);
                                    div.setAttribute('data-state', state);
                                    div.setAttribute('data-path', '' + slayer.id);
                                    var icon = document.createElement('img');
                                    icon.className = 'tree-content-icon';
                                    icon.src = tree.opt.iconType[slayer.type][state];
                                    div.appendChild(icon);
                                    var span = document.createElement('span');
                                    span.className = 'tree-content-text';
                                    span.textContent = slayer.name;
                                    div.appendChild(span);
                                    resultDiv_1.appendChild(div);
                                });
                            Array.prototype.forEach.call(tree.target.getElementsByClassName('tree-search-result-list'), function (element) {
                                element.innerHTML = '';
                                element.appendChild(resultDiv_1);
                            });
                        }
                    });
                }
                //  Highlight toggle
                // o.target
                //     .on('click', '.highlight', function (e: any): void {
                //         if ($(e.currentTarget).prev().data('st') === 'open') {
                //             if ($(e.currentTarget).find('input').is(':checked')) {
                //                 o.opt.highlight.push($(e.currentTarget)
                //                     .prev().data('id'));
                //             } else {
                //                 const index = o.opt.highlight.
                //                     indexOf($(e.currentTarget).prev().data('id'));
                //                 if (index > -1) {
                //                     o.opt.highlight.splice(index, 1);
                //                 }
                //             }
                //             o.target.trigger('onHighlight', e.currentTarget);
                //         }
                //     });
            };
            /**
             * 移除圖層並觸發click trigger
             *
             * @private
             * @memberof Tree
             */
            Tree.prototype.removeActiveData = function () {
                var tree = this;
                // this.opt.highlight.forEach(function (id: number): void {
                //     const highlight = tree.target
                //         .find(`.tree-item[data-id="${id}"]`)
                //         .next();
                //     highlight.find('input').prop('checked', false);
                //     highlight.find('input').prop('disabled', true);
                //     tree.target.trigger('onHighlight', highlight.get(0));
                // });
                this.opt.activeItem.forEach(function (actIdentity) {
                    var item = tree.target.querySelector(".tree-item[data-id=\"" + actIdentity + "\"]");
                    if (item) {
                        var merge = __assign({}, tree.getSlayerDetail(actIdentity), {selected: false});
                        item.setAttribute('data-state', 'close');
                        Array.prototype.forEach.call(item.getElementsByClassName('tree-content-icon'), function (element) {
                            element.setAttribute('src', tree.opt.iconType[tree.getSlayerDetail(actIdentity).type]['close']);
                        });
                        // domItem.next().find('input').prop('disabled', true);
                        tree.layerClickObject = merge;
                        var event_3 = tree.layerClickEvent;
                        if (event_3) {
                            tree.target.dispatchEvent(event_3);
                        }
                    }
                });
                this.opt.activeItem = [];
                this.opt.highlight = [];
            };
            /**
             * 取得點擊物件
             *
             * @private
             * @returns {ToggleLayerOptionInterface | undefined}
             * @memberof Tree
             */
            Tree.prototype.getLayClickObject = function () {
                return this.layerClickObject;
            };
            /**
             * 註冊外部事件
             *
             * @private
             * @memberof Tree
             */
            Tree.prototype.subscribeEvents = function () {
                var tree = this;
                //  先解除所有事件接口
                //  綁定點擊事件接口
                if (typeof (this.opt.layerClick) === 'function') {
                    this.layerClickEvent = document.createEvent('CustomEvent');
                    this.layerClickEvent.initCustomEvent('layerclick', true, false, {});
                    this.target.addEventListener('layerclick', function (e) {
                        tree.opt.layerClick(e, tree.getLayClickObject());
                    });
                }
                // if (typeof (this.opt.onHighlight) === 'function') {
                //     this.target.on('onHighlight', this.opt.onHighlight);
                // }
            };
            /**
             *
             *
             * @private
             * @param {number} id
             * @returns {SlayerOptionsInterface}
             * @memberof Tree
             */
            Tree.prototype.getSlayerDetail = function (id) {
                var target = this.config.slayers
                    .filter(function (slayer) {
                        return slayer.id === id;
                    })[0];
                return target;
            };
            return Tree;
        }());
        exports.Tree = Tree;

    }, {}], 6: [function (require, module, exports) {
        (function (global) {
            (function () {
                global.sungyeh = global.sungyeh || {};
                global.sungyeh.fusion = require('./lib/fusion-extend-map');
                global.sungyeh.map = global.sungyeh.map || {};
                global.sungyeh.map.object = require('./lib/map-object');
                global.sungyeh.map.config = require('./lib/map-config');
                global.sungyeh.map.service = require('./lib/map-service');
                global.sungyeh.map.util = require('./lib/map-util');

            }).call(this)
        }).call(this, typeof global !== "undefined" ? global : typeof self !== "undefined" ? self : typeof window !== "undefined" ? window : {})
    }, {
        "./lib/fusion-extend-map": 1,
        "./lib/map-config": 2,
        "./lib/map-object": 3,
        "./lib/map-service": 4,
        "./lib/map-util": 5
    }]
}, {}, [6]);
