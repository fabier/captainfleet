<html>

<head>
    <meta name='layout' content='admin'/>
    <title>Utilisateurs</title>
</head>

<body>

<div class="container margin-top-20">

    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <div class="row">
                <g:render template="/templates/flashMessage"/>

                <div class="col-md-6">
                    <g:form action='userSearch' name='userSearchForm' class="form-horizontal">
                        <div class="form-group">
                            <label for="username" class="col-md-2 control-label">Nom</label>

                            <div class="col-md-8">
                                <input class="form-control" id="username" name="username" placeholder="Nom"
                                       value="${params.username ?: ""}"/>
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

            <g:if test="${searched}">
                <div class="row">
                    <table class="table table-hover small nomargin">
                        <thead>
                        <tr>
                            <th>
                                #
                            </th>
                            <th>
                                Nom
                            </th>
                            <th>
                                Email
                            </th>
                            <th>
                                Actif
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${results}" var="user">
                            <tr class="clickable-row"
                                data-href="${createLink(action: "edit", id: user.id)}">
                                <td>
                                    ${fieldValue(bean: user, field: "id")}
                                </td>
                                <td>
                                    ${fieldValue(bean: user, field: "username")}
                                </td>
                                <td>
                                    ${fieldValue(bean: user, field: "email")}
                                </td>
                                <td>
                                    <g:if test="${user.enabled}">
                                        <i class="glyphicon glyphicon-check"></i>
                                    </g:if>
                                    <g:else>
                                        <i class="glyphicon glyphicon-unchecked"></i>
                                    </g:else>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <g:if test="${totalCount > results.size()}">
                        <div class="text-center">
                            <ul class="pagination">
                                <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="search"
                                            total="${totalCount}"/>
                            </ul>
                        </div>
                    </g:if>
                </div>
            </g:if>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $("#username").focus().autocomplete({
            minLength: 3,
            cache: false,
            source: "${createLink(action: 'ajaxUserSearch')}"
        });
    });

    <s2ui:initCheckboxes/>

</script>

</body>
</html>
