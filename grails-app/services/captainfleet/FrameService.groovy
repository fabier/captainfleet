package captainfleet

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
    ZoneService zoneService
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
                    eq("duplicate", true)
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
                eq("duplicate", true)
                notEqual("id", frame.id)
                order("dateCreated", "asc")
            }
        } else {
            return Collections.emptyList()
        }
    }

    Pair<Frame, Frame> getPreviousAndNextFrame(Frame frame) {
        Frame previousFrame = getPreviousFrame(frame)
        Frame nextFrame = getNextFrame(frame)
        return new Pair(previousFrame, nextFrame)
    }

    Frame getPreviousFrame(Frame frame) {
        return Frame.createCriteria().get {
            lt("id", frame.id)
            eq("device", frame.device)
            eq("duplicate", false)
            maxResults(1)
            uniqueResult()
            order("dateCreated", "desc")
        }
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

    Frame getNextFrame(Frame frame) {
        return Frame.createCriteria().get {
            gt("id", frame.id)
            eq("device", frame.device)
            eq("duplicate", false)
            maxResults(1)
            uniqueResult()
            order("dateCreated", "asc")
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

    Frame doCreateFrame(Map params, FrameProtocol frameProtocol) {
        Frame frame = createAndSaveFrameFromParams(frameProtocol, params)
        checkIfAnyZoneIsToRaiseForFrame(frame)
        return frame
    }

    def checkIfAnyZoneIsToRaiseForFrame(Frame frame) {
        // On ne crée des logs et des alertes que pour les frames qui ne sont pas duplicate,
        // sinon on aurait plusieurs fois les mêmes logs et plusieurs fois les mêmes alertes
        if (frame?.location && !frame.duplicate) {
            // Cette frame a une géolocalisation
            List<User> users = userService.getUsersByDevice(frame.device)
            Device device = frame.device

            // Pour chaque utilisateur du boitier
            users?.each {
                // Parcourir les zones et voir si une zone a changé d'état
                User user = it
                List<Zone> zones = zoneService.getZonesForUser(user)

                // Pour chaque zone, ajouter un log et lever une alerte par mail si le boitier est dans la zone
                zones.each {
                    Zone zone = it
                    boolean isRaisedNow = zoneService.isFrameWithinZoneGeometry(zone, frame)

                    DeviceZone deviceZone = DeviceZone.findOrSaveByDeviceAndZone(device, zone)

                    // On ajoute l'information au log des zones
                    new DeviceZoneLog(
                            device: device,
                            zone: zone,
                            isRaised: isRaisedNow,
                            frame: frame
                    ).save()

                    if (deviceZone.isRaised != isRaisedNow) {
                        // Changement d'état de la zone

                        // On loggue ce changement d'état
                        new DeviceZoneLogAggregate(
                                device: device,
                                zone: zone,
                                isRaised: isRaisedNow,
                                frame: frame
                        ).save()

                        if (isRaisedNow) {
                            // Début d'alerte, alors que le précedent état n'indique pas la présence du boitier dans la zone
                            try {
                                String message = "<html><head></head><body>" +
                                        "Début d'alerte [${zone.id} - ${zone.name}] pour le boitier [${device.sigfoxId} - ${device.name}]" +
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
                        } else if (deviceZone.isRaised) {
                            // Fin d'alerte, alors que le précedent état indiquait la présence du boitier dans la zone
                            try {
                                String message = "<html><head></head><body>" +
                                        "Fin d'alerte [${zone.id} - ${zone.name}] pour le boitier [${device.sigfoxId} - ${device.name}]" +
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
                        deviceZone.isRaised = isRaisedNow
                        deviceZone.save()
                    }

                    if (isRaisedNow) {
                        zone.isRaised = true
                    } else {
                        // On doit regarder les autres devices s'ils sont en état "zone levée"
                        DeviceZone anotherZoneIsRaised = DeviceZone.findAllByZone(zone)?.find {
                            zone.isRaised
                        }

                        // Si un seul autre device est en "état haut", alors la zone est en "état haut".
                        if (anotherZoneIsRaised != null) {
                            zone.isRaised = true
                        } else {
                            zone.isRaised = false
                        }
                    }

                    if (zone.isDirty("isRaised")) {
                        zone.save()
                    }
                }
            }
        }
    }

    Frame createAndSaveFrameFromParams(FrameProtocol frameProtocol, Map params) {
        SigFoxWSData sigFoxWSData = parserService.tryParseSigFoxWSData(params)

        Frame frame = new Frame(
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
                frameProtocol: frameProtocol,
                frameExtra: decoderService.tryDecode(frameProtocol, sigFoxWSData.data)
        )

        // Mise à jour de données dénormalisées
        updateFrameTypeIfUnavailable(frame)
        updateDeviceFamilyFromFrame(frame)
        updateFrameLocationIfLocationIsAvailableAndCorrect(frame)

        // Sauvegarde de la nouvelle trame
        frame.save()
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

    Frame getLastFrameWithGeolocationWithin24Hours(Device device) {
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        Date dateLowerBound = calendar.getTime()
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            gte("dateCreated", dateLowerBound)
            isNotNull("location")
            maxResults(1)
            uniqueResult()
            order("dateCreated", "desc")
        }
    }

    Frame getRandomFrame(Device device) {
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
            maxResults(1)
            uniqueResult()
            sqlRestriction "1=1 order by random()"
            // http://stackoverflow.com/questions/2810693/hibernate-criteria-api-get-n-random-rows
        }
    }

    Frame getRandomFrameWithGeolocation(Device device) {
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

    List<Frame> getRandomFrames(Device device, int count) {
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

    List<Frame> getFramesForZone(Zone zone) {
        getFramesInArea(zone.geometry)
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

    List<Frame> getLastFrames(Device device, int max) {
        return Frame.createCriteria().list {
            eq("device", device)
            eq("duplicate", false)
            maxResults(max)
            order("dateCreated", "desc")
        }
    }

    List<Frame> getLastFramesSinceLastDays(Device device, int numberOfDays) {
        assert numberOfDays > 0
        Calendar calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.add(Calendar.DAY_OF_YEAR, -numberOfDays)
        Date dateLowerBound = calendar.getTime()
        return Frame.createCriteria().list {
            eq("device", device)
            eq("duplicate", false)
            gte("dateCreated", dateLowerBound)
            order("dateCreated", "asc")
        }
    }

    def updateFrameTypeIfUnavailable(Frame frame) {
        if (frame != null && frame.frameType == null && frame.frameExtra?.frameType != null) {
            frame.frameType = frame.frameExtra?.frameType
        }
    }

    def updateDeviceFamilyFromFrame(Frame frame) {
        if (frame != null) {
            Device device = frame.device
            if (device != null && device.deviceFamily == null) {
                // La famille de ce device n'est pas renseignée
                if (frame.frameExtra) {
                    device.deviceFamily = DeviceFamily.TRACKER
                } else {
                    device.deviceFamily = DeviceFamily.UNKNOWN
                }
                device.save()
            }
        }
    }

    def updateFrameLocationIfLocationIsAvailableAndCorrect(Frame frame) {
        if (frame != null) {
            FrameExtra frameExtra = frame.frameExtra
            if (frameExtra?.hasGeolocationData()) {
                LatitudeLongitude latitudeLongitude = new LatitudeLongitude(latitude: frameExtra.latitude,
                        longitude: frameExtra.longitude)
                if (latitudeLongitude.validate()) {
                    // On met à jour la donnée géolocalisée
                    frame.location = new GeometryFactory().createPoint(new Coordinate(frameExtra.longitude, frameExtra.latitude))
                } else {
                    // La position géographique est incohérente, on ne l'enregistre pas
                    frame.location = null
                }
                frame.save()
            }
        }
    }
}