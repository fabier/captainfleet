package trackr

import grails.transaction.Transactional

@Transactional
class StationService {

    List<Frame> getFrames(Station station) {
        Frame.withCriteria {
            eq("station", station)
            order("dateCreated", "desc")
        }
    }

    List<Frame> getFrames(Station station, Map params) {
        Frame.withCriteria {
            eq("station", station)
            if (params.max) {
                maxResults(params.max)
            }
            if (params.offset) {
                firstResult(params.offset)
            }
            if (params.sort) {
                order(params.sort, params.order ?: "desc")
            }
        }
    }

    int countFrames(Station station) {
        Frame.countByStationAndDuplicate(station, false)
    }

    List<Frame> getFramesWithGeolocation(Station station) {
        Frame.withCriteria {
            eq("station", station)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            order("dateCreated", "desc")
        }
    }

    List<Frame> getFramesWithGeolocation(Station station, Map params) {
        Frame.withCriteria {
            eq("station", station)
            eq("frameType", FrameType.MESSAGE)
            isNotNull("location")
            if (params.max) {
                maxResults(params.max)
            }
            if (params.offset) {
                firstResult(params.offset)
            }
            if (params.sort) {
                order(params.sort, params.order ?: "desc")
            }
        }
    }
}
