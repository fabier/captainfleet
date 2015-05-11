<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="map"/>
    <title>Carte</title>
    <script type="application/javascript">
        $(function () {
            var map = initMap('map');
            zoomToExtent(map, 0.0, -60.0, 0.0, 60.0);
        });
    </script>
</head>

<body>
<div class="container-fluid">
    <div class="col-md-4 wrapper-lateral">
        <div class="row">
            <table class="table table-hover small">
                <thead>
                <th>Identifiant</th>
                <th>Nom du device</th>
                <th></th>
                </thead>
                <tbody>
                <g:each in="${devices}" var="device">
                    <tr>
                        <td>${device.sigfoxId}</td>
                        <td>${device.name}</td>
                        <td>
                            <g:link controller="device" action="map" id="${device.id}">
                                Accéder au détail
                            </g:link>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>

    <g:render template="/templates/map8col"/>
</div>
</body>
</html>