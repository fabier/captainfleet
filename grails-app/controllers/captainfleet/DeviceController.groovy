package captainfleet

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
    MapMarkerIconService mapMarkerIconService
    SpringSecurityService springSecurityService

    def beforeInterceptor = {
        if (actionName in ['index', 'addDevice'] || Device.get(params.id)) {
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
                devices             : devices.subList(offset, endIndex),
                totalCount          : totalCount,
                defaultMapMarkerIcon: mapMarkerIconService.getDefault()
        ]
    }

    def map(long id) {
        Device device = Device.get(id)
        Date date = parserService.tryParseDate(params.date)
        if (date == null) {
            Frame lastFrame = frameService.getLastFrame(device)
            date = lastFrame?.dateCreated ?: new Date()
        }
        Date dateUpperBound = DateUtils.ceiling(date, Calendar.DAY_OF_MONTH)
        Date dateLowerBound = DateUtils.addDays(dateUpperBound, -1)

        def frames = frameService.getFramesForDevice(device, dateLowerBound, dateUpperBound)
        Set<com.vividsolutions.jts.geom.Point> points = new HashSet<>()
        List<Frame> geoFrames = new ArrayList<>()
        List<Frame> serviceFrames = new ArrayList<>()
        List<Frame> errorFrames = new ArrayList<>()
        frames?.each {
            switch (it.frameType) {
                case FrameType.MESSAGE:
                    if (it.location instanceof com.vividsolutions.jts.geom.Point) {
                        points.add(it.location as com.vividsolutions.jts.geom.Point)
                        geoFrames.add(it)
                    }
                    break
                case FrameType.SERVICE:
                    serviceFrames.add(it)
                    break
                case FrameType.ERROR:
                    errorFrames.add(it)
                    break
                default:
                    break
            }
        }
        MapOptions mapOptions = mapService.buildFromDeviceAndPoints(device, points)

        Frame lastFrame = frameService.getLastFrameWithGeolocationWithin24Hours(device)

        date = date ?: new Date()

        Calendar calendar = Calendar.getInstance()
        calendar.setTime(date)
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        def previousDay = calendar.getTime()
        calendar.add(Calendar.DAY_OF_YEAR, 2)
        def nextDay = calendar.getTime()

        render view: "map", model: [
                device              : device,
                date                : date,
                previousDay         : previousDay,
                nextDay             : nextDay,
                now                 : new Date(),
                frame               : lastFrame,
                frames              : frames,
                geoFrames           : geoFrames,
                serviceFrames       : serviceFrames,
                errorFrames         : errorFrames,
                mapOptions          : mapOptions,
                defaultMapMarkerIcon: mapMarkerIconService.getDefault()
        ]
    }

    def edit(long id) {
        Device device = Device.get(id)
        def mapMarkerIcons = MapMarkerIcon.all
        render view: "edit", model: [
                device             : device,
                deviceMapMarkerIcon: device.mapMarkerIcon ?: mapMarkerIconService.getDefault(),
                mapMarkerIcons     : mapMarkerIcons,
                mapMarkerIconIds   : mapMarkerIcons*.id,
        ]
    }

    def remove(long id) {
        Device device = Device.get(id)
        User user = springSecurityService.currentUser
        UserDevice.remove(user, device)
        redirect action: "index"
    }

    def update(long id) {
        Device device = Device.get(id)
        bindData(device, params, [include: ["name"]])
        MapMarkerIcon mapMarkerIcon = MapMarkerIcon.get(params.mapMarkerIcon)
        if (mapMarkerIcon != null) {
            if (device.mapMarkerIcon == null && mapMarkerIconService.getDefault() == mapMarkerIcon) {
                // Pas de modification, on continue d'utiliser l'icone par défaut
            } else {
                device.mapMarkerIcon = mapMarkerIcon
            }
        }
        device.save()
        flash.message = "Enregistrement effectué"
        redirect action: "index"
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
