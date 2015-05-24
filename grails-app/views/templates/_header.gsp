<!-- Fixed navbar -->
<nav class="navbar navbar-default navbar-fixed-top ${backofficeAdminPage ? "bg-danger" : ""}">
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
                CaptainFleet${backofficeAdminPage ? " Administration" : ""}
            </g:link>
        </div>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
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
                <sec:ifLoggedIn>
                    <li class="${controllerName in ["account", "alert"] ? "active" : ""}">
                        <g:link controller="account">
                            Mon Compte
                        </g:link>
                    </li>
                    <li>
                        <g:link controller="logout" action="index" title="Logout">
                            <i class="glyphicon glyphicon-log-out"></i>
                        </g:link>
                    </li>
                </sec:ifLoggedIn>
                <sec:ifNotLoggedIn>
                    <li class="${controllerName in ['login', 'register'] ? "active" : ""}">
                        <g:link controller="common">
                            Connexion
                        </g:link>
                    </li>
                </sec:ifNotLoggedIn>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>