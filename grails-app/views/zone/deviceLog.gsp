<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Zones</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-8">
            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Logs pour la zone "${zone.name ?: zone.id}" et le boitier "${device?.name ?: device?.sigfoxId}"
            </legend>


            <g:if test="${deviceZoneLogs.isEmpty()}">
                <div class="row">
                    <div class="alert alert-info">
                        Aucun log pour la zone "${zone.name ?: zone.id}" et le boitier "${device.name ?: device?.sigfoxId}"
                    </div>
                </div>
            </g:if>
            <g:else>
                <div class="row">
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th class="col-md-1">#</th>
                            <th class="col-md-2">Date</th>
                            <th class="col-md-9">Etat</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${deviceZoneLogAggregates}" var="deviceZoneLogAggregate">
                            <tr class="clickable-row" data-href="#">
                                <td>${deviceZoneLogAggregate.id}</td>
                                <td>
                                    <g:formatDate format="dd MMMM HH'h'mm"
                                                  date="${deviceZoneLogAggregate.dateCreated}"/>
                                </td>
                                <td>
                                    <g:if test="${deviceZoneLogAggregate.isRaised}">
                                        <i class="glyphicon glyphicon-log-in"></i>
                                        &nbsp;
                                        Entrée dans la zone
                                    </g:if>
                                    <g:else>
                                        <i class="glyphicon glyphicon-log-out"></i>
                                        &nbsp;
                                        Sortie de la zone
                                    </g:else>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>

                <legend>
                    Détail des logs
                </legend>

                <div class="row">
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th class="col-md-1">#</th>
                            <th class="col-md-2">Date</th>
                            <th class="col-md-9">Etat</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${deviceZoneLogs}" var="deviceZoneLog">
                            <tr class="clickable-row" data-href="#">
                                <td>${deviceZoneLog.id}</td>
                                <td>
                                    <g:formatDate format="dd MMMM HH'h'mm"
                                                  date="${deviceZoneLog.dateCreated}"/>
                                </td>
                                <td>
                                    <g:if test="${deviceZoneLog.isRaised}">
                                        Dans la zone
                                    </g:if>
                                    <g:else>
                                        Hors zone
                                    </g:else>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </g:else>
        </div>

        <div class="col-md-2">
            <g:render template="/zone/actions"/>
        </div>
    </div>
</div>
</body>
</html>
