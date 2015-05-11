<html>

<head>
    <meta name='layout' content='admin'/>
    <title>Station</title>
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

                <div class="alert alert-danger">
                    Attention, éditer une station est dangereux. Il est déconseillé de modifier une station.
                </div>

                <div class="col-md-6">
                    <g:form action='update' class="form-horizontal" id="${station.id}">
                        <g:hiddenField name="id" value="${station?.id}"/>
                        <g:hiddenField name="version" value="${station?.version}"/>

                        <div class="form-group">
                            <label for="name" class="col-md-2 control-label">Name</label>

                            <div class="col-md-10">
                                <g:field type="text" name="name" value="${station.name}" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="sigfoxId" class="col-md-2 control-label">SigFoxId</label>

                            <div class="col-md-10">
                                <g:field type="text" name="sigfoxId" value="${station.sigfoxId}"
                                         class="form-control" disabled="disabled"/>
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
    </div>
</div>
</body>
</html>
