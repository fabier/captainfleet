package trackr

import grails.transaction.Transactional

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
}
