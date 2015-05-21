<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation" class="${controllerName == 'user' && user == null ? "active" : ""}">
        <g:link controller="user" action="userSearch" class="lateral-menu">
            <i class="glyphicon glyphicon-user"></i>
            Utilisateurs
        </g:link>
    </li>
    <g:if test="${user}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-arrow-right"></i>
                        ${user.username}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
    <li role="presentation" class="${controllerName == 'role' && role == null ? "active" : ""}">
        <g:link controller="role" action="roleSearch" class="lateral-menu">
            <i class="glyphicon glyphicon-check"></i>
            Roles
        </g:link>
    </li>
    <g:if test="${role}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-arrow-right"></i>
                        ${role.authority}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
    <li role="presentation"
        class="${controllerName == 'adminDevice' && actionName == 'search' ? "active" : ""}">
        <g:link controller="adminDevice" action="search" class="lateral-menu">
            <i class="glyphicon glyphicon-open"></i>
            Terminaux
        </g:link>
    </li>
    <g:if test="${device}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-arrow-right"></i>
                        ${device.sigfoxId}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
    <li role="presentation" class="${controllerName == 'adminStation' && actionName == 'search' ? "active" : ""}">
        <g:link controller="adminStation" action="search" class="lateral-menu">
            <i class="glyphicon glyphicon-cloud-upload"></i>
            Stations
        </g:link>
    </li>
    <g:if test="${station}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-arrow-right"></i>
                        ${station.sigfoxId}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
</ul>