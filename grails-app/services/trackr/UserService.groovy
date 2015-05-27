package trackr

import grails.transaction.Transactional

@Transactional
class UserService {

    List<User> getUsersByDevice(Device device) {
        UserDevice.findAllByDevice(device)*.user?.asList()
    }
}
