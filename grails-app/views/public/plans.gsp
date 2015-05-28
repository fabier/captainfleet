<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Plans</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container">
    <div class="pricing-tables attached">
        <div class="row">
            <div class="col-sm-4 col-md-4">

                <div class="plan first">

                    <div class="head">
                        <h2>Starter</h2>
                        Idéal pour découvrir CaptainFleet !
                    </div>

                    <ul class="item-list">
                        <li><span class="label label-primary text-large"><strong>3</strong> Boitiers</span></li>
                        <li><strong>1</strong> Utilisateur</li>
                        <li><strong>1</strong> Zone d'alerte</li>
                        <li>Statistiques mensuelles et annuelles</li>
                        <li>Alerte par mail</li>
                        <li>Support par mail</li>
                        <li>Engagement minimum 1 an</li>
                    </ul>

                    <div class="price">
                        <h3>29<span class="symbol">€</span></h3>
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
                        <h2>Basic</h2>
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
                        <h2>Pro</h2>
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
</body>
</html>