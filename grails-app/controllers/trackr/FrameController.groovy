package trackr

import org.geotools.geometry.GeometryBuilder
import org.geotools.referencing.crs.DefaultGeographicCRS
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
            GeometryBuilder geometryBuilder = new GeometryBuilder(DefaultGeographicCRS.WGS84);
            Set<org.opengis.geometry.primitive.Point> points = [geometryBuilder.createPoint(dataDecoded.longitude, dataDecoded.latitude)]
            long delay = frame.dateCreated.getTime() - frame.time.getTime()
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
