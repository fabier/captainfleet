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

<g:if test="${frameExtra.currentTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° actuelle</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.currentTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaCurrentTemperature()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.averageTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° moyenne</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.averageTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaAverageTemperature()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.minTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° min</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.minTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaMinTemperature()}</code>
            </span>
        </td>
    </tr>
</g:if>

<g:if test="${frameExtra.maxTemperature != null}">
    <tr>
        <td align="right" class="col-md-3">T° max</td>
        <td class="col-md-6"><g:formatNumber number="${frameExtra.maxTemperature}" maxFractionDigits="1"/> °C</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaMaxTemperature()}</code>
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

<g:if test="${frameExtra.modemKOCount != null}">
    <tr>
        <td align="right" class="col-md-3">Nombre de KO Modem</td>
        <td class="col-md-6">${frameExtra.modemKOCount}</td>
        <td class="col-md-3">
            <span class="display-block">
                <code>${frameExtra.hexaModemKOCount()}</code>
            </span>
        </td>
    </tr>
</g:if>