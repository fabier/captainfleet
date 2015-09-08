package captainfleet

class CaptainFleetTagLib {
    static defaultEncodeAs = [taglib: 'html']
    //static encodeAsForTags = [tagName: [taglib:'html'], otherTagName: [taglib:'none']]

    def formatBinary = { attr, body ->
        assert attr.value != null
        def padding = attr.padding ?: 4
        out << String.format("%${padding}s", Integer.toBinaryString(attr.value)).replaceAll(' ', '0')
    }

    def formatDelay = { attr, body ->
        assert attr.lowerDate
        assert attr.upperDate

        long delay = attr.upperDate.getTime() - attr.lowerDate.getTime()
        Date delayDate = new Date(Math.abs(delay))
        out << (Math.signum(delay) > 0 ? "+" : "-")
        out << formatDate(date: delayDate, format: "mm:ss''''SSS", timeZone: "UTC")
    }

    def formatArea = { attr, body ->
        assert attr.number
        double area = attr.number

        String unit
        int fractionDigits
        if (area >= 100000.0) {
            area = area / 1000000.0
            unit = "km²"
            fractionDigits = 1
        } else {
            unit = "m²"
            fractionDigits = 0
        }
        out << formatNumber([
                number           : area,
                minFractionDigits: fractionDigits,
                maxFractionDigits: fractionDigits,
                locale           : Locale.ENGLISH
        ])
        out << " ${unit}"
    }

    def formatBooleanIcon = { attr, body ->
        if (attr.boolean == null) {
            out << raw("<i class=\"glyphicon glyphicon-ban-circle\"></i>")
        } else if (attr.boolean) {
            out << raw("<i class=\"glyphicon glyphicon-check\"></i>")
        } else {
            out << raw("<i class=\"glyphicon glyphicon-unchecked\"></i>")
        }
    }

    def formatBooleanYesNo = { attr, body ->

        if (attr.boolean == null) {
            out << raw("N/A")
        } else if (attr.boolean) {
            out << raw("Oui")
        } else {
            out << raw("Non")
        }
    }

    def formatArrayForGoogleChartRow = { attr, body ->
        assert attr.data != null
        Object[] data = attr.data
        StringBuilder dataAsGoogleChartRow = new StringBuilder()
        for (Object cellData : data) {
            if (dataAsGoogleChartRow.length() > 0) {
                dataAsGoogleChartRow.append(",")
            }
            dataAsGoogleChartRow.append(formatObjectForGoogleChartRow(data: cellData))
        }
        while (dataAsGoogleChartRow.length() > 0 && dataAsGoogleChartRow.lastIndexOf(',') == dataAsGoogleChartRow.length() - 1) {
            dataAsGoogleChartRow.deleteCharAt(dataAsGoogleChartRow.length() - 1)
        }
        out << dataAsGoogleChartRow.toString()
    }


    def formatObjectForGoogleChartRow = { attr, body ->
        Object o = attr.data
        if (o == null) {
            out << "null"
        } else if (o instanceof Number) {
            out << formatNumber([number: o, type: "number", locale: "EN", minFractionDigits: "2", maxFractionDigits: "2"])
        } else if (o instanceof Date) {
            out << "new Date(${formatDate([date: o, format: "yyyy, MM, dd, HH, mm, ss, SSS"])})"
        } else {
            throw new UnsupportedOperationException("TrackTagLib.formatObject cannot handle class : ${o.class}")
        }
    }

    def plural = { attr, body ->
        assert attr.val != null
        out << (attr.val > 1 ? "s" : "")
    }


    def formatRSSI = { attr, body ->
        if (attr.rssi) {
            float rssi = attr.rssi

            String valueAsText
//            String spanClass
            String color
            if (rssi > -80) {
                valueAsText = "excellent"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else if (rssi > -100) {
                valueAsText = "très bon"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else if (rssi > -120) {
                valueAsText = "correct"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else {
                valueAsText = "faible"
//                spanClass = "label label-warning"
                color = "f0ad4e"
            }

//            out << raw("<span class=\"${spanClass}\">${valueAsText}</span>")
            out << raw("<i class=\"glyphicon glyphicon-ok-sign\" style=\"color: #${color};\" title=\"${valueAsText}\"></i>")
        } else {
//            out << raw("<span class=\"label label-warning\">inconnu</span>")
            out << raw("<i class=\"glyphicon glyphicon-ok-sign\" style=\"color: #d0d0d0;\"></i>")
        }
    }

    def formatSolarArrayVoltage = { attr, body ->
        if (attr.solarArrayVoltage) {
            float solarArrayVoltage = attr.solarArrayVoltage

            String valueAsText
//            String spanClass
            String color
            if (solarArrayVoltage > 2.4) {
                valueAsText = "excellent"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else if (solarArrayVoltage > 2.0) {
                valueAsText = "très bon"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else if (solarArrayVoltage > 1.0) {
                valueAsText = "correct"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else {
                valueAsText = "faible"
//                spanClass = "label label-warning"
                color = "f0ad4e"
            }

//            out << raw("<span class=\"${spanClass}\">${valueAsText}</span>")
            out << raw("<i class=\"glyphicon glyphicon-ok-sign\" style=\"color: #${color};\" title=\"${valueAsText}\"></i>")
        } else {
//            out << raw("<span class=\"label label-warning\">inconnu</span>")
            out << raw("<i class=\"glyphicon glyphicon-ok-sign\" style=\"color: #d0d0d0;\"></i>")
        }
    }

    def formatSuperCapacitorVoltage = { attr, body ->
        if (attr.superCapacitorVoltage) {
            float superCapacitorVoltage = attr.superCapacitorVoltage

            String valueAsText
//            String spanClass
            String color
            if (superCapacitorVoltage > 2.5) {
                valueAsText = "excellent"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else if (superCapacitorVoltage > 2.2) {
                valueAsText = "très bon"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else if (superCapacitorVoltage > 2.0) {
                valueAsText = "correct"
//                spanClass = "label label-success"
                color = "5cb85c"
            } else {
                valueAsText = "faible"
//                spanClass = "label label-warning"
                color = "f0ad4e"
            }

//            out << raw("<span class=\"${spanClass}\">${valueAsText}</span>")
            out << raw("<i class=\"glyphicon glyphicon-ok-sign\" style=\"color: #${color};\" title=\"${valueAsText}\"></i>")
        } else {
//            out << raw("<span class=\"label label-warning\">inconnu</span>")
            out << raw("<i class=\"glyphicon glyphicon-ok-sign\" style=\"color: #d0d0d0;\"></i>")
        }
    }

    def getFrameErrorReason = { attr, body ->
        int reason = attr.reason
        String reasonCode = "captainfleet.frame.error.reason.${reason}"
        out << message(code: reasonCode, default: "${reason} : Error unknown", args: [reason])
    }

    def getFrameType = { attr, body ->
        String type = attr.type
        String typeCode = "captainfleet.frame.type.${type}"
        out << message(code: typeCode, default: "${typeCode}", args: [type])
    }

    def getSatelliteCount = { attr, body ->
        int satelliteCount = attr.satelliteCount
        String satelliteCountCode = "captainfleet.frame.satelliteCount.${satelliteCount}"
        out << message(code: satelliteCountCode, default: "${satelliteCount} : Unknown", args: [satelliteCount])
    }
}
