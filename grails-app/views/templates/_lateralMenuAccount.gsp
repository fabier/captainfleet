<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation" class="${controllerName == "account" && actionName == 'index' ? "active" : ""}">
        <g:link controller="account" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-user"></i>
            Mon compte
        </g:link>
    </li>
    <li role="presentation" class="${controllerName == "account" && actionName == 'devices' ? "active" : ""}">
        <g:link controller="account" action="devices" class="lateral-menu">
            <i class="glyphicon glyphicon-th-large"></i>
            Boitiers
        </g:link>
    </li>
    <g:if test="${device}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-arrow-right"></i>
                        ${device.name ?: "Boitier ${device.id}"}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
    <li role="presentation" class="${controllerName == "alert" && actionName == "index" ? "active" : ""}">
        <g:link controller="alert" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-bell"></i>
            Alertes
        </g:link>
    </li>
    <g:if test="${alert}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-arrow-right"></i>
                        ${alert.name ?: "Alerte ${alert.id}"}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
    <li role="presentation" class="${actionName == 'preferences' ? "active" : ""}">
        <g:link controller="account" action="preferences" class="lateral-menu">
            <i class="glyphicon glyphicon-cog"></i>
            Préferences
        </g:link>
    </li>
</ul>