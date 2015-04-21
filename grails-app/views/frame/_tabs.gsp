<ul class="nav nav-tabs">
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
                    <td align="right" class="col-sm-3">Signal</td>
                    <td colspan="2" class="col-sm-9">${f.signal} dB</td>
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
                    <td colspan="2" class="col-sm-9">
                        <span class="display-block">
                            <code>0x${f.data.toUpperCase()}</code>
                        </span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </g:each>

    <g:if test="${dataDecoded}">
        <table class="table table-hover table-nolineseparator small nomargin">
            <tbody>
            <tr>
                <td align="right" class="col-sm-3">Latitude</td>
                <td class="col-sm-6">${dataDecoded.latitude}</td>
                <td class="col-sm-3">
                    <span class="display-block">
                        <code>0x${dataDecoded.hexaLatitude().toUpperCase()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Longitude</td>
                <td>${dataDecoded.longitude}</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${dataDecoded.hexaLongitude().toUpperCase()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">GPS TimeToFix</td>
                <td>${dataDecoded.gpsTimeToFix} s</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${dataDecoded.hexaGpsTimeToFix().toUpperCase()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Tension panneau solaire</td>
                <td>${dataDecoded.solarArrayVoltage} V</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${dataDecoded.hexaSolarArrayVoltage().toUpperCase()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Tension condensateur</td>
                <td>${dataDecoded.superCapacitorVoltage} V</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${dataDecoded.hexaSuperCapacitorVoltage().toUpperCase()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Nb de protections capacité</td>
                <td>${dataDecoded.superCapacitorProtectCount}</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0x${dataDecoded.hexaSuperCapacitorProtectCount().toUpperCase()}</code>
                    </span>
                </td>
            </tr>
            <g:set var="isDayBinary"
                   value="${formatBinary(value: dataDecoded.isDayAndFrameCount >> 3 & 0b0001, padding: 1)}"/>
            <g:set var="frameCountBinary"
                   value="${formatBinary(value: dataDecoded.isDayAndFrameCount & 0b0111, padding: 3)}"/>
            <tr>
                <td align="right">Jour</td>
                <td>${dataDecoded.isDay ? "Oui" : "Non"}</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0b${isDayBinary}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Compteur de trames</td>
                <td>${dataDecoded.frameCount}</td>
                <td colspan="4">
                    <span class="display-block">
                        <code>0b${frameCountBinary}</code>
                    </span>
                </td>
            </tr>
        </table>
    </g:if>
</div>