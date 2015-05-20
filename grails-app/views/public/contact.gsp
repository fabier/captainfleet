<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Contact</title>
</head>

<body>

<div class="container">
    <g:render template="/templates/flashMessage"/>
    <div class="row">
        <div class="col-md-8 center-block">
            <g:if test="${flash.message}">
                <g:link uri="/" class="btn btn-primary">
                    Revenir à la page d'accueil
                </g:link>
            </g:if>
            <g:else>
                <div class="jumbotron">
                    <p>
                        <strong>Envoyez un message avec le formulaire ci dessous.</strong>
                        <sec:ifNotLoggedIn>
                            <br/>
                            <small>Merci de renseigner votre adresse email.</small>
                        </sec:ifNotLoggedIn>
                    </p>
                </div>

                <g:form controller="public" action="sendMail" role="form" class="form-horizontal">
                    <div class="form-group">
                        <label for="email" class="col-md-2 control-label">Email</label>

                        <div class="col-md-6">
                            <sec:ifLoggedIn>
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="Votre adresse email"
                                       value="${sec.loggedInUserInfo(field: "username")}"
                                       disabled="disabled"/>
                            </sec:ifLoggedIn>
                            <sec:ifNotLoggedIn>
                                <input type="email" class="form-control" id="email" name="email"
                                       placeholder="Votre adresse email" value="${params.email ?: ""}"/>
                            </sec:ifNotLoggedIn>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="subject" class="col-md-2 control-label">Sujet</label>

                        <div class="col-md-10">
                            <input type="text" class="form-control" id="subject" name="subject"
                                   placeholder="Sujet" value="${params.subject ?: ""}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="message" class="col-md-2 control-label">Message</label>

                        <div class="col-md-10">
                            <textarea class="form-control" id="message" name="message" rows="5"
                                      placeholder="Taper ici votre message">${params.message ?: ""}</textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-4">
                        </div>

                        <div class="col-md-8">
                            <button type="submit" class="btn btn-primary">
                                Envoyer le message
                            </button>
                            &nbsp;
                            <button type="button" class="btn btn-link" onclick="history.go(-1)">
                                Annuler
                            </button>
                        </div>
                    </div>
                </g:form>
            </g:else>
        </div>
    </div>
</div>
</body>
</html>