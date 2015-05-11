package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import org.apache.commons.lang3.time.DateUtils
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class DeviceController {

    DecoderService decoderService
    FrameService frameService
    ParserService parserService
    MapService mapService

    def beforeInterceptor = {
        if (Device.get(params.id)) {
        } else {
            response.sendError(404)
            return false
        }
    }

    def map(long id) {
        Device device = Device.get(id)
        Date date = parserService.tryParseDate(params.date)
        if (date == null) {
            Frame lastFrame = Frame.createCriteria().get {
                eq("device", device)
                eq("duplicate", false)
                maxResults(1)
                uniqueResult()
                order("dateCreated", "desc")
            }
            date = lastFrame?.dateCreated ?: new Date()
        }
        Date dateLowerBound = DateUtils.truncate(date, Calendar.DAY_OF_MONTH)
        Date dateUpperBound = DateUtils.addDays(dateLowerBound, 1)
        def frames = frameService.getFramesForDevice(device, dateLowerBound, dateUpperBound)
        List<FrameData_V1> frameDataList = new ArrayList()
        GeometryFactory geometryFactory = new GeometryFactory()
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        if (frames.isEmpty()) {
            points.add(geometryFactory.createPoint(new Coordinate(0.0, -80.0)))
            points.add(geometryFactory.createPoint(new Coordinate(0.0, 80.0)))
        } else {
            frames.each {
                FrameData_V1 frameData = decoderService.tryDecode(it)
                if (frameData) {
                    frameDataList.add(frameData)
                    points.add(geometryFactory.createPoint(new Coordinate(frameData.longitude, frameData.latitude)))
                }
            }
        }
        MapOptions mapOptions = mapService.buildUsingPoints(points)

        date = date ?: new Date()

        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        def previousDay = calendar.getTime()
        calendar.add(Calendar.DAY_OF_YEAR, 2)
        def nextDay = calendar.getTime()

        render view: "map", model: [
                device       : device,
                date         : date,
                previousDay  : previousDay,
                nextDay      : nextDay,
                now          : new Date(),
                frames       : frames,
                frameDataList: frameDataList,
                mapOptions   : mapOptions
        ]
    }
}
