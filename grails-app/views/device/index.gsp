<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Boitiers</title>
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
                <i class="glyphicon glyphicon-th-large"></i>
                &nbsp;Mes boitiers
            </legend>

            <g:if test="${devices.isEmpty() && params.name == null}">
                <div class="alert alert-info">
                    <p>
                        Vous n'avez pas encore de boitier associé à votre compte, veuillez saisir le code inscrit au dos du boitier pour l'associer à votre compte.
                    </p>
                </div>
            </g:if>

            <g:if test="${!devices.isEmpty() || params.name}">
                <g:render template="searchForm"/>
            </g:if>

            <g:if test="${devices.isEmpty() && params.name != null}">
                <div class="alert alert-info">
                    <p>
                        Aucun résultat pour cette recherche
                    </p>
                </div>
            </g:if>

            <g:if test="${!devices.isEmpty()}">
                <table class="table table-hover small nomargin-left-right">
                    <thead>
                    <th class="col-md-6">Nom du boitier</th>
                    <th class="col-md-2">SigFox</th>
                    <th class="col-md-2">Code</th>
                    </thead>
                    <tbody>
                    <g:each in="${devices}" var="device">
                        <tr class="clickable-row"
                            data-href="${createLink(controller: "device", action: "edit", id: device.id)}">
                            <td>
                                <img src="${createLink(controller: "mapMarker", action: "index", id: (device.mapMarkerIcon ?: defaultMapMarkerIcon).id)}">
                                <span class="text-larger bolder">${device.name}</span>
                            </td>
                            <td>${device.sigfoxId}</td>
                            <td>${device.code}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

                <g:if test="${totalCount > devices.size()}">
                    <div class="text-center">
                        <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="index" total="${totalCount}"/>
                    </div>
                </g:if>
            </g:if>

            <legend>Ajouter un boitier</legend>

            <div class="alert alert-info">
                <p>
                    Pour associer un nouveau boitier tapez son code, puis cliquez sur "Ajouter".
                </p>
            </div>
            <g:form action="addDevice" class="form-horizontal">
                <div class="form-group">
                    <label for="code" class="col-md-2 control-label">Code</label>

                    <div class="col-md-2">
                        <g:field class="form-control" type="text" name="code" value="${params.code}"
                                 placeholder="Code"/>
                    </div>

                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="glyphicon glyphicon-plus-sign"></i>
                            Ajouter
                        </button>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
