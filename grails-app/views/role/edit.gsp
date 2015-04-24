<html>

<head>
    <meta name='layout' content='admin'/>
    <title>Role</title>
</head>

<body>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-sm-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-sm-10">
            <div class="row">
                <g:render template="/templates/flashMessage"/>

                <div class="alert alert-danger">
                    Attention, éditer un role peut couper les droits à l'ensemble des utilisateurs, y compris vous même !
                    <br>
                    Soyez absolument sûrs de ce que vous faites avant de modifier un rôle !
                </div>

                <div class="col-sm-6">
                    <g:form action='update' class="form-horizontal" id="${role.id}">
                        <g:hiddenField name="id" value="${role?.id}"/>
                        <g:hiddenField name="version" value="${role?.version}"/>

                        <div class="form-group">
                            <label for="authority" class="col-sm-2 control-label">Role</label>

                            <div class="col-sm-10">
                                <input class="form-control" id="authority" name="authority" placeholder="Name"
                                       value="${role.authority ?: ""}"/>
                            </div>
                        </div>


                        <div class="form-group">
                            <div class="col-sm-10 col-sm-offset-2">
                                <button type="submit" class="btn btn-primary">
                                    Enregistrer
                                </button>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>