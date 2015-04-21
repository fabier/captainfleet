<html>

<head>
    <title><g:message code='spring.security.ui.resetPassword.title'/></title>
    <meta name='layout' content='main'/>
</head>

<body>

<div class="container">
    <div class="row">
        <div class="col-sm-8 center-block">

            <g:render template="/templates/flashMessage"/>

            <div class="panel panel-default">
                <div class="panel-body">
                    <g:form action='resetPassword' name='resetPasswordForm' role="form" class="form-horizontal">
                        <g:hiddenField name='t' value='${token}'/>

                        <div class="form-group">
                            <label for="password" class="col-sm-4 control-label">
                                <g:message code="resetPasswordCommand.password.label" default="Mot de passe"/>
                            </label>

                            <div class="col-sm-6">
                                <input type="password" name="password" id="password" size="20" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password2" class="col-sm-4 control-label">
                                <g:message code="resetPasswordCommand.password2.label" default="Mot de passe (répéter)"/>
                            </label>

                            <div class="col-sm-6">
                                <input type="password" name="password2" id="password2" size="20" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-8 col-sm-offset-4">
                                <button type="submit" class="btn btn-primary">
                                    <g:message code="spring.security.ui.resetPassword.submit" default="Enregistrer"/>
                                </button>
                            </div>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#password').focus();
    });
</script>

</body>
</html>
