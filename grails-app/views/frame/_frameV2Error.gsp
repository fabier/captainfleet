<g:if test="${frameExtra.errorType != null}">
    <tr>
        <td align="right" class="col-md-3">Type de trame d'erreur</td>
        <td class="col-md-6">${frameExtra.errorType}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaErrorType()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.reason != null}">
    <tr>
        <td align="right" class="col-md-3">Raison</td>
        <td class="col-md-6">${frameExtra.reason}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaReason()}</code>
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

<g:if test="${frameExtra.counter != null}">
    <tr>
        <td align="right" class="col-md-3">Compteur</td>
        <td class="col-md-6">${frameExtra.counter}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaCounter()}</code>
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
                <code>${frameExtra.hexaSolarArrayVoltage8bits()}</code>
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
                <code>${frameExtra.hexaSuperCapacitorVoltage8bits()}</code>
            </span>
        </td>
    </tr>
</g:if>
