<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="map"/>
    <title>Carte</title>
    <script type="application/javascript">
        $(function () {
            var map = initMap('map');
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            addPoint(map, ${dataDecoded.longitude}, ${dataDecoded.latitude}, "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:each>
            zoomToExtent(map, ${mapOptions.boundingBox.getMinimum(0)}, ${mapOptions.boundingBox.getMinimum(1)},
                    ${mapOptions.boundingBox.getMaximum(0)}, ${mapOptions.boundingBox.getMaximum(1)});
            </g:if>
        });
    </script>
</head>

<body>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-sm-4 wrapper">
            <div class="row">
                <div class="col-sm-12">
                    <g:render template="/templates/breadcrumb">
                        <g:render template="/templates/breadcrumb/home"/>
                        <li><g:link controller="device" action="map"
                                    id="${device.id}">Device ${device.sigfoxId}</g:link></li>
                        <li class="active">Frame ${frame.id}</li>
                    </g:render>

                    <g:render template="/frame/tabs" model="[frames: frames, dataDecoded: dataDecoded]"/>
                </div>
            </div>
        </div>
        <g:render template="/templates/map8col"/>
    </div>
</div>
</body>
</html>