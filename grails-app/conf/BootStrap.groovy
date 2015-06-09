import trackr.*

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

        Frame.findAllByEpochTime(null).each {
            Date time = it.time
            if (time) {
                it.epochTime = time.getTime()
                it.save()
            }
        }

        Frame.findAllByFrameType(null).each {
            def frameData = decoderService.tryDecode(it)
            frameService.updateFrameTypeIfUnavailable(it, frameData)
        }
    }

    def destroy = {
    }
}
