<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="map"/>
    <title>CaptainFleet - Trame</title>
    <script type="application/javascript">
        $(function () {
            map = initMap('map');
            selectLayer(map, "OpenStreetMap");
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:if test="${frame?.location}" >
            addPoint(map, ${frame.location?.getX()}, ${frame.location?.getY()},
                    "${mapMarkerLayer.mapMarkerStyle.path}");
            </g:if>
            </g:each>
            zoomToExtent(map, ${mapOptions.boundingBox.getMinX()}, ${mapOptions.boundingBox.getMinY()},
                    ${mapOptions.boundingBox.getMaxX()}, ${mapOptions.boundingBox.getMaxY()});
            var zoomLevel = Math.max(4, map.getView().getZoom() - 2);
            map.getView().setZoom(zoomLevel);
            </g:if>
        });
    </script>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid with-map">
    <div class="row-fluid">
        <div class="col-md-4 wrapper">
            <div class="row">
                <div class="col-md-12">
                    <div class="row margin-bottom-20">
                        <div class="col-md-4">
                            <g:link controller="device" action="map" id="${device.id}">
                                < Retour
                            </g:link>
                        </div>
                    </div>

                    <div class="row margin-bottom-20">
                        <g:render template="/device/deviceHeader"
                                  model="[device: device, frame: frame, defaultMapMarkerIcon: defaultMapMarkerIcon]"/>
                    </div>

                    <hr/>

                    <div class="row">
                        <div class="col-md-3">
                            <g:if test="${previousFrame}">
                                <g:link action="map" id="${previousFrame.id}"
                                        class="btn btn-primary small">&vltri;</g:link>
                            </g:if>
                        </div>

                        <div class="col-md-6 text-center">
                            <g:formatDate date="${frame.time}" format="dd MMMM HH'h'mm"/>
                            <br/>
                            <span class="text-xsmall text-muted">
                                ${frame.frameType}
                            </span>
                        </div>

                        <div class="col-md-3">
                            <g:if test="${nextFrame}">
                                <g:link action="map" id="${nextFrame.id}"
                                        class="btn btn-primary small pull-right">&vrtri;</g:link>
                            </g:if>
                        </div>
                    </div>

                    <g:render template="/frame/tabs" model="[frames: frames, frame: frame]"/>
                </div>
            </div>
        </div>
        <g:render template="/templates/map8col"/>
    </div>
</div>
</body>
</html>