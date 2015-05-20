<g:if test="${frameData.hdop}">
    <tr>
        <td align="right" class="col-md-3">Dilution horizontale</td>
        <td class="col-md-6">${frameData.hdop}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaHdop()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.satelliteCount}">
    <tr>
        <td align="right" class="col-md-3">Nombre de satellites</td>
        <td class="col-md-6">${frameData.satelliteCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaSatelliteCount()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.isDay}">
    <tr>
        <td align="right" class="col-md-3">Jour</td>
        <td class="col-md-6">${frameData.isDay ? "Oui" : "Non"}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaIsDay()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.frameCount}">
    <tr>
        <td align="right" class="col-md-3">Compteur de trames</td>
        <td class="col-md-6">${frameData.frameCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaFrameCount()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.gpsTimeToFix}">
    <tr>
        <td align="right" class="col-md-3">GPS TimeToFix</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.gpsTimeToFix}" maxFractionDigits="3"/> s</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaGpsTimeToFix()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.speed}">
    <tr>
        <td align="right" class="col-md-3">Vitesse</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.speed}" maxFractionDigits="3"/> km/h</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaSpeed()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.azimuth}">
    <tr>
        <td align="right" class="col-md-3">Azimuth</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.azimuth}" maxFractionDigits="3"/> °</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaAzimuth()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.solarArrayVoltage}">
    <tr>
        <td align="right" class="col-md-3">Tension panneau solaire</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.solarArrayVoltage}" maxFractionDigits="3"/> V</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaSolarArrayVoltage()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.superCapacitorVoltage}">
    <tr>
        <td align="right" class="col-md-3">Tension condensateur</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.superCapacitorVoltage}" maxFractionDigits="3"/> V</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaSuperCapacitorVoltage()}</code>
            </span>
        </td>
    </tr>
</g:if>







