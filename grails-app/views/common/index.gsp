<%@ page contentType="text/html;charset=UTF-8" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name="layout" content="map"/>
    <title>CaptainFleet - Accueil</title>
    <script type="application/javascript">
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
            var zoomLevel = Math.max(4, map.getView().getZoom() - 2);
            map.getView().setZoom(zoomLevel);
            </g:if>
            </g:if>
        });
    </script>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid with-map">
    <div class="row-fluid">
    <div class="col-md-4 wrapper">
        <g:if test="${devices}">
            <div class="row">
        %{--<div class="col-md-12">--}%
            <table class="table table-hover small nomargin-left-right">
                <thead>
                <th class="col-md-8">Liste des boitiers</th>
                <th class="col-md-4"></th>
                </thead>
                <tbody>
            <g:each in="${devices}" var="device">
                <g:set var="frame" value="${deviceFrameMap.get(device)}"/>
                <g:set var="lastFrame" value="${deviceLastFrameMap.get(device)}"/>
                <tr class="clickable-row"
                    data-href="${raw(createLink(controller: "device", action: "map", id: device.id))}">
                    <td>
                        <div style="float: left;">
                            <img src="${raw(createLink(controller: "mapMarker", action: "index", id: (device.mapMarkerIcon ?: defaultMapMarkerIcon).id))}">
                        </div>
                        <ul class="nodecoration">
                            <li class="display-block">
                                <span class="text-larger bolder">
                                    ${device.name}
                                </span>

                                <g:if test="${frame == null}">
                                    <span class="label label-danger">
                                        Aucune position connue depuis 24h
                                    </span>
                                </g:if>
                            </li>
                            <li>
                                <span class="text-xsmall text-muted">
                                    Dernière transmission
                                    <g:formatDate format="d MMM à HH'h'mm" date="${lastFrame.time}"/>
                                </span>
                            </li>
                        </ul>
                    </td>
                    <td style="text-align: right;">
                        %{--<div class="text-small text-muted text-muted">--}%
                        %{--&nbsp;--}%
                        %{--<span class="label label-default">--}%
                        %{--${device.sigfoxId}--}%
                        %{--</span>--}%
                        %{--</div>--}%

                        <div class="text-xsmall text-muted text-muted">
                            Signal :
                            <g:formatRSSI rssi="${lastFrame.rssi}"/>
                            <br/>
                            Panneau solaire :
                            <g:formatSolarArrayVoltage
                                    solarArrayVoltage="${lastFrame.frameExtra.solarArrayVoltage}"/>
                            <br/>
                            Batterie :
                            <g:formatSuperCapacitorVoltage
                                    superCapacitorVoltage="${lastFrame.frameExtra.superCapacitorVoltage}"/>
                            <br/>
                        </div>
                    </td>
                </div>
            </td>
        </tr>
            </g:each>
            </tbody>
        </table>
        %{--</div>--}%
            </div>
        </g:if>
        <g:else>
            <div class="row">
                <div class="col-md-12">
                    <div class="alert alert-info">
                        Vous n'avez pas encore de boitier associé à votre compte
                    </div>
                </div>
            </div>
        </g:else>

        <g:render template="addDevice"/>
    </div>

    <g:render template="/templates/map8col"/>
</div>
</div>
</body>
</html>