package captainfleet

import grails.transaction.Transactional
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsParameterMap

import java.text.ParseException
import java.text.SimpleDateFormat

@Transactional
class ParserService {

    DeviceService deviceService

    Double tryParseDouble(String s) {
        Double val = null
        if (s) {
            try {
                val = Double.parseDouble(s)
            } catch (NumberFormatException e) {
                // Quiet fail
            }
        }
        return val
    }

    Float tryParseFloat(String s) {
        Float val = null
        if (s) {
            try {
                val = Float.parseFloat(s)
            } catch (NumberFormatException e) {
                // Quiet fail
            }
        }
        return val
    }

    Boolean tryParseBoolean(String s) {
        return Boolean.parseBoolean(s)
    }

    Long tryParseLong(String s) {
        Long val = null
        if (s) {
            try {
                val = Long.parseLong(s)
            } catch (NumberFormatException e) {
                // Quiet fail
            }
        }
        return val
    }

    Date tryParseDate(String s) {
        Date date = null
        if (s) {
            try {
                date = new SimpleDateFormat("yyyy/MM/dd").parse(s)
            } catch (ParseException pe) {
                // Silent
            }
        }
        return date
    }

    /**
     * http://pfabier.no-ip.org/captainfleet/sigfoxws?id=89C3&device=89C3&time=1427469936&duplicate=false&snr=22.13&station=0402&data=02951a600013efca29f8c23a&avgSignal=24.83&lat=44&lng=2&rssi=-114.50
     * @param params
     * @return
     */
    SigFoxWSData tryParseSigFoxWSData(GrailsParameterMap params) {
        Device device = null
        if (params.id == null) {
            // Pas de symbole device transmis !
            log.warn("No device transmitted to WebService ! (id == null)")
        } else {
            device = Device.findOrSaveBySigfoxId(params.id)
            if (device.code == null) {
                if (deviceService.generateNewUniqueCodeForDevice(device)) {
                    device.save()
                } else {
                    log.warn("Impossible to generate a new unique code for device [${device.id}] : ${device.sigfoxId}")
                }
            }
            device.deviceState = DeviceState.NORMAL
            if (device.isDirty()) {
                device.save()
            }
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
            epochTime = tryParseLong(params.time)
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

        Float snr = null
        if (params.snr == null) {
            // Pas de symbole snr transmis !
            log.warn("No snr transmitted to WebService !")
        } else {
            snr = tryParseFloat(params.snr)
            if (snr == null) {
                log.warn("snr transmitted to WebService is not parseable as a float ! (snr == ${params.snr})")
            }
        }

        Float avgSignal = null
        if (params.avgSignal == null) {
            // Pas de symbole avgSignal transmis !
            log.warn("No avgSignal transmitted to WebService !")
        } else {
            avgSignal = tryParseFloat(params.avgSignal)
            if (avgSignal == null) {
                log.warn("avgSignal transmitted to WebService is not parseable as a float ! (avgSignal == ${params.avgSignal})")
            }
        }

        Point position = null
        if (params.lat == null || params.lng == null) {
            // Pas de symbole lat/lng transmis !
            log.warn("No lat/lng transmitted to WebService !")
        } else {
            Double latitude = tryParseDouble(params.lat)
            Double longitude = tryParseDouble(params.lng)
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
            rssi = tryParseFloat(params.rssi)
            if (rssi == null) {
                log.warn("rssi transmitted to WebService is not a float !")
            }
        }

        return new SigFoxWSData(
                device: device,
                time: time,
                epochTime: epochTime,
                duplicate: duplicate,
                snr: snr,
                station: station,
                data: data,
                avgSignal: avgSignal,
                position: position,
                rssi: rssi
        )
    }
}
