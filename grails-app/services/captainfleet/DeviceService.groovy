package captainfleet

import grails.transaction.Transactional

@Transactional
class DeviceService {

    CodeGeneratorService codeGeneratorService

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

    List<Device> getDevicesByUser(User user) {
        UserDevice.findAllByUser(user)*.device?.asList()
    }
}
