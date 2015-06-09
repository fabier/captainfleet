<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Alertes</title>
    <script type="application/javascript">
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
            var zoomLevel = Math.max(4, map.getView().getZoom() - 2);
            map.getView().setZoom(zoomLevel);
            </g:if>
            </g:if>
        });
    </script>
    <script type="application/javascript">
        var draw; // global so we can remove it later
        var source = new ol.source.Vector(); // source contient les source des features dessinées
        var alertFeature; // Globale, dès qu'on a créé l'alerte

        function addDrawInteraction() {
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
                    if (features.length >= 1) {
                        alertFeature = features[features.length - 1];
                        var geometry = alertFeature.getGeometry().clone();
                        geometry = geometry.transform('EPSG:3857', 'EPSG:4326');
                        var geometryAsWKT = format.writeGeometry(geometry);
                        $('#wkt').val(geometryAsWKT);
                        alertFeature.on('change', function (event) {
                            var format = new ol.format.WKT();
                            var geometry = this.getGeometry().clone();
                            geometry = geometry.transform('EPSG:3857', 'EPSG:4326');
                            var geometryAsWKT = format.writeGeometry(geometry);
                            $('#wkt').val(geometryAsWKT);
                        });
                        map.removeInteraction(draw);
                        addDragNDropInteraction();
                        addModifyInteraction();
                    }
                });
            }
        }

        function getDraggableFeature() {
            return alertFeature;
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

            addDrawInteraction();
        });
    </script>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-10">
            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Nouvelle alerte
            </legend>

            <div class="row">
                <g:form action="createAlertUsingGeometry" class="form-horizontal">

                    <div class="form-group">
                        <label for="name" class="col-md-2 control-label">Nom</label>

                        <div class="col-md-4">
                            <g:field type="text" name="name" value="${alert?.name}" class="form-control"/>
                        </div>

                        <div class="col-md-2">
                            <label>
                                <g:checkBox name="isGeometryInverted" value="${alert?.isGeometryInverted}"/>
                                Zone inversée
                            </label>
                        </div>

                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary">
                                Enregistrer
                            </button>
                        </div>
                    </div>

                    <g:hiddenField name="wkt"/>
                </g:form>
            </div>

            <div class="row">
                <g:render template="/templates/mapFixedHeight"/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
