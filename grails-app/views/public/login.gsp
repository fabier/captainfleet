<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="map"/>
    <title>CaptainFleet - Accueil</title>
    <script type="application/javascript">
        $(function () {
            map = initMap('map');
            selectLayer(map, "Stamen Watercolor");
            <g:if test="${mapOptions}">
            <g:each in="${mapOptions.mapMarkerLayers}" var="mapMarkerLayer">
            <g:each in="${mapMarkerLayer?.points}" var="point">
            addPoint(map, ${point.getCoordinate().getOrdinate(0)}, ${point.getCoordinate().getOrdinate(1)},
                    "${mapMarkerLayer.mapMarkerStyle.path}");
            </g:each>
            </g:each>
            <g:if test="${mapOptions.boundingBox}">
            zoomToExtent(map, ${mapOptions.boundingBox.getMinX()}, ${mapOptions.boundingBox.getMinY()},
                    ${mapOptions.boundingBox.getMaxX()}, ${mapOptions.boundingBox.getMaxY()});
            var zoomLevel = Math.max(4, map.getView().getZoom() / 2);
            map.getView().setZoom(4);
            </g:if>
            </g:if>
        });
    </script>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid with-map">
    <div class="row-fluid">
        <div id="map" class="map"></div>
    </div>
</div>

<div class="homepage-header-left">
    <div class="homepage-header-right">
        <div class="container">
            <div class="row">
                <div class="col-md-6 center-block">
                    <div class="jumbotron header-background nomargin-left-right">
                        <div class="container">
                            <div class="row">
                                <form action='${postUrl}' method='POST' id="loginForm" name="loginForm"
                                      class="form-horizontal">
                                    <div class="form-group">
                                        <label for="username" class="col-md-4 control-label">
                                            <g:message code="captainfleet.email" default="Email"/>
                                        </label>

                                        <div class="col-md-8">
                                            <input name="j_username" id="username" class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="password" class="col-md-4 control-label">
                                            <g:message code="captainfleet.password" default="Mot de passe"/>
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
                                                <g:message code="captainfleet.rememberMe" default="Rester connecté"/>
                                            </label>
                                        </div>

                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-8 col-md-offset-4">
                                            <button type="submit" class="btn btn-primary">
                                                <g:message code="captainfleet.login" default="Se connecter"/>
                                            </button>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-md-8 col-md-offset-4">
                                            <span class="text-small">Vous n'avez pas encore de compte ?</span>
                                            <br/>
                                            <span class="text-small">
                                                <g:link controller="register" action="index">
                                                    Cliquez ici pour créer un compte.
                                                </g:link>
                                            </span>
                                        </div>

                                        %{--<div>--}%
                                        %{--<g:link controller="register" action="index" class="btn btn-success">--}%
                                        %{--<g:message code="captainfleet.createAccount"/>--}%
                                        %{--</g:link>--}%
                                        %{--</div>--}%
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
