<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="admin"/>
    <title>CaptainFleet - Boitiers</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <div class="row">
                <g:form action='deviceSearch' name='deviceSearchForm' class="form-horizontal">
                    <div class="form-group">
                        <label for="name" class="col-md-1 control-label">Nom</label>

                        <div class="col-md-4">
                            <input class="form-control" id="name" name="name" placeholder="Recherche"
                                   value="${params.name ?: ""}"/>
                        </div>

                        <div class="col-md-7">
                            <button type="submit" class="btn btn-primary">
                                Rechercher
                            </button>
                            <g:link action="create" class="btn btn-primary">
                                <i class="glyphicon glyphicon-plus-sign"></i>
                                &nbsp;Créer
                            </g:link>
                        </div>
                    </div>
                </g:form>
            </div>

            <div class="row">
                <table class="table table-hover small nomargin-left-right">
                    <thead>
                    <tr>
                        <th>Nom</th>
                        <th>SigFoxId</th>
                        <th>Code</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${results}" var="device">
                        <tr class="clickable-row"
                            data-href="${raw(createLink(controller: "adminDevice", action: "show", id: device.id))}">
                            <td>
                                <img src="${raw(createLink(controller: "mapMarker", action: "index", id: (device.mapMarkerIcon ?: defaultMapMarkerIcon).id))}">
                                <span class="text-larger bolder">${device.name}</span>
                            </td>
                            <td>${device.sigfoxId}</td>
                            <td>${device.code}</td>
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
        </div>
    </div>
</div>
</body>
</html>
