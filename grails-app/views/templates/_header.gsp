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
            <g:link uri="/" class="navbar-brand">Tracklala</g:link>
        </div>

        <div id="navbar" class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li class="${controllerName == 'public' && actionName == "about" ? "active" : ""}">
                    <g:link controller="public" action="about">
                        A propos
                    </g:link>
                </li>
                <li class="${controllerName == 'public' && actionName == "contact" ? "active" : ""}">
                    <g:link controller="public" action="contact">
                        Contact
                    </g:link>
                </li>
                <sec:ifLoggedIn>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                           aria-expanded="false">Mon compte <span class="caret"></span></a>
                        <ul class="dropdown-menu" role="menu">
                            <li>
                                <g:link controller="account">
                                    Mon Compte
                                </g:link>
                            </li>
                            <sec:ifAllGranted roles="ROLE_ADMIN">
                                %{--<li class="divider"></li>--}%
                                <li>
                                    <g:link controller="admin">
                                        Admin
                                    </g:link>
                                </li>
                            </sec:ifAllGranted>
                            <li>
                                <g:link controller="logout" action="index">
                                    Logout
                                </g:link>
                            </li>
                        </ul>
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