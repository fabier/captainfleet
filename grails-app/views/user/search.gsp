<html>

<head>
    <meta name='layout' content='admin'/>
    <title>CaptainFleet - Utilisateurs</title>
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
                    <table class="table table-hover small nomargin-left-right">
                        <thead>
                        <tr>
                            <th class="col-md-1">
                                #
                            </th>
                            <th class="col-md-2">
                                Nom
                            </th>
                            <th class="col-md-3">
                                Email
                            </th>
                            <th class="col-md-1">
                                Date de création
                            </th>
                            <th class="col-md-1">
                                Actif
                            </th>
                            <th class="col-md-1">
                                Verrouillé
                            </th>
                            <th class="col-md-3">
                                Supprimer
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <g:each in="${results}" var="user">
                            <tr class="clickable-row"
                                data-href="${raw(createLink(action: "edit", id: user.id))}">
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
                                    <g:formatDate date="${user.dateCreated}" format="d MMM yyyy"/>
                                </td>
                                <td>
                                    <g:link controller="user" action="toggleEnabled" id="${user.id}" params="${params}">
                                        <g:if test="${user.enabled}">
                                            <i class="glyphicon glyphicon-check"></i>
                                        </g:if>
                                        <g:else>
                                            <i class="glyphicon glyphicon-unchecked"></i>
                                        </g:else>
                                    </g:link>
                                </td>
                                <td>
                                    <g:link controller="user" action="toggleLocked" id="${user.id}" params="${params}">
                                        <g:if test="${user.accountLocked}">
                                            <i class="glyphicon glyphicon-lock"></i>
                                        </g:if>
                                        <g:else>
                                            <i class="glyphicon glyphicon-minus"></i>
                                        </g:else>
                                    </g:link>
                                </td>
                                <td>
                                    <g:link controller="user" action="delete" id="${user.id}" params="${params}">
                                        <i class="glyphicon glyphicon-trash"></i>
                                    </g:link>
                                </td>
                            </tr>
                        </g:each>
                        </tbody>
                    </table>

                    <g:if test="${totalCount > results.size()}">
                        <div class="text-center">
                            <ul class="pagination">
                                <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="userSearch"
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
            source: "${raw(createLink(action: 'ajaxUserSearch'))}"
        });
    });

    <s2ui:initCheckboxes/>

</script>

</body>
</html>
