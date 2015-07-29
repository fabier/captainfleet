<html>
<head>
    <meta name="layout" content="main">
    <title>CaptainFleet - Trame</title>
</head>

<body>
<g:render template="/templates/flashMessage"/>

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralDashboard"/>
        </div>

        <div class="col-md-10">
            <g:each in="${zones.collate(2)}" var="zoneGroup">
                <div class="row">
                    <g:each in="${zoneGroup}" var="zone">
                        <g:set var="state" value="${stateZoneDeviceMap.get(zone)}"/>
                        <div class="col-md-6">
                            <div class="panel panel-info">
                                <div class="panel-heading">
                                    <h4 class="nomargin">${zone.name}</h4>
                                </div>

                                <div class="panel-body">
                                    <g:if test="${state[0] > 0}">
                                        <p class="margin-top-bottom-10">
                                            <span class="label label-success text-large">${state[0]}</span>
                                            boitier<g:plural val="${state[0]}"/> dans la zone
                                        </p>
                                    </g:if>

                                    <g:if test="${state[1] > 0}">
                                        <p class="margin-top-bottom-10">
                                            <span class="label label-info text-large">${state[1]}</span>
                                            boitier<g:plural val="${state[1]}"/> hors zone
                                        </p>
                                    </g:if>

                                    <g:if test="${state[2] > 0}">
                                        <p class="margin-top-bottom-10">
                                            <span class="label label-warning text-large">${state[2]}</span>
                                            boitier<g:plural val="${state[2]}"/> en état inconnu
                                        </p>
                                    </g:if>
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