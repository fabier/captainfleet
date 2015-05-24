<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Credits</title>
</head>

<body>

<div class="container">
    <g:render template="/templates/flashMessage"/>
    <div class="row">
        <div class="col-md-8 center-block">
            <div class="jumbotron">
                <p>
                    <strong>Crédits</strong>
                    <br/>
                    <small>et mentions légales</small>
                </p>
            </div>

            <p>
                <strong>
                    Toutes les technologies employées dans
                    <span class="captain-fleet">CaptainFleet</span>
                    sont des standards massivement utilisés dans l'industrie.
                </strong>
            </p>

            <p>
                Ces technologies permettent à
                <span class="captain-fleet">CaptainFleet</span>
                d'être stable, simple à faire évoluer et d'être riche en fonctionnalités.
            </p>

            <br/>
            <br/>

            <ul class="list-unstyled">
                <li>
                    <strong>Icones :</strong>
                    <g:link url="https://mapicons.mapsmarker.com" target="_blank">
                        <img src="https://mapicons.mapsmarker.com/wp-content/uploads/2011/03/miclogo-88x31.gif"
                             class="pull-right"/>
                    </g:link>
                    Maps Icons Collection
                    <br/>
                    <g:link url="https://mapicons.mapsmarker.com" target="_blank">
                        https://mapicons.mapsmarker.com
                    </g:link>
                </li>
                <li>&nbsp;</li>
                <li>
                    <strong>Fond de carte :</strong>
                    <g:link url="http://www.openstreetmap.org" target="_blank">
                        © OpenStreetMap contributors
                    </g:link>
                    <br/>
                    <g:link url="http://www.openstreetmap.org/copyright" target="_blank">
                        Données disponible sous la licence Open Database Licence
                    </g:link>
                </li>
                <li>&nbsp;</li>
                <li>
                    <p>Tiles Courtesy of <a href="http://www.mapquest.com/" target="_blank">MapQuest</a> <img
                            src="http://developer.mapquest.com/content/osm/mq_logo.png"></p>
                </li>
                <li>&nbsp;</li>
                <li>
                    <p>
                        <strong>Grails 2.4.5</strong>
                        <br/>
                        <a href="http://www.grails.org" target="_blank">
                            http://www.grails.org
                        </a>
                    </p>
                </li>
                <li>&nbsp;</li>
                <li>
                    <p>
                        <strong>PostgreSQL 9.4 avec PostGis</strong>
                        <br/>
                        <a href="http://www.postgresql.com" target="_blank">
                            http://www.postgresql.com
                        </a>
                        <br/>
                        <a href="http://www.postgis.org" target="_blank">
                            http://www.postgis.org
                        </a>
                    </p>
                </li>
                <li>&nbsp;</li>
                <li>
                    <p>
                        <strong>Glassfish 4.1</strong>
                        <br/>
                        <a href="http://www.glassfish.org" target="_blank">
                            http://www.glassfish.org
                        </a>
                    </p>
                </li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>