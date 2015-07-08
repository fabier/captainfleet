package trackr

import org.springframework.security.access.annotation.Secured

@Secured("hasRole('ROLE_ADMIN')")
class DashboardController {

    FrameService frameService
    DecoderService decoderService

    def index() {
        Device pierreDevice = Device.get(12541)
        List<Frame> frames = frameService.getLastFrames(pierreDevice, 50)
        render view: "index", model: [frames: frames]
    }
}
