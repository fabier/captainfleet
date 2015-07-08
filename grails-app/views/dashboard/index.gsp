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
            drawChartSuperCap();
        }

        function drawChartSuperCap() {
            var dataSuperCap = new google.visualization.DataTable();
            dataSuperCap.addColumn('date', 'Date');
            dataSuperCap.addColumn('number', 'Capacité (V)');
            dataSuperCap.addColumn('number', 'Panneau solaire (V)');

            <g:each in="${frames}" var="frame">
            dataSuperCap.addRow([
                new Date(${formatDate([date:frame.dateCreated, format:"yyyy, MM, dd, HH, mm, ss, SSS"])}),
                ${frame.frameExtra?.superCapacitorVoltage?formatNumber([number:frame.frameExtra?.superCapacitorVoltage, type: "number", locale: "EN",minFractionDigits: "2", maxFractionDigits: "2"]):"null"},
                ${frame.frameExtra?.solarArrayVoltage?formatNumber([number:frame.frameExtra?.solarArrayVoltage, type: "number", locale: "EN", minFractionDigits: "2",maxFractionDigits: "2"]):"null"}
            ]);
            </g:each>

            var options = {
                chart: {
                    'title': 'Tension de la supercapacité',
                    subtitle: 'en volts'
                },
                'height': 320,
                curveType: 'function'
            };

            var chart = new google.charts.Line(document.getElementById('linechart_material'));

            chart.draw(dataSuperCap, options);
        }
    </script>
</head>

<body>
<g:render template="/templates/flashMessage"/>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <div id="linechart_material"></div>
        </div>
    </div>
</div>
</body>
</html>