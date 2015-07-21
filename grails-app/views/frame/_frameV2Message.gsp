<g:if test="${frameExtra.hdop != null}">
    <tr>
        <td align="right" class="col-md-3">Dilution horizontale</td>
        <td class="col-md-6">${frameExtra.hdop}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaHdop()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.satelliteCount != null}">
    <tr>
        <td align="right" class="col-md-3">Nombre de satellites</td>
        <td class="col-md-6">${frameExtra.satelliteCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaSatelliteCount()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.isDay != null}">
    <tr>
        <td align="right" class="col-md-3">Jour</td>
        <td class="col-md-6">${frameExtra.isDay ? "Oui" : "Non"}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaIsDay()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.frameCount != null}">
    <tr>
        <td align="right" class="col-md-3">Compteur de trames</td>
        <td class="col-md-6">${frameExtra.frameCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaFrameCount()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.gpsTimeToFix != null}">
    <tr>
        <td align="right" class="col-md-3">GPS TimeToFix</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.gpsTimeToFix}" maxFractionDigits="3"/> s</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaGpsTimeToFix()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.speed != null}">
    <tr>
        <td align="right" class="col-md-3">Vitesse</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.speed}" maxFractionDigits="3"/> km/h</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaSpeed()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.azimuth != null}">
    <tr>
        <td align="right" class="col-md-3">Azimuth</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.azimuth}" maxFractionDigits="3"/> °</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaAzimuth()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.solarArrayVoltage != null}">
    <tr>
        <td align="right" class="col-md-3">Tension panneau solaire</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.solarArrayVoltage}" maxFractionDigits="3"/> V</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaSolarArrayVoltage()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.superCapacitorVoltage != null}">
    <tr>
        <td align="right" class="col-md-3">Tension condensateur</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.superCapacitorVoltage}" maxFractionDigits="3"/> V</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaSuperCapacitorVoltage()}</code>
            </span>
        </td>
    </tr>
</g:if>
