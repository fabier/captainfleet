<tr>
    <td align="right">GPS TimeToFix</td>
    <td><g:formatNumber number="${frameData.gpsTimeToFix}" maxFractionDigits="3"/> s</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaGpsTimeToFix()}</code>
        </span>
    </td>
</tr>
<tr>
    <td align="right">Tension panneau solaire</td>
    <td><g:formatNumber number="${frameData.solarArrayVoltage}" maxFractionDigits="3"/> V</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaSolarArrayVoltage()}</code>
        </span>
    </td>
</tr>
<tr>
    <td align="right">Tension condensateur</td>
    <td><g:formatNumber number="${frameData.superCapacitorVoltage}" maxFractionDigits="3"/> V</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaSuperCapacitorVoltage()}</code>
        </span>
    </td>
</tr>
<tr>
    <td align="right">Nb de protections capacité</td>
    <td>${frameData.superCapacitorProtectCount}</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaSuperCapacitorProtectCount()}</code>
        </span>
    </td>
</tr>
<tr>
    <td align="right">Jour</td>
    <td>${frameData.isDay ? "Oui" : "Non"}</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaIsDay()}</code>
        </span>
    </td>
</tr>
<tr>
    <td align="right">Compteur de trames</td>
    <td>${frameData.frameCount}</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaFrameCount()}</code>
        </span>
    </td>
</tr>