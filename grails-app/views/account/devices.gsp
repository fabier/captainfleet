<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Boitiers</title>
</head>

<body>
<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-10">
            <g:render template="/templates/flashMessage"/>

            <g:if test="${devices.isEmpty()}">
                <div class="alert alert-info">
                    <p>
                        Vous n'avez pas encore de boitier associé à votre compte, veuillez saisir le code inscrit au dos du boitier pour l'associer à votre compte.
                    </p>
                </div>
            </g:if>
            <g:else>
                <div class="alert alert-info">
                    <p>
                        Pour associer un nouveau boitier tapez son code, puis cliquez sur "Ajouter".
                    </p>
                </div>
            </g:else>
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

            <table class="table table-hover small nomargin-left-right">
                <thead>
                <th>#</th>
                <th>Nom du boitier</th>
                <th>Code</th>
                </thead>
                <tbody>
                <g:each in="${devices}" var="device">
                    <tr class="clickable-row"
                        data-href="${createLink(controller: "device", action: "edit", id: device.id)}">
                        <td>${device.sigfoxId}</td>
                        <td>${device.name}</td>
                        <td>${device.code}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
