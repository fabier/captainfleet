<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation" class="${controllerName == "account" && actionName == 'index' ? "active" : ""}">
        <g:link controller="account" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-user"></i>
            Mon compte
        </g:link>
    </li>
    <li role="presentation" class="${controllerName == "device" || actionName == 'devices' ? "active" : ""}">
        <g:link controller="account" action="devices" class="lateral-menu">
            <i class="glyphicon glyphicon-open"></i>
            Terminaux
        </g:link>
    </li>
    <li role="presentation" class="${controllerName == "alert" && actionName == 'index' ? "active" : ""}">
        <g:link controller="alert" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-bell"></i>
            Alertes
        </g:link>
    </li>
    <li role="presentation" class="${actionName == 'preferences' ? "active" : ""}">
        <g:link controller="account" action="preferences" class="lateral-menu">
            <i class="glyphicon glyphicon-cog"></i>
            Préferences
        </g:link>
    </li>
</ul>