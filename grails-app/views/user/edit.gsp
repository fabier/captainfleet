<html>
<head>
    <meta name='layout' content='admin'/>
    <title>CaptainFleet - Utilisateur</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <div class="row">
                <div class="alert alert-danger">
                    Attention, éditer un utilisateur est dangereux, soyez attentifs aux modifications apportées.
                </div>

                <div class="col-md-6">
                    <g:form action="update" id="${user?.id}" name='userEditForm'
                            class="button-style form-horizontal">
                        <g:hiddenField name="id" value="${user?.id}"/>
                        <g:hiddenField name="version" value="${user?.version}"/>

                        <div class="form-group">
                            <label for="username" class="col-md-2 control-label">Nom</label>

                            <div class="col-md-10">
                                <input class="form-control" id="username" name="username" placeholder="Username"
                                       value="${user?.username ?: ""}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="email" class="col-md-2 control-label">Email</label>

                            <div class="col-md-10">
                                <input class="form-control" id="email" name="email" placeholder="email"
                                       value="${user?.email ?: ""}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="password" class="col-md-2 control-label">Mot de passe</label>

                            <div class="col-md-10">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="Password"
                                       value="${user?.password ?: ""}"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-md-10 col-md-offset-2">
                                <g:checkBox name="enabled" value="${user?.enabled}"/>
                                <label for="enabled">Actif</label>
                            </div>
                        </div>

                        <g:each var="entry" in="${roleMap}">
                            <div class="form-group">
                                <div class="col-md-10 col-md-offset-2">
                                    <g:checkBox name="${entry.key.authority}" value="${entry.value}"/>
                                    <label for="${entry.key.authority}">${entry.key.authority.encodeAsHTML()}</label>
                                </div>
                            </div>
                        </g:each>

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

%{--<g:if test='${canRunAs}'>--}%
%{--<form name='runAsForm' action='${request.contextPath}/j_spring_security_switch_user' method='POST'>--}%
%{--<g:hiddenField name='j_username' value="${user.username}"/>--}%
%{--<input type='submit' class='s2ui_hidden_button'/>--}%
%{--</form>--}%
%{--</g:if>--}%

</body>
</html>
