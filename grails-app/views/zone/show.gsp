<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Zones</title>
    <script type="application/javascript">
        var source = new ol.source.Vector(); // source contient les source des features dessinées
        var format = new ol.format.WKT();

        // Polygone correspondant à la zone
        var zoneFeature = format.readFeature('${wktGeometry}');

        var format = new ol.format.WKT();
        $('#wktDiv').text(format.writeGeometry(zoneFeature.getGeometry()));

        zoneFeature.getGeometry().transform('EPSG:4326', 'EPSG:3857');

        zoneFeature.on('change', function (event) {
            var format = new ol.format.WKT();
            var geometry = this.getGeometry().clone();
            geometry = geometry.transform('EPSG:3857', 'EPSG:4326');
            var geometryAsWKT = format.writeGeometry(geometry);
            $('#wkt').val(geometryAsWKT);
//            $('#wktDiv').text(geometryAsWKT);
        });

        $(function () {
            map = initMap('map');
            selectLayer(map, "OpenStreetMap");
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:each in="${mapMarkerLayer.points}" var="point">
            addPoint(map, ${point.getCoordinate().getOrdinate(0)}, ${point.getCoordinate().getOrdinate(1)},
                    "${mapMarkerLayer.mapMarkerStyle.path}");
            </g:each>
            </g:each>
            <g:if test="${mapOptions.boundingBox}">
            zoomToExtent(map, ${mapOptions.boundingBox.getMinX()}, ${mapOptions.boundingBox.getMinY()},
                    ${mapOptions.boundingBox.getMaxX()}, ${mapOptions.boundingBox.getMaxY()});
            </g:if>
            </g:if>

            var vector = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [zoneFeature]
                }),
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
            addDragNDropInteraction();
            addModifyInteraction();
        });

        function getDraggableFeature() {
            return zoneFeature;
        }
    </script>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-8">
            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Fiche zone
            </legend>

            <div class="row">
                <g:form action="update" id="${zone?.id ?: 0}" class="form-horizontal">

                    <div class="form-group">
                        <label for="name" class="col-md-2 control-label">Nom</label>

                        <div class="col-md-4">
                            <g:field type="text" name="name" value="${zone?.name}" class="form-control"/>
                        </div>

                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary">
                                Enregistrer
                            </button>
                        </div>
                    </div>

                    <g:hiddenField name="wkt" value="${wktGeometry}"/>
                </g:form>
            </div>

            <div class="row">
                <g:render template="/templates/mapFixedHeight"/>
            </div>
        </div>

        <div class="col-md-2">
            <g:render template="actions"/>
        </div>
    </div>
</div>
</body>
</html>
