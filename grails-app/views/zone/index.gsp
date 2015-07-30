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
                &nbsp;Mes zones
            </legend>

            <g:if test="${zones.isEmpty() && params.name == null}">
                <div class="row">
                    <div class="alert alert-info">
                        Vous n'avez pas encore défini de zone, vous pouvez le faire en cliquant sur le bouton "Créer une nouvelle zone".
                    </div>
                </div>
            </g:if>

            <g:if test="${!zones.isEmpty() || params.name}">
                <g:render template="searchForm"/>
            </g:if>

            <g:if test="${zones.isEmpty() && params.name != null}">
                <div class="alert alert-info">
                    <p>
                        Aucun résultat pour cette recherche
                    </p>
                </div>
            </g:if>

            <g:if test="${zones.isEmpty() && params.name}">
                <div class="row">
                    <div class="alert alert-info">
                        Aucune zone ne correspond à votre recherche.
                    </div>
                </div>
            </g:if>

            <g:if test="${!zones.isEmpty()}">
                <div class="row">
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th class="col-md-1">#</th>
                            <th class="col-md-2">Nom de la zone</th>
                            <th class="col-md-2">Aire en m²</th>
                            <th class="col-md-2">Nombre de points</th>
                            <th class="col-md-1">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${zones}" var="zone">
                            <tr class="clickable-row"
                                data-href="${raw(createLink(action: "show", id: zone.id))}">
                                <td>${zone.id}</td>
                                <td>${zone.name}</td>
                                <td>
                                    <g:formatArea number="${zone.getAreaInSquareMeters()}"/>
                                </td>
                                <td>${zone.geometry?.getNumPoints()}</td>
                                <td>
                                    <g:link controller="zone" action="delete" id="${zone.id}">
                                        <i class="glyphicon glyphicon-trash"></i>
                                    </g:link>
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

            <div class="row margin-bottom-20">
                <g:if test="${zones.isEmpty() || zones.size() < 5}">
                    <g:link action="create" class="btn btn-primary">
                        <i class="glyphicon glyphicon-plus"></i>
                        Créer une nouvelle zone
                    </g:link>
                </g:if>
            </div>

            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Mes zones - Évènements sur les 7 derniers jours
            </legend>

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
                                data-href="${raw(createLink(controller: "zone", action: "devicesLog", id1: zone.id, id2: device.id))}">
                                <td>${deviceZoneLogAggregate.id}</td>
                                <td>${zone.name}</td>
                                <td>${device.name}</td>
                                <td>
                                    <g:formatDate format="EEEE dd MMMM HH'h'mm"
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
            </g:if>
        </div>
    </div>
</div>
</body>
</html>
