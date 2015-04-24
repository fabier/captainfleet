<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Credits</title>
</head>

<body>

<div class="container">
    <g:render template="/templates/flashMessage"/>
    <div class=" row">
        <div class="col-sm-6 center-block">
            <div class="alert alert-info">
                <p>
                    Crédits et mentions légales
                </p>
            </div>

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
                    © OpenStreetMap contributors
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
            </ul>
        </div>
    </div>
</div>
</body>
</html>