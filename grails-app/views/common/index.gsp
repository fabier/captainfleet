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
            <g:each in="${mapMarkerLayer.points}" var="point">
            addPoint(map, ${point.getDirectPosition().coordinate[0]}, ${point.getDirectPosition().coordinate[1]},
                    "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:each>
            </g:each>
            <g:if test="${mapOptions.boundingBox}">
            zoomToExtent(map, ${mapOptions.boundingBox.getMinimum(0)}, ${mapOptions.boundingBox.getMinimum(1)},
                    ${mapOptions.boundingBox.getMaximum(0)}, ${mapOptions.boundingBox.getMaximum(1)});
            </g:if>
            </g:if>
        });
    </script>
</head>

<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-sm-4 wrapper">
            <g:if test="${devices}">
                <div class="row">
                    <div class="col-sm-12">
                        <table class="table table-hover small nomargin">
                            <thead>
                            <th>#</th>
                            <th>Nom du terminal</th>
                            <th></th>
                            </thead>
                            <tbody>
                            <g:each in="${devices}" var="device">
                                <tr class="clickable-row"
                                    data-href="${createLink(controller: "device", action: "map", id: device.id)}">
                                    <td>${device.sigfoxId}</td>
                                    <td>${device.name}</td>
                                    <td>
                                        <i class="glyphicon glyphicon-log-in"></i>
                                        Accéder au détail
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
                    <div class="col-sm-12">
                        <div class="alert alert-info">
                            Vous n'avez pas encore de boitier associé à votre compte
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-12">
                        <g:link controller="account" action="devices" class="btn btn-primary center-block">
                            Cliquez ici pour associer votre boitier
                        </g:link>
                    </div>
                </div>
            </g:else>
        </div>

        <g:render template="/templates/map8col"/>
    </div>
</div>
</body>
</html>