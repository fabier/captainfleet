<html>

<head>
    <g:set var="hideLoginBlock" value="${true}" scope="request"/>
    <meta name='layout' content='main'/>
    <title>CaptainFleet - Mot de passe perdu</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container">
    <div class="row">
        <div class="col-md-8 center-block">
            <g:if test='${emailSent}'>
                <div class="alert alert-info">
                    <g:message code='captainfleet.emailSent.forgotPassword'/>
                </div>
                <g:link uri="/" class="btn btn-primary">
                    Revenir à la page d'accueil
                </g:link>
            </g:if>
            <g:else>
                <div class="panel panel-default">
                    <div class="panel-body">
                        <g:form controller="register" action="forgotPassword" class="form-horizontal" role="form"
                                method="POST">
                            <div class="form-group">
                                <label for="username" class="col-md-4 control-label">
                                    <g:message code="captainfleet.email" default="Email"/>
                                </label>

                                <div class="col-md-4">
                                    <input name="username" id="username" size="20" class="form-control"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-md-4">
                                </div>

                                <div class="col-md-8">
                                    <button type="submit" class="btn btn-primary">
                                        Récupérer mon mot de passe
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
