<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="map"/>
    <title>CaptainFleet - Accueil</title>
    <script type="application/javascript">
        var map; // global so we can access it later
        $(function () {
            map = initMap('map');
            selectLayer(map, "Stamen Watercolor");
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:each in="${mapMarkerLayer?.points}" var="point">
            addPoint(map, ${point.getCoordinate().getOrdinate(0)}, ${point.getCoordinate().getOrdinate(1)},
                    "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:each>
            </g:each>
            <g:if test="${mapOptions.boundingBox}">
            zoomToExtent(map, ${mapOptions.boundingBox.getMinX()}, ${mapOptions.boundingBox.getMinY()},
                    ${mapOptions.boundingBox.getMaxX()}, ${mapOptions.boundingBox.getMaxY()});
            var zoomLevel = map.getView().getZoom() / 3;
            map.getView().setZoom(zoomLevel);
            </g:if>
            </g:if>
        });
    </script>
</head>

<body>
<!-- Begin page content -->

<div class="container-fluid">
    <div class="row-fluid">
        <div id="map" class="map"></div>
    </div>
</div>

<div class="homepage-header-left">
    <div class="homepage-header-right">
        <div class="container">
            <div class="row">
                <div class="jumbotron header-background nomargin-left-right">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-8">
                                <h1 class="captain-fleet">CaptainFleet</h1>

                                <p class="small">
                                    <span class="captain-fleet">CaptainFleet</span> est un outil de gestion de flotte, il vous permet de savoir où sont vos véhicules, vos bateaux, vos engins agricoles.
                                </p>
                                <g:link controller="public" action="about" class="btn btn-link">
                                    <i class="glyphicon glyphicon-arrow-right"></i>
                                    <g:message code="trackr.discoverMore"/>
                                </g:link>
                                <g:link controller="register" action="index" class="btn btn-success pull-right">
                                    <g:message code="trackr.createAccount"/>
                                </g:link>
                            </div>

                            <div class="col-md-4">
                                <form action='${postUrl}' method='POST' id="loginForm" name="loginForm"
                                      class="form-horizontal">
                                    <div class="form-group">
                                        <label for="username" class="col-md-4 control-label">
                                            <g:message code="trackr.email" default="Email"/>
                                        </label>

                                        <div class="col-md-8">
                                            <input name="j_username" id="username" class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="password" class="col-md-4 control-label">
                                            <g:message code="trackr.password" default="Mot de passe"/>
                                        </label>

                                        <div class="col-md-8">
                                            <input type="password" name="j_password" id="password"
                                                   class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-8 col-md-offset-4">
                                            <input type='checkbox' class='chk' name='${rememberMeParameter}'
                                                   id='remember_me' checked='checked'/>
                                            <label for='remember_me'>
                                                <g:message code="trackr.rememberMe" default="Rester connecté"/>
                                            </label>
                                        </div>

                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-8 col-md-offset-4">
                                            <button type="submit" class="btn btn-primary">
                                                <g:message code="trackr.login" default="Se connecter"/>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
