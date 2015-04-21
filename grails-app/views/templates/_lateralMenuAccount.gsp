<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation" class="${actionName == 'index' ? "active" : ""}">
        <g:link action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-user"></i>
            Mon compte
        </g:link>
    </li>
    <li role="presentation" class="${actionName == 'devices' ? "active" : ""}">
        <g:link action="devices" class="lateral-menu">
            <i class="glyphicon glyphicon-open"></i>
            Terminaux
        </g:link>
    </li>
    <li role="presentation" class="${actionName == 'alerts' ? "active" : ""}">
        <g:link action="alerts" class="lateral-menu">
            <i class="glyphicon glyphicon-bell"></i>
            Alertes
        </g:link>
    </li>
    <li role="presentation" class="${actionName == 'preferences' ? "active" : ""}">
        <g:link action="preferences" class="lateral-menu">
            <i class="glyphicon glyphicon-cog"></i>
            Préferences
        </g:link>
    </li>
</ul>