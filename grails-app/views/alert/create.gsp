<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Alertes</title>
    <script type="application/javascript">
        var map; // global so we can access it later
        $(function () {
            map = initMap('map');
            selectLayer(map, "OpenStreetMap");
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:each in="${mapMarkerLayer.points}" var="point">
            addPoint(map, ${point.getCoordinate().getOrdinate(0)}, ${point.getCoordinate().getOrdinate(1)},
                    "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:each>
            </g:each>
            <g:if test="${mapOptions.boundingBox}">
            zoomToExtent(map, ${mapOptions.boundingBox.getMinX()}, ${mapOptions.boundingBox.getMinY()},
                    ${mapOptions.boundingBox.getMaxX()}, ${mapOptions.boundingBox.getMaxY()});
            var zoomLevel = Math.max(0, map.getView().getZoom() - 2);
            map.getView().setZoom(zoomLevel);
            </g:if>
            </g:if>
        });
    </script>
    <script type="application/javascript">
        var draw; // global so we can remove it later
        var source = new ol.source.Vector(); // source contient les source des features dessinées

        function addInteraction() {
            var value = 'Polygon';
            if (value !== 'None') {
                draw = new ol.interaction.Draw({
                    source: source,
                    type: 'Polygon'
                });
                map.addInteraction(draw);
                draw.on('drawend', function (evt) {
                    var format = new ol.format.WKT();
                    var features = source.getFeatures();
                    for (var i in features) {
                        var feature = features[i];
                        var geometry = feature.getGeometry().clone();
                        geometry = geometry.transform('EPSG:3857', 'EPSG:4326');
                        var geometryAsWKT = format.writeGeometry(geometry);
                        $('#wkt').val(geometryAsWKT);
                        $('#createAlertUsingGeometryForm').submit();
                    }
                });
            }
        }

        $(function () {
            var vector = new ol.layer.Vector({
                source: source,
                style: new ol.style.Style({
                    fill: new ol.style.Fill({
                        color: 'rgba(255, 255, 255, 0.5)'
                    }),
                    stroke: new ol.style.Stroke({
                        color: '#0099ff',
                        width: 2
                    }),
                    image: new ol.style.Circle({
                        radius: 7,
                        fill: new ol.style.Fill({
                            color: '#ffcc33'
                        })
                    })
                })
            });

            map.addLayer(vector);

            addInteraction();
        });
    </script>
</head>

<body>
<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-10">
            <g:render template="/templates/mapFixedHeight"/>

            <g:form name="createAlertUsingGeometryForm" action="createAlertUsingGeometry">
                <g:hiddenField name="wkt"/>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
