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
        FrameData frameData = decoderService.tryDecode(frame)
        MapOptions mapOptions
        Set<com.vividsolutions.jts.geom.Point> points
        if (frameData) {
            if (frameData.hasGeolocationData()) {
                GeometryFactory geometryBuilder = new GeometryFactory();
                points = [geometryBuilder.createPoint(new Coordinate(frameData.longitude, frameData.latitude))]
            }
        }
        mapOptions = mapService.buildUsingPoints(points)

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
