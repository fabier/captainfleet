<ul class="nav nav-pills nav-stacked">
    <li class="${controllerName == "zone" && actionName == 'show' ? "active" : ""}">
        <g:link controller="zone" action="show" id="${zone.id}">
            Carte
        </g:link>
    </li>
    <li class="${controllerName == "zone" && actionName in 'devices'\
     || controllerName == "deviceZone" && actionName == 'show' ? "active" : ""}">
        <g:link controller="zone" action="devices" id="${zone.id}">
            Boitiers
        </g:link>
    </li>
</ul>