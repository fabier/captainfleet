var map;
var modifyInteraction;
var dragNDropInteraction;

function initMap(mapId) {
    var map = new ol.Map({
            target: mapId,
            layers: [
                new ol.layer.Group({
                    title: 'Satellite',
                    layers: [
                        new ol.layer.Tile({
                            title: 'MapQuest',
                            type: 'base',
                            source: new ol.source.MapQuest({layer: 'sat'}),
                            visible: false
                        }),
                        new ol.layer.Tile({
                            source: new ol.source.MapQuest({layer: 'hyb'}),
                            visible: false
                        })
                    ]
                }),
                new ol.layer.Group({
                    title: 'Classique',
                    layers: [
                        new ol.layer.Tile({
                            title: 'Stamen Watercolor',
                            type: 'base',
                            source: new ol.source.Stamen({
                                layer: 'watercolor',
                                maxZoom: 6
                            }),
                            visible: false
                        }),
                        new ol.layer.Tile({
                            title: 'MapQuest',
                            type: 'base',
                            source: new ol.source.MapQuest({layer: 'osm'}),
                            visible: false
                        }),
                        new ol.layer.Tile({
                                title: 'HikeBikeMap',
                                type: 'base',
                                source: new ol.source.OSM({
                                    url: "http://{a-c}.tiles.wmflabs.org/hikebike/{z}/{x}/{y}.png",
                                    crossOrigin: null,
                                    attribution: "Map Data © <a href='http://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
                                }),
                                visible: false
                            }
                        ),
                        new ol.layer.Tile({
                                title: 'Humanitarian',
                                type: 'base',
                                source: new ol.source.OSM({
                                    url: "http://{a-c}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png",
                                    crossOrigin: null,
                                    attribution: "&copy; <a href='http://www.openstreetmap.org/'>OpenStreetMap</a> and contributors, under an <a href='http://www.openstreetmap.org/copyright' title='ODbL'>open license</a>. Humanitarian style by <a href='http://hot.openstreetmap.org'>H.O.T.</a>"
                                }),
                                visible: false
                            }
                        ),
                        new ol.layer.Tile({
                            title: 'OpenStreetMap',
                            type: 'base',
                            source: new ol.source.OSM(),
                            visible: false
                        })
                    ]
                })
            ]
        }
    );
    map.addControl(new ol.control.ScaleLine());
    map.addControl(new ol.control.LayerSwitcher({
        tipLabel: 'Fonds de carte'
    }));
    return map;
}

function zoomToExtent(map, minx, miny, maxx, maxy) {
    min = ol.proj.transform([minx, miny], 'EPSG:4326', 'EPSG:3857');
    max = ol.proj.transform([maxx, maxy], 'EPSG:4326', 'EPSG:3857');
    //var extent = [min[0], min[1], max[0], max[1]];
    var geometry = new ol.geom.MultiPoint([min, max], 'XY')
    map.getView().fitGeometry(geometry, map.getSize(), {maxZoom: 16});
}

function addPoint(map, longitude, latitude, path) {
    var coordinates = ol.proj.transform([longitude, latitude], 'EPSG:4326', 'EPSG:3857');
    var point = new ol.geom.Point(coordinates);

    var iconFeature = new ol.Feature({
        geometry: point
    });

    var iconStyle = new ol.style.Style({
        image: new ol.style.Icon(({
            anchor: [0.5, 1.0],
            opacity: 1.0,
            src: path
        }))
    });

    iconFeature.setStyle(iconStyle);

    var vectorSource = new ol.source.Vector({
        features: [iconFeature]
    });

    var vectorLayer = new ol.layer.Vector({
        source: vectorSource
    });

    map.addLayer(vectorLayer);
}

function selectLayer(map, layerName) {
    var layers = map.getLayers();
    selectInLayers(layers, layerName);
}

function selectInLayers(layers, layerName) {
    layers.forEach(function (layer, index, array) {
        if (typeof layer.getLayers === 'function') {
            selectInLayers(layer.getLayers(), layerName);
        } else {
            layer.setVisible(layer.get("title") == layerName);
        }
    });
}

function addModifyInteraction() {
    modifyInteraction = new ol.interaction.Modify({
        features: new ol.Collection([alertFeature])
    });
    map.addInteraction(modifyInteraction);
}

function removeModifyInteraction() {
    map.removeInteraction(modifyInteraction);
}


/**
 * Define a namespace for the application.
 */
window.app = {};
var app = window.app;

/**
 * @constructor
 * @extends {ol.interaction.Pointer}
 */
app.Drag = function () {

    ol.interaction.Pointer.call(this, {
        handleDownEvent: app.Drag.prototype.handleDownEvent,
        handleDragEvent: app.Drag.prototype.handleDragEvent,
        handleMoveEvent: app.Drag.prototype.handleMoveEvent,
        handleUpEvent: app.Drag.prototype.handleUpEvent
    });

    /**
     * @type {ol.Pixel}
     * @private
     */
    this.coordinate_ = null;

    /**
     * @type {string|undefined}
     * @private
     */
    this.cursor_ = 'pointer';

    /**
     * @type {ol.Feature}
     * @private
     */
    this.feature_ = null;

    /**
     * @type {string|undefined}
     * @private
     */
    this.previousCursor_ = undefined;

};
ol.inherits(app.Drag, ol.interaction.Pointer);


/**
 * @param {ol.MapBrowserEvent} evt Map browser event.
 * @return {boolean} `true` to start the drag sequence.
 */
app.Drag.prototype.handleDownEvent = function (evt) {
    var map = evt.map;

    var feature = map.forEachFeatureAtPixel(evt.pixel,
        function (feature, layer) {
            if (feature === getDraggableFeature()) {
                return feature;
            } else {
                return null;
            }
        });

    if (feature) {
        this.coordinate_ = evt.coordinate;
        this.feature_ = feature;
        removeModifyInteraction();
    }

    return !!feature;
};


/**
 * @param {ol.MapBrowserEvent} evt Map browser event.
 */
app.Drag.prototype.handleDragEvent = function (evt) {
    var map = evt.map;

    var feature = map.forEachFeatureAtPixel(evt.pixel,
        function (feature, layer) {
            if (feature === getDraggableFeature()) {
                return feature;
            } else {
                return null;
            }
        });

    var deltaX = evt.coordinate[0] - this.coordinate_[0];
    var deltaY = evt.coordinate[1] - this.coordinate_[1];

    var geometry = /** @type {ol.geom.SimpleGeometry} */
        (this.feature_.getGeometry());
    geometry.translate(deltaX, deltaY);

    this.coordinate_[0] = evt.coordinate[0];
    this.coordinate_[1] = evt.coordinate[1];
};


/**
 * @param {ol.MapBrowserEvent} evt Event.
 */
app.Drag.prototype.handleMoveEvent = function (evt) {
    if (this.cursor_) {
        var map = evt.map;
        var feature = map.forEachFeatureAtPixel(evt.pixel,
            function (feature, layer) {
                if (feature === getDraggableFeature()) {
                    return feature;
                } else {
                    return null;
                }
            });
        var element = evt.map.getTargetElement();
        if (feature) {
            if (element.style.cursor != this.cursor_) {
                this.previousCursor_ = element.style.cursor;
                element.style.cursor = this.cursor_;
            }
        } else if (this.previousCursor_ !== undefined) {
            element.style.cursor = this.previousCursor_;
            this.previousCursor_ = undefined;
        }
    }
};

/**
 * @param {ol.MapBrowserEvent} evt Map browser event.
 * @return {boolean} `false` to stop the drag sequence.
 */
app.Drag.prototype.handleUpEvent = function (evt) {
    this.coordinate_ = null;
    this.feature_ = null;
    addModifyInteraction();
    return false;
};

function addDragNDropInteraction() {
    dragNDropInteraction = new app.Drag();
    map.addInteraction(dragNDropInteraction);
}

function removeDragNDropInteraction() {
    map.removeInteraction(dragNDropInteraction);
}