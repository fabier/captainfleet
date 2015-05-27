package trackr

import grails.plugin.springsecurity.SpringSecurityService
import org.apache.commons.lang3.time.DateUtils
import org.springframework.security.access.annotation.Secured

import java.util.regex.Pattern

@Secured("hasRole('ROLE_USER')")
class DeviceController {

    static defaultAction = "index"

    FrameService frameService
    ParserService parserService
    MapService mapService
    SpringSecurityService springSecurityService

    def beforeInterceptor = {
        if (actionName in ['index'] || Device.get(params.id)) {
        } else {
            response.sendError(404)
            return false
        }
    }

    def index() {
        User user = springSecurityService.currentUser
        def devices = UserDevice.findAllByUser(user)*.device
        if (params.name) {
            Pattern pattern = Pattern.compile(".*${params.name}.*", Pattern.CASE_INSENSITIVE)
            devices = devices.findAll {
                pattern.matcher(it.name ?: "").matches() || pattern.matcher(it.code ?: "").matches() || pattern.matcher(it.sigfoxId ?: "").matches()
            }
        }

        int offset = params.offset ?: 0
        int max = params.max ?: 10
        int totalCount = devices.size()
        int endIndex = Math.min(totalCount, offset + max)

        render view: "index", model: [
                devices   : devices.subList(offset, endIndex),
                totalCount: totalCount
        ]
    }

    def map(long id) {
        Device device = Device.get(id)
        Date date = parserService.tryParseDate(params.date)
        if (date == null) {
            Frame lastFrame = frameService.lastFrame(device)
            date = lastFrame?.dateCreated ?: new Date()
        }
        Date dateLowerBound = DateUtils.truncate(date, Calendar.DAY_OF_MONTH)
        Date dateUpperBound = DateUtils.addDays(dateLowerBound, 1)
        def frames = frameService.getFramesForDeviceWithGeolocation(device, dateLowerBound, dateUpperBound)
        List<Frame> framesWithGeolocation = new ArrayList()
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        frames?.each {
            if (it.location instanceof com.vividsolutions.jts.geom.Point) {
                points.add(it.location as com.vividsolutions.jts.geom.Point)
                framesWithGeolocation.add(it)
                ((com.vividsolutions.jts.geom.Point) it.location).getX()
            }
        }
        MapOptions mapOptions = mapService.buildFromPoints(points)

        date = date ?: new Date()

        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        def previousDay = calendar.getTime()
        calendar.add(Calendar.DAY_OF_YEAR, 2)
        def nextDay = calendar.getTime()

        render view: "map", model: [
                device               : device,
                date                 : date,
                previousDay          : previousDay,
                nextDay              : nextDay,
                now                  : new Date(),
                frames               : frames,
                framesWithGeolocation: framesWithGeolocation,
                mapOptions           : mapOptions
        ]
    }

    def edit(long id) {
        Device device = Device.get(id)
        render view: "edit", model: [
                device: device
        ]
    }

    def update(long id) {
        Device device = Device.get(id)
        bindData(device, params, [include: ["name"]])
        device.save()
        flash.message = "Enregistrement effectué"
        redirect action: "edit", id: id
    }

    def addDevice() {
        User user = springSecurityService.currentUser
        Device device = Device.findByCode(params.code)
        if (device) {
            UserDevice.create(user, device)
            flash.message = "Vous avez maintenant le boitier '${params.code}' accessible."
            redirect action: "index"
        } else {
            flash.error = "Impossible de trouver un boitier avec le code '${params.code}'.<br/>Veuillez vérifier votre saisie."
            redirect action: "index"
        }
    }
}
