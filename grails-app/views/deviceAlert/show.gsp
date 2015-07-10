<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Alertes</title>
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
                &nbsp;Logs pour l'alerte "${alert.name ?: alert.id}" et le boitier ${device.sigfoxId}
            </legend>


            <g:if test="${deviceAlertLogs.isEmpty()}">
                <div class="row">
                    <div class="alert alert-info">
                        Aucun log pour l'alerte "${alert.name ?: alert.id}" et le boitier ${device.sigfoxId}
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
                        <g:set var="state" value="${deviceAlertLogs?.first().isRaised}"/>
                        <g:set var="previousDeviceAlertLog" value="${deviceAlertLogs?.first()}"/>
                        <g:each in="${deviceAlertLogs}" var="deviceAlertLog">
                            <g:if test="${state != deviceAlertLog.isRaised}">
                                <tr class="clickable-row" data-href="#">
                                    <td>${previousDeviceAlertLog.id}</td>
                                    <td>
                                        <g:formatDate format="dd MMMM HH'h'mm"
                                                      date="${previousDeviceAlertLog.dateCreated}"/>
                                    </td>
                                    <td>
                                        <g:if test="${previousDeviceAlertLog.isRaised}">
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
                                <g:set var="state" value="${deviceAlertLog.isRaised}"/>
                            </g:if>
                            <g:set var="previousDeviceAlertLog" value="${deviceAlertLog}"/>
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
                        <g:each in="${deviceAlertLogs}" var="deviceAlertLog">
                            <tr class="clickable-row" data-href="#">
                                <td>${deviceAlertLog.id}</td>
                                <td>
                                    <g:formatDate format="dd MMMM HH'h'mm"
                                                  date="${deviceAlertLog.dateCreated}"/>
                                </td>
                                <td>
                                    <g:if test="${deviceAlertLog.isRaised}">
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
            <g:render template="/alert/actions"/>
        </div>
    </div>
</div>
</body>
</html>
