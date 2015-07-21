import org.apache.commons.beanutils.BeanUtils
import org.apache.commons.beanutils.PropertyUtils
import captainfleet.*

class BootStrap {

    CodeGeneratorService codeGeneratorService
    DecoderService decoderService
    FrameService frameService

    def init = { servletContext ->
        def adminRole = Role.findByAuthority('ROLE_ADMIN') ?: new Role(authority: "ROLE_ADMIN").save(failOnError: true, flush: true)
        def userRole = Role.findByAuthority('ROLE_USER') ?: new Role(authority: "ROLE_USER").save(failOnError: true, flush: true)

        def adminUser = User.findByEmail('fabier@free.fr') ?: new User(username: 'Pierre FABIER', password: '668GDrW6a4hw3CuHt9buJ5Ps', email: "fabier@free.fr", enabled: true).save(failOnError: true, flush: true)
        if (!UserRole.findByUserAndRole(adminUser, adminRole)) {
            UserRole.create adminUser, adminRole, true
        }
        if (!UserRole.findByUserAndRole(adminUser, userRole)) {
            UserRole.create adminUser, userRole, true
        }

        Device.findAllByDeviceState(null).each {
            it.deviceState = DeviceState.NORMAL
            it.save()
        }

        Device.findAllByCode(null).each {
            it.code = codeGeneratorService.newCodeForDevice()
            it.save()
        }

        // Update
        Frame.findAllByEpochTime(null).each {
            Date time = it.time
            if (time) {
                it.epochTime = time.getTime()
                it.save()
            }
        }

        // Decode
        // decodeAllFrames()

        // BUGFIX pour le décodage des trames d'erreur
        bugfixErrorFrames()
    }

    /**
     * Decodes all frames and creates FrameExtra for each one.
     * @return
     */
    def decodeAllFrames() {
        log.info("Trying to decode all frames without FrameExtra in database...")
        List<Frame> allFrames = Frame.findAllByFrameExtraIsNull()
        int frameCount = allFrames.size()
        log.info("Found ${frameCount} frames to process...")
        int frameIndex = 0
        int collateSize = 100
        allFrames.collate(collateSize).each {
            List<Frame> frames = it
            int upperFrameIndex = Math.min(frameIndex + collateSize, frameCount)
            log.info("Storing frameExtra for frames [${frameIndex}-${upperFrameIndex}] out of ${frameCount} frames...")
            frames.each {
                Frame frame = it
                FrameExtra frameExtra = decoderService.tryDecode(frame.frameProtocol, frame.data)
                if (frameExtra != null) {
                    frame.frameExtra = frameExtra
                    frame.save()
                }
            }
            frameIndex = frameIndex + collateSize
        }
        log.info("Trying to decode all frames in database... DONE")
    }

    def bugfixErrorFrames() {
        log.info("Trying to fix all error frames : recalculating frameExtra...")
        List<Frame> allFrames = Frame.findAllByFrameType(FrameType.ERROR)
        int frameCount = allFrames.size()
        log.info("Found ${frameCount} frames to process...")
        int frameIndex = 0
        int collateSize = 10
        allFrames.collate(collateSize).each {
            List<Frame> frames = it
            int upperFrameIndex = Math.min(frameIndex + collateSize, frameCount)
            log.info("Updating frameExtra for frames [${frameIndex}-${upperFrameIndex}] out of ${frameCount} frames...")
            frames.each {
                Frame frame = it
                FrameExtra frameExtra = decoderService.tryDecode(frame.frameProtocol, frame.data)
                if (frameExtra != null) {
                    frame.frameExtra = frameExtra
                    frame.save()
                }
            }
            frameIndex = frameIndex + collateSize
        }
        log.info("Trying to fix all error frames : recalculating frameExtra... DONE")
    }

    def destroy = {
    }
}
