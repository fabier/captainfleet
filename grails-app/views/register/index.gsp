<html>

<head>
    <meta name='layout' content='main'/>
    <title>CaptainFleet - Créer un compte</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container">
    <div class="row">
        <div class="col-md-8 center-block">
            <g:if test='${emailSent}'>
                <div class="alert alert-info">
                    <g:message code='captainfleet.emailSent'/>
                </div>
                <g:link uri="/" class="btn btn-primary">
                    Revenir à la page d'accueil
                </g:link>
            </g:if>
            <g:else>
                <div class="alert alert-info">
                    Remplissez le formulaire, puis cliquez sur "Créer un compte".
                </div>

                <div class="panel panel-default">
                    <div class="panel-body">
                        <g:form action='register' name='registerForm' role="form" class="form-horizontal">
                            <div class="form-group ${hasErrors(bean: command, field: "username") {
                                "has-error has-feedback"
                            }}">
                                <label for="username" class="col-md-4 control-label">
                                    <g:message code="captainfleet.username" default="Nom"/>
                                </label>

                                <div class="col-md-6">
                                    <input type="text" class="form-control" id="username" name="username"
                                           placeholder="Nom Prénom" value="${command.username}"/>
                                    <g:hasErrors bean="${command}" field="username">
                                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                    </g:hasErrors>
                                </div>

                                <g:hasErrors bean="${command}" field="username">
                                    <div class="row">
                                        <span class="col-md-offset-4 text-danger">
                                            Vous devez spécifier un nom d'utilisateur
                                        </span>
                                    </div>
                                </g:hasErrors>
                            </div>

                            <div class="form-group ${hasErrors(bean: command, field: "email") {
                                "has-error has-feedback"
                            }}">
                                <label for="email" class="col-md-4 control-label">
                                    <g:message code="captainfleet.email" default="E-mail"/>
                                </label>

                                <div class="col-md-6">
                                    <input type="email" class="form-control" id="email" name="email"
                                           placeholder="Votre adresse email" value="${command.email}"/>
                                    <g:hasErrors bean="${command}" field="email">
                                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                    </g:hasErrors>
                                </div>

                                <g:hasErrors bean="${command}" field="email">
                                    <div class="row">
                                        <span class="col-md-offset-4 text-danger">
                                            Saisissez un email valide
                                        </span>
                                    </div>
                                </g:hasErrors>
                            </div>

                            <div class="form-group ${hasErrors(bean: command, field: "password") {
                                "has-error has-feedback"
                            }}">
                                <label for="password" class="col-md-4 control-label">
                                    <g:message code="captainfleet.password" default="Mot de passe"/>
                                </label>

                                <div class="col-md-6">
                                    <input type="password" class="form-control" id="password" name="password"
                                           placeholder="Votre mot de passe" value="${command.password}"/>
                                    <g:hasErrors bean="${command}" field="password">
                                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                    </g:hasErrors>
                                </div>

                                <g:hasErrors bean="${command}" field="password">
                                    <div class="row">
                                        <span class="col-md-offset-4 text-danger">
                                            Saisissez un mot de passe (6 caractères min.)
                                        </span>
                                    </div>
                                </g:hasErrors>
                            </div>

                            <div class="form-group ${hasErrors(bean: command, field: "password2") {
                                "has-error has-feedback"
                            }}">
                                <label for="password2" class="col-md-4 control-label">
                                    <g:message code="captainfleet.password2" default="Mot de passe\n(répéter)"/>
                                </label>

                                <div class="col-md-6">
                                    <input type="password" class="form-control" id="password2" name="password2"
                                           placeholder="Répéter votre mot de passe" value="${command.password2}"/>
                                    <g:hasErrors bean="${command}" field="password2">
                                        <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                    </g:hasErrors>
                                </div>

                                <g:hasErrors bean="${command}" field="password2">
                                    <div class="row">
                                        <span class="col-md-offset-4 text-danger">
                                            Les mots de passe ne correspondent pas
                                        </span>
                                    </div>
                                </g:hasErrors>
                            </div>

                            <div class="form-group ${hasErrors(bean: command, field: "acceptsConditions") {
                                "has-error has-feedback"
                            }}">

                                <div class="col-md-8 col-md-offset-4 text-small">
                                    <label class="${hasErrors(bean: command, field: "acceptsConditions") {
                                        "form-control"
                                    }}">
                                        <g:checkBox name="acceptsConditions" value="${command.acceptsConditions}"/>
                                        J'ai lu et j'accepte les
                                        <g:link controller="public" action="cgu" target="_blank">
                                            conditions générales d'utilisation
                                        </g:link>
                                        <g:hasErrors bean="${command}" field="acceptsConditions">
                                            <span class="glyphicon glyphicon-remove form-control-feedback"></span>
                                        </g:hasErrors>
                                    </label>
                                </div>

                                <g:hasErrors bean="${command}" field="acceptsConditions">
                                    <div class="row">
                                        <span class="col-md-offset-4 text-danger">
                                            Merci de lire et d'accepter les conditions générales d'utilisation.
                                        </span>
                                    </div>
                                </g:hasErrors>
                            </div>

                            <div class="form-group">
                                <label for="password2" class="col-md-4 control-label">
                                </label>

                                <div class="col-md-8">
                                    <button type="submit" class="btn btn-primary">
                                        Créer un compte
                                    </button>
                                </div>
                            </div>
                        </g:form>
                    </div>
                </div>
            </g:else>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        $('#username').focus();
    });
</script>

</body>
</html>
