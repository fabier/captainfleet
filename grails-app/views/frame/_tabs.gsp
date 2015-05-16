<%@ page import="trackr.FrameProtocol" %>
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
                        <code>${frameData.hexaLatitude()}</code>
                    </span>
                </td>
            </tr>
            <tr>
                <td align="right">Longitude</td>
                <td><g:formatNumber number="${frameData.longitude}" maxFractionDigits="6"/></td>
                <td colspan="4">
                    <span class="display-block">
                        <code>${frameData.hexaLongitude()}</code>
                    </span>
                </td>
            </tr>

            <g:if test="${frame.frameProtocol == FrameProtocol.V1}">
                <g:render template="frameV1" model="[frameData: frameData]"/>
            </g:if>
            <g:elseif test="${frame.frameProtocol == FrameProtocol.V2}">
                <g:render template="frameV2" model="[frameData: frameData]"/>
            </g:elseif>
        </table>
    </g:if>
</div>