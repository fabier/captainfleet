<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin">
    <title>CaptainFleet - Icone</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <g:form action="update" id="${mapMarkerIcon.id}" class="form-horizontal">

                <div class="form-group">
                    <label for="name" class="col-md-2 control-label">Nom</label>

                    <div class="col-md-10">
                        <g:field type="text" name="name" value="${mapMarkerIcon.name}" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="filename" class="col-md-2 control-label">Filename</label>

                    <div class="col-md-10">
                        <g:field type="text" name="filename" value="${mapMarkerIcon.filename}" class="form-control"
                                 disabled="disabled"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="name" class="col-md-2 control-label">Icone</label>

                    <div class="col-md-10">
                        <img src="${raw(createLink(controller: "mapMarker", action: "index", id: mapMarkerIcon.id))}">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-6 col-md-offset-2">
                        <button type="submit" class="btn btn-primary">
                            Enregistrer
                        </button>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-4 col-md-offset-2">
                        <g:if test="${!mapMarkerIcon.isDefault}">
                            <g:link controller="adminMapMarkerIcon" action="setAsDefault" id="${mapMarkerIcon.id}"
                                    class="btn btn-link">
                                Définir comme icone par défaut
                            </g:link>
                        </g:if>
                        <g:else>
                            <i class="glyphicon glyphicon-check"></i>
                            Icone par défaut
                        </g:else>
                    </div>
                </div>
            </g:form>
        </div>
    </div>
</div>
</body>
</html>