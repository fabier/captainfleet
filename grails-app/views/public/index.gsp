<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="onepagedesign"/>
    <title>CaptainFleet - OnePageDesign</title>
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
            <a class="navbar-brand page-scroll" href="#page-top">CaptainFleet</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a class="page-scroll" href="#services">Fonctionnalités</a>
                </li>
                <li>
                    <a class="page-scroll" href="#portfolio">Technologies</a>
                </li>
                <li>
                    <a class="page-scroll" href="#pricing">Tarifs</a>
                </li>
                <li>
                    <a class="page-scroll" href="#contact">Contact</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<header>
    <div class="header-content">
        <div class="header-content-inner">
            <h1>CaptainFleet</h1>
            <hr>

            <p>
                Le tracking réinventé, tout simplement
                <br/>
                Vous avez besoin d'une solution pour suivre votre flotte de véhicules,
                vos bateaux, vos engins mécaniques, mais toutes les solutions du marché ont un point faible :
                prix, installation, maintenance, gestion d'énergie, couverture réseau, fonctionnement complexe.
                <br/>
                Nous avons pensé un système simple, sans configuration, sans maintenance et prêt à être utilisé.
            </p>

            <a href="#services" class="btn btn-primary btn-xl page-scroll margin-left-right-20">
                En savoir plus
            </a>

            <g:link controller="public" action="login" class="btn btn-success btn-xl margin-left-right-20">
                Se connecter
            </g:link>
        </div>
    </div>
</header>

<section id="services" class="padding-top-bottom-20">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2 class="section-heading">
                    Points clés
                </h2>
                <hr class="primary">
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-lg-4 col-md-4 text-center">
                <div class="service-box">
                    <i class="glyphicon glyphicon-heart wow bounceIn text-primary"></i>

                    <h3>Sans batterie</h3>

                    <ul class="text-muted">
                        <li>Pas de source d'énergie.</li>
                        <li>Peut être installé partout.</li>
                        <li>Durée de vie optimale.</li>
                    </ul>

                    <p class="text-muted">
                        Le système ne fonctionne qu'à l'énergie solaire, et pas besoin d'être en plein soleil, le système fonctionne même avec une couverture nuageuse totale.
                    </p>
                </div>
            </div>

            <div class="col-lg-4 col-md-4 text-center">
                <div class="service-box">
                    <i class="glyphicon glyphicon-map-marker wow bounceIn text-primary" data-wow-delay=".1s"></i>

                    <h3>Sans réseau 3G</h3>

                    <ul class="text-muted">
                        <li>Pas de carte SIM.</li>
                        <li>Pas d'abonnement télécom.</li>
                        <li>Pas de fonctionnement aléatoire.</li>
                    </ul>

                    <p class="text-muted">
                        Pas besoin d'avoir une couverture réseau pour que le système fonctionne. Le système peut dès maintenant fonctionner en France, Espagne, Belgique et très bientôt aux USA.
                    </p>
                </div>
            </div>

            <div class="col-lg-4 col-md-4 text-center">
                <div class="service-box">
                    <i class="glyphicon glyphicon-tag wow bounceIn text-primary" data-wow-delay=".2s"></i>

                    <h3>Prix bas</h3>

                    <ul class="text-muted">
                        <li>Un abonnement mensuel attractif.</li>
                        <li>Pas de maintenance.</li>
                        <li>Pas de frais annexes.</li>
                    </ul>

                    <p class="text-muted">
                        Le tracking est simple, donc abordable, et c'est sa force. Ce produit est idéal pour les petites entreprises qui souhaitent faire du tracking sans avoir à y laisser du temps et de l'argent.
                    </p>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="portfolio" class="padding-top-bottom-20">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 text-center">
                <h2 class="section-heading">
                    Comment ca fonctionne ?
                </h2>
                <hr class="primary">
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row no-gutter">
            <div class="col-lg-6 col-md-6">
                <div class="portfolio-box">
                    <g:img src="portfolio/desk.jpg" class="img-responsive" alt=""/>
                </div>
            </div>

            <div class="col-lg-6 col-md-6">
                <div class="service-box">
                    <h3 class="text-center">Un système pensé pour être simple</h3>

                    <p class="text-muted">
                        Le maitre mot de CaptainFleet c'est la simplicité. A chaque étape de la conception, nous avons pensé à mettre en oeuvre des technologies éprouvées, simples et robustes. Le produit est fiable, résistant, et élimine le plus de contraintes possible pour que sa mise en place soit un jeu d'enfant.
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row no-gutter">
            <div class="col-lg-6 col-sm-6">
                <div class="service-box">
                    <h3 class="text-center">Des technologies à la pointe</h3>

                    <ul class="text-muted">
                        <li>Système GPS.</li>
                        <li>Capteur photovoltaïque.</li>
                        <li>Sans batterie, sans pile.</li>
                        <li>Système radio à faible energie.</li>
                        <li>Boitier étanche IP 67.</li>
                        <li>Boitier petit : taille d'un paquet de cigarette.</li>
                    </ul>
                </div>
            </div>

            <div class="col-lg-6 col-sm-6">
                <div class="portfolio-box">
                    <g:img src="portfolio/sigfox.jpg" class="img-responsive" alt=""/>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row no-gutter">
            <div class="col-lg-6 col-md-6">
                <div class="portfolio-box">
                    <g:img src="portfolio/map.jpg" class="img-responsive" alt=""/>
                </div>
            </div>

            <div class="col-lg-6 col-md-6">
                <div class="service-box">
                    <h3 class="text-center">Une interface de suivi moderne</h3>

                    <p class="text-muted">
                        Pour suivre votre flotte, nous mettons à votre disposition un site web qui permet de suivre le déplacement des boîtiers.
                        L'interface permet de mettre en place des systèmes d'alerte par mail lorsqu'un boitier sort d'une zone définie, ou lorsque le système arrête de communiquer avec nos serveurs.
                        D'autres fonctionnalités sont en cours de développement, contactez nous pour en savoir plus.
                    </p>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="row no-gutter">
            <div class="col-lg-6 col-sm-6">
                <div class="service-box">
                    <h3 class="text-center">Fonctionnalités</h3>

                    <ul class="text-muted">
                        <li>Accès à une carte pour voir les objets suivis.</li>
                        <li>Affichage par date ou par boitier.</li>
                        <li>Gestion des alertes par mail : entrée/sortie d'une zone géographique, communication rompue, etc.</li>
                        <li>Récapitulatif hebdomadaire et mensuel.</li>
                    </ul>
                </div>
            </div>

            <div class="col-lg-6 col-sm-6">
                <div class="portfolio-box">
                    <g:img src="portfolio/alerts.jpg" class="img-responsive" alt=""/>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="pricing" class="padding-top-bottom-20">
    <div class="container">
        <div class="pricing-tables attached">
            <div class="row">
                <div class="col-sm-4 col-md-4">

                    <div class="plan first">

                        <div class="head">
                            <h2 class="white">Starter</h2>
                            Idéal pour découvrir CaptainFleet !
                        </div>

                        <ul class="item-list black">
                            <li><span class="label label-primary text-large"><strong>3</strong> Boitiers</span></li>
                            <li><strong>1</strong> Utilisateur</li>
                            <li><strong>1</strong> Zone d'alerte</li>
                            <li>Statistiques mensuelles et annuelles</li>
                            <li>Alerte par mail</li>
                            <li>Support par mail</li>
                            <li>Engagement minimum 1 an</li>
                        </ul>

                        <div class="price">
                            <h3>39<span class="symbol">€</span></h3>
                            <h4>
                                par mois
                                <br/>
                                + 100€ à l'achat
                            </h4>
                        </div>

                        <button type="button" class="btn btn-success">Commander !</button>
                    </div>
                </div>

                <div class="col-sm-4 col-md-4 ">

                    <div class="plan recommended">

                        <div class="head">
                            <h2 class="white">Basic</h2>
                            Le choix préféré des PME/TPE
                        </div>

                        <ul class="item-list">
                            <li><span class="label label-primary text-large"><strong>10</strong> Boitiers</span></li>
                            <li><strong>3</strong> Utilisateurs</li>
                            <li><strong>5</strong> Zones d'alerte</li>
                            <li>Statistiques mensuelles et annuelles</li>
                            <li>Alerte par mail</li>
                            <li>Support par mail</li>
                            <li>Engagement minimum 1 an</li>
                        </ul>

                        <div class="price">
                            <h3>99<span class="symbol">€</span></h3>
                            <h4>
                                par mois
                                <br/>
                                + 200€ à l'achat
                            </h4>
                        </div>

                        <button type="button" class="btn btn-success">Commander !</button>
                    </div>
                </div>


                <div class="col-sm-4 col-md-4">
                    <div class="plan last">

                        <div class="head">
                            <h2 class="white">Pro</h2>
                            Notre offre grandes entreprises
                        </div>

                        <ul class="item-list">
                            <li><span class="label label-primary text-large"><strong>50</strong> Boitiers</span></li>
                            <li>Utilisateurs <strong>illimités</strong></li>
                            <li>Zones d'alerte <strong>illimités</strong></li>
                            <li>Statistiques hebdomadaires, mensuelles et annuelles</li>
                            <li>Alerte par mail et SMS</li>
                            <li>Support par mail et téléphone</li>
                            <li>Engagement minimum 1 an</li>
                        </ul>

                        <div class="price">
                            <h3>399<span class="symbol">€</span></h3>
                            <h4>
                                par mois
                                <br/>
                                + 1000€ à l'achat
                            </h4>
                        </div>

                        <button type="button" class="btn btn-success">Commander !</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<section id="contact" class="bg-dark forest">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2 text-center">
                <h1 class="section-heading">Intéressé ?</h1>
                <hr class="light">

                <p class="text-faded">
                    Vous souhaitez en savoir plus ?
                    <br/>
                    Laissez votre nom et votre email.
                    <br/>
                    Nous prendrons contact avec vous.
                </p>
            </div>
        </div>

        <div class="row">
            <div class="text-center">
                <g:form controller="public" action="sendNameEmailMail" class="form-inline">
                    <div class="form-group">
                        <div class="col-md-4">
                            <input type="text" class="form-control" id="name" name="name"
                                   placeholder="Nom" value=""/>
                        </div>

                        <div class="col-md-4">
                            <input type="email" class="form-control" id="email" name="email"
                                   placeholder="Email" value=""/>
                        </div>

                        <div class="col-md-4">
                            <button type="submit" class="btn btn-primary">
                                Envoyer
                            </button>
                        </div>
                    </div>
                </g:form>
            </div>
        </div>
    </div>
</section>

<section id="contact" class="bg-dark text-center">
    <div class="container">
        <div class="row">
            <p>
                CaptainFleet&nbsp;<g:meta name="app.version"/>
                &nbsp;-&nbsp;
            &copy;&nbsp;2015
                <br/>
                <g:link controller="public" action="legal" class="white nohover">
                    Mentions légales
                </g:link>
                &nbsp;-&nbsp;
                <g:link controller="public" action="cgu" class="white nohover">
                    CGU
                </g:link>
                &nbsp;-&nbsp;
                <g:link controller="public" action="cgv" class="white nohover">
                    CGV
                </g:link>
                &nbsp;-&nbsp;
                <g:link controller="public" action="contact" class="white nohover">
                    Contact
                </g:link>
                &nbsp;-&nbsp;
                <g:link controller="public" action="credits" class="white nohover">
                    Credits
                </g:link>
                <sec:ifAllGranted roles="ROLE_ADMIN">
                    &nbsp;-&nbsp;
                    <g:link controller="admin" class="white nohover">
                        Administration
                    </g:link>
                </sec:ifAllGranted>
            </p>
        </div>
    </div>
</section>

</body>

</html>
