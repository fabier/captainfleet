package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import org.springframework.security.access.annotation.Secured

@Secured("permitAll")
class SigFoxWSController {

    static defaultAction = "v1"

    ParserService parserService
    DecoderService decoderService
    FrameService frameService

    /**
     * // http://pfabier.no-ip.org/trackr/sigfoxws/v1?id=89C3&device=89C3&time=1427469936&duplicate=false&signal=22.13&station=0402&data=02951a600013efca29f8c23a&avgSignal=24.83&lat=44&lng=2&rssi=-114.50
     * @return
     */
    def v1() {
        doCreateFrame(FrameProtocol.V1)
        render text: "OK"
    }

    /**
     * // http://pfabier.no-ip.org/trackr/sigfoxws/v2?id=89C3&device=89C3&time=1427469936&duplicate=false&signal=22.13&station=0402&data=02951a600013efca29f8c23a&avgSignal=24.83&lat=44&lng=2&rssi=-114.50
     * @return
     */
    def v2() {
        doCreateFrame(FrameProtocol.V2)
        render text: "OK"
    }

    def doCreateFrame(FrameProtocol frameProtocol) {
        Frame frame = createAndSaveFrameFromParams(frameProtocol)
        FrameData frameData = decoderService.tryDecode(frame)
        frameService.checkDeviceFamilyForFrame(frame, frameData)
        frameService.checkIfLocationIsAvailable(frame, frameData)
    }

    Frame createAndSaveFrameFromParams(FrameProtocol frameProtocol) {
        SigFoxWSData sigFoxWSData = parserService.tryParseSigFoxWSData(params)

        new Frame(
                device: sigFoxWSData.device,
                time: sigFoxWSData.time,
                epochTime: sigFoxWSData.epochTime,
                duplicate: sigFoxWSData.duplicate,
                signal: sigFoxWSData.signal,
                station: sigFoxWSData.station,
                data: sigFoxWSData.data,
                avgSignal: sigFoxWSData.avgSignal,
                position: sigFoxWSData.position,
                rssi: sigFoxWSData.rssi,
                frameProtocol: frameProtocol
        ).save()
    }
}
