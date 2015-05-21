package trackr

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
        frameService.doCreateFrame(params, FrameProtocol.V1)
        render text: "OK"
    }

    /**
     * // http://pfabier.no-ip.org/trackr/sigfoxws/v2?id=89C3&device=89C3&time=1427469936&duplicate=false&signal=22.13&station=0402&data=02951a600013efca29f8c23a&avgSignal=24.83&lat=44&lng=2&rssi=-114.50
     * @return
     */
    def v2() {
        frameService.doCreateFrame(params, FrameProtocol.V2)
        render text: "OK"
    }
}
