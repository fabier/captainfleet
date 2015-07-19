<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="admin"/>
    <title>CaptainFleet - Icones</title>
    <asset:stylesheet src="dropzone"/>
    <asset:javascript src="dropzone"/>
    <script type="application/javascript">
        Dropzone.autoDiscover = false;

        $(function () {
            var myDropzone = new Dropzone("#uploadData", {
                paramName: "file", // The name that will be used to transfer the file
                maxFilesize: 2 // MB
            });

            myDropzone.on("complete", function (file) {
                location.reload();
            });
        })
    </script>
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
                <g:form action='mapMarkerIconSearch' name='mapMarkerIconSearchForm' class="form-horizontal">
                    <div class="form-group">
                        <label for="name" class="col-md-1 control-label">Nom</label>

                        <div class="col-md-4">
                            <input class="form-control" id="name" name="name" placeholder="Recherche"
                                   value="${params.name ?: ""}"/>
                        </div>

                        <div class="col-md-5">
                            <button type="submit" class="btn btn-primary">
                                Rechercher
                            </button>
                        </div>

                        <div class="col-md-2">
                            <g:link action="removeAll" class="btn btn-warning">
                                Supprimer tout
                            </g:link>
                        </div>
                    </div>
                </g:form>
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
                <div class="col-md-8 col-md-offset-1">
                    <g:form name="uploadData" action="uploadData" class="dropzone">
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
