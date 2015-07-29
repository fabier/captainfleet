<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation"
        class="${controllerName == 'dashboard' && actionName == 'devices' ? "active" : ""}">
        <g:link controller="dashboard" action="devices" class="lateral-menu">
            <i class="glyphicon glyphicon-th-large"></i>
            Boitiers
        </g:link>
    </li>
    <li role="presentation"
        class="${controllerName == 'dashboard' && actionName == 'zones' ? "active" : ""}">
        <g:link controller="dashboard" action="zones" class="lateral-menu">
            <i class="glyphicon glyphicon-bell"></i>
            Zones
        </g:link>
    </li>
</ul>