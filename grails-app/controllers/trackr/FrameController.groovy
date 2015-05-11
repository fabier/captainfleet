package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import grails.util.Pair
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
        FrameData_V1 frameData = decoderService.tryDecode(frame)
        MapOptions mapOptions
        if (frameData) {
            GeometryFactory geometryBuilder = new GeometryFactory();
            Set<com.vividsolutions.jts.geom.Point> points = [geometryBuilder.createPoint(new Coordinate(frameData.longitude, frameData.latitude))]
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

        def previousAndNextFrame = frameService.getPreviousAndNextFrame(frame)

        render view: "map", model: [
                frame        : frame,
                previousFrame: previousAndNextFrame.aValue,
                nextFrame    : previousAndNextFrame.bValue,
                device       : frame.device,
                frameData    : frameData,
                frames       : frames,
                mapOptions   : mapOptions
        ]
    }
}
