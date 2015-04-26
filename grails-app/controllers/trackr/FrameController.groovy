package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_USER')")
class FrameController {
    static defaultAction = "map"

    DecoderService decoderService
    FrameService frameService
    MapService mapService

    def beforeInterceptor = {
        if (Frame.get(params.id)) {
        } else {
            response.sendError(404)
            return false
        }
    }

    def map(long id) {
        Frame frame = Frame.get(id)
        DataDecoded dataDecoded = decoderService.tryDecode(frame.data)
        MapOptions mapOptions
        if (dataDecoded) {
            GeometryFactory geometryBuilder = new GeometryFactory();
            Set<com.vividsolutions.jts.geom.Point> points = [geometryBuilder.createPoint(new Coordinate(dataDecoded.longitude, dataDecoded.latitude))]
            mapOptions = mapService.buildUsingPoints(points)
        } else {
            mapOptions = mapService.defaultMapOptions()
        }

        def frames = new ArrayList<Frame>()
        frames.add(frame)
        def duplicates = frameService.getDuplicates(frame)
        if (duplicates) {
            frames.addAll(duplicates)
        }

        render view: "map", model: [
                frame      : frame,
                device     : frame.device,
                dataDecoded: dataDecoded,
                frames     : frames,
                mapOptions : mapOptions
        ]
    }
}
