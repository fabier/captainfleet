<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>Mon compte</title>
</head>

<body>
<div class="container margin-top-20">
    <div class="row">
        <div class="col-sm-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-sm-10">
            <g:render template="/templates/flashMessage"/>

            <g:if test="${devices.isEmpty()}">
                <div class="alert alert-info">
                    <p>
                        Vous n'avez pas encore de terminal associé à votre compte, veuillez saisir le code inscrit au dos du boitier pour l'associer à votre compte.
                    </p>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-info">
                    <p>
                        Pour associer un nouveau device tapez son code, puis validez.
                    </p>
                </div>
            </g:else>
            <g:form action="addDevice" class="form-horizontal">
                <div class="form-group">
                    <label for="code" class="col-sm-2 control-label">Code</label>

                    <div class="col-sm-2">
                        <g:field class="form-control" type="text" name="code" value="${params.code}"
                                 placeholder="Code"/>
                    </div>

                    <div class="col-sm-2">
                        <button type="submit" class="btn btn-primary">
                            <i class="glyphicon glyphicon-plus-sign"></i>
                            Ajouter
                        </button>
                    </div>
                </div>
            </g:form>

            <table class="table table-hover small nomargin">
                <thead>
                <th>#</th>
                <th>Nom du terminal</th>
                <th></th>
                </thead>
                <tbody>
                <g:each in="${devices}" var="device">
                    <tr>
                        <td>${device.sigfoxId}</td>
                        <td>${device.name}</td>
                        <td>
                            <g:link controller="device" action="map" id="${device.id}">
                                Accéder au détail
                            </g:link>
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
