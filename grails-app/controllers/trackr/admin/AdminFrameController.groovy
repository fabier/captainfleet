package trackr.admin

import org.springframework.security.access.annotation.Secured
import trackr.FrameData_V1
import trackr.DecoderService
import trackr.Frame
import trackr.FrameService

@Secured("hasRole('ROLE_ADMIN')")
class AdminFrameController {
    static defaultAction = "show"

    DecoderService decoderService
    FrameService frameService

    def show(long id) {
        Frame frame = Frame.get(id)
        FrameData_V1 frameData = decoderService.tryDecode_V1(frame.data)

        def frames = new ArrayList<Frame>()
        frames.add(frame)
        def duplicates = frameService.getDuplicates(frame)
        if (duplicates) {
            frames.addAll(duplicates)
        }

        render view: "show", model: [
                frame      : frame,
                device     : frame.device,
                frameData: frameData,
                frames     : frames
        ]
    }
}
