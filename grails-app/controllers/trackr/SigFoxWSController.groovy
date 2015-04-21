package trackr

import org.springframework.security.access.annotation.Secured

@Secured("permitAll")
class SigFoxWSController {

    static defaultAction = "insertFrame"

    ParserService parserService
    DecoderService decoderService

    def insertFrame() {
        // http://pfabier.no-ip.org/trackr/sigfoxws?id=89C3&device=89C3&time=1427469936&duplicate=false&signal=22.13&station=0402&data=02951a600013efca29f8c23a&avgSignal=24.83&lat=44&lng=2&rssi=-114.50
        Device device = null
        if (params.id == null) {
            // Pas de symbole device transmis !
            log.warn("No device transmitted to WebService ! (id == null)")
        } else {
            device = Device.findOrSaveBySigfoxId(params.id)
            device.deviceState = DeviceState.NORMAL
            device.save()
        }

        Station station = null
        if (params.station == null) {
            // Pas de symbole station transmis !
            log.warn("No station transmitted to WebService ! (station == null)")
        } else {
            station = Station.findOrSaveBySigfoxId(params.station)
        }

        String data = null
        if (params.data == null) {
            // Pas de symbole data transmis !
            log.warn("No data transmitted to WebService ! (data == null)")
        } else {
            data = params.data
        }

        Date time = null
        Long epochTime = null
        if (params.time == null) {
            // Pas de symbole time transmis !
            log.warn("No time transmitted to WebService !")
        } else {
            epochTime = parserService.tryParseLong(params.time)
            if (epochTime == null) {
                log.warn("Time transmitted to WebService is not parseable as a long ! (time == ${params.time})")
            } else {
                time = new Date(epochTime * 1000) // time est en secondes
            }
        }

        Boolean duplicate = false
        if (params.duplicate == null) {
            // Pas de symbole duplicate transmis !
            log.warn("No duplicate transmitted to WebService !")
        } else {
            duplicate = Boolean.parseBoolean(params.duplicate)
            if (duplicate == null) {
                log.warn("duplicate transmitted to WebService is not parseable as a boolean ! (duplicate == ${params.duplicate})")
            }
        }

        Float signal = null
        if (params.signal == null) {
            // Pas de symbole signal transmis !
            log.warn("No signal transmitted to WebService !")
        } else {
            signal = parserService.tryParseFloat(params.signal)
            if (signal == null) {
                log.warn("signal transmitted to WebService is not parseable as a float ! (signal == ${params.signal})")
            }
        }

        Float avgSignal = null
        if (params.avgSignal == null) {
            // Pas de symbole avgSignal transmis !
            log.warn("No avgSignal transmitted to WebService !")
        } else {
            avgSignal = parserService.tryParseFloat(params.avgSignal)
            if (avgSignal == null) {
                log.warn("avgSignal transmitted to WebService is not parseable as a float ! (avgSignal == ${params.avgSignal})")
            }
        }

        Point position = null
        if (params.lat == null || params.lng == null) {
            // Pas de symbole lat/lng transmis !
            log.warn("No lat/lng transmitted to WebService !")
        } else {
            Double latitude = parserService.tryParseDouble(params.lat)
            Double longitude = parserService.tryParseDouble(params.lng)
            if (latitude == null || longitude == null) {
                log.warn("latitude/longitude transmitted to WebService are both not parseable as double ! (lat == ${params.lat}, lng == ${params.lng})")
            } else {
                position = Point.findOrSaveByLatitudeAndLongitude(latitude, longitude)
            }
        }

        Float rssi = null
        if (params.rssi == null) {
            // Pas de symbole rssi transmis !
            log.warn("No rssi transmitted to WebService !")
        } else {
            rssi = parserService.tryParseFloat(params.rssi)
            if (rssi == null) {
                log.warn("rssi transmitted to WebService is not a float !")
            }
        }

        Frame frame = new Frame()

        frame.device = device
        frame.time = time
        frame.epochTime = epochTime
        frame.duplicate = duplicate
        frame.signal = signal
        frame.station = station
        frame.data = data
        frame.avgSignal = avgSignal
        frame.position = position
        frame.rssi = rssi

        frame.save()

        if (device.deviceFamily == null) {
            DataDecoded dataDecoded = decoderService.tryDecode(data)
            if (dataDecoded) {
                device.deviceFamily = DeviceFamily.TRACKER
            } else {
                device.deviceFamily = DeviceFamily.UNKNOWN
            }
        }

        render text: "OK"
    }
}
