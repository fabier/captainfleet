<ul class="nav nav-pills nav-stacked lateral-menu">
    <li role="presentation" class="${controllerName == "device" && actionName == 'index' ? "active" : ""}">
        <g:link controller="device" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-th-large"></i>
            &nbsp;Boitiers
        </g:link>
    </li>
    <g:if test="${controllerName == "device" && device}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-th-large"></i>
                        &nbsp;${device.name ?: "Boitier ${device.id}"}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
    <li role="presentation" class="${controllerName == "alert" && actionName in ["index", "create"] ? "active" : ""}">
        <g:link controller="alert" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-bell"></i>
            &nbsp;Alertes
        </g:link>
    </li>
    <g:if test="${alert}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-bell"></i>
                        &nbsp;${alert.name ?: "Alerte ${alert.id}"}
                    </a>
                </li>
            </ul>
        </li>
    </g:if>
    <li role="presentation" class="${controllerName == "account" && actionName == 'index' ? "active" : ""}">
        <g:link controller="account" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-user"></i>
            &nbsp;Mon compte
        </g:link>
    </li>
    %{--<li role="presentation" class="${actionName == 'preferences' ? "active" : ""}">--}%
    %{--<g:link controller="account" action="preferences" class="lateral-menu">--}%
    %{--<i class="glyphicon glyphicon-cog"></i>--}%
    %{--&nbsp;Préferences--}%
    %{--</g:link>--}%
    %{--</li>--}%
</ul>