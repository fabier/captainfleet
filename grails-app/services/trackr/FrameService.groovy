package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.geom.GeometryFactory
import grails.plugin.mail.MailService
import grails.transaction.Transactional
import grails.util.Pair
import org.hibernatespatial.criterion.SpatialRestrictions

@Transactional
class FrameService {

    UserService userService
    AlertService alertService
    DecoderService decoderService
    ParserService parserService
    MailService mailService

    def grailsApplication

    /**
     * Retourne tous les doublons d'une frame, sauf lui même.
     * Les doublons retournés ne sont pas forcément notés comme "duplicate"
     *
     * @param frame
     * @return
     */
    List<Frame> getDuplicates(Frame frame) {
        if (frame && frame.device && frame.epochTime) {
            return Frame.createCriteria().list {
                eq("device", frame.device)
                eq("epochTime", frame.epochTime)
                notEqual("id", frame.id)
                order("dateCreated", "asc")
            }
        } else {
            return Collections.emptyList()
        }
    }

    /**
     * Retourne tous les doublons d'une frame, y compris lui même.
     * Les doublons retournés ne sont pas forcément notés comme "duplicate"
     *
     * @param frame
     * @return
     */
    List<Frame> getAllDuplicates(Frame frame) {
        if (frame) {
            if (frame.device && frame.epochTime) {
                return Frame.createCriteria().list {
                    eq("device", frame.device)
                    eq("epochTime", frame.epochTime)
                    order("dateCreated", "asc")
                }
            } else {
                return Collections.singletonList(frame)
            }
        } else {
            return Collections.emptyList()
        }
    }

    /**
     * Retourne toutes les frames marquées comme "duplicate",
     * y compris la frame passée en paramètre si elle est elle même marquée comme duplicate
     *
     * @param frame
     * @return
     */
    List<Frame> getAllDuplicatesWithDuplicateFlagOn(Frame frame) {
        if (frame) {
            if (frame.device && frame.epochTime) {
                return Frame.createCriteria().list {
                    eq("device", frame.device)
                    eq("epochTime", frame.epochTime)
                    eq("duplicate", false)
                    order("dateCreated", "asc")
                }
            } else {
                return Collections.singletonList(frame)
            }
        } else {
            return Collections.emptyList()
        }
    }

    /**
     * Retourne toutes les frames marquées comme "duplicate", sauf la frame passée en paramètre.
     *
     * @param frame
     * @return
     */
    List<Frame> getDuplicatesWithDuplicateFlagOn(Frame frame) {
        if (frame && frame.device && frame.epochTime) {
            return Frame.createCriteria().list {
                eq("device", frame.device)
                eq("epochTime", frame.epochTime)
                eq("duplicate", false)
                notEqual("id", frame.id)
                order("dateCreated", "asc")
            }
        } else {
            return Collections.emptyList()
        }
    }

    Pair<Frame, Frame> getPreviousAndNextFrame(Frame frame) {
//        List<Frame> frames = getFramesForDeviceWithGeolocation(frame.device)
//        int index = frames.indexOf(frame)
        Frame previousFrame = getPreviousFrameWithGeolocation(frame)
        Frame nextFrame = getNextFrameWithGeolocation(frame)
//        if (index >= 0) {
//            if (index - 1 >= 0) {
//                previousFrame = frames.get(index - 1)
//            }
//            if (index + 1 < frames.size()) {
//                nextFrame = frames.get(index + 1)
//            }
//        }
        return new Pair(previousFrame, nextFrame)
    }

    Frame getPreviousFrameWithGeolocation(Frame frame) {
        return Frame.createCriteria().get {
            lt("id", frame.id)
            eq("device", frame.device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            maxResults(1)
            uniqueResult()
            order("dateCreated", "desc")
        }
    }

    Frame getNextFrameWithGeolocation(Frame frame) {
        return Frame.createCriteria().get {
            gt("id", frame.id)
            eq("device", frame.device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            maxResults(1)
            uniqueResult()
            order("dateCreated", "asc")
        }
    }

    List<Frame> getFramesForDeviceWithGeolocation(Device device) {
        getFramesForDeviceWithGeolocation(device, -1)
    }

    List<Frame> getFramesForDeviceWithGeolocation(Device device, int max) {
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            if (max >= 0) {
                maxResults(max)
            }
            order("dateCreated", "asc")
        }
    }

    List<Frame> getFramesForDeviceWithGeolocation(Device device, Date dateLowerBound, Date dateUpperBound) {
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
    }

    List<Frame> getFramesForStationWithGeolocation(Station station) {
        getFramesForStationWithGeolocation(station, null)
    }

    List<Frame> getFramesForStationWithGeolocation(Station station, Map params) {
        Frame.withCriteria {
            eq("station", station)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            if (params.max) {
                maxResults(params.max as int)
            }
            if (params.offset) {
                firstResult(params.offset as int)
            }
            if (params.sort) {
                order(params.sort, params.order ?: "desc")
            }
        }
    }

    int countFramesForStationWithGeolocation(Station station) {
        Frame.createCriteria().count {
            eq("station", station)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
        }
    }

    def updateFrameLocationIfLocationIsAvailableAndCorrect(Frame frame, FrameData frameData) {
        if (frameData?.hasGeolocationData()) {
            LatitudeLongitude latitudeLongitude = new LatitudeLongitude(latitude: frameData.latitude,
                    longitude: frameData.longitude)
            if (latitudeLongitude.validate()) {
                // On met à jour la donnée géolocalisée
                frame.location = new GeometryFactory().createPoint(new Coordinate(frameData.longitude, frameData.latitude))
            } else {
                // La position géographique est incohérente, on ne l'enregistre pas
                frame.location = null
            }
            frame.save()
        }
    }

    def updateDeviceFamilyFromFrameData(Device device, FrameData frameData) {
        if (device != null && device.deviceFamily == null) {
            // La famille de ce device n'est pas renseignée
            if (frameData) {
                device.deviceFamily = DeviceFamily.TRACKER
            } else {
                device.deviceFamily = DeviceFamily.UNKNOWN
            }
            device.save()
        }
    }

    def doCreateFrame(Map params, FrameProtocol frameProtocol) {
        Frame frame = createAndSaveFrameFromParams(frameProtocol, params)
        FrameData frameData = decoderService.tryDecode(frame)
        updateFrameTypeIfUnavailable(frame, frameData)
        updateDeviceFamilyFromFrameData(frame.device, frameData)
        updateFrameLocationIfLocationIsAvailableAndCorrect(frame, frameData)
        checkIfAnyAlertIsToRaiseForFrame(frame)
    }

    def checkIfAnyAlertIsToRaiseForFrame(Frame frame) {
        if (frame?.location && !frame.duplicate) {
            // Cette frame a une géolocalisation
            List<User> users = userService.getUsersByDevice(frame.device)
            Device device = frame.device

            // Pour chaque utilisateur du boitier
            users?.each {
                // Parcourir les alertes et voir si une alerte a changé d'état
                User user = it
                List<Alert> alerts = alertService.getAlertsForUser(user)

                // Pour chaque alerte, ajouter un log et lever l'alerte si le boitier est dans la zone
                alerts.each {
                    Alert alert = it
                    boolean isRaisedNow = alertService.isFrameWithinAlertGeometry(alert, frame)

                    DeviceAlert deviceAlert = DeviceAlert.findOrSaveByDeviceAndAlert(device, alert)

                    // On ajoute l'information au log des alertes
                    DeviceAlertLog deviceAlertLog = new DeviceAlertLog(
                            deviceAlert: deviceAlert,
                            isRaised: isRaisedNow
                    ).save()

                    if (deviceAlert.isRaised != isRaisedNow) {
                        // Changement d'état de l'alerte
                        if (isRaisedNow) {
                            // Début d'alerte, alors qu'on a un ancien état
                            // indiquant que l'alerte n'était pas précédemment levée
                            try {
                                String message = "<html><head></head><body>" +
                                        "Début d'alerte [${alert.id} - ${alert.name}] pour le boitier [${device.sigfoxId} - ${device.name}]" +
                                        "</body></html>"
                                mailService.sendMail {
                                    async true
                                    to "${user.username} <${user.email}>"
                                    subject "[CaptainFleet] Début d'alerte"
                                    html message
                                    from "CaptainFleet <${grailsApplication.config.grails.mail.username}>"
                                }
                            } catch (Exception e) {
                                log.error "Impossible d'envoyer le message de début d'alerte par mail", e
                            }
                        } else if (deviceAlert.isRaised) {
                            // Fin d'alerte : l'état précédent indique que l'alerte était précédemment levée
                            try {
                                String message = "<html><head></head><body>" +
                                        "Fin d'alerte [${alert.id} - ${alert.name}] pour le boitier [${device.sigfoxId} - ${device.name}]" +
                                        "</body></html>"
                                mailService.sendMail {
                                    async true
                                    to "${user.username} <${user.email}>"
                                    subject "[CaptainFleet] Fin d'alerte"
                                    html message
                                    from "CaptainFleet <${grailsApplication.config.grails.mail.username}>"
                                }
                            } catch (Exception e) {
                                log.error "Impossible d'envoyer le message de fin d'alerte par mail", e
                            }
                        }
                        deviceAlert.isRaised = isRaisedNow
                        deviceAlert.save()
                    }

                    if (isRaisedNow) {
                        it.isRaised = true
                    } else {
                        // On doit regarder les autres devices s'ils sont en état "alerte levée"
                        DeviceAlert anotherAlertIsRaised = DeviceAlert.findAllByAlert(it)?.find {
                            it.isRaised
                        }

                        // Si un seul autre device est en "état haut", alors l'alerte est en "état haut".
                        if (anotherAlertIsRaised != null) {
                            it.isRaised = true
                        } else {
                            it.isRaised = false
                        }
                    }

                    if (it.isDirty("isRaised")) {
                        it.save()
                    }
                }
            }
        }
    }

    Frame createAndSaveFrameFromParams(FrameProtocol frameProtocol, params) {
        SigFoxWSData sigFoxWSData = parserService.tryParseSigFoxWSData(params)

        new Frame(
                device: sigFoxWSData.device,
                time: sigFoxWSData.time,
                epochTime: sigFoxWSData.epochTime,
                duplicate: sigFoxWSData.duplicate,
                snr: sigFoxWSData.snr,
                station: sigFoxWSData.station,
                data: sigFoxWSData.data,
                avgSignal: sigFoxWSData.avgSignal,
                position: sigFoxWSData.position,
                rssi: sigFoxWSData.rssi,
                frameProtocol: frameProtocol
        ).save()
    }


    Frame getLastFrame(Device device) {
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
            maxResults(1)
            uniqueResult()
            order("dateCreated", "desc")
        }
    }

    Frame getLastFrameWithGeolocation(Device device) {
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            maxResults(1)
            uniqueResult()
            order("dateCreated", "desc")
        }
    }

    Frame randomFrame(Device device) {
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
            maxResults(1)
            uniqueResult()
            sqlRestriction "1=1 order by random()"
            // http://stackoverflow.com/questions/2810693/hibernate-criteria-api-get-n-random-rows
        }
    }

    Frame randomFrameWithGeolocation(Device device) {
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            maxResults(1)
            uniqueResult()
            sqlRestriction "1 = 1 order by random()"
            // lat !=0  et long != 0
            // http://stackoverflow.com/questions/2810693/hibernate-criteria-api-get-n-random-rows
        }
    }

    List<Frame> randomFrames(Device device, int count) {
        return Frame.createCriteria().list {
            eq("device", device)
            eq("duplicate", false)
            maxResults(count)
            sqlRestriction "1=1 order by random()"
            // http://stackoverflow.com/questions/2810693/hibernate-criteria-api-get-n-random-rows
        }
    }

    List<Frame> getBestFramesWithGeolocation(Station station) {
        getBestFramesWithGeolocation(station, 10)
    }

    List<Frame> getBestFramesWithGeolocation(Station station, int max) {
        getFramesWithGeolocation(station, [max: max, sort: "rssi", order: "desc"])
    }

    List<Frame> getFramesWithGeolocation(Station station, Map params) {
        int max = params.max ?: -1
        int offset = params.offset ?: 0
        Frame.withCriteria {
            eq("station", station)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            if (max >= 0) {
                maxResults(max)
            }
            if (offset > 0) {
                firstResult(offset)
            }
            if (params.sort) {
                order(params.sort, params.order ?: "desc")
            }
        }
    }

    List<Frame> getFramesNotInArea(Geometry geometry) {
        Frame.createCriteria().add(SpatialRestrictions.disjoint("location", geometry)).list()
    }

    List<Frame> getFramesInArea(Geometry geometry) {
        Frame.createCriteria().add(SpatialRestrictions.within("location", geometry)).list()
    }

    List<Frame> getFramesForAlert(Alert alert) {
        getFramesInArea(alert.geometry)
    }

    def getServiceFramesForDevice(Device device, Date dateLowerBound, Date dateUpperBound) {
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.SERVICE)
            isNull("location")
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
    }

    def getErrorFramesForDevice(Device device, Date dateLowerBound, Date dateUpperBound) {
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.ERROR)
            isNull("location")
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
    }

    def getFramesForDevice(Device device, Date dateLowerBound, Date dateUpperBound) {
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
    }

    def updateFrameTypeIfUnavailable(Frame frame, FrameData frameData) {
        if (frame != null && frame.frameType == null && frameData?.frameType != null) {
            frame.frameType = frameData.frameType
            frame.save()
        }
    }
}