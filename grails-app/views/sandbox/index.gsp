<!doctype html>
<html lang="en">
<head>
    %{--<link rel="stylesheet" href=".ol3/ol.css" type="text/css">--}%
    <asset:stylesheet src="ol.css"/>
    <asset:javascript src="ol.js"/>
    <style>
    #map {
        height: 100%;
        width: 100%;
    }
    </style>
    %{--<script src="ol3/ol.js" type="text/javascript"></script>--}%
    <title>OpenLayers 3 example</title>
</head>

<body>
<div id="map"></div>
<script type="text/javascript">
    var source = new ol.source.GeoJSON({
        url: "${assetPath(src: "7day-M2.5.json")}"
    });
    var style = new ol.style.Style({
        image: new ol.style.Circle({
            radius: 7,
            fill: new ol.style.Fill({
                color: [0, 153, 255, 1]
            }),
            stroke: new ol.style.Stroke({
                color: [255, 255, 255, 0.75],
                width: 1.5
            })
        }),
        zIndex: 100000
    });
    var select = new ol.interaction.Select({style: style});
    var modify = new ol.interaction.Modify({
        features: select.getFeatures()
    });
    var map = new ol.Map({
        interactions: ol.interaction.defaults().extend([select, modify]),
        target: 'map',
        layers: [
            new ol.layer.Tile({
                title: "Global Imagery",
                source: new ol.source.TileWMS({
                    url: 'http://maps.opengeo.org/geowebcache/service/wms',
                    params: {LAYERS: 'bluemarble', VERSION: '1.1.1'}
                })
            }),
            new ol.layer.Vector({
                title: 'Earthquakes',
                source: source,
                style: new ol.style.Style({
                    image: new ol.style.Circle({
                        radius: 5,
                        fill: new ol.style.Fill({
                            color: '#0000FF'
                        }),
                        stroke: new ol.style.Stroke({
                            color: '#000000'
                        })
                    })
                })
            })
        ],
        view: new ol.View({
            projection: 'EPSG:4326',
            center: [0, 0],
            zoom: 2
        })
    });
</script>
</body>
</html>