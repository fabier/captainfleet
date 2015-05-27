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
                            Accueil
                        </g:link>
                    </li>
                    <li class="${controllerName == 'public' && actionName == "about" ? "active" : ""}">
                        <g:link controller="public" action="about">
                            Présentation
                        </g:link>
                    </li>
                    <li class="${controllerName == 'public' && actionName == "plans" ? "active" : ""}">
                        <g:link controller="public" action="plans">
                            Tarifs
                        </g:link>
                    </li>
                </sec:ifNotLoggedIn>
                <sec:ifLoggedIn>
                    <li class="${controllerName in ["account", "alert"] ? "active" : ""}">
                        <g:link controller="account">
                            <i class="glyphicon glyphicon-user"></i>
                            Mon Compte
                        </g:link>
                    </li>
                    <li>
                        <g:link controller="logout" action="index" title="Se déconnecter">
                            <i class="glyphicon glyphicon-log-out"></i>
                            Déconnexion
                        </g:link>
                    </li>
                </sec:ifLoggedIn>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>