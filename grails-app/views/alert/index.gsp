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
            <g:if test="${totalCount == 0}">
                <div class="row">
                    <div class="alert alert-info">
                        Vous n'avez pas encore défini d'alerte, vous pouvez le faire en cliquant sur le bouton "Créer une nouvelle alerte".
                    </div>
                </div>
            </g:if>
            <g:else>
                <div class="row">
                    <g:render template="/templates/flashMessage"/>

                    <div class="col-md-6">
                        <g:form action='search' name='searchForm' class="form-horizontal">
                            <div class="form-group">
                                <label for="name" class="col-md-2 control-label">Nom</label>

                                <div class="col-md-8">
                                    <input class="form-control" id="name" name="name" placeholder="Recherche"
                                           value="${params.name ?: ""}"/>
                                </div>

                                <div class="col-md-2">
                                    <button type="submit" class="btn btn-primary">
                                        Rechercher
                                    </button>
                                </div>
                            </div>
                        </g:form>
                    </div>
                </div>

                <div class="row">
                    <table class="table table-hover small nomargin">
                        <thead>
                        <tr>
                            <th class="col-md-2">#</th>
                            <th class="col-md-4">Type</th>
                            <th class="col-md-6">Nombre de points</th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${results}" var="alert">
                            <tr class="clickable-row"
                                data-href="${createLink(action: "show", id: alert.id)}">
                                <td>${alert.id}</td>
                                <td>${alert.geometry?.getGeometryType()}</td>
                                <td>${alert.geometry?.getNumPoints()}</td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <g:if test="${totalCount > results.size()}">
                        <div class="text-center">
                            <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="deviceSearch"
                                        total="${totalCount}"/>
                        </div>
                    </g:if>
                </div>
            </g:else>

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
