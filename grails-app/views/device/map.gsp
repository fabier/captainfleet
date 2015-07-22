<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="map"/>
    <title>CaptainFleet - Boitier</title>
    <asset:javascript src="deviceMap.js"/>
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
        });
    </script>
    <script type="application/javascript">
        $(function () {
            map = initMap('map');
            selectLayer(map, "OpenStreetMap");
            <g:if test="${mapOptions}">
            var markers = [];
            <g:each in="${geoFrames}" var="geoFrame" status="i">
            markers[${i}] = ol.proj.transform([${geoFrame.location?.getX()}, ${geoFrame.location?.getY()}],
                    'EPSG:4326', 'EPSG:3857');
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
            <g:if test="${!geoFrames.isEmpty()}">
            addPoint(map, ${geoFrames.first().location?.getX()}, ${geoFrames.first().location?.getY()},
                    "${mapMarkerLayer.mapMarkerStyle.path}");
            <g:if test="${geoFrames.size()>1}" >
            addPoint(map, ${geoFrames.last().location?.getX()}, ${geoFrames.last().location?.getY()},
                    "${mapMarkerLayer.mapMarkerStyle.path}");
            </g:if>
            </g:if>
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

<div class="container-fluid with-map">
    <div class="row-fluid">
        <div class="col-md-4 wrapper">
            <div class="row margin-bottom-20">
                <div class="col-md-4">
                    <g:link uri="/">
                        < Retour
                    </g:link>
                </div>
            </div>

            <div class="row margin-bottom-20">
                <g:render template="/device/deviceHeader"
                          model="[device: device, frame: frame, defaultMapMarkerIcon: defaultMapMarkerIcon]"/>
            </div>

            <hr/>

            <div class="row">
                <div class="col-md-3">
                    <g:link action="map" id="${device.id}"
                            params="[date: raw(formatDate(date: previousDay, format: 'yyyy/MM/dd'))]"
                            class="btn btn-primary small">&vltri;</g:link>
                </div>

                <div class="col-md-6">

                    <g:form action="map" id="${device.id}" class="form-horizontal">
                        <div class="form-group">
                            <label for="date" class="col-md-2 control-label">Date</label>

                            <div class="col-md-10">
                                <input type="text" class="form-control" id="date" name="date"
                                       value="${date ? formatDate(date: date, format: "yyyy/MM/dd") : ""}"/>
                            </div>
                        </div>
                    </g:form>
                </div>

                <div class="col-md-3">
                    <g:link action="map" id="${device.id}"
                            params="[date: raw(formatDate(date: nextDay, format: 'yyyy/MM/dd'))]"
                            class="btn btn-primary small pull-right">&vrtri;</g:link>
                </div>
            </div>

            <g:if test="${frames.isEmpty()}">
                <p class="alert alert-warning">
                    Pas de donnée pour cette journée.
                </p>
            </g:if>
            <g:else>
                <table class="table table-hover small nomargin-left-right">
                    <thead>
                    <th>#</th>
                    <th>Date</th>
                    <th>Station</th>
                    <th>Type</th>
                    </thead>
                    <tbody>
                    <g:each in="${frames}" var="frame">
                        <tr class="clickable-row"
                            data-href="${createLink(controller: "frame", action: "map", id: frame.id)}">
                            <td>
                                ${frame.id}
                            </td>
                            <td>
                                <g:formatDate format="dd MMMM HH'h'mm" date="${frame.dateCreated}"/>
                            </td>
                            <td>
                                ${frame.station.sigfoxId}
                            </td>
                            <td>
                                ${frame.frameType}
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </g:else>
        </div>
        <g:render template="/templates/map8col"/>
    </div>

</div>
</body>
</html>