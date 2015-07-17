package captainfleet.admin

import org.springframework.security.access.annotation.Secured
import captainfleet.DecoderService
import captainfleet.Frame
import captainfleet.FrameService

@Secured("hasRole('ROLE_ADMIN')")
class AdminFrameController {
    static defaultAction = "show"

    DecoderService decoderService
    FrameService frameService

    def show(long id) {
        Frame frame = Frame.get(id)

        def frames = new ArrayList<Frame>()
        frames.add(frame)
        def duplicates = frameService.getDuplicates(frame)
        if (duplicates) {
            frames.addAll(duplicates)
        }

        render view: "show", model: [
                frame    : frame,
                device   : frame.device,
                frames   : frames
        ]
    }
}
