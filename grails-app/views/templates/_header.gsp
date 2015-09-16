<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <g:link uri="/" class="navbar-brand danger">
                <span>
                    <g:img dir="images" file="favicons/favicon-32x32.png" class="logo-top-frontpage"/>
                    CaptainFleet
                </span>
            </g:link>
        </div>

        <g:if test="${backofficeAdminPage}">
            <div class="nav navbar-nav">
                <div class="admin-header-nav">
                    <span class="badge bg-danger admin-header">Administration</span>
                </div>
            </div>
        </g:if>

        <div id="navbar" class="collapse navbar-collapse">
            <sec:ifLoggedIn>
                <ul class="nav navbar-nav">
                    <li class="${controllerName in ["dashboard"] ? "active" : ""}">
                        <g:link controller="dashboard" action="index">
                            <i class="glyphicon glyphicon-dashboard"></i>
                            &nbsp;Tableau de bord
                        </g:link>
                    </li>
                    <li class="${controllerName in ["common"]\
                              || controllerName in ["device", "frame"] && actionName in ["map"] ? "active" : ""}">
                        <g:link controller="common" action="index" class="lateral-menu">
                            <i class="glyphicon glyphicon-map-marker"></i>
                            &nbsp;Carte
                        </g:link>
                    </li>
                    <li class="${controllerName in ["device"] && actionName == "index" ? "active" : ""}">
                        <g:link controller="device" action="index" class="lateral-menu">
                            <i class="glyphicon glyphicon-th-large"></i>
                            &nbsp;Boitiers
                        </g:link>
                    </li>
                    <li class="${controllerName in ["zone"] ? "active" : ""}">
                        <g:link controller="zone" action="index">
                            <i class="glyphicon glyphicon-bell"></i>
                            &nbsp;Zones
                        </g:link>
                    </li>
                </ul>
            </sec:ifLoggedIn>

            <ul class="nav navbar-nav navbar-right">
                <sec:ifLoggedIn>
                    <li class="${controllerName in ["account"] && actionName == "index" ? "active" : ""}">
                        <g:link controller="account">
                            <i class="glyphicon glyphicon-user"></i>
                            &nbsp;Mon Compte
                        </g:link>
                    </li>
                    <li>
                        <g:link controller="logout" action="index" title="Se déconnecter">
                            <i class="glyphicon glyphicon-log-out"></i>
                            &nbsp;Déconnexion
                        </g:link>
                    </li>
                </sec:ifLoggedIn>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>