<%@ page import="captainfleet.FrameProtocol" %>
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
            <table class="table table-hover table-nolineseparator small nomargin-left-right">
                <tbody>
                <tr>
                    <td align="right" class="col-md-3">Signal</td>
                    <td colspan="2" class="col-md-9">${f.snr} dB</td>
                </tr>
                <tr>
                    <td align="right">RSSI</td>
                    <td colspan="2">${f.rssi} dB</td>
                </tr>
                <tr>
                    <td align="right">Timestamp</td>
                    <td colspan="2">
                        <g:formatDate date="${f.time}" format="dd MMMM HH'h'mm:ss.SSSZZ"/>
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

    <g:set var="frameExtra" value="${frame.frameExtra}"/>
    <g:if test="${frameExtra}">
        <table class="table table-hover table-nolineseparator small nomargin-left-right">
            <tbody>
            <g:if test="${frameExtra.latitude != null}">
                <tr>
                    <td align="right" class="col-md-3">Latitude</td>
                    <td class="col-md-6"><g:formatNumber number="${frameExtra.latitude}" maxFractionDigits="6"/></td>
                    <td class="col-md-3">
                        <span class="display-block">
                            <code>${frameExtra.hexaLatitude()}</code>
                        </span>
                    </td>
                </tr>
            </g:if>

            <g:if test="${frameExtra.longitude != null}">
                <tr>
                    <td align="right" class="col-md-3">Longitude</td>
                    <td class="col-md-6"><g:formatNumber number="${frameExtra.longitude}" maxFractionDigits="6"/></td>
                    <td class="col-md-3">
                        <span class="display-block">
                            <code>${frameExtra.hexaLongitude()}</code>
                        </span>
                    </td>
                </tr>
            </g:if>

            <g:if test="${frame.frameProtocol == FrameProtocol.V1}">
                <g:render template="/frame/frameV1" model="[frameExtra: frameExtra]"/>
            </g:if>
            <g:elseif test="${frame.frameProtocol == FrameProtocol.V2}">
                <g:render template="/frame/frameV2" model="[frameExtra: frameExtra]"/>
            </g:elseif>
        </table>
    </g:if>
</div>
