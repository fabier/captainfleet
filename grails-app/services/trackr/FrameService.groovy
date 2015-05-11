package trackr

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
//        List<Frame> frames = getFramesForDevice(frame.device)
//        int index = frames.indexOf(frame)
        Frame previousFrame = getPreviousFrame(frame)
        Frame nextFrame = getNextFrame(frame)
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

    Frame getPreviousFrame(Frame frame) {
        Frame.createCriteria().get {
            lt("id", frame.id)
            eq("device", frame.device)
            eq("duplicate", false)
            order("dateCreated", "desc")
            maxResults(1)
        }
    }

    Frame getNextFrame(Frame frame) {
        Frame.createCriteria().get {
            gt("id", frame.id)
            eq("device", frame.device)
            eq("duplicate", false)
            order("dateCreated", "asc")
            maxResults(1)
        }
    }

    List<Frame> getFramesForDevice(Device device) {
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            order("dateCreated", "asc")
        }
    }

    List<Frame> getFramesForDevice(Device device, Date dateLowerBound, Date dateUpperBound) {
        Frame.withCriteria {
            eq("device", device)
            eq("duplicate", false)
            between("dateCreated", dateLowerBound, dateUpperBound)
            order("dateCreated", "asc")
        }
    }
}
