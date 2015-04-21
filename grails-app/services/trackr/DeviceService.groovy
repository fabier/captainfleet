package trackr

import grails.transaction.Transactional

@Transactional
class DeviceService {

    Frame lastFrame(Device device) {
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
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

    Device randomDevice() {
        return Device.createCriteria().get {
            eq("deviceState", DeviceState.NORMAL)
            maxResults(1)
            uniqueResult()
            sqlRestriction "1=1 order by random()"
            // http://stackoverflow.com/questions/2810693/hibernate-criteria-api-get-n-random-rows
        }
    }
}
