<table class="margin-auto">
    <tr>
        <td>
            <div class="td-icon-ul">
                <div>
                    <img src="${raw(createLink(controller: "mapMarker", action: "index", id: (device.mapMarkerIcon ?: defaultMapMarkerIcon).id))}">
                    <br/>
                </div>

                <div>
                    <ul>
                        <li>
                            <span class="text-larger bolder">
                                ${device.name}
                            </span>
                            &nbsp;
                            <code>
                                ${device.sigfoxId}
                            </code>
                        </li>
                        <g:if test="${frame}">
                            <li>
                                <span class="text-xsmall text-muted">
                                    Dernière transmission
                                    <g:formatDate format="d MMM à HH'h'mm" date="${frame.time}"/>
                                </span>
                            </li>
                            <li class="text-xsmall text-muted">
                                Signal :
                                <g:formatRSSI rssi="${frame.rssi}"/>
                                <g:if test="${frame.frameExtra}">
                                    Panneau solaire :
                                    <g:formatSolarArrayVoltage
                                            solarArrayVoltage="${frame.frameExtra.solarArrayVoltage}"/>
                                    Capacité :
                                    <g:formatSuperCapacitorVoltage
                                            superCapacitorVoltage="${frame.frameExtra.superCapacitorVoltage}"/>
                                </g:if>
                            </li>
                        </g:if>
                        <g:else>
                            <li>
                                <span class="label label-warning">
                                    Aucune transmission depuis 24h
                                </span>
                            </li>
                        </g:else>
                    </ul>
                </div>
            </div>
        </td>
    </tr>
</table>