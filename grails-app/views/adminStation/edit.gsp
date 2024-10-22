<html>

<head>
    <meta name='layout' content='admin'/>
    <title>CaptainFleet - Station</title>
    <script type="application/javascript">
        $(function () {
            map = initMap('map');
            selectLayer(map, "OpenStreetMap");
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:each in="${mapMarkerLayer?.points}" var="point">
            addPoint(map, ${point.getX()}, ${point.getY()},
                    "${mapMarkerLayer.mapMarkerStyle.path}");
            </g:each>
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

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <div class="row">
                <div class="alert alert-danger">
                    Attention, éditer une station est dangereux. Il est déconseillé de modifier une station.
                </div>

                <div class="col-md-6">
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
                            <div class="col-md-8 col-md-offset-4">
                                <button type="submit" class="btn btn-primary">
                                    Enregistrer
                                </button>
                            </div>
                        </div>
                    </g:form>

                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <th>#</th>
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
                                    <g:formatDate format="dd MMMM HH'h'mm" date="${frame.dateCreated}"/>
                                </td>
                                <td>
                                    <g:formatNumber number="${frame.snr}" type="number" locale="EN"
                                                    minFractionDigits="2"
                                                    maxFractionDigits="2"/> dB
                                </td>
                                <td>
                                    <g:formatNumber number="${frame.avgSignal}" type="number" locale="EN"
                                                    minFractionDigits="2" maxFractionDigits="2"/> dB
                                </td>
                                <td>
                                    <g:formatNumber number="${frame.rssi}" type="number" locale="EN"
                                                    minFractionDigits="2"
                                                    maxFractionDigits="2"/> dBm
                                </td>
                            </tr>
                        </g:each>

                        </tbody>
                    </table>

                    %{--<div class="row center-block">--}%
                    %{--<div class="col-md-6">--}%
                    <nav class="text-center">
                        <g:paginate next="&gt;" prev="&lt;" maxsteps="5" controller="adminStation" id="${station.id}"
                                    action="edit" total="${totalCount}"/>
                    </nav>
                    %{--</div>--}%
                    %{--</div>--}%
                </div>

                <div class="col-md-6">
                    <div id="map" class="map-500"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
