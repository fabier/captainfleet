<html>

<head>
    <meta name='layout' content='admin'/>
    <title>CaptainFleet - Rôles</title>
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
                    <g:form action='roleSearch' name='roleSearchForm' class="form-horizontal">
                        <div class="form-group">
                            <label for="authority" class="col-md-2 control-label">Role</label>

                            <div class="col-md-8">
                                <input class="form-control" id="authority" name="authority" placeholder="Search"
                                       value="${params.authority ?: ""}"/>
                            </div>

                            <div class="col-md-2">
                                <button type="submit" class="btn btn-primary">
                                    Search
                                </button>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>

            <g:if test="${searched}">
                <div class="row">
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th>
                                #
                            </th>
                            <th>
                                Nom
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${results}" var="role">
                            <tr class="clickable-row"
                                data-href="${raw(createLink(action: "edit", id: role.id))}">
                                <td>
                                    ${fieldValue(bean: role, field: "id")}
                                </td>
                                <td>
                                    ${fieldValue(bean: role, field: "authority")}
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <g:if test="${totalCount > results.size()}">
                        <nav class="text-center">
                            <ul class="pagination">
                                <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="search"
                                            total="${totalCount}"/>
                            </ul>
                        </nav>
                    </g:if>
                </div>
            </g:if>
        </div>
    </div>
</div>
</body>
</html>
