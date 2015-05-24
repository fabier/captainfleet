<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="map"/>
    <title>CaptainFleet - Accueil</title>
    <script type="application/javascript">
        var map; // global so we can access it later
        $(function () {
            map = initMap('map');
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
            </g:if>
            </g:if>
        });
    </script>
</head>

<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-md-4 wrapper">
            <g:if test="${devices}">
                <div class="row">
                    <div class="col-md-12">
                        <table class="table table-hover small nomargin-left-right">
                            <thead>
                            <th>#</th>
                            <th>Nom du boitier</th>
                            <th>Code</th>
                            </thead>
                            <tbody>
                            <g:each in="${devices}" var="device">
                                <tr class="clickable-row"
                                    data-href="${createLink(controller: "device", action: "map", id: device.id)}">
                                    <td>${device.sigfoxId}</td>
                                    <td>${device.name}</td>
                                    <td>${device.code}</td>
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
            <
            <g:render template="addDevice"/>
        </div>

        <g:render template="/templates/map8col"/>
    </div>
</div>
</body>
</html>