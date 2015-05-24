<html>

<head>
    <meta name='layout' content='admin'/>
    <title>CaptainFleet - Station</title>
    <script type="application/javascript">
        var map; // global so we can access it later
        $(function () {
            map = initMap('map');
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:each in="${mapMarkerLayer?.points}" var="point">
            addPoint(map, ${point.getX()}, ${point.getY()},
                    "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:each>
            </g:each>
            zoomToExtent(map, ${mapOptions.boundingBox.getMinX()}, ${mapOptions.boundingBox.getMinY()},
                    ${mapOptions.boundingBox.getMaxX()}, ${mapOptions.boundingBox.getMaxY()});
            </g:if>
        });
    </script>
</head>

<body>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <div class="row">
                <g:render template="/templates/flashMessage"/>

                <div class="col-md-6">

                    <div class="alert alert-danger">
                        Attention, éditer une station est dangereux.<br/>
                        Il est déconseillé de modifier une station.
                    </div>

                    <g:form action='update' class="form-horizontal" id="${station.id}">
                        <g:hiddenField name="id" value="${station?.id}"/>
                        <g:hiddenField name="version" value="${station?.version}"/>

                        <div class="form-group">
                            <label for="name" class="col-md-4 control-label">Nom</label>

                            <div class="col-md-8">
                                <g:field type="text" name="name" value="${station.name}" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="sigfoxId" class="col-md-4 control-label">SigFoxId</label>

                            <div class="col-md-8">
                                <g:field type="text" name="sigfoxId" value="${station.sigfoxId}"
                                         class="form-control" disabled="disabled"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-10 col-md-offset-2">
                                <button type="submit" class="btn btn-primary">
                                    Enregistrer
                                </button>
                            </div>
                        </div>
                    </g:form>
                </div>

                <div class="col-md-6">
                    <div id="map" class="map-station"></div>
                </div>
            </div>

            <div class="row">
                <table class="table table-hover small nomargin">
                    <thead>
                    <th>#</th>
                    <th>Data</th>
                    <th>Date</th>
                    <th>Signal</th>
                    <th>Avg Signal</th>
                    <th>RSSI</th>
                    </thead>
                    <tbody>
                    <g:each in="${results}" var="frame">
                        <tr>
                            <td>${frame.id}</td>
                            <td>
                                ${frame.data}
                            </td>
                            <td>
                                <g:formatDate format="yyyy-MM-dd HH:mm" date="${frame.dateCreated}"/>
                            </td>
                            <td>
                                <g:formatNumber number="${frame.signal}" type="number" locale="EN" minFractionDigits="2"
                                                maxFractionDigits="2"/> dB
                            </td>
                            <td>
                                <g:formatNumber number="${frame.avgSignal}" type="number" locale="EN"
                                                minFractionDigits="2" maxFractionDigits="2"/> dB
                            </td>
                            <td>
                                <g:formatNumber number="${frame.rssi}" type="number" locale="EN" minFractionDigits="2"
                                                maxFractionDigits="2"/> dBm
                            </td>
                        </tr>
                    </g:each>

                    </tbody>
                </table>
            </div>

            <div class="row">
                <div class="col-md-6 center-block">
                    <nav class="text-center">
                        <g:paginate next="&gt;" prev="&lt;" maxsteps="5" controller="adminStation" id="${station.id}"
                                    action="edit" total="${totalCount}"/>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
