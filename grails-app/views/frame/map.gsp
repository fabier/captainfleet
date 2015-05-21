<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="map"/>
    <title>CaptainFleet - Trame</title>
    <script type="application/javascript">
        $(function () {
            var map = initMap('map');
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:if test="${frame?.location}" >
            addPoint(map, ${frame.location?.getX()}, ${frame.location?.getY()},
                    "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:if>
            </g:each>
            zoomToExtent(map, ${mapOptions.boundingBox.getMinX()}, ${mapOptions.boundingBox.getMinY()},
                    ${mapOptions.boundingBox.getMaxX()}, ${mapOptions.boundingBox.getMaxY()});
            </g:if>
        });
    </script>
</head>

<body>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-4 wrapper">
            <div class="row">
                <div class="col-md-12">
                    <g:render template="/templates/breadcrumb">
                        <g:render template="/templates/breadcrumb/home"/>
                        <li><g:link controller="device" action="map"
                                    id="${device.id}">Device ${device.sigfoxId}</g:link></li>
                        <li class="active">Frame ${frame.id}</li>
                    </g:render>

                    <div class="row">
                        <div class="col-md-3">
                            <g:if test="${previousFrame}">
                                <g:link action="map" id="${previousFrame.id}"
                                        class="btn btn-primary small">&vltri;</g:link>
                            </g:if>
                        </div>

                        <div class="col-md-6"></div>

                        <div class="col-md-3">
                            <g:if test="${nextFrame}">
                                <g:link action="map" id="${nextFrame.id}"
                                        class="btn btn-primary small pull-right">&vrtri;</g:link>
                            </g:if>
                        </div>
                    </div>

                    <g:render template="/frame/tabs" model="[frames: frames, frame: frame, frameData: frameData]"/>
                </div>
            </div>
        </div>
        <g:render template="/templates/map8col"/>
    </div>
</div>
</body>
</html>