package trackr

import org.apache.commons.lang3.time.DateUtils
import org.geotools.geometry.GeometryBuilder
import org.geotools.referencing.crs.DefaultGeographicCRS
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class DeviceController {

    DecoderService decoderService
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
        def frames = Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
        List<DataDecoded> dataDecodedList = new ArrayList()
        GeometryBuilder geometryBuilder = new GeometryBuilder(DefaultGeographicCRS.WGS84);
        Set<org.opengis.geometry.primitive.Point> points = new HashSet<>()
        if (frames.isEmpty()) {
            points.add(geometryBuilder.createPoint(0.0, -80.0))
            points.add(geometryBuilder.createPoint(0.0, 80.0))
        } else {
            frames.each {
                DataDecoded dataDecoded = decoderService.tryDecode(it.data)
                if (dataDecoded) {
                    dataDecodedList.add(dataDecoded)
                    points.add(geometryBuilder.createPoint(dataDecoded.longitude, dataDecoded.latitude))
                }
            }
        }
        MapOptions mapOptions = mapService.buildUsingPoints(points)
        render view: "map", model: [
                device         : device,
                date           : date ?: new Date(),
                now            : new Date(),
                frames         : frames,
                dataDecodedList: dataDecodedList,
                mapOptions     : mapOptions
        ]
    }
}