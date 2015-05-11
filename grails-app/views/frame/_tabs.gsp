<ul class="nav nav-tabs margin-top-20">
    <g:each in="${frames}" var="f" status="i">
        <li role="presentation" class="${i == 0 ? "active" : ""}">
            <a href="#station-${f.stationId}" data-toggle="tab">Station ${f.station.sigfoxId}</a>
        </li>
    </g:each>
</ul>

<div class="tab-content">
    <g:each in="${frames}" var="f" status="i">
        <div class="tab-pane ${i == 0 ? "active" : ""}" id="station-${f.stationId}">
            <table class="table table-hover table-nolineseparator small nomargin">
                <tbody>
                <tr>
                    <td align="right" class="col-md-3">Signal</td>
                    <td colspan="2" class="col-md-9">${f.signal} dB</td>
                </tr>
                <tr>
                    <td align="right">RSSI</td>
                    <td colspan="2">${f.rssi} dB</td>
                </tr>
                <tr>
                    <td align="right">Timestamp</td>
                    <td colspan="2">
                        <g:formatDate date="${f.time}" format="yyyy-MM-dd HH:mm:ss.SSSZZ"/>
                        <br/>
                        <g:formatDate date="${f.dateCreated}"
                                      format="yyyy-MM-dd HH:mm:ss.SSSZZ"/> (in database)
                        <br/>
                        <g:formatDelay lowerDate="${f.time}" upperDate="${f.dateCreated}"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">Data</td>
                    <td colspan="2" class="col-md-9">
                        <span class="display-block">
                            <code>0x${f.data}</code>
                        </span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </g:each>

    <g:if test="${frameData}">
        <table class="table table-hover table-nolineseparator small nomargin">
            <tbody>
            <tr>
                <td align="right" class="col-md-3">Latitude</td>
                <td class="col-md-6"><g:formatNumber number="${frameData.latitude}" maxFractionDigits="6"/></td>
                <td class="col-md-3">
                    <span class="display-block">
                        <code>0x${frameData.hexaLatitude()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Longitude</td>
                <td><g:formatNumber number="${frameData.longitude}" maxFractionDigits="6"/></td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${frameData.hexaLongitude()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">GPS TimeToFix</td>
                <td><g:formatNumber number="${frameData.gpsTimeToFix}" maxFractionDigits="3"/> s</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${frameData.hexaGpsTimeToFix()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Tension panneau solaire</td>
                <td><g:formatNumber number="${frameData.solarArrayVoltage}" maxFractionDigits="3"/> V</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${frameData.hexaSolarArrayVoltage()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Tension condensateur</td>
                <td><g:formatNumber number="${frameData.superCapacitorVoltage}" maxFractionDigits="3"/> V</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${frameData.hexaSuperCapacitorVoltage()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Nb de protections capacité</td>
                <td>${frameData.superCapacitorProtectCount}</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${frameData.hexaSuperCapacitorProtectCount()}</code>
                    </span>
                </td>
            </tr>
            <g:set var="isDayBinary"
                   value="${formatBinary(value: frameData.isDay ? 1 : 0, padding: 1)}"/>
            <g:set var="frameCountBinary"
                   value="${formatBinary(value: frameData.frameCount, padding: 3)}"/>
            <tr>
                <td align="right">Jour</td>
                <td>${frameData.isDay ? "Oui" : "Non"}</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0b${isDayBinary}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Compteur de trames</td>
                <td>${frameData.frameCount}</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0b${frameCountBinary}</code>
                    </span>
                </td>
            </tr>
        </table>
    </g:if>
</div>