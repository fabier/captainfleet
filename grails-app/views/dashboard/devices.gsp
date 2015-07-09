<html>
<head>
    <meta name="layout" content="main">
    <title>CaptainFleet - Trame</title>
    <script type="text/javascript"
            src="https://www.google.com/jsapi?autoload={
            'modules':[{
              'name':'visualization',
              'version':'1',
              'packages':['corechart', 'line']
            }]
          }"></script>
    <script type="text/javascript">
        google.setOnLoadCallback(drawChart);

        function drawChart() {
            var options = {
                'height': 140,
                curveType: 'function'
            };

            <g:each in="${devices}" var="device">
            <g:set var="frames" value="${framesMap.get(device)}"/>
            <g:if test="${frames != null && !frames.isEmpty()}">
            var dataSuperCap = new google.visualization.DataTable();
            dataSuperCap.addColumn('date', 'Date');
            dataSuperCap.addColumn('number', 'Tension Super capacité (V)');
            dataSuperCap.addColumn('number', 'Tension Panneau solaire (V)');
            <g:each in="${framesMap.get(device)}" var="frame">
            dataSuperCap.addRow([new Date(${formatDate([date:frame.dateCreated, format:"yyyy, MM, dd, HH, mm, ss, SSS"])}), ${frame.frameExtra?.superCapacitorVoltage?formatNumber([number:frame.frameExtra?.superCapacitorVoltage, type: "number", locale: "EN",minFractionDigits: "2", maxFractionDigits: "2"]):"null"}, ${frame.frameExtra?.solarArrayVoltage?formatNumber([number:frame.frameExtra?.solarArrayVoltage, type: "number", locale: "EN", minFractionDigits: "2",maxFractionDigits: "2"]):"null"}]);
            </g:each>
            var chart = new google.charts.Line(document.getElementById('linechart_material_${device.id}'));
            chart.draw(dataSuperCap, options);
            </g:if>
            </g:each>
        }
    </script>
</head>

<body>
<g:render template="/templates/flashMessage"/>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralDashboard"/>
        </div>

        <div class="col-md-10">
            <g:each in="${devices.collate(2)}" var="deviceGroup">
                <div class="row">
                    <g:each in="${deviceGroup}" var="device">
                        <g:set var="frames" value="${framesMap.get(device)}"/>
                        <div class="col-md-6">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <h4 class="nomargin">[${device.id}] ${device.name}</h4>
                                </div>

                                <div class="panel-body">
                                    <p>
                                        Dernière transmission : <g:formatDate format="dd MMMM HH'h'mm" date="${lastFrameMap.get(device)?.time}"/>
                                    </p>
                                    <g:if test="${frames != null && !frames.isEmpty()}">
                                        <div id="linechart_material_${device.id}"></div>
                                    </g:if>
                                    <g:else>
                                        <p>
                                            <i class="glyphicon glyphicon-warning-sign"></i>&nbsp;
                                        Pas de données pour ce boitier
                                        </p>
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