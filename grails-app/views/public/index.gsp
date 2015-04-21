<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="map"/>
    <title>Accueil</title>
    <script type="application/javascript">
        $(function () {
            var map = initMap('map');
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:each in="${mapMarkerLayer.points}" var="point">
            addPoint(map, ${point.getDirectPosition().coordinate[0]}, ${point.getDirectPosition().coordinate[1]},
                    "${assetPath(src:mapMarkerLayer.mapMarkerStyle.path)}");
            </g:each>
            </g:each>
            zoomToExtent(map, ${mapOptions.boundingBox.getMinimum(0)}, ${mapOptions.boundingBox.getMinimum(1)},
                    ${mapOptions.boundingBox.getMaximum(0)}, ${mapOptions.boundingBox.getMaximum(1)});
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
                <div class="jumbotron header-background nomargin">
                    <div class="container">
                        <div class="row">
                            <div class="col-sm-8">
                                <h1>Tracklala</h1>

                                <p class="small">
                                    Tracklala est un outil de suivi, il vous permet de savoir où sont vos véhicules, vos bateaux, vos engins agricoles.
                                </p>
                                <g:link controller="register" action="index" class="btn btn-success">
                                    <g:message code="trackr.createAccount"/>
                                </g:link>
                            </div>

                            <div class="col-sm-4">
                                <form action='${postUrl}' method='POST' id="loginForm" name="loginForm"
                                      class="form-horizontal">
                                    <div class="form-group">
                                        <label for="username" class="col-sm-4 control-label">
                                            <g:message code="trackr.email" default="Email"/>
                                        </label>

                                        <div class="col-sm-8">
                                            <input name="j_username" id="username" class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="password" class="col-sm-4 control-label">
                                            <g:message code="trackr.password" default="Mot de passe"/>
                                        </label>

                                        <div class="col-sm-8">
                                            <input type="password" name="j_password" id="password"
                                                   class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-8 col-sm-offset-4">
                                            <input type='checkbox' class='chk' name='${rememberMeParameter}'
                                                   id='remember_me' checked='checked'/>
                                            <label for='remember_me'>
                                                <g:message code="trackr.rememberMe" default="Rester connecté"/>
                                            </label>
                                        </div>

                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-8 col-sm-offset-4">
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
