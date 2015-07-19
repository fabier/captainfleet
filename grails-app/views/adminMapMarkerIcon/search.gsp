<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="admin"/>
    <title>CaptainFleet - Icones</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">

            <legend>
                <i class="glyphicon glyphicon-map-marker"></i>
                &nbsp;Liste des marqueurs
            </legend>

            <div class="row">
                <div class="col-md-6">
                    <g:form action='mapMarkerIconSearch' name='mapMarkerIconSearchForm' class="form-horizontal">
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
                <table class="table table-hover small nomargin-left-right">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Nom</th>
                        <th>Nom de fichier</th>
                        <th>Icone</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${results}" var="mapMarkerIcon">
                        <tr class="clickable-row"
                            data-href="${createLink(controller: "adminMapMarkerIcon", action: "show", id: mapMarkerIcon.id)}">
                            <td>${mapMarkerIcon.id}</td>
                            <td>${mapMarkerIcon.name}</td>
                            <td>${mapMarkerIcon.filename}</td>
                            <td>
                                <img src="${createLink(controller: "mapMarker", action: "index", id: mapMarkerIcon.id)}">
                            </td>
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

            <legend>
                <i class="glyphicon glyphicon-map-marker"></i>
                &nbsp;Créer un nouveau marqueur
            </legend>

            <div class="row">
                <g:uploadForm action="uploadData">
                    <div class="form-group">
                        <label for="name" class="col-md-2 control-label">Fichier</label>

                        <div class="col-md-8">
                            <input type="file" name="markerFile"/>
                        </div>

                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary">
                                Créer
                            </button>
                        </div>
                    </div>
                </g:uploadForm>
            </div>
        </div>
    </div>
</div>
</body>
</html>
