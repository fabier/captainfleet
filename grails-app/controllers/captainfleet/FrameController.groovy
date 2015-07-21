package captainfleet

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
        MapOptions mapOptions
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        if (frame.location instanceof com.vividsolutions.jts.geom.Point) {
            points.add(frame.location as com.vividsolutions.jts.geom.Point)
        }
        mapOptions = mapService.buildFromDeviceAndPoints(frame.device, points)

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
                frames       : frames,
                mapOptions   : mapOptions
        ]
    }
}
