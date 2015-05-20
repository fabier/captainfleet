<g:if test="${frameData.hdop != null}">
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

<g:if test="${frameData.satelliteCount != null}">
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

<g:if test="${frameData.isDay != null}">
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

<g:if test="${frameData.frameCount != null}">
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

<g:if test="${frameData.gpsTimeToFix != null}">
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

<g:if test="${frameData.speed != null}">
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

<g:if test="${frameData.azimuth != null}">
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

<g:if test="${frameData.solarArrayVoltage != null}">
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

<g:if test="${frameData.superCapacitorVoltage != null}">
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

<g:if test="${frameData.currentTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° actuelle</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.currentTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaCurrentTemperature()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.averageTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° moyenne</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.averageTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaAverageTemperature()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.minTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° min</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.minTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaMinTemperature()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.maxTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° max</td>
        <td class="col-md-6"><g:formatNumber number="${frameData.maxTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaMaxTemperature()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.superCapacitorProtectCount != null}">
    <tr>
        <td align="right" class="col-md-3">Nb de protections capacité</td>
        <td class="col-md-6">${frameData.superCapacitorProtectCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaSuperCapacitorProtectCount()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameData.modemKOCount != null}">
    <tr>
        <td align="right" class="col-md-3">Nombre de KO Modem</td>
        <td class="col-md-6">${frameData.modemKOCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameData.hexaModemKOCount()}</code>
            </span>
        </td>
    </tr>
</g:if>
