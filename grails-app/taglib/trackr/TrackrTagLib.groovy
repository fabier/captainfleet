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
}
