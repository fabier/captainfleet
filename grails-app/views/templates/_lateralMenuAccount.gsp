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
    <li role="presentation"
        class="${controllerName == "zone" && actionName in ["index", "create", "logs"] ? "active" : ""}">
        <g:link controller="zone" action="index" class="lateral-menu">
            <i class="glyphicon glyphicon-bell"></i>
            &nbsp;Zones
        </g:link>
    </li>
    <g:if test="${zone}">
        <li>
            <ul class="nav nav-pills nav-stacked">
                <li class="active">
                    <a>
                        <i class="glyphicon glyphicon-bell"></i>
                        &nbsp;${zone.name ?: "Zone ${zone.id}"}
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