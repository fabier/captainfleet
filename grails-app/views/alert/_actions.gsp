<ul class="nav nav-pills nav-stacked">
    <li class="${controllerName == "alert" && actionName == 'show' ? "active" : ""}">
        <g:link controller="alert" action="show" id="${alert.id}">
            Carte
        </g:link>
    </li>
    <li class="${controllerName == "alert" && actionName == 'devices' ? "active" : ""}">
        <g:link controller="alert" action="devices" id="${alert.id}">
            Boitiers
        </g:link>
    </li>
</ul>