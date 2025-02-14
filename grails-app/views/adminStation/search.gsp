<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="admin"/>
    <title>CaptainFleet - Station</title>
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
                <div class="col-md-6">
                    <g:form action='stationSearch' name='stationSearchForm' class="form-horizontal">
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
                        <th class="col-md-2">#</th>
                        <th class="col-md-6">SigFoxId</th>
                        <th class="col-md-4">Nombre de messages recus</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${results}" var="station">
                        <tr class="clickable-row"
                            data-href="${raw(createLink(controller: "adminStation", action: "edit", id: station.id))}">
                            <td>${station.id}</td>
                            <td>${station.sigfoxId}</td>
                            <td>${station.frameCount()}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>

                <g:if test="${totalCount > results.size()}">
                    <div class="text-center">
                        <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="stationSearch"
                                    total="${totalCount}"/>
                    </div>
                </g:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
