package trackr

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import grails.transaction.Transactional
import grails.util.Pair

@Transactional
class FrameService {

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
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
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

    def checkIfLocationIsAvailable(Frame frame, FrameData frameData) {
        if (frameData?.hasGeolocationData()) {
            // On met à jour la donnée géolocalisée
            frame.location = new GeometryFactory().createPoint(new Coordinate(frameData.longitude, frameData.latitude))
        }
    }

    def checkDeviceFamilyForFrame(Frame frame, FrameData frameData) {
        Device device = frame.device
        if (device != null && device.deviceFamily == null) {
            // La famille de ce device n'est pas renseignée
            if (frameData) {
                device.deviceFamily = DeviceFamily.TRACKER
            } else {
                device.deviceFamily = DeviceFamily.UNKNOWN
            }
        }
    }
}
