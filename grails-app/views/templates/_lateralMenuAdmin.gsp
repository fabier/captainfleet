<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation" class="${controllerName == 'user' ? "active" : ""}">
        <g:link controller="user" action="userSearch" class="lateral-menu">
            <i class="glyphicon glyphicon-user"></i>
            Utilisateurs
        </g:link>
    </li>
    <li role="presentation" class="${controllerName == 'role' ? "active" : ""}">
        <g:link controller="role" action="roleSearch" class="lateral-menu">
            <i class="glyphicon glyphicon-check"></i>
            Roles
        </g:link>
    </li>
    <li role="presentation" class="${controllerName == 'adminDevice'||controllerName == 'adminFrame' ? "active" : ""}">
        <g:link controller="adminDevice" action="search" class="lateral-menu">
            <i class="glyphicon glyphicon-open"></i>
            Terminaux
        </g:link>
    </li>
    <li role="presentation" class="${controllerName == 'adminStation' ? "active" : ""}">
        <g:link controller="adminStation" action="search" class="lateral-menu">
            <i class="glyphicon glyphicon-cloud-upload"></i>
            Stations
        </g:link>
    </li>
</ul>