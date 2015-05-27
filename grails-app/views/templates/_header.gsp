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
                CaptainFleet
                <g:if test="${backofficeAdminPage}">
                    <span class="badge bg-danger">Administration</span>
                </g:if>
            </g:link>
        </div>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <sec:ifNotLoggedIn>
                    <li class="${controllerName == 'public' && actionName == "index" ? "active" : ""}">
                        <g:link controller="public" action="index">
                            <i class="glyphicon glyphicon-home"></i>
                            &nbsp;Accueil
                        </g:link>
                    </li>
                    <li class="${controllerName == 'public' && actionName == "about" ? "active" : ""}">
                        <g:link controller="public" action="about">
                        %{--<i class="glyphicon glyphicon-blackboard"></i>--}%
                            <i class="glyphicon glyphicon-th-list"></i>
                            &nbsp;Présentation
                        </g:link>
                    </li>
                    <li class="${controllerName == 'public' && actionName == "plans" ? "active" : ""}">
                        <g:link controller="public" action="plans">
                        %{--<i class="glyphicon glyphicon-euro"></i>--}%
                            <i class="glyphicon glyphicon-credit-card"></i>
                            &nbsp;Tarifs
                        </g:link>
                    </li>
                </sec:ifNotLoggedIn>
                <sec:ifLoggedIn>
                    <li class="${controllerName in ["account"]&& actionName == "index"  ? "active" : ""}">
                        <g:link controller="account">
                            <i class="glyphicon glyphicon-user"></i>
                            &nbsp;Mon Compte
                        </g:link>
                    </li>
                    <li class="${controllerName in ["account"] && actionName == "devices" ? "active" : ""}">
                        <g:link controller="account" action="devices" class="lateral-menu">
                            <i class="glyphicon glyphicon-th-large"></i>
                            Boitiers
                        </g:link>
                    </li>
                    <li class="${controllerName in ["alert"] ? "active" : ""}">
                        <g:link controller="alert" action="index">
                            <i class="glyphicon glyphicon-bell"></i>
                            Alertes
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