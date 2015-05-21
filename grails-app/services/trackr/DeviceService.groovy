package trackr

import grails.transaction.Transactional

@Transactional
class DeviceService {

    DecoderService decoderService
    CodeGeneratorService codeGeneratorService

    Frame lastFrame(Device device) {
        return Frame.createCriteria().get {
            eq("device", device)
            eq("duplicate", false)
            maxResults(1)
            uniqueResult()
            order("dateCreated", "desc")
        }
    }

    Frame lastFrameWithGeolocation(Device device) {
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

    Device randomDevice() {
        return Device.createCriteria().get {
            eq("deviceState", DeviceState.NORMAL)
            maxResults(1)
            uniqueResult()
            sqlRestriction "1=1 order by random()"
            // http://stackoverflow.com/questions/2810693/hibernate-criteria-api-get-n-random-rows
        }
    }

    boolean generateNewUniqueCodeForDevice(Device device) {
        generateNewUniqueCodeForDevice(device, 100)
    }

    boolean generateNewUniqueCodeForDevice(Device device, int maxTryCount) {
        device.code = codeGeneratorService.newCodeForDevice()
        // On limite à maxTryCount le nombre d'essais
        int tryCount = 0
        while (!device.validate() && tryCount < maxTryCount) {
            // Le code doit certainement être doublonné
            device.code = codeGeneratorService.newCodeForDevice()
            tryCount++
        }
    }
}
