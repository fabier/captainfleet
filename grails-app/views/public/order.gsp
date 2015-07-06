<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="onepagedesign"/>
    <title>CaptainFleet</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        %{--<!-- Brand and toggle get grouped for better mobile display -->--}%
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <g:link action="index" fragment="pricing" class="navbar-brand page-scroll">
                CaptainFleet
            </g:link>
        </div>
    </div>
</nav>

<section id="contact" class="order bg-dark boat">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 text-center">
                <h1 class="section-heading">
                    Commander Pack ${params.packname}
                </h1>

                <p class="text-large">
                    Vous souhaitez commander le pack ${params.packname}.
                    <br/>
                    Merci de renseigner le formulaire suivant, un conseiller prendra contact avec vous.
                </p>
            </div>
        </div>

        <div class="row">
            <div class="col-md-8 center-block">
                <g:form controller="public" action="sendOrderMail" role="form" class="form-horizontal">
                    <div class="form-group">
                        <label for="name" class="col-md-4 control-label">Nom</label>

                        <div class="col-md-4">
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="Nom Prénom" value="${params.name ?: ""}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="email" class="col-md-4 control-label">Email</label>

                        <div class="col-md-4">
                            <input type="email" class="form-control" id="email" name="email"
                                   placeholder="Email" value="${params.email ?: ""}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="phonenumber" class="col-md-4 control-label">Téléphone</label>

                        <div class="col-md-4">
                            <input type="text" class="form-control" id="phonenumber" name="phonenumber"
                                   placeholder="Numéro de téléphone" value="${params.phonenumber ?: ""}"/>
                        </div>
                    </div>

                    <g:hiddenField name="packname" value="${params.packname}"/>

                    <div class="form-group">
                        <div class="col-md-4">
                        </div>

                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary btn-block">
                                Envoyer
                            </button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</section>

</body>

</html>
