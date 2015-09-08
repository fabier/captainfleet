<g:if test="${frameExtra.gpsTimeToFix != null}">
    <tr>
        <td align="right" class="col-md-3">GPS TimeToFix</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.gpsTimeToFix}" maxFractionDigits="3"locale="EN"/> s</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaGpsTimeToFix()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.solarArrayVoltage != null}">
    <tr>
        <td align="right" class="col-md-3">Tension panneau solaire</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.solarArrayVoltage}" maxFractionDigits="3"locale="EN"/> V</td>
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
        <td class="col-md-6"><g:formatNumber number="${frameExtra.superCapacitorVoltage}" maxFractionDigits="3"locale="EN"/> V</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaSuperCapacitorVoltage()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.superCapacitorProtectCount != null}">
    <tr>
        <td align="right" class="col-md-3">Nb de protections capacité</td>
        <td class="col-md-6">${frameExtra.superCapacitorProtectCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaSuperCapacitorProtectCount()}</code>
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