<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Alertes</title>
    <script type="application/javascript">
        var map; // global so we can access it later
        var draw; // global so we can remove it later
        var source = new ol.source.Vector(); // source contient les source des features dessinées
        var format = new ol.format.WKT();
        var modify; // Interaction de modification

        // Polygone correspondant à l'alerte
        var alertFeature = format.readFeature('${wktGeometry}');
        <g:if test="${alert.isGeometryInverted}">
        var geometry = alertFeature.getGeometry();
        var world = [[-170, -80], [-170, 80], [170, 80], [170, -80], [-170, -80]];
        //        geometry = new ol.geom.Polygon([world, geometry]);
        //        alertFeature.setGeometry(geometry);
        </g:if>

        var format = new ol.format.WKT();
        $('#wktDiv').text(format.writeGeometry(alertFeature.getGeometry()));

        alertFeature.getGeometry().transform('EPSG:4326', 'EPSG:3857');

        alertFeature.on('change', function (event) {
            var format = new ol.format.WKT();
            var geometry = this.getGeometry().clone();
            geometry = geometry.transform('EPSG:3857', 'EPSG:4326');
            var geometryAsWKT = format.writeGeometry(geometry);
            $('#wkt').val(geometryAsWKT);
//            $('#wktDiv').text(geometryAsWKT);
        });

        function addModifyInteraction() {
            modify = new ol.interaction.Modify({
                features: new ol.Collection([alertFeature])
            });
            map.addInteraction(modify);
        }

        function removeModifyInteraction() {
            map.removeInteraction(modify);
            modify = null;
        }

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
//            var zoomLevel = Math.max(0, map.getView().getZoom());
//            map.getView().setZoom(zoomLevel);
            </g:if>
            </g:if>

            var vector = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [alertFeature]
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
            map.addInteraction(new app.Drag());
            addModifyInteraction();
        });
    </script>

    <script type="application/javascript">
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
                        if (feature === alertFeature) {
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
                        if (feature === alertFeature) {
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
                            if (feature === alertFeature) {
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
    </script>

</head>

<body>
<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-10">
            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Fiche alerte
            </legend>

            <div class="row">
                <g:form action="update" id="${alert.id}" class="form-horizontal">

                    <div class="form-group">
                        <label for="name" class="col-md-2 control-label">Nom</label>

                        <div class="col-md-4">
                            <g:field type="text" name="name" value="${alert.name}" class="form-control"/>
                        </div>

                        <div class="col-md-2">
                            <label>
                                <g:checkBox name="isGeometryInverted" value="${alert.isGeometryInverted}"/>
                                Zone inversée
                            </label>
                        </div>

                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary">
                                Enregistrer
                            </button>
                        </div>
                    </div>

                    <g:hiddenField name="wkt" value="${wktGeometry}"/>

                %{--<div id="wktDiv">--}%
                %{--${wktGeometry}--}%
                %{--</div>--}%
                </g:form>
            </div>

            <div class="row">
                <g:render template="/templates/mapFixedHeight"/>
            </div>

            %{--<g:form name="updateAlertGeometry" action="updateAlertGeometry" id="${alert.id}" class="form-horizontal">--}%
            %{--<div class="form-group">--}%
            %{--<label for="name" class="col-md-2 control-label">WKT</label>--}%
            %{----}%
            %{--<div class="col-md-10">--}%
            %{--<div id="wkt">--}%
            %{--${wktGeometry}--}%
            %{--</div>--}%
            %{--<g:textArea type="text" name="wkt" value="${wktGeometry}" class="form-control"/>--}%
            %{--</div>--}%
            %{--</div>--}%
            %{--</g:form>--}%
        </div>
    </div>
</div>
</body>
</html>
