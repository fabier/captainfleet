function initMap(mapId) {
    var map = new ol.Map({
        target: mapId,
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        view: new ol.View({
            center: ol.proj.transform([0.0, 0.0], 'EPSG:4326', 'EPSG:3857'),
            zoom: 3
        })
    });
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