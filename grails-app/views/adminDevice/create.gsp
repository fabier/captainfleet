<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Boitier</title>
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
                <i class="glyphicon glyphicon-th-large"></i>
                &nbsp;Nouveau boitier
            </legend>

            <g:form action="save" class="form-horizontal">

                <div class="form-group">
                    <label for="sigfoxId" class="col-md-2 control-label">SigFoxId</label>

                    <div class="col-md-10">
                        <g:field type="text" name="sigfoxId" value="${params.sigfoxId}" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="name" class="col-md-2 control-label">Nom</label>

                    <div class="col-md-10">
                        <g:field type="text" name="name" value="${params.name}" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="code" class="col-md-2 control-label">Code</label>

                    <div class="col-md-10">
                        <g:field type="text" name="code" value="${params.code}" class="form-control" disabled="disabled"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-2 control-label">Icone</label>

                    <div class="col-md-10">
                        <g:each in="${mapMarkerIcons}" var="mapMarker">
                            <label>
                                <input type="radio" name="mapMarkerIcon"
                                       value="${mapMarker.id}" ${defaultMapMarker.id == mapMarker.id ? "checked" : ""}/>
                                <img src="${raw(createLink(controller: "mapMarker", action: "index", id: mapMarker.id))}">
                            </label>
                        </g:each>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-10 col-md-offset-2">
                        <button type="submit" class="btn btn-primary">
                            Enregistrer
                        </button>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
