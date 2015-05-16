<tr>
    <td align="right">Dilution horizontale</td>
    <td>${frameData.hdop}</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaHdop()}</code>
        </span>
    </td>
</tr>

<tr>
    <td align="right">Nombre de satellites</td>
    <td>${frameData.satelliteCount}</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaSatelliteCount()}</code>
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
    <td align="right">Vitesse</td>
    <td><g:formatNumber number="${frameData.speed}" maxFractionDigits="3"/> km/h</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaSpeed()}</code>
        </span>
    </td>
</tr>

<tr>
    <td align="right">Azimuth</td>
    <td><g:formatNumber number="${frameData.azimuth}" maxFractionDigits="3"/> °</td>
    <td colspan="4">
        <span class="display-block">
            <code>${frameData.hexaAzimuth()}</code>
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







