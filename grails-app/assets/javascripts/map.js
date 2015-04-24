//new ol.layer.Tile({
//        title: 'Stamen Toner',
//        type: 'base',
//        source: new ol.source.Stamen({
//            layer: 'toner'
//        })
//    }
//),
//var mapQuestGroup =;

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
                            source: new ol.source.MapQuest({layer: 'sat'})
                        }),
                        new ol.layer.Tile({
                            source: new ol.source.MapQuest({layer: 'hyb'})
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
                            })
                        }),
                        new ol.layer.Tile({
                            title: 'MapQuest',
                            type: 'base',
                            source: new ol.source.MapQuest({layer: 'osm'})
                        }),
                        new ol.layer.Tile({
                                title: 'HikeBikeMap',
                                type: 'base',
                                source: new ol.source.OSM({
                                    url: "http://{a-c}.tiles.wmflabs.org/hikebike/{z}/{x}/{y}.png",
                                    crossOrigin: null,
                                    attribution: "Map Data © <a href='http://www.openstreetmap.org/copyright'>OpenStreetMap</a> contributors"
                                })
                            }
                        ),
                        new ol.layer.Tile({
                                title: 'Humanitarian',
                                type: 'base',
                                source: new ol.source.OSM({
                                    url: "http://{a-c}.tile.openstreetmap.fr/hot/{z}/{x}/{y}.png",
                                    crossOrigin: null,
                                    attribution: "&copy; <a href='http://www.openstreetmap.org/'>OpenStreetMap</a> and contributors, under an <a href='http://www.openstreetmap.org/copyright' title='ODbL'>open license</a>. Humanitarian style by <a href='http://hot.openstreetmap.org'>H.O.T.</a>"
                                })
                            }
                        ),
                        new ol.layer.Tile({
                            title: 'OpenStreetMap',
                            type: 'base',
                            source: new ol.source.OSM()
                        })
                    ]
                }),
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