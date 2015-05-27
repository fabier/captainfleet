<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Boitier</title>
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
                <i class="glyphicon glyphicon-th-large"></i>
                &nbsp;Fiche boitier
            </legend>

            <g:form action="update" id="${device.id}" class="form-horizontal">

                <div class="form-group">
                    <label for="sigfoxId" class="col-md-2 control-label">SigFoxId</label>

                    <div class="col-md-10">
                        <g:field type="text" name="sigfoxId" value="${device.sigfoxId}" class="form-control"
                                 disabled="disabled"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="name" class="col-md-2 control-label">Nom</label>

                    <div class="col-md-10">
                        <g:field type="text" name="name" value="${device.name}" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="code" class="col-md-2 control-label">Code</label>

                    <div class="col-md-10">
                        <g:field type="text" name="code" value="${device.code}" class="form-control"
                                 disabled="disabled"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-10 col-md-offset-2">
                        <button type="submit" class="btn btn-primary">
                            Enregistrer
                        </button>
                        <g:if test="${device.code == null}">
                            <g:link action="randomCode" id="${device.id}" class="btn btn-danger">
                                Générer un nouveau code aléatoire
                            </g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>
