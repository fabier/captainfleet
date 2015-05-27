<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Alertes</title>
</head>

<body>
<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-10">
            <g:render template="/templates/flashMessage"/>

            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Mes alertes
            </legend>

            <g:if test="${alerts.isEmpty() && params.name == null}">
                <div class="row">
                    <div class="alert alert-info">
                        Vous n'avez pas encore défini d'alerte, vous pouvez le faire en cliquant sur le bouton "Créer une nouvelle alerte".
                    </div>
                </div>
            </g:if>

            <g:if test="${!alerts.isEmpty() || params.name}">
                <g:render template="searchForm"/>
            </g:if>

            <g:if test="${alerts.isEmpty() && params.name != null}">
                <div class="alert alert-info">
                    <p>
                        Aucun résultat pour cette recherche
                    </p>
                </div>
            </g:if>

            <g:if test="${alerts.isEmpty() && params.name}">
                <div class="row">
                    <div class="alert alert-info">
                        Aucune alerte ne correspond à votre recherche.
                    </div>
                </div>
            </g:if>

            <g:if test="${!alerts.isEmpty()}">
                <div class="row">
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th class="col-md-1">#</th>
                            <th class="col-md-2">Nom de l'alerte</th>
                            <th class="col-md-2">Type</th>
                            <th class="col-md-2">Nombre de points</th>
                            <th class="col-md-1">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${alerts}" var="alert">
                            <tr class="clickable-row"
                                data-href="${createLink(action: "show", id: alert.id)}">
                                <td>${alert.id}</td>
                                <td>${alert.name}</td>
                                <td>${alert.geometry?.getGeometryType()}</td>
                                <td>${alert.geometry?.getNumPoints()}</td>
                                <td>
                                    <g:link controller="alert" action="delete" id="${alert.id}">
                                        <i class="glyphicon glyphicon-trash"></i>
                                    </g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <g:if test="${totalCount > alerts.size()}">
                        <div class="text-center">
                            <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="index" total="${totalCount}"/>
                        </div>
                    </g:if>
                </div>
            </g:if>

            <div class="row">
                <div class="col-md-4">
                    <g:link action="create" class="btn btn-primary">
                        <i class="glyphicon glyphicon-plus"></i>
                        Créer une nouvelle alerte
                    </g:link>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
