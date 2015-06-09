package trackr

/**
 * Created by fabier on 11/05/15.
 */
class SigFoxWSData {
    Device device
    Station station
    String data
    Date time
    Long epochTime
    Boolean duplicate
    Double snr
    Double avgSignal
    Point position
    Double rssi
}
