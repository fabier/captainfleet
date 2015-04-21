<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="map"/>
    <title>Carte</title>
    <script type="application/javascript">
        $(function () {
            var dateInput = $('#date');
            dateInput.datepicker({
                format: "yyyy/mm/dd",
                endDate: "-",
                weekStart: 1,
                todayBtn: "linked",
                forceParse: false,
                todayHighlight: true,
                language: "fr"
            });
            dateInput.on("changeDate", function (event) {
                location.href =
                        "${createLink(controller: "device", action: "map", id: device.id, params: [date:"_DATE_"])}"
                                .replace("_DATE_", $("#date").datepicker('getFormattedDate'));
            });

            var map = initMap('map');
            <g:if test="${mapOptions}">
            var markers = [];
            <g:each in="${dataDecodedList}" var="dataDecoded" status="i">
            markers[${i}] = ol.proj.transform([${dataDecoded.longitude}, ${dataDecoded.latitude}], 'EPSG:4326', 'EPSG:3857');
            </g:each>
            var lineFeature = new ol.Feature({
                geometry: new ol.geom.LineString(markers, 'XY'),
                name: 'Line'
            })
            lineFeature.setStyle(
                    new ol.style.Style({
                        stroke: new ol.style.Stroke({
                            color: [79, 147, 190, 1],
                            width: 4
                        })
                    })
            );
            var layerLines = new ol.layer.Vector({
                source: new ol.source.Vector({
                    features: [lineFeature]
                })
            });

            map.addLayer(layerLines);

            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:if test="${!dataDecodedList.isEmpty()}">
            addPoint(map, ${dataDecodedList.first().longitude}, ${dataDecodedList.first().latitude}, "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            addPoint(map, ${dataDecodedList.last().longitude}, ${dataDecodedList.last().latitude}, "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:if>
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
                        <li class="active">Device ${device.sigfoxId}</li>
                    </g:render>

                    <g:form action="map" id="${device.id}" class="form-horizontal">
                        <div class="form-group">
                            <label for="date" class="col-sm-2 control-label">Date</label>

                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="date" name="date"
                                       value="${date ? formatDate(date: date, format: "yyyy/MM/dd") : ""}"/>
                            </div>

                            <div class="col-sm-2">
                                <button type="submit" class="btn btn-primary">
                                    OK
                                </button>
                            </div>
                        </div>
                    </g:form>

                    <g:if test="${frames.isEmpty()}">
                        <p class="alert alert-warning">
                            Pas de donnée pour cette journée.
                        </p>
                    </g:if>
                    <g:else>
                        <table class="table table-hover small nomargin">
                            <thead>
                            <th>#</th>
                            <th>Date</th>
                            <th>Station</th>
                            <th>Signal</th>
                            <th>RSSI</th>
                            </thead>
                            <tbody>
                            <g:each in="${frames}" var="frame">
                                <tr class="clickable-row"
                                    data-href="${createLink(controller: "frame", action: "map", id: frame.id)}">
                                    <td>
                                        ${frame.id}
                                    </td>
                                    <td>
                                        <g:formatDate format="yyyy-MM-dd HH:mm" date="${frame.dateCreated}"/>
                                    </td>
                                    <td>
                                        ${frame.station.sigfoxId}
                                    </td>
                                    <td>
                                        ${frame.signal} dB
                                    </td>
                                    <td>
                                        ${frame.rssi} dBm
                                    </td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </g:else>
                </div>
            </div>
        </div>
        <g:render template="/templates/map8col"/>
    </div>

</div>
</body>
</html>