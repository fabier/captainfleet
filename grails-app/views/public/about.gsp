<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - A propos</title>
</head>

<body>

<div class="container">
    <g:render template="/templates/flashMessage"/>
    <div class="row">
        <div class="col-md-8 center-block">
            <div class="alert alert-info">
                <p>
                    <span class="text-large">Découvrez <strong><span class="captain-fleet">CaptainFleet</span> !
                    </strong></span>
                </p>
            </div>

            <p>
                <span class="captain-fleet">CaptainFleet</span> est basé sur 3 idées principales :
            </p>

            <p>
            <ul>
                <li>suivre des objets doit être <strong>simple.</strong></li>
                <li>suivre des objets doit être <strong>sans maintenance.</strong></li>
                <li>suivre des objets doit être <strong>économique.</strong></li>
            </ul>
        </p>

            <p>
                Pour celà, <span
                    class="captain-fleet">CaptainFleet</span> a concu et réalisé un système électronique innovant, utilisant des <b>technologies de pointe</b> de l'<b>internet des objets</b> et ne nécessitant <b>pas de batterie</b>.
            </p>

            <br/>

            <div class="alert alert-info">
                <p>
                    <span class="text-large">Un système <strong>fiable, simple et économique</strong>.</span>
                </p>
            </div>

            <p>
            <ul>
                <li>
                    Un boitier qu'on peut installer <b>partout</b>.
                </li>
                <li>
                    Sans carte SIM, sans réseau GSM, sans SMS/MMS, <b>sans batterie</b>.
                </li>
                <li>
                    Une interface web <b>facile à utiliser</b>, que vous pouvez utiliser <b>sans formation !</b>
                </li>
                <li>
                    Des prix clairs, sans surprise, les moins chers du marché.
                    <g:link controller="public" action="plans">
                        <i class="glyphicon glyphicon-arrow-right"></i>
                        Voir les tarifs
                    </g:link>
                </li>
            </ul>
        </p>
        </div>
    </div>
</div>
</div>
</div>
</body>
</html>