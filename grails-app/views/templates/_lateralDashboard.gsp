<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation"
        class="${controllerName == 'dashboard' && actionName == 'devices' ? "active" : ""}">
        <g:link controller="dashboard" action="devices" class="lateral-menu">
            <i class="glyphicon glyphicon-th-large"></i>
            Boitiers
        </g:link>
    </li>
    <li role="presentation"
        class="${controllerName == 'dashboard' && actionName == 'alerts' ? "active" : ""}">
        <g:link controller="dashboard" action="alerts" class="lateral-menu">
            <i class="glyphicon glyphicon-bell"></i>
            Alertes
        </g:link>
    </li>
</ul>