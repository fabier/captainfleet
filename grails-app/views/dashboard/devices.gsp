<html>
<head>
    <meta name="layout" content="main">
    <title>CaptainFleet - Trame</title>
    <gvisualization:apiImport/>
</head>

<body>
<g:render template="/templates/flashMessage"/>

<div class="container-fluid">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralDashboard"/>
        </div>

        <div class="col-md-10">
            <g:each in="${devices.collate(1)}" var="deviceGroup">
                <div class="row">
                    <g:each in="${deviceGroup}" var="device">
                        <g:set var="frames" value="${framesMap.get(device)}"/>
                        <div class="col-md-12">
                            <div class="panel panel-info dashboard-device">
                                <div class="panel-heading">
                                    <h4 class="nomargin">${device.name}</h4>
                                </div>

                                <div class="panel-body">
                                    <g:if test="${frames != null && !frames.isEmpty()}">
                                        <div class="row">
                                            <div class="col-md-6">
                                                <gvisualization:lineChart
                                                        elementId="device_chart_${device.id}"
                                                        height="${240}"
                                                        columns="${deviceGraphDataColumns}"
                                                        data="${deviceGraphDataMap.get(device)}"/>
                                                <div id="device_chart_${device.id}"></div>
                                            </div>

                                            <div class="col-md-6">
                                                <gvisualization:lineChart
                                                        elementId="device_temperature_${device.id}"
                                                        height="${240}"
                                                        columns="${deviceGraphTemperatureDataColumns}"
                                                        data="${deviceGraphTemperatureDataMap.get(device)}"/>
                                                <div id="device_temperature_${device.id}"></div>
                                            </div>
                                        </div>
                                    </g:if>
                                    <g:else>
                                        <div class="">
                                            <p>
                                                <span class="label label-warning">
                                                    <i class="glyphicon glyphicon-warning-sign"></i>&nbsp;
                                                Pas de données suffisamment récentes pour ce boitier.
                                                </span>
                                            </p>

                                            <p>
                                                Dernière transmission : <g:formatDate format="dd MMMM HH'h'mm"
                                                                                      date="${lastFrameMap.get(device)?.time}"/>
                                            </p>
                                        </div>
                                    </g:else>
                                </div>
                            </div>
                        </div>
                    </g:each>
                </div>
            </g:each>
        </div>
    </div>
</div>
</body>
</html>



%{--<gvisualization:lineCoreChart hAxis="${new Expando([title: 'x'])}"--}%
%{--vAxis="${new Expando([title: 'y', viewWindowMode: 'explicit', viewWindow: new Expando([min: -5, max: 5])])}"--}%
%{--curveType="function"--}%
%{--elementId="linechart"--}%
%{--width="${400}"--}%
%{--height="${240}"--}%
%{--title="Function Graph"--}%
%{--columns="${deviceGraphDataColumnsMap.get(device)}"--}%
%{--data="${deviceGraphDataMap.get(device)}"/>--}%