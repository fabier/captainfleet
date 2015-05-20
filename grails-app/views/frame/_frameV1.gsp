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