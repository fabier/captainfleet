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

        <div class="col-md-10">
            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Mes zones - Évènements sur les 7 derniers jours
            </legend>

            <g:if test="${zones.isEmpty() && params.name == null}">
                <div class="row">
                    <div class="alert alert-info">
                        Vous n'avez pas encore défini de zone, vous pouvez le faire en cliquant sur le bouton "Créer une nouvelle zone".
                    </div>
                </div>
            </g:if>

            <g:if test="${!deviceZoneLogAggregates.isEmpty()}">
                <div class="row">
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th class="col-md-1">#</th>
                            <th class="col-md-2">Nom de la zone</th>
                            <th class="col-md-2">Boitier</th>
                            <th class="col-md-2">Date</th>
                            <th class="col-md-3">Etat</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${deviceZoneLogAggregates}" var="deviceZoneLogAggregate">
                            <g:set var="zone" value="${deviceZoneLogAggregate.zone}"/>
                            <g:set var="device" value="${deviceZoneLogAggregate.device}"/>
                            <tr class="clickable-row"
                                data-href="${createLink(controller: "zone", action: "devicesLog", id1: zone.id, id2: device.id)}">
                                <td>${deviceZoneLogAggregate.id}</td>
                                <td>${zone.name}</td>
                                <td>${device.name}</td>
                                <td>
                                    <g:formatDate format="dd MMMM HH'h'mm" date="${deviceZoneLogAggregate.dateCreated}"/>
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

                    <g:if test="${totalCount > zones.size()}">
                        <div class="text-center">
                            <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="index" total="${totalCount}"/>
                        </div>
                    </g:if>
                </div>
            </g:if>
        </div>
    </div>
</div>
</body>
</html>
