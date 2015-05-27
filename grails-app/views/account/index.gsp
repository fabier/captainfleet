<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Mon compte</title>
</head>

<body>
<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-10">
            <legend>
                <i class="glyphicon glyphicon-user"></i>
                &nbsp;Mon compte
            </legend>

            <div class="row">
                <div class="alert alert-info">
                    <p>
                        Vous pouvez utiliser le formulaire ci dessous pour changer votre nom.
                    </p>
                </div>

                <g:render template="/templates/flashMessage"/>

                <div class="col-md-6">
                    <g:form action='update' class="form-horizontal">

                        <div class="form-group">
                            <label for="username" class="col-md-2 control-label">Nom</label>

                            <div class="col-md-10">
                                <g:field type="text" name="username" value="${user.username}"
                                         class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="email" class="col-md-2 control-label">Email</label>

                            <div class="col-md-10">
                                <g:field type="text" name="email" value="${user.email}" class="form-control"
                                         disabled="disabled"/>
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
