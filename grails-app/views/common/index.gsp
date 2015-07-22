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
                    <div class="col-md-12">
                        <table class="table table-hover small nomargin-left-right">
                            <thead>
                            <th>Liste des boitiers</th>
                            </thead>
                            <tbody>
                            <g:each in="${devices}" var="device">
                                <g:set var="frame" value="${deviceFrameMap.get(device)}"/>
                                <tr class="clickable-row"
                                    data-href="${createLink(controller: "device", action: "map", id: device.id)}">
                                    <td>
                                        <div class="td-icon-ul">
                                            <div>
                                                <img src="${createLink(controller: "mapMarker", action: "index", id: (device.mapMarkerIcon ?: defaultMapMarkerIcon).id)}">
                                            </div>

                                            <div>
                                                <ul>
                                                    <li><span class="text-larger bolder">${device.name}</span></li>
                                                    <g:if test="${frame}">
                                                        <li>
                                                            <span class="text-xsmall text-muted">
                                                                Dernière transmission à
                                                                <g:formatDate format="HH'h'mm" date="${frame.time}"/>
                                                            </span>
                                                        </li>
                                                        <li class="text-xsmall text-muted">
                                                            Signal :
                                                            <g:formatRSSI rssi="${frame.rssi}"/>
                                                            Panneau solaire :
                                                            <g:formatSolarArrayVoltage
                                                                    solarArrayVoltage="${frame.frameExtra.solarArrayVoltage}"/>
                                                            Capacité :
                                                            <g:formatSuperCapacitorVoltage
                                                                    superCapacitorVoltage="${frame.frameExtra.superCapacitorVoltage}"/>
                                                        </li>
                                                    </g:if>
                                                    <g:else>
                                                        <li>
                                                            <span class="label label-warning">
                                                                Aucune transmission depuis 24h
                                                            </span>
                                                        </li>
                                                    </g:else>
                                                </ul>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
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