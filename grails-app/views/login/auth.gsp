<html>

<head>
    <g:set var="hideLoginBlock" value="${true}" scope="request"/>
    <title><g:message code='spring.security.ui.login.title'/></title>
    <meta name='layout' content='main'/>
</head>

<body>

<div class="container ">

    <g:render template="/templates/flashMessage"/>
    <div class="row">
        <div class="col-md-6 center-block">

            <form action='${postUrl}' method='POST' id="loginForm" name="loginForm" class="form-horizontal">
                <div class="form-group">
                    <label for="username" class="col-md-4 control-label">
                        <g:message code="trackr.email" default="Email"/>
                    </label>

                    <div class="col-md-8">
                        <input name="j_username" id="username" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="password" class="col-md-4 control-label">
                        <g:message code="trackr.password" default="Mot de passe"/>
                    </label>

                    <div class="col-md-8">
                        <input type="password" name="j_password" id="password" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-8 col-md-offset-4">
                        <input type='checkbox' class='chk' name='${rememberMeParameter}'
                               id='remember_me' checked='checked'/>
                        <label for='remember_me'>
                            <g:message code="trackr.rememberMe" default="Rester connecté"/>
                        </label>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-8 col-md-offset-4">
                        <button type="submit" class="btn btn-primary">
                            <g:message code="trackr.login" default="Se connecter"/>
                        </button>
                        <g:link controller="register" action="index" class="btn btn-success pull-right">
                            <g:message code="trackr.createAccount"/>
                        </g:link>
                        <br/>
                        <br/>
                        <g:link controller='register' action='forgotPassword' class="paddingleft10">
                            <g:message code="trackr.forgotPassword" default="Mot de passe oublié ?"/>
                        </g:link>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#username').focus();
    });

    <s2ui:initCheckboxes/>

</script>

</body>
</html>
