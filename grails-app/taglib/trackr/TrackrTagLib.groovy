package trackr

class TrackrTagLib {
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
}