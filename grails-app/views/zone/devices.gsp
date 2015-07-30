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
                &nbsp;Fiche zone - Etat des boitiers
            </legend>

            <g:if test="${deviceZones.isEmpty()}">
                <div class="row">
                    <div class="alert alert-info">
                        Pas encore de données pour cette zone.
                    </div>
                </div>

                <div class="row">
                    <g:link action="updateDeviceState" id="${zone.id}" class="btn btn-primary">
                        Chercher des données
                    </g:link>
                </div>
            </g:if>
            <g:else>
                <div class="row">
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th class="col-md-1">#</th>
                            <th class="col-md-2">Nom du boitier</th>
                            <th class="col-md-2">Dans la zone</th>
                            <th class="col-md-2">Dernière mise à jour</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${deviceZones}" var="deviceZone">
                            <tr class="clickable-row"
                                data-href="${createLink(controller: "zone", action: "deviceLog", params: [id1: zone.id, id2: deviceZone.deviceId])}">
                                <td>${deviceZone.device.sigfoxId}</td>
                                <td>${deviceZone.device.name}</td>
                                <td>
                                    <g:formatBooleanYesNo boolean="${deviceZone.isRaised ?: false}"/>
                                </td>
                                <td>
                                    <g:formatDate format="dd MMMM HH'h'mm" date="${deviceZone.lastUpdated}"/>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>
                </div>
            </g:else>
        </div>

        <div class="col-md-2">
            <g:render template="actions"/>
        </div>
    </div>
</div>
</body>
</html>
